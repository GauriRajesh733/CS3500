package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a calendar that can store single events and series of events that can be edited.
 */
public class CalendarModelImpl implements CalendarModel {
  private final Map<LocalDate, ArrayList<AEvent>> calendar;

  public CalendarModelImpl() {
    this.calendar = new HashMap<>();
  }

  @Override
  public void addSingleEvent(
          String subject, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    // check if event already exists in calendar
    if (this.findSingleEvent(startDateTime, endDateTime, subject).isEmpty()) {
      new SingleEvent(
              subject, startDateTime, endDateTime, null, null, null).addToCalendar(this.calendar);
    } else {
      throw new IllegalArgumentException("Given event with start date, end date, and subject " +
              "already exists in calendar");
    }
  }

  @Override
  public void addSeriesEvent(String subject, DayOfWeek[] daysOfWeek, int occurrences,
                             LocalDateTime startDateTime, LocalDateTime endDateTime) {
    // check if event already exists in calendar
    if (this.findSingleEvent(startDateTime, endDateTime, subject).isEmpty()) {
      new SeriesEvent(subject, daysOfWeek, occurrences,
              startDateTime, endDateTime, null, null, null).addToCalendar(this.calendar);
    } else {
      throw new IllegalArgumentException(
              "Given event with start date, end date, and subject already exists in calendar");
    }
  }

  @Override
  public void editSingleEvent(
          EventProperty propertyToEdit, LocalDateTime startDate,
          LocalDateTime endDate, String subject, String newProperty) throws
          IllegalArgumentException {
    ArrayList<AEvent> eventsToEdit = this.findSingleEvent(startDate, endDate, subject);

    if (eventsToEdit.isEmpty()) {
      throw new IllegalArgumentException(
              "No events in calendar with given start date, end date, and subject");
    }

    for (AEvent event : eventsToEdit) {
      // remove event from calendar
      this.removeEvent(event);
      // create updated event
      AEvent newEvent = event.editSingleEvent(propertyToEdit, newProperty);
      // add updated event back to calendar
      newEvent.addSingleEventToCalendar(this.calendar);
    }
  }


  /**
   * Get list of dates between given start and end date.
   * @param start represents start date.
   * @param end represents end date.
   * @return an array of dates in given range.
   */
  private LocalDate[] datesBetween(LocalDate start, LocalDate end) {
    LocalDate[] dates = new LocalDate[(int) start.until(end, ChronoUnit.DAYS) + 1];
    for (int i = 0; i < dates.length; i++) {
      dates[i] = start.plusDays(i);
    }
    return dates;
  }

  /**
   * Remove the given event from this calendar and the date if there are no other events that day.
   *
   * @param eventToRemove represents the event to remove.
   **/
  public void removeEvent(AEvent eventToRemove) {
    LocalDate[] dateRange = this.datesBetween(eventToRemove.startDateTime.toLocalDate(),
            eventToRemove.endDateTime.toLocalDate());

    for (LocalDate dateToRemoveFrom : dateRange) {

      ArrayList<AEvent> newDateEntries = new ArrayList<>();
      ArrayList<AEvent> dayToRemoveEvent = this.calendar.get(dateToRemoveFrom);

      for (AEvent event : dayToRemoveEvent) {
        if (!eventToRemove.sameEvent(
                event.getSubject(), event.getStartDate(), event.getEndDate())) {
          newDateEntries.add(event);
        }
      }

      this.calendar.put(dateToRemoveFrom, newDateEntries);
    }

    // remove calendar date keys if no events
    for (LocalDate date : dateRange) {

      ArrayList<AEvent> dayToRemoveEvent = this.calendar.get(date);
      if (dayToRemoveEvent.isEmpty()) {
        this.calendar.remove(date);
      }
    }
  }

  @Override
  public void removeEvents(ArrayList<AEvent> eventsToRemove, boolean removeSeries) {
    for (AEvent event : eventsToRemove) {
      LocalDate[] dateRange = this.datesBetween(event.startDateTime.toLocalDate(),
              event.endDateTime.toLocalDate());

      for (LocalDate dateToRemoveFrom : dateRange) {
        ArrayList<AEvent> newDateEntries = new ArrayList<>();

        if (!this.calendar.containsKey(dateToRemoveFrom)) {
          continue;
        }

        ArrayList<AEvent> dayToRemoveEvent = this.calendar.get(dateToRemoveFrom);

        for (AEvent dayEvent : dayToRemoveEvent) {
          if (removeSeries
                  && !event.sameSubjectAndStart(dayEvent.getSubject(), dayEvent.getStartDate())) {
            newDateEntries.add(dayEvent);
          } else if (!removeSeries
                  && !event.sameEvent(
                  dayEvent.getSubject(), dayEvent.getStartDate(), dayEvent.getEndDate())) {
            newDateEntries.add(dayEvent);
          }
        }

        this.calendar.put(dateToRemoveFrom, newDateEntries);
      }

      // remove calendar date keys if no events
      for (LocalDate date : dateRange) {
        if (!this.calendar.containsKey(date)) {
          continue;
        }

        ArrayList<AEvent> dayToRemoveEvent = this.calendar.get(date);
        if (dayToRemoveEvent.isEmpty()) {
          this.calendar.remove(date);
        }
      }
    }
  }

  @Override
  public void editEvents(
          EventProperty propertyToEdit, String subject,
          LocalDateTime startDate, String newProperty) throws IllegalArgumentException {
    ArrayList<AEvent> eventsToEdit = this.findSeries(startDate, subject);

    if (eventsToEdit.isEmpty()) {
      throw new IllegalArgumentException(
              "No events in calendar with given start date, end date, and subject");
    }

    for (AEvent event : eventsToEdit) {
      if (propertyToEdit != EventProperty.START) {
        // remove all events from calendar
        this.removeEvents(event.getEvents(), false);
      } else {
        // only remove events from date onward
        ArrayList<AEvent> eventsToRemove = event.getEvents();
        for (int i = eventsToRemove.size() - 1; i >= 0; i--) {
          if (eventsToRemove.get(i).getStartDate().isBefore(event.getStartDate())) {
            eventsToRemove.remove(i);
          }
        }
        this.removeEvents(eventsToRemove, false);
      }

      // update event
      AEvent newEvent = event.editEvents(propertyToEdit, newProperty);
      // add event back to calendar
      newEvent.addToCalendar(this.calendar);
    }
  }


  //MULTIDAY EVENTS :(
  @Override
  public ArrayList<String> printEventsForDate(LocalDate date) {
    ArrayList<String> events = new ArrayList<>();
    for (ArrayList<AEvent> eventList : this.calendar.values()) {
      for (AEvent event : eventList) {
        LocalDate eventStartDate = event.getStartDate().toLocalDate();
        LocalDate eventEndDate = event.getEndDate().toLocalDate();
        // check if date falls within range
        if (!date.isBefore(eventStartDate) && !date.isAfter(eventEndDate)) {
          events.add(event.toString());
        }
      }
    }
    return events;
  }

  //location
  @Override
  public ArrayList<String> printEventsUsingInterval(LocalDateTime start, LocalDateTime end) {
    ArrayList<String> events = new ArrayList<>();
    for (ArrayList<AEvent> eventList : this.calendar.values()) {
      for (AEvent event : eventList) {
        LocalDateTime eventStartDateTime = event.getStartDate();
        LocalDateTime eventEndDateTime = event.getEndDate();
        if (!eventStartDateTime.isBefore(start) && !eventEndDateTime.isAfter(end)) {
          events.add(event.toString());
        }
      }
    }
    return events;
  }

  @Override
  public void editSeries(
          EventProperty propertyToEdit, String subject,
          LocalDateTime startDate, String newProperty) throws IllegalArgumentException {
    // find event or events if single multiday event
    ArrayList<AEvent> eventToEdit = this.findSeries(startDate, subject);

    if (eventToEdit.isEmpty()) {
      throw new IllegalArgumentException(
              "No events in calendar with given start date, end date, and subject");
    }

    for (AEvent event : eventToEdit) {
      // remove event from calendar
      this.removeEvents(event.getEvents(), true);
      // update event
      AEvent newEvent = event.editSeriesEvent(propertyToEdit, newProperty);
      // add event back to calendar
      newEvent.addToCalendar(this.calendar);
    }
  }

  @Override
  public boolean showCalendarStatus(LocalDateTime dateTime) {
    LocalDate queryDate = dateTime.toLocalDate();

    if (!calendar.containsKey(queryDate)) {
      return false;
    }

    ArrayList<AEvent> dayEvents = calendar.get(queryDate);
    for (AEvent event : dayEvents) {
      LocalDateTime eventStartDate = event.getStartDate();
      LocalDateTime eventEndDate = event.getEndDate();

      if (!dateTime.isBefore(eventStartDate) && dateTime.isBefore(eventEndDate)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Finds the single event or list of events if a multiday event in this calendar based on the
   * given information.
   * @param startDate represents the start date.
   * @param endDate represents the end date.
   * @param subject represents the subject.
   * @return list with single event or multiple events if multiday event based on given information.
   */
  private ArrayList<AEvent> findSingleEvent(
          LocalDateTime startDate, LocalDateTime endDate, String subject) {
    ArrayList<AEvent> singleEvent = new ArrayList<>();

    if (!this.calendar.containsKey(startDate.toLocalDate())) {
      return singleEvent;
    }

    LocalDate[] dateRange = datesBetween(startDate.toLocalDate(), endDate.toLocalDate());

    for (LocalDate date : dateRange) {
      if (!calendar.containsKey(date)) {
        continue;
      }

      ArrayList<AEvent> dayEvents = this.calendar.get(date);

      for (AEvent event : dayEvents) {
        if (event.sameEvent(subject, startDate, endDate)) {
          singleEvent.add(event);
          // if single day event return event
          if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
            return singleEvent;
          }
        }
      }
    }

    return singleEvent;
  }

  /**
   * Find list of all events in a series based on start date and subject.
   * @param startDate represents the start date.
   * @param subject represents the subject.
   * @return list of events in this series.
   */
  private ArrayList<AEvent> findSeries(LocalDateTime startDate, String subject) {
    ArrayList<AEvent> seriesEvents = new ArrayList<>();
    ArrayList<AEvent> dayEvents = this.calendar.get(startDate.toLocalDate());

    for (AEvent event : dayEvents) {
      if (event.sameSubjectAndStart(subject, startDate)) {
        seriesEvents.add(event);
        if (event.getStartDate().toLocalDate().equals(event.getEndDate().toLocalDate())) {
          return seriesEvents;
        }
      }
    }

    return seriesEvents;
  }

}
