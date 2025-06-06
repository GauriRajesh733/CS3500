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
  private final DayOfWeek[] days;

  protected SeriesEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime,
                        String description, Location location, Status status,
                        SeriesEvent prev, SeriesEvent next, DayOfWeek[] days) {
    super(subject, startDateTime, endDateTime, description, location, status);
    this.prev = prev;
    if (this.hasPrev()) {
      this.prev.setNext(this);
    }
    this.next = next;
    if (this.hasNext()) {
      this.next.setPrev(this);
    }
    this.days = days;
  }

  public SeriesEvent(String subject, DayOfWeek[] days, int occurrences,
                     LocalDateTime startDateTime, LocalDateTime endDateTime,
                     String description, Location location, Status status) {
    super(subject, startDateTime, endDateTime, description, location, status);
    this.days = days;

    int eventCount = 0;
    this.prev = null;
    this.next = null;

    SeriesEvent currentEvent = this;
    SeriesEvent prevEvent = null;
    SeriesEvent nextEvent;

    while (eventCount < occurrences - 1) {
      LocalDateTime nextStartDate = getNextWeekDate(days, currentEvent, true);
      LocalDateTime nextEndDate = getNextWeekDate(days, currentEvent, false);
      nextEvent = new SeriesEvent(days, subject, nextStartDate, nextEndDate);

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

  public SeriesEvent(DayOfWeek[] days, String subject, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    super(subject, startDateTime, endDateTime, null, null, null);
    this.next = null;
    this.prev = null;
    this.days = days;
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

  public void addSingleEventToCalendar(Map<LocalDate, ArrayList<AEvent>> calendar) {
    if (!calendar.containsKey(this.startDateTime.toLocalDate())) {
      calendar.put(this.startDateTime.toLocalDate(), new ArrayList<AEvent>());
    }
    calendar.get(this.startDateTime.toLocalDate()).add(this);
  }

  @Override
  public AEvent editSingleEvent(EventProperty propertyToEdit, String newProperty) throws
          IllegalArgumentException {
    switch (propertyToEdit) {
      // if not editing start date keep event in series
      case SUBJECT:
        return new SeriesEvent(newProperty, this.startDateTime, this.endDateTime, this.description
                , this.location, this.status, this.prev, this.next, this.days);
      case DESCRIPTION:
        return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime, newProperty
                , this.location, this.status, this.prev, this.next, this.days);
      case LOCATION:
        return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime, this.description
                , Location.fromInput(newProperty), this.status, this.prev, this.next, this.days);
      case STATUS:
        return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime, this.description
                , this.location, Status.fromInput(newProperty), this.prev, this.next, this.days);
      case END:
        LocalDateTime newEndDateTime = LocalDateTime.parse(newProperty);
        if (this.startDateTime.isAfter(newEndDateTime)) {
          throw new IllegalArgumentException("Start date time must be before end date time");
        }
        if (this.startDateTime.until(newEndDateTime, ChronoUnit.DAYS) >= 1) {
          throw new IllegalArgumentException("Start date and end date for event series must be same");
        }
        return new SeriesEvent(this.subject, this.startDateTime, newEndDateTime, this.description
                , this.location, this.status, this.prev, this.next, this.days);
      case START:
        LocalDateTime newStartDate = LocalDateTime.parse(newProperty);
        if (newStartDate.isAfter(this.endDateTime)) {
          throw new IllegalArgumentException("Start date time must be before end date time");
        }
        return new SingleEvent(this.subject, newStartDate, this.endDateTime, this.description,
                this.location, this.status);
      default:
        throw new IllegalArgumentException("Unsupported property: " + propertyToEdit);
    }
  }

  @Override
  public SeriesEvent editSeriesEvent(EventProperty propertyToEdit, String newProperty) {
    return this.firstEvent().editEvents(propertyToEdit, newProperty);
  }

  protected long daysBetween(SeriesEvent other) {
    return this.startDateTime.until(other.startDateTime, ChronoUnit.DAYS);
  }

  protected SeriesEvent firstEvent() {
    SeriesEvent curr = new SeriesEvent(this.subject, this.startDateTime, this.endDateTime,
            this.description, this.location, this.status, this.prev, this.next, this.days);
    while (curr.hasPrev()) {
      curr = curr.prev();
    }
    return curr;
  }

  @Override
  public SeriesEvent editEvents(EventProperty propertyToEdit, String newProperty) {
    SeriesEvent newSeries;
    switch (propertyToEdit) {
      case SUBJECT:
        if (this.hasNext()) {
          return new SeriesEvent(newProperty, this.startDateTime, this.endDateTime, this.description
                  , this.location, this.status, this.prev, this.next.editEvents(propertyToEdit, newProperty)
                  , this.days);
        } else {
          return new SeriesEvent(newProperty, this.startDateTime, this.endDateTime, this.description
                  , this.location, this.status, this.prev, this.next
                  , this.days);
        }

      case DESCRIPTION:
        if (this.hasNext()) {
          return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime, newProperty
                  , this.location, this.status, this.prev, this.next.editEvents(propertyToEdit, newProperty)
                  , this.days);
        } else {
          return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime, newProperty
                  , this.location, this.status, this.prev, this.next
                  , this.days);
        }

      case LOCATION:
        if (this.hasNext()) {
          return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime, this.description
                  , Location.fromInput(newProperty), this.status, this.prev, this.next.editEvents(propertyToEdit, newProperty)
                  , this.days);
        } else {
          return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime, this.description
                  , Location.fromInput(newProperty), this.status, this.prev, this.next, this.days);
        }

      case STATUS:
        if (this.hasNext()) {
          return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime, this.description
                  , this.location, Status.fromInput(newProperty), this.prev, this.next.editEvents(propertyToEdit, newProperty)
                  , this.days);
        } else {
          return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime, this.description
                  , this.location, Status.fromInput(newProperty), this.prev, this.next, this.days);
        }

      case END:
        LocalDateTime newEndDateTime = LocalDateTime.parse(newProperty);
        if (this.startDateTime.isAfter(newEndDateTime)) {
          throw new IllegalArgumentException("Start date time must be before end date time");
        }
        if (this.startDateTime.until(newEndDateTime, ChronoUnit.DAYS) >= 1) {
          throw new IllegalArgumentException("Start date and end date for event series must be same");
        }
        if (this.hasNext()) {
          return new SeriesEvent(this.subject, this.startDateTime, newEndDateTime, this.description
                  , this.location, this.status, this.prev, this.next.editEvents(propertyToEdit, newProperty), this.days);
        } else {
          return new SeriesEvent(this.subject, this.startDateTime, newEndDateTime, this.description
                  , this.location, this.status, this.prev, this.next, this.days);
        }
        // if editing start date update this event and following events in series; unlink first from previous
      case START:
        LocalDateTime newStartDate = LocalDateTime.parse(newProperty);
        if (newStartDate.isAfter(this.endDateTime)) {
          throw new IllegalArgumentException("Start date time must be before end date time");
        }
        int followingOccurrences = this.followingOccurrences();
        return new SeriesEvent(this.subject, this.days, followingOccurrences, newStartDate,
                this.endDateTime, this.description, this.location, this.status);
      default:
        throw new IllegalArgumentException("Unsupported property: " + propertyToEdit);
    }
  }

  protected int followingOccurrences() {
    int i = 1;
    SeriesEvent curr = new SeriesEvent(this.subject, this.startDateTime, this.endDateTime,
            this.description, this.location, this.status, this.prev, this.next, this.days);
    while (curr.hasNext()) {
      i++;
      curr = curr.next();
    }
    return i;
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