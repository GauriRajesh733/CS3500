package model;

import java.time.LocalDateTime;

public interface CalendarModel {
  void addEvent(AEvent event);

  void editSingleEvent(EventProperty propertyToEdit, LocalDateTime startDate, LocalDateTime endDate, String subject, String newProperty);

  void editEvents(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty);
  void editSeries(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty);
}
