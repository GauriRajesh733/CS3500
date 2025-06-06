package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

interface Event {
  void addToCalendar(Map<LocalDate, ArrayList<AEvent>> calendar);

  void addSingleEventToCalendar(Map<LocalDate, ArrayList<AEvent>> calendar);

  String getSubject();

  Location getLocation();

  String getDescription();

  Status getStatus();

  LocalDateTime getStartDate();

  LocalDateTime getEndDate();

  AEvent editSingleEvent(EventProperty propertyToEdit, String newProperty);

  AEvent editSeriesEvent(EventProperty propertyToEdit, String newProperty);

  AEvent editEvents(EventProperty propertyToEdit, String newProperty);

  boolean sameEvent(String subject, LocalDateTime startDate, LocalDateTime endDate);

  boolean sameSubjectAndStart(String subject, LocalDateTime startDate);

  ArrayList<AEvent> getEvents();
}
