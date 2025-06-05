package control.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.CalendarModel;
import view.CalendarView;

import java.time.DayOfWeek;

/**
 * Command to create a series of events that occur on specific weekdays.
 * This command can create events that repeat a specified number of times or until a certain date.
 */

public class CreateEventSeries implements CalendarCommand {
  private final String subject;
  private final DayOfWeek[] daysOfWeek;
  private final Integer occurrences;
  private final LocalDateTime startDateTime;
  private final LocalDateTime endDateTime;

  // create event series on specific weekdays that repeats N times
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, Integer occurrences, LocalDateTime startDateTime,
          LocalDateTime endDateTime) {

    // start and end dates for series event must be on same day
    if (!startDateTime.toLocalDate().equals(endDateTime.toLocalDate())) {
      throw new IllegalArgumentException("Start date and end date must be the same for series event");
    }
    this.subject = subject;
    this.daysOfWeek = daysOfWeek;
    this.occurrences = occurrences;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
  }

  // create event series on specific weekdays until specific end date and time
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, LocalDateTime startDateTime,
          LocalDateTime endDateTime, LocalDate seriesEndDate) {

    // start and end dates for series event must be on same day
    if (!startDateTime.toLocalDate().equals(endDateTime.toLocalDate())) {
      throw new IllegalArgumentException("Start date and end date must be the same for series event");
    }

    this.subject = subject;
    this.daysOfWeek = daysOfWeek;
    this.occurrences = this.calculateOccurrences(startDateTime, seriesEndDate.atStartOfDay(),
            daysOfWeek);
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
  }

  // create a series of all day events that repeat N times on specific weekdays
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, Integer occurrences, LocalDate startDate) {

    this.startDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 8, 0);
    this.endDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 17, 0);
    this.daysOfWeek = daysOfWeek;
    this.occurrences = occurrences;
    this.subject = subject;
  }

  // create a series of all day events until a specific date (inclusive)
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, LocalDate startDate,
          LocalDate seriesEndDate) {

    this.startDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 8, 0);
    this.endDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 17, 0);
    this.daysOfWeek = daysOfWeek;
    this.subject = subject;
    this.occurrences = this.calculateOccurrences(startDateTime, seriesEndDate.atStartOfDay(), daysOfWeek);
  }

  @Override
  public void go(CalendarModel m, CalendarView v) {
    m.addSeriesEvent(subject, daysOfWeek, occurrences, startDateTime, endDateTime);
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
