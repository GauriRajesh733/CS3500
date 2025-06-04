package control.commands;

import java.time.LocalDateTime;
import java.util.List;

import control.CalendarCommand;
import model.AEvent;
import model.CalendarModel;

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

  //no events found in the view
  @Override
  public void go(CalendarModel m) {
    m.printEventsUsingInterval(this.start, this.end);
  }
}
