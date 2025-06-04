package view;

import java.time.LocalDate;
import java.util.ArrayList;

import model.AEvent;

public interface CalendarView {
  void showEventsOnDate(ArrayList<AEvent> events, LocalDate date);

  void showEventsInRange(ArrayList<AEvent> events, LocalDate startDate, LocalDate endDate);

  void showStatus(ArrayList<AEvent> events);
}
