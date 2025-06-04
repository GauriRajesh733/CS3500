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
  LocalDateTime start;
  LocalDateTime end;

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
  public void go(CalendarModel m) {
    List< AEvent> events = m.printEventsUsingInterval(this.start, this.end);
    if (events.isEmpty()) {
      System.out.println("No events found between " + this.start + " and " + this.end);
    } else {
      for (AEvent e : events) {
        System.out.println(e);
      }
    }
  }
}
