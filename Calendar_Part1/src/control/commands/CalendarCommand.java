package control.commands;

import model.CalendarModel;
import view.CalendarView;

/**
 * Represents a command that can be executed on the calendar model and view.
 * This interface defines a method to run the command,
 * taking a CalendarModel and CalendarView as parameters.
 * Implementing classes should provide the specific logic for the command.
 */
public interface CalendarCommand {
  void run(CalendarModel m, CalendarView v);
}
