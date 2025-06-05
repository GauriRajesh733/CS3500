package control.commands;

import java.time.LocalDate;

import model.CalendarModel;
import view.CalendarView;

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
  public void go(CalendarModel m, CalendarView v) {
    v.showEventsOnDate(m.printEventsForDate(this.date), this.date);
  }
}
