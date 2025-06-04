package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CalendarModel {
  void addEvent(AEvent event);
  List<AEvent> printEventsForDate(LocalDate date);
  List<AEvent> printEventsUsingInterval(LocalDateTime start, LocalDateTime end);
}
