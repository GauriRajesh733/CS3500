package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class SeriesEvent extends AEvent {
  private SeriesEvent next;
  private SeriesEvent prev;


  public SeriesEvent(String subject, DayOfWeek[] daysOfWeek, int occurrences,
                     LocalDateTime startDateTime, LocalDateTime endDateTime) {
    super(subject, startDateTime, endDateTime);

    int eventCount = 0;
    this.prev = null;
    this.next = null;

    SeriesEvent currentEvent = this;
    SeriesEvent prevEvent = null;
    SeriesEvent nextEvent;

    while (eventCount < occurrences - 1) {
      DayOfWeek currentDayOfWeek = daysOfWeek[eventCount % daysOfWeek.length];
      LocalDateTime nextStartDate = nextDayOfWeekDate(currentDayOfWeek, currentEvent.getStartDate());
      LocalDateTime nextEndDate = nextDayOfWeekDate(currentDayOfWeek, currentEvent.getEndDate());
      nextEvent = new SeriesEvent(subject, nextStartDate, nextEndDate);

      currentEvent.setNext(nextEvent);
      nextEvent.setPrev(currentEvent);
      currentEvent.setPrev(prevEvent);
      if (prevEvent != null) {
        prevEvent.setNext(currentEvent);
      }

      prevEvent = currentEvent;
      currentEvent = nextEvent;

      eventCount++;
    }
  }

  public SeriesEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    super(subject, startDateTime, endDateTime);
    this.next = null;
    this.prev = null;
  }

  @Override
  public void addToCalendar(Map<LocalDate, ArrayList<AEvent>> calendar) {
    // add this single event to calendar
    if (!calendar.containsKey(this.startDateTime.toLocalDate())) {
      calendar.put(this.startDateTime.toLocalDate(), new ArrayList<AEvent>());
    }
    calendar.get(this.startDateTime.toLocalDate()).add(this);

    // add other events in this series to calendar
    if (this.hasNext()) {
      this.next.addToCalendar(calendar);
    }
  }

  @Override
  public void editSingleEvent(EventProperty propertyToEdit, String newProperty) {
    switch (propertyToEdit) {
      // if not editing start or end date call super method
      case SUBJECT:
      case DESCRIPTION:
      case LOCATION:
      case STATUS:
      case END:
        super.editSingleEvent(propertyToEdit, newProperty);
        // if editing start date update this event and unlink from series
      case START:
        this.startDateTime = LocalDateTime.parse(newProperty);
        this.prev.setNext(this.next);
        this.next.setPrev(this.prev);
    }
  }

  @Override
  public void editSeriesEvent(EventProperty propertyToEdit, String newProperty) {
    switch (propertyToEdit) {
      // if not editing start or end date call super method on this and following events in series
      case SUBJECT:
      case DESCRIPTION:
      case LOCATION:
      case STATUS:
      case END:
        super.editSingleEvent(propertyToEdit, newProperty);
        if (this.hasPrev()) {
          this.prev.editSeriesEvent(propertyToEdit, newProperty);
        }
        if (this.hasNext()) {
          this.next.editSeriesEvent(propertyToEdit, newProperty);
        }
        // if editing start date update this event and following events in series
      case START:
        this.startDateTime = LocalDateTime.parse(newProperty);
        if (this.hasPrev()) {
          this.prev.editSeriesEvent(propertyToEdit, newProperty);
        }
        if (this.hasNext()) {
          this.next.editSeriesEvent(propertyToEdit, newProperty);
        }
    }
  }

  @Override
  public void editEvents(EventProperty propertyToEdit, String newProperty) {
    switch (propertyToEdit) {
      // if not editing start or end date call super method on this and following events in series
      case SUBJECT:
      case DESCRIPTION:
      case LOCATION:
      case STATUS:
      case END:
        super.editSingleEvent(propertyToEdit, newProperty);
        if (this.hasNext()) {
          this.next.editEvents(propertyToEdit, newProperty);
        }
        // if editing start date update this event and following events in series; unlink first from previous
      case START:
        this.startDateTime = LocalDateTime.parse(newProperty);
        this.prev.setNext(null);
        if (this.hasNext()) {
          this.next.editFollowingEvents(propertyToEdit, newProperty);
        }
    }
  }

  protected void setPrev(SeriesEvent seriesEvent) {
    this.prev = seriesEvent;
  }

  protected void setNext(SeriesEvent seriesEvent) {
    this.next = seriesEvent;
  }

  protected boolean hasNext() {
    return this.next != null;
  }

  protected boolean hasPrev() {
    return this.prev != null;
  }

  protected SeriesEvent next() {
    if (this.hasNext()) {
      return this.next;
    }
    throw new IllegalArgumentException("There is no event after this");
  }

  protected SeriesEvent prev() {
    if (this.hasPrev()) {
      return this.prev;
    }
    throw new IllegalArgumentException("There is no event before this");
  }

  private LocalDateTime nextDayOfWeekDate(DayOfWeek dayOfWeek, LocalDateTime startDate) {
    LocalDateTime currentDate = startDate.plusDays(1);

    while (currentDate.getDayOfWeek() != dayOfWeek) {
      currentDate = currentDate.plusDays(1);
    }

    return currentDate;
  }

  protected void editFollowingEvents(EventProperty propertyToEdit, String newProperty) {
    // if editing start date update this event and following events in series
    if (propertyToEdit == EventProperty.START) {
      this.startDateTime = LocalDateTime.parse(newProperty);
      if (this.hasNext()) {
        this.next.editFollowingEvents(propertyToEdit, newProperty);
      }
    }
  }
}