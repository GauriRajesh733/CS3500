package control.commands;

import java.time.LocalDateTime;
import java.time.DayOfWeek;

import control.CalendarCommand;
import model.CalendarModel;
import model.SeriesEvent;

// NOTE: CATCH IF MULTIDAY SERIES EVENT!
public class CreateEventSeries implements CalendarCommand {
  private final SeriesEvent firstEvent;

  // create event series on specific weekdays that repeats N times
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, Integer occurrences, LocalDateTime startDateTime,
          LocalDateTime endDateTime) {
    this.firstEvent = new SeriesEvent(subject, daysOfWeek, occurrences, startDateTime, endDateTime);
  }

  // create event series on specific weekdays until specific end date and time
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, LocalDateTime startDateTime,
          LocalDateTime endDateTime, LocalDateTime seriesEndDate) {
    this.firstEvent = new SeriesEvent(subject, daysOfWeek, this.calculateOccurrences(startDateTime, seriesEndDate, daysOfWeek), startDateTime, endDateTime);
  }

  // create a series of all day events that repeat N times on specific weekdays
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, Integer occurrences, LocalDateTime startDate) {
    LocalDateTime endDate = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 15, 0);
    this.firstEvent = new SeriesEvent(subject, daysOfWeek, occurrences, startDate, endDate);
  }

  // create a series of all day events until a specific date (inclusive)
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, LocalDateTime startDate,
          LocalDateTime seriesEndDate) {
    LocalDateTime endDate = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 15, 0);
    this.firstEvent = new SeriesEvent(subject, daysOfWeek,
            this.calculateOccurrences(startDate, seriesEndDate, daysOfWeek), startDate, endDate);
  }

  @Override
  public void go(CalendarModel m) {
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
