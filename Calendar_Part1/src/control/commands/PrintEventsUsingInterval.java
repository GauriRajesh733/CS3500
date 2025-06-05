package control.commands;

import java.time.LocalDateTime;

import model.CalendarModel;
import view.CalendarView;

/**
 * Command to print events within a specified time interval.
 */
public class PrintEventsUsingInterval implements CalendarCommand {
  private final LocalDateTime start;
  private final LocalDateTime end;

  /**
   * Constructor for the PrintEventsUsingInterval command.
   *
   * @param start The start time of the interval.
   * @param end   The end time of the interval.
   */
  public PrintEventsUsingInterval(LocalDateTime start, LocalDateTime end) {
    this.start = start;
    this.end = end;
  }

  @Override
  public void go(CalendarModel m, CalendarView v) {
    v.showEventsInRange(m.printEventsUsingInterval(this.start, this.end), this.start, this.end);
  }
}
