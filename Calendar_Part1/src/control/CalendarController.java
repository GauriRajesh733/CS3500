package control;

import model.CalendarModel;
import view.CalendarView;

/**
 * CalendarController interface that defines the contract for controlling
 * the calendar application. It provides a method to run the controller
 * with a given model and view.
 * Implementing classes should handle the interaction between
 * the model and view, processing user input, and executing commands.
 * This is the main entry point for the calendar application.
 */
public interface CalendarController {
  /**
   * Runs the calendar controller with the specified model and view.
   * This method initializes the controller, sets up event listeners,
   * and starts the application loop to handle user interactions.
   *
   * @param m The CalendarModel instance representing the calendar data.
   * @param v The CalendarView instance for displaying the calendar UI.
   */
  void run(CalendarModel m, CalendarView v);
}
