package control.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.CalendarModel;
import view.CalendarView;

import java.time.DayOfWeek;
import java.util.Arrays;

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

  /**
   * Constructor to create event series on specific weekdays that repeats N times.
   * @param subject subject of the event
   * @param daysOfWeek days of the week on which the event occurs
   * @param occurrences number of occurrences for the event series
   * @param startDateTime start date and time for the first event in the series
   * @param endDateTime end date and time for the first event in the series
   */
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, Integer occurrences, LocalDateTime startDateTime,
          LocalDateTime endDateTime) {

    // start and end dates for series event must be on same day
    if (!startDateTime.toLocalDate().equals(endDateTime.toLocalDate())) {
      throw new IllegalArgumentException(
              "Start date and end date must be the same for series event");
    }
    this.subject = subject;
    this.daysOfWeek = daysOfWeek;
    this.occurrences = occurrences;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
  }

  /**
   * Constructor to create event series on specific weekdays until a specific end date.
   *
   * @param subject subject of the event
   * @param daysOfWeek days of the week on which the event occurs
   * @param startDateTime start date and time for the first event in the series
   * @param endDateTime end date and time for the first event in the series
   * @param seriesEndDate end date for the series
   */
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, LocalDateTime startDateTime,
          LocalDateTime endDateTime, LocalDate seriesEndDate) {

    // start and end dates for series event must be on same day
    if (!startDateTime.toLocalDate().equals(endDateTime.toLocalDate())) {
      throw new IllegalArgumentException(
              "Start date and end date must be the same for series event");
    }

    this.subject = subject;
    this.daysOfWeek = daysOfWeek;
    this.occurrences = this.calculateOccurrences(startDateTime, seriesEndDate.atTime(23, 59),
            daysOfWeek);
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
  }

  /**
   * Constructor to create event series on specific weekdays that starts on a specific date.
   * @param subject subject of the event
   * @param daysOfWeek days of the week on which the event occurs
   * @param occurrences number of occurrences for the event series
   * @param startDate start date for the first event in the series
   */
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

  /**
   * Constructor to create a series of all day events until a specific date (inclusive).
   * @param subject subject of the event
   * @param daysOfWeek days of the week on which the event occurs
   * @param startDate start date for the first event in the series
   * @param seriesEndDate end date for the series
   */
  public CreateEventSeries(
          String subject, DayOfWeek[] daysOfWeek, LocalDate startDate,
          LocalDate seriesEndDate) {

    this.startDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 8, 0);
    this.endDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 17, 0);
    this.daysOfWeek = daysOfWeek;
    this.subject = subject;
    this.occurrences = this.calculateOccurrences(startDateTime, seriesEndDate.atTime(23, 59), daysOfWeek);
  }

  @Override
  public void run(CalendarModel m, CalendarView v) {
    m.addSeriesEvent(subject, daysOfWeek, occurrences, startDateTime, endDateTime);
  }

  private int calculateOccurrences(
          LocalDateTime startDate, LocalDateTime endDate, DayOfWeek[] daysOfWeek) {
    LocalDateTime currentDate = startDate;
    int count = 0;

    if (Arrays.asList((daysOfWeek)).contains(currentDate.getDayOfWeek())) {
      count++;
    }

    while (true) {
      currentDate = findNextOccurrence(currentDate, daysOfWeek);

      if (currentDate.isAfter(endDate)) {
        break;
      }
      count++;
    }
    return count;
  }

  private LocalDateTime findNextOccurrence(LocalDateTime currentDate, DayOfWeek[] daysOfWeek) {
    LocalDateTime nextDate = currentDate.plusDays(1);

    while (true) {
      for (DayOfWeek dayOfWeek : daysOfWeek) {
        if (nextDate.getDayOfWeek() == dayOfWeek) {
          return nextDate;
        }
      }
      nextDate = nextDate.plusDays(1);
    }
  }
}
