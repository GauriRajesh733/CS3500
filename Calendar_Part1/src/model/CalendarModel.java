package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface CalendarModel {
  void addSingleEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime);

  void addSeriesEvent(String subject, DayOfWeek[] daysOfWeek, int occurrences,
                      LocalDateTime startDateTime, LocalDateTime endDateTime);

  ArrayList<String> printEventsForDate(LocalDate date);

  ArrayList<String> printEventsUsingInterval(LocalDateTime start, LocalDateTime end);

  void editSingleEvent(EventProperty propertyToEdit, LocalDateTime startDate, LocalDateTime endDate, String subject, String newProperty);

  void editEvents(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty);

  void editSeries(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty);

  boolean showCalendarStatus(LocalDateTime date);

  void removeEvents(ArrayList<AEvent> eventsToRemove, boolean removeSeries);
}
