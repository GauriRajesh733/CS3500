package control.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.CalendarModel;
import view.CalendarView;

/**
 * Command to create a single event in the calendar.
 * This command can create an all-day event or a single-day event with specific start and end times.
 */
public class CreateSingleEvent implements CalendarCommand {
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private final String subject;

  /**
   * Constructor to create an all-day event on a specific date.
   * @param subject subject of the event
   * @param startDate date for the event (all
   */
  public CreateSingleEvent(String subject, LocalDate startDate) {
    this.subject = subject;
    this.startDate = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 8, 0);
    ;
    this.endDate = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 17, 0);
  }

  /**
   * Constructor to create a single event with specific start and end times.
   * @param subject subject of the event
   * @param startDateTime start date and time for the event
   * @param endDateTime end date and time for the event
   */
  public CreateSingleEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    this.subject = subject;
    this.startDate = startDateTime;
    this.endDate = endDateTime;
  }

  @Override
  public void run(CalendarModel m, CalendarView v) {
    m.addSingleEvent(this.subject, this.startDate, this.endDate);
  }
}
