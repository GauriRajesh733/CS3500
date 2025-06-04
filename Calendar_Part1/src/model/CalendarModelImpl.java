package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarModelImpl implements CalendarModel {
  private final Map<LocalDate, ArrayList<AEvent>> calendar;

  public CalendarModelImpl() {
    this.calendar = new HashMap<>();
  }

  @Override
  public void addEvent(AEvent event) {
    // check if event already exists in calendar
    if (this.findSingleEvent(event.getStartDate(), event.getEndDate(), event.getSubject()).isEmpty()) {
      event.addToCalendar(this.calendar);
    }
    else {
      throw new IllegalArgumentException("Given event with start date, end date, and subject already exists in calendar");
    }

  }

  @Override
  public void editSingleEvent(EventProperty propertyToEdit, LocalDateTime startDate, LocalDateTime endDate, String subject, String newProperty) {
    // find event or events if single multiday event
    ArrayList<AEvent> eventToEdit = this.findSingleEvent(startDate, endDate, subject);

    if (eventToEdit.isEmpty()) {
      throw new IllegalArgumentException("No events in calendar with given start date, end date, and subject");
    }

    for (AEvent event : eventToEdit) {
      // if not editing start date update event
      if (propertyToEdit != EventProperty.START) {
        event.editSingleEvent(propertyToEdit, newProperty);
      }
      // if editing start date
      else {
        // remove event from calendar
        this.removeEvent(event);
        // update event
        event.editSingleEvent(propertyToEdit, newProperty);
        // add event back to calendar
        event.addToCalendar(this.calendar);
      }
    }
  }

  /**
   * Remove the given event from this calendar and the date if there are no other events that day.
   *
   * @param eventToRemove represents the event to remove.
   */
  private void removeEvent(AEvent eventToRemove) {
    LocalDate dateToRemoveFrom = eventToRemove.getStartDate().toLocalDate();
    ArrayList<AEvent> dayToRemoveEvent = this.calendar.get(dateToRemoveFrom);
    dayToRemoveEvent.remove(eventToRemove);

    // remove calendar date key if no events
    if (dayToRemoveEvent.isEmpty()) {
      this.calendar.remove(dateToRemoveFrom);
    }
  }

  @Override
  public void editEvents(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty) {
    // find event or events if single multiday event
    ArrayList<AEvent> eventToEdit = this.findSeries(startDate, subject);

    if (eventToEdit.isEmpty()) {
      throw new IllegalArgumentException("No events in calendar with given start date, end date, and subject");
    }

    for (AEvent event : eventToEdit) {
      // if editing start date update event
      if (propertyToEdit != EventProperty.START) {
        event.editEvents(propertyToEdit, newProperty);
      }
      // if editing start date
      else {
        // remove event from calendar
        this.removeEvent(event);
        // update event
        event.editEvents(propertyToEdit, newProperty);
        // add event back to calendar
        event.addToCalendar(this.calendar);
      }
    }
  }

  // NOTE: pass to view here??
  @Override
  public List<AEvent> printEventsForDate(LocalDate date) {
    List<AEvent> events = new ArrayList<>();
    for (Map.Entry<LocalDate, ArrayList<AEvent>> entry : this.calendar.entrySet()) {
      if (entry.getKey().equals(date)) {
        events.addAll(entry.getValue());
      }
    }
    return events;
  }

  @Override
  public List<AEvent> printEventsUsingInterval(LocalDateTime start, LocalDateTime end) {
    List<AEvent> events = new ArrayList<>();
    for (Map.Entry<LocalDate, ArrayList<AEvent>> entry : this.calendar.entrySet()) {
      LocalDate eventStart = entry.getKey();
      if ((eventStart.isAfter(start.toLocalDate()) || eventStart.isEqual(start.toLocalDate())) &&
              (eventStart.isBefore(end.toLocalDate()) || eventStart.isEqual(end.toLocalDate()))) {
        events.addAll(entry.getValue());
      }
    }
    return events;
  }

  @Override
  public void editSeries(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty) {
    // find event or events if single multiday event
    ArrayList<AEvent> eventToEdit = this.findSeries(startDate, subject);

    if (eventToEdit.isEmpty()) {
      throw new IllegalArgumentException("No events in calendar with given start date, end date, and subject");
    }

    for (AEvent event : eventToEdit) {
      // if not editing start date update event
      if (propertyToEdit != EventProperty.START) {
        event.editSeriesEvent(propertyToEdit, newProperty);
      }
      // if editing start date
      else {
        // remove event from calendar
        this.removeEvent(event);
        // update event
        event.editSeriesEvent(propertyToEdit, newProperty);
        // add event back to calendar
        event.addToCalendar(this.calendar);
      }
    }
  }

  private ArrayList<AEvent> findSingleEvent(LocalDateTime startDate, LocalDateTime endDate, String subject) {
    ArrayList<AEvent> singleEvent = new ArrayList<AEvent>();

    ArrayList<AEvent> dayEvents = this.calendar.get(startDate.toLocalDate());

    for (AEvent event : dayEvents) {
      if (event.sameEvent(subject, startDate, endDate)) {
        singleEvent.add(event);
        // if single day event return event
        if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
          return singleEvent;
        }
      }
    }

    return singleEvent;
  }

  private ArrayList<AEvent> findSeries(LocalDateTime startDate, String subject) {
    ArrayList<AEvent> seriesEvent = new ArrayList<AEvent>();

    ArrayList<AEvent> dayEvents = this.calendar.get(startDate.toLocalDate());

    for (AEvent event : dayEvents) {
      if (event.sameSubjectAndStart(subject, startDate)) {
        seriesEvent.add(event);
        // if single day event or series event return event, if multiday single event get other days
        if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
          return seriesEvent;
        }
      }
    }

    return seriesEvent;
  }

}
