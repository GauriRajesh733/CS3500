package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;

class SeriesEvent extends AEvent {
  private SeriesEvent next;
  private SeriesEvent prev;

  public SeriesEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime,
                     String description, Location location, Status status) {
    super(subject, startDateTime, endDateTime, description, location, status);
  }

  public SeriesEvent(String subject, DayOfWeek[] daysOfWeek, int occurrences,
                     LocalDateTime startDateTime, LocalDateTime endDateTime) {
    super(subject, startDateTime, endDateTime, null, null, null);

    int eventCount = 0;
    this.prev = null;
    this.next = null;

    SeriesEvent currentEvent = this;
    SeriesEvent prevEvent = null;
    SeriesEvent nextEvent;

    while (eventCount < occurrences - 1) {
      DayOfWeek currentDayOfWeek = daysOfWeek[eventCount % daysOfWeek.length];
      LocalDateTime nextStartDate = getNextWeekDate(daysOfWeek, currentEvent, true);
      LocalDateTime nextEndDate = getNextWeekDate(daysOfWeek, currentEvent, false);
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
    super(subject, startDateTime, endDateTime, null, null, null);
    this.next = null;
    this.prev = null;
  }

  protected LocalDateTime getNextWeekDate(DayOfWeek[] daysOfWeek, AEvent currentEvent, boolean startDate) {
    LocalDateTime[] dates = new LocalDateTime[daysOfWeek.length];

    // get all possible next dates based on weekdays
    for (int i = 0; i < daysOfWeek.length; i++) {
      DayOfWeek dayOfWeek = daysOfWeek[i];
      LocalDateTime nextDate;
      if (startDate) {
        nextDate = nextDayOfWeekDate(dayOfWeek, currentEvent.getStartDate());
      } else {
        nextDate = nextDayOfWeekDate(dayOfWeek, currentEvent.getEndDate());
      }

      dates[i] = nextDate;
    }

    // get closest next date
    LocalDateTime closestDate = dates[0];

    for (LocalDateTime date : dates) {
      if (date.isBefore(closestDate)) {
        closestDate = date;
      }
    }

    return closestDate;
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
  public AEvent editSingleEvent(EventProperty propertyToEdit, String newProperty) throws
          IllegalArgumentException {
    switch (propertyToEdit) {
      // if not editing start or end date call super method
      case SUBJECT:
        return new SeriesEvent(newProperty, this.startDateTime, this.endDateTime, this.description
                , this.location, this.status);
      case DESCRIPTION:
        return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime, newProperty
                , this.location, this.status);
      case LOCATION:
        return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime, this.description
                , Location.fromInput(newProperty), this.status);
      case STATUS:
        return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime, this.description
                , this.location, Status.fromInput(newProperty));
      case END:
        return new SeriesEvent(this.subject, this.startDateTime, LocalDateTime.parse(newProperty));
      case START:
        return new SingleEvent(newProperty, LocalDateTime.parse(newProperty), this.endDateTime);
      default:
        throw new IllegalArgumentException("Unsupported property: " + propertyToEdit);
    }
  }

  @Override
  public SeriesEvent editSeriesEvent(EventProperty propertyToEdit, String newProperty) {
    switch (propertyToEdit) {
      // if not editing start or end date call super method
      case SUBJECT:
      case DESCRIPTION:
      case LOCATION:
      case STATUS:
      case END:
        super.editSingleEvent(propertyToEdit, newProperty);
        // update all following events
        if (this.hasNext()) {
          this.next.editNextSeriesEvents(propertyToEdit, newProperty);
        }
        // update all previous events
        if (this.hasPrev()) {
          this.prev.editPrevSeriesEvents(propertyToEdit, newProperty);
        }
        break;
      // if editing start date update previous and following event start dates accordingly
      case START:
        this.startDateTime = LocalDateTime.parse(newProperty);
        if (this.hasNext()) {
          long daysToNext = this.daysBetween(this.next);
          // calculate updated start date for following series event
          LocalDateTime nextStartDate = LocalDateTime.parse(newProperty).plusDays(daysToNext);
          this.next.editNextEventsStart(propertyToEdit, nextStartDate.toString());
        }
        // update all previous events
        if (this.hasPrev()) {
          long daysAfterPrev = this.prev.daysBetween(this);
          // calculate updated start date for following series event
          LocalDateTime nextStartDate = LocalDateTime.parse(newProperty).minusDays(daysAfterPrev);
          this.prev.editPrevEventsStart(propertyToEdit, nextStartDate.toString());
        }
    }
  }

  protected void editNextSeriesEvents(EventProperty propertyToEdit, String newProperty) {
    super.editSingleEvent(propertyToEdit, newProperty);

    // update all following events
    if (this.hasNext()) {
      this.next.editNextSeriesEvents(propertyToEdit, newProperty);
    }
  }

  protected void editPrevSeriesEvents(EventProperty propertyToEdit, String newProperty) {
    super.editSingleEvent(propertyToEdit, newProperty);

    // update all following events
    if (this.hasPrev()) {
      this.prev.editPrevSeriesEvents(propertyToEdit, newProperty);
    }
  }

  protected long daysBetween(SeriesEvent other) {
    return this.startDateTime.until(other.startDateTime, ChronoUnit.DAYS);
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
        break;
      // if editing start date update this event and following events in series; unlink first from previous
      case START:
        this.startDateTime = LocalDateTime.parse(newProperty);
        if (this.hasPrev()) {
          this.prev.setNext(null);
          this.setPrev(null);
        }
        if (this.hasNext()) {
          long daysToNext = this.daysBetween(this.next);
          // calculate updated start date for following series event
          LocalDateTime nextStartDate = LocalDateTime.parse(newProperty).plusDays(daysToNext);
          this.next.editEvents(propertyToEdit, nextStartDate.toString());
        }
        break;
    }
  }

  protected void relinkSeriesEvent() {
    this.prev.setNext(this);
    this.setPrev(this.prev);
    this.next.setPrev(this);
    this.setNext(this.next);
  }

  @Override
  public ArrayList<AEvent> getEvents() {
    return this.getSeriesEvents();
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

  protected void editNextEventsStart(EventProperty propertyToEdit, String newProperty) {
    // update start of this event
    if (propertyToEdit == EventProperty.START) {
      this.startDateTime = LocalDateTime.parse(newProperty);
    }

    // update start of following events
    if (this.hasNext()) {
      this.startDateTime = LocalDateTime.parse(newProperty);
      long daysToNext = this.daysBetween(this.next);

      // calculate updated start date for following series event
      LocalDateTime nextStartDate = LocalDateTime.parse(newProperty).plusDays(daysToNext);
      this.next.editNextEventsStart(propertyToEdit, nextStartDate.toString());
    }
  }

  protected void editPrevEventsStart(EventProperty propertyToEdit, String newProperty) {
    // update start of this event
    if (propertyToEdit == EventProperty.START) {
      this.startDateTime = LocalDateTime.parse(newProperty);
    }

    // update start of previous events
    if (this.hasPrev()) {
      this.startDateTime = LocalDateTime.parse(newProperty);
      long daysAfterPrev = this.prev.daysBetween(this);

      // calculate updated start date for following series event
      LocalDateTime nextStartDate = LocalDateTime.parse(newProperty).minusDays(daysAfterPrev);
      this.prev.editPrevEventsStart(propertyToEdit, nextStartDate.toString());
    }
  }

  protected ArrayList<AEvent> getSeriesEvents() {
    ArrayList<AEvent> eventsInSeries = new ArrayList<>();
    eventsInSeries.add(this);

    if (this.hasNext()) {
      eventsInSeries.add(this.next());
      eventsInSeries.addAll(this.next.getNextSeriesEvents());
    }
    if (this.hasPrev()) {
      eventsInSeries.add(this.prev());
      eventsInSeries.addAll(this.prev.getPrevSeriesEvents());
    }

    return eventsInSeries;
  }

  protected ArrayList<AEvent> getNextSeriesEvents() {
    ArrayList<AEvent> eventsInSeries = new ArrayList<>();

    if (this.hasNext()) {
      eventsInSeries.add(this.next());
      eventsInSeries.addAll(this.next.getNextSeriesEvents());
    }


    return eventsInSeries;
  }

  protected ArrayList<AEvent> getPrevSeriesEvents() {
    ArrayList<AEvent> eventsInSeries = new ArrayList<>();

    if (this.hasPrev()) {
      eventsInSeries.add(this.prev());
      eventsInSeries.addAll(this.prev.getPrevSeriesEvents());
    }


    return eventsInSeries;
  }

}