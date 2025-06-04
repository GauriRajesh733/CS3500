package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CalendarModel {
  void addEvent(AEvent event);

  List<AEvent> printEventsForDate(LocalDate date);

  List<AEvent> printEventsUsingInterval(LocalDateTime start, LocalDateTime end);

  void editSingleEvent(EventProperty propertyToEdit, LocalDateTime startDate, LocalDateTime endDate, String subject, String newProperty);

  void editEvents(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty);

  void editSeries(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty);

  boolean showCalendarStatus(LocalDateTime date);
}
