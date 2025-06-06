package control;

import control.commands.CalendarCommand;
/**
 * Interface for a factory that creates calendar commands based on user input.
 * This interface defines a method to create a CalendarCommand from a given input string.
 */
interface CommandFactory {
  /**
   * Creates a CalendarCommand based on the provided input string.
   *
   * @param input the user input string representing the command
   * @return a CalendarCommand instance corresponding to the input
   * @throws IllegalArgumentException if the input is invalid or cannot be parsed into a command
   */
  CalendarCommand createCalendarCommand(String input);
}
