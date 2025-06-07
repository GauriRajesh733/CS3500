package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface MultiCalendarModel {
  /**
   * Adds a new calendar to the model.
   * @param calendarName the name of the new calendar
   */
  void createCalendar(String calendarName);

  /**
   * Removes a calendar from the model.
   * @param calendarName the name of the calendar to remove
   */
  void editCalendar(String calendarName);

  /**
   * Lists all calendars in the model.
   * @return an array of calendar names
   */
  String[] listCalendars();

  /**
   * Switches the active calendar to the specified one.
   * @param calendarName the name of the calendar to switch to
   */
  Calendar getCalendar(String calendarName);

  /**
   * Copy an event from one calendar to another.
   * @param sourceCalendar Origin calendar from which the event is copied
   * @param targetCalendar Destination calendar to which the event is copied
   * @param eventToCopy the event to copy
   * @param targetDateTime the date and time in the target calendar where the event should be copied
   */
  void copyEvent(
          Calendar sourceCalendar, Calendar targetCalendar,
          AEvent eventToCopy, LocalDateTime targetDateTime);

  /**
   * Copy all events from date in source calendar to date in target calendar.
   * @param sourceDate source date from which events are copied
   * @param targetDate target date to which events are copied
   * @param sourceCalendar source calendar
   * @param targetCalendar target calendar
   */
  void copyEvents(LocalDate sourceDate, LocalDate targetDate,
                  Calendar sourceCalendar, Calendar targetCalendar);

  /**
   * Copy all events from a specific date in the source calendar to a specific time in the target
   * calendar.
   * @param sourceDate source date from which events are copied
   * @param targetDateTime target date and time in the target calendar
   * @param sourceCalendar source calendar
   * @param targetCalendar target calendar
   */
  void copyEventsFromDateToTime(
          LocalDate sourceDate, LocalDateTime targetDateTime,
          Calendar sourceCalendar, Calendar targetCalendar);

}
