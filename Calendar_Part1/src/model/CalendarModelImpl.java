package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;

public class CalendarModelImpl implements CalendarModel {
  private final Map<LocalDateTime, ArrayList<AEvent>> calendar;

  public CalendarModelImpl() {
    // add comparator to treemap!
    this.calendar = new HashMap<>();
  }

  @Override
  public void addEvent(AEvent event) {
    // check for repeated date??
    event.addToCalendar(this.calendar);
  }

  @Override
  public void editSingleEvent(EventProperty propertyToEdit, LocalDateTime startDate, LocalDateTime endDate, String subject, String newProperty) {

  }

  @Override
  public void editEvents(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty) {

  }

  //addSingleEvent
  //addSeriesEvent

  //MULTIDAY EVENTS :(
  @Override
  public List<AEvent> printEventsForDate(LocalDate date) {
    List<AEvent> events = new ArrayList<>();
    for (ArrayList<AEvent> eventList : this.calendar.values()) {
      for (AEvent event : eventList) {
        LocalDate eventStartDate = event.getStartDate().toLocalDate();
        LocalDate eventEndDate = event.getEndDate().toLocalDate();
        if (!date.isBefore(eventStartDate) && !date.isAfter(eventEndDate)) {
          events.add(event);
        }
      }
    }
    return events;
  }

  //location
  @Override
  public List<AEvent> printEventsUsingInterval(LocalDateTime start, LocalDateTime end) {
    List<AEvent> events = new ArrayList<>();
    for (ArrayList<AEvent> eventList: this.calendar.values()) {
      for (AEvent event : eventList) {

        LocalDateTime eventStartDateTime = event.toLocalDateTime();
        LocalDateTime eventEndDateTime = event.getEndDate().toLocalDateTime();
        if ((eventStartDate.isAfter(start) || eventStartDate.isEqual(start)) &&
                (eventEndDate.isBefore(end) || eventEndDate.isEqual(end))) {
          events.addAll(entry.getValue());
        }
      }
    }
    return events;
  }

  @Override
  public void editSeries(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty) {

  }

  @Override
  public boolean showCalendarStatus(LocalDateTime dateTime) {
    LocalDate date = dateTime.toLocalDate();

    for (ArrayList<AEvent> eventList: this.calendar.values()) {
      for (AEvent event: eventList) {
        LocalDate eventStartDate = event.getStartDate().toLocalDate();
        LocalDate eventEndDate = event.getEndDate().toLocalDate();

        if (!date.isBefore(eventStartDate) && !date.isAfter(eventEndDate)) {
          return true;
        }
      }
    }
    return false;
  }

  private AEvent findEvent();

  // find date

  // find subject

  // if it exists then update

  // if editing non-date property just mutate or create new event

  // if editing date property

}
