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
  LocalDate date;
  /**
   * Constructor for the PrintEvents command.
   *
   * @param date The date for which events should be printed.
   */
  public PrintEvents(LocalDate date) {
    this.date = date;
  }

  //no events found in the view
  @Override
  public void go(CalendarModel m) {
    m.printEventsForDate(this.date);
  }
}
