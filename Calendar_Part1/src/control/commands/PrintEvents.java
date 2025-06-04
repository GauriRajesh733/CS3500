package control.commands;

import java.time.LocalDate;
import java.util.List;

import control.CalendarCommand;
import model.AEvent;
import model.CalendarModel;

/**
 * Command to print events for a specific date.
 */
public class PrintEvents implements CalendarCommand {
  private final LocalDate date;
  /**
   * Constructor for the PrintEvents command.
   *
   * @param date The date for which events should be printed.
   */
  public PrintEvents(LocalDate date) {
    this.date = date;
  }

  @Override
  public void go(CalendarModel m) {
    List<AEvent> event = m.printEventsForDate(this.date);

    if (event.isEmpty()) {
      System.out.println("No events found for " + this.date);
    } else {
      for (AEvent e : event) {
        System.out.println(e);
      }
    }

  }
}
