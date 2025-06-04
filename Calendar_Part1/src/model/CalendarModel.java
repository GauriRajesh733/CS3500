package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface CalendarModel {
  void addEvent(AEvent event);

  ArrayList<AEvent> printEventsForDate(LocalDate date);

  ArrayList<AEvent> printEventsUsingInterval(LocalDateTime start, LocalDateTime end);

  void editSingleEvent(EventProperty propertyToEdit, LocalDateTime startDate, LocalDateTime endDate, String subject, String newProperty);

  void editEvents(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty);

  void editSeries(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty);

  boolean showCalendarStatus(LocalDateTime date);
}
