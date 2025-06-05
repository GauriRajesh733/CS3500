package control.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.CalendarModel;
import model.SeriesEvent;
import view.CalendarView;

import java.time.DayOfWeek;

// NOTE: CATCH IF MULTIDAY SERIES EVENT!

/**
 * Command to create a series of events that occur on specific weekdays.
 * This command can create events that repeat a specified number of times or until a certain date.
 */

public class CreateEventSeries implements CalendarCommand {
  private final SeriesEvent firstEvent;

  // create event series on specific weekdays that repeats N times
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, Integer occurrences, LocalDateTime startDateTime,
          LocalDateTime endDateTime) {
    // start and end dates for series event must be on same day
    if (!startDateTime.toLocalDate().equals(endDateTime.toLocalDate())) {
      throw new IllegalArgumentException("Start date and end date must be the same for series event");
    }
    this.firstEvent = new SeriesEvent(subject, daysOfWeek, occurrences, startDateTime, endDateTime);
  }

  // create event series on specific weekdays until specific end date and time
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, LocalDateTime startDateTime,
          LocalDateTime endDateTime, LocalDate seriesEndDate) {
    // start and end dates for series event must be on same day
    if (!startDateTime.toLocalDate().equals(endDateTime.toLocalDate())) {
      throw new IllegalArgumentException("Start date and end date must be the same for series event");
    }
    this.firstEvent = new SeriesEvent(subject, daysOfWeek, this.calculateOccurrences(startDateTime, seriesEndDate.atStartOfDay(), daysOfWeek), startDateTime, endDateTime);
  }

  // create a series of all day events that repeat N times on specific weekdays
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, Integer occurrences, LocalDate startDate) {
    LocalDateTime startDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 8, 0);
    LocalDateTime endDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 17, 0);
    this.firstEvent = new SeriesEvent(subject, daysOfWeek, occurrences, startDateTime, endDateTime);
  }

  // create a series of all day events until a specific date (inclusive)
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, LocalDate startDate,
          LocalDate seriesEndDate) {
    LocalDateTime startDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 8, 0);
    LocalDateTime endDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 17, 0);
    this.firstEvent = new SeriesEvent(subject, daysOfWeek,
            this.calculateOccurrences(startDateTime, seriesEndDate.atStartOfDay(), daysOfWeek), startDateTime, endDateTime);
  }

  @Override
  public void go(CalendarModel m, CalendarView v) {
    m.addEvent(this.firstEvent);
  }

  private LocalDateTime nextDayOfWeekDate(DayOfWeek dayOfWeek, LocalDateTime startDate) {
    LocalDateTime currentDate = startDate.plusDays(1);

    while (currentDate.getDayOfWeek() != dayOfWeek) {
      currentDate = currentDate.plusDays(1);
    }

    return currentDate;
  }

  private int calculateOccurrences(
          LocalDateTime startDate, LocalDateTime endDate, DayOfWeek[] daysOfWeek) {
    LocalDateTime currentDate = startDate;
    int count = 0;

    while (!currentDate.isAfter(endDate)) {
      DayOfWeek currentDayOfWeek = daysOfWeek[count % daysOfWeek.length];
      currentDate = nextDayOfWeekDate(currentDayOfWeek, currentDate);
      count++;
    }

    return count;
  }
}
