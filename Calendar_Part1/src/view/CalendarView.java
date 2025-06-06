package view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents view of calendar that is presented to users.
 */
public interface CalendarView {

  /**
   * Displays list of events for given day.
   * @param events represents list of events to display.
   * @param day represents day these list of events are on.
   */
  void showEventsOnDate(ArrayList<String> events, LocalDate day);

  /**
   * Displays list of events in given range of dates.
   * @param events represents list of events to display.
   * @param start represents start date.
   * @param end represents end date.
   */
  void showEventsInRange(ArrayList<String> events, LocalDateTime start, LocalDateTime end);

  /**
   * Displays status on given date.
   * @param status is true if busy, false if available.
   * @param dateTime represents date.
   */
  void showStatus(boolean status, LocalDateTime dateTime);

  /**
   * Displays error message.
   * @param errorMessage represents error message.
   */
  void showErrorMessage(String errorMessage);
}
