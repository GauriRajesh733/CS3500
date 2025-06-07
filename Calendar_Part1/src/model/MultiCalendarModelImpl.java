package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Implementation of the MultiCalendarModel interface.
 * This class provides methods to manage multiple calendars and perform operations on them.
 */
public class MultiCalendarModelImpl implements  MultiCalendarModel{

  @Override
  public void createCalendar(String calendarName) {

  }

  @Override
  public void editCalendar(String calendarName) {

  }

  @Override
  public String[] listCalendars() {
    return new String[0];
  }

  @Override
  public Calendar getCalendar(String calendarName) {
    return null;
  }

  @Override
  public void copyEvent(
          Calendar sourceCalendar, Calendar targetCalendar, AEvent eventToCopy,
          LocalDateTime targetDateTime) {

  }

  @Override
  public void copyEvents(
          LocalDate sourceDate, LocalDate targetDate, Calendar sourceCalendar,
          Calendar targetCalendar) {

  }

  @Override
  public void copyEventsFromDateToTime(
          LocalDate sourceDate, LocalDateTime targetDateTime, Calendar sourceCalendar,
          Calendar targetCalendar) {

  }
}
