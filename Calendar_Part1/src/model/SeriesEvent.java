package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Map;

/**
 * Represents a series of events in a calendar.
 */
class SeriesEvent extends AEvent {
  private SeriesEvent next;
  private SeriesEvent prev;
  private final DayOfWeek[] days;

  /**
   * Constructs a series of events.
   * @param subject represents subject.
   * @param startDateTime represents start date.
   * @param endDateTime represents end date.
   * @param description represents description.
   * @param location represents location.
   * @param status represents status.
   * @param prev represents series event that comes before this.
   * @param next represents series event that comes after this.
   * @param days represents days of week that this event repeats on.
   */
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

  /**
   * Constructs series of events from the beginning.
   * @param subject represents subject.
   * @param days represents days of week this event repeats on.
   * @param occurrences represents occurrences of this series event.
   * @param startDateTime represents start date.
   * @param endDateTime represents end date.
   * @param description represents description.
   * @param location represents location.
   * @param status represents status.
   */
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

  /**
   * Constructs series event with no previous or following events yet.
   * @param days represents days of week this event repeasts on.
   * @param subject represents subject of this event.
   * @param startDateTime represents start date of this event.
   * @param endDateTime represents end date of this event.
   */
  public SeriesEvent(
          DayOfWeek[] days, String subject, LocalDateTime
                  startDateTime, LocalDateTime endDateTime) {
    super(subject, startDateTime, endDateTime, null, null, null);
    this.next = null;
    this.prev = null;
    this.days = days;
  }

  /**
   * Gets the closest possible date of the next event in this series.
   * @param daysOfWeek represents days of week this series repeats on.
   * @param currentEvent represents current event in series.
   * @param startDate represents start date of current event.
   * @return closest date for next event in series.
   */
  protected LocalDateTime getNextWeekDate(
          DayOfWeek[] daysOfWeek, AEvent currentEvent, boolean startDate) {
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
    // first get to first event in series event
    SeriesEvent currentEvent = this.firstEvent();

    // add this single event to calendar
    if (!calendar.containsKey(currentEvent.getStartDate().toLocalDate())) {
      calendar.put(currentEvent.getStartDate().toLocalDate(), new ArrayList<AEvent>());
    }
    calendar.get(currentEvent.getStartDate().toLocalDate()).add(currentEvent);

    // add other events in this series to calendar
    if (currentEvent.hasNext()) {
      currentEvent.next().addRestToCalendar(calendar);
    }
  }

  /**
   * Adds events following this series event to given calendar.
   * @param calendar represents map of dates to list of events in calendar.
   */
  protected void addRestToCalendar(Map<LocalDate, ArrayList<AEvent>> calendar) {
    // add this single event to calendar
    if (!calendar.containsKey(this.startDateTime.toLocalDate())) {
      calendar.put(this.startDateTime.toLocalDate(), new ArrayList<AEvent>());
    }
    calendar.get(this.startDateTime.toLocalDate()).add(this);

    // add other events in this series to calendar
    if (this.hasNext()) {
      this.next().addRestToCalendar(calendar);
    }
  }

  @Override
  public boolean sameEvent(String subject, LocalDateTime startDate, LocalDateTime endDate) {
    return super.sameEvent(subject, startDate, endDate);
  }

  @Override
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
        if (this.startDateTime.until(newEndDateTime, ChronoUnit.DAYS) > 1) {
          throw new IllegalArgumentException(
                  "Series event cannot span multiple days");
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

  /**
   * Gets first event in this series of events.
   * @return first series event in this series.
   */
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
                  , this.location, this.status, this.prev,
                  this.next.editEvents(propertyToEdit, newProperty), this.days);
        } else {
          return new SeriesEvent(newProperty, this.startDateTime, this.endDateTime, this.description
                  , this.location, this.status, this.prev, this.next
                  , this.days);
        }

      case DESCRIPTION:
        if (this.hasNext()) {
          return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime, newProperty
                  , this.location, this.status, this.prev,
                  this.next.editEvents(propertyToEdit, newProperty), this.days);
        } else {
          return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime, newProperty
                  , this.location, this.status, this.prev, this.next
                  , this.days);
        }

      case LOCATION:
        if (this.hasNext()) {
          return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime,
                  this.description, Location.fromInput(newProperty), this.status, this.prev,
                  this.next.editEvents(propertyToEdit, newProperty), this.days);
        } else {
          return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime,
                  this.description, Location.fromInput(newProperty), this.status,
                  this.prev, this.next, this.days);
        }

      case STATUS:
        if (this.hasNext()) {
          return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime,
                  this.description, this.location, Status.fromInput(newProperty), this.prev,
                  this.next.editEvents(propertyToEdit, newProperty), this.days);
        } else {
          return new SeriesEvent(this.subject, this.startDateTime, this.endDateTime,
                  this.description, this.location, Status.fromInput(newProperty), this.prev,
                  this.next, this.days);
        }

      case END:
        LocalDateTime newEndDateTime = LocalDateTime.parse(newProperty);
        if (this.startDateTime.isAfter(newEndDateTime)) {
          throw new IllegalArgumentException("Start date time must be before end date time");
        }
        if (this.startDateTime.until(newEndDateTime, ChronoUnit.DAYS) > 1) {
          throw new IllegalArgumentException(
                  "Series event cannot span multiple days");
        }
        if (this.hasNext()) {
          return new SeriesEvent(this.subject, this.startDateTime, newEndDateTime, this.description
                  , this.location, this.status, this.prev,
                  this.next.editEvents(propertyToEdit, newProperty), this.days);
        } else {
          return new SeriesEvent(this.subject, this.startDateTime, newEndDateTime, this.description
                  , this.location, this.status, this.prev, this.next, this.days);
        }
        // if editing start date update this event and following events in series;
        // unlink first from previous
      case START:
        LocalDateTime newStartDate = LocalDateTime.parse(newProperty);
        if (newStartDate.isAfter(this.endDateTime)) {
          throw new IllegalArgumentException("Start date time must be before end date time");
        }
        if (newStartDate.until(this.endDateTime, ChronoUnit.DAYS) > 1) {
          throw new IllegalArgumentException(
                  "Series event cannot span multiple days");
        }
        // unlink from previous events
        if (this.hasPrev()) {
          this.prev.setNext(null);
          this.setPrev(null);
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

  /**
   * Sets previous event.
   * @param seriesEvent represents event before this event.
   */
  protected void setPrev(SeriesEvent seriesEvent) {
    this.prev = seriesEvent;
  }

  /**
   * Sets next event.
   * @param seriesEvent represents event after this event.
   */
  protected void setNext(SeriesEvent seriesEvent) {
    this.next = seriesEvent;
  }

  /**
   * Determines if there is an event after this event in series.
   * @return true if there is a following event, false otherwise.
   */
  protected boolean hasNext() {
    return this.next != null;
  }

  /**
   * Determines if there is an event before this event in series.
   * @return true if there is a previous event, false otherwise.
   */
  protected boolean hasPrev() {
    return this.prev != null;
  }

  /**
   * Gets the next event in series.
   * @return next event.
   * @throws IllegalArgumentException if no event exists.
   */
  protected SeriesEvent next() throws IllegalArgumentException {
    if (this.hasNext()) {
      return this.next;
    }
    throw new IllegalArgumentException("There is no event after this");
  }

  /**
   * Gets previous event in series.
   * @return previous event.
   * @throws IllegalArgumentException if no event exists.
   */
  protected SeriesEvent prev() throws IllegalArgumentException {
    if (this.hasPrev()) {
      return this.prev;
    }
    throw new IllegalArgumentException("There is no event before this");
  }

  /**
   * Gets next date based on start date and day of week.
   * @param dayOfWeek day of week this event repeats on.
   * @param startDate represents start date of this event.
   * @return closest date based on given day of week.
   */
  private LocalDateTime nextDayOfWeekDate(DayOfWeek dayOfWeek, LocalDateTime startDate) {
    LocalDateTime currentDate = startDate.plusDays(1);

    while (currentDate.getDayOfWeek() != dayOfWeek) {
      currentDate = currentDate.plusDays(1);
    }

    return currentDate;
  }

  /**
   * Gets all events in this series of events.
   * @return list of all series events.
   */
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

  /**
   * Get following events in this series.
   * @return list of series events following this event.
   */
  protected ArrayList<AEvent> getNextSeriesEvents() {
    ArrayList<AEvent> eventsInSeries = new ArrayList<>();

    if (this.hasNext()) {
      eventsInSeries.add(this.next());
      eventsInSeries.addAll(this.next.getNextSeriesEvents());
    }

    return eventsInSeries;
  }

  /**
   * Get previous events in this series.
   * @return list of series events before this event.
   */
  protected ArrayList<AEvent> getPrevSeriesEvents() {
    ArrayList<AEvent> eventsInSeries = new ArrayList<>();

    if (this.hasPrev()) {
      eventsInSeries.add(this.prev());
      eventsInSeries.addAll(this.prev.getPrevSeriesEvents());
    }


    return eventsInSeries;
  }
}