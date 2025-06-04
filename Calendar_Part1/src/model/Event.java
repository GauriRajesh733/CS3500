package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public interface Event {
  void addToCalendar(Map<LocalDate, ArrayList<AEvent>> calendar);

  String getSubject();

  LocalDateTime getStartDate();

  LocalDateTime getEndDate();

  void editSingleEvent(EventProperty propertyToEdit, String newProperty);

  void editSeriesEvent(EventProperty propertyToEdit, String newProperty);

  void editEvents(EventProperty propertyToEdit, String newProperty);

  boolean sameEvent(String subject, LocalDateTime startDate, LocalDateTime endDate);

  boolean sameSubjectAndStart(String subject, LocalDateTime startDate);

}
