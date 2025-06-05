package view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.AEvent;

public interface CalendarView {
  void showEventsOnDate(ArrayList<String> events, LocalDate day);

  void showEventsInRange(ArrayList<String> events, LocalDateTime start, LocalDateTime end);

  void showStatus(boolean status, LocalDateTime dateTime);

  void showErrorMessage(String errorMessage);
}
