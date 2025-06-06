package control;


import control.commands.CalendarCommand;

final class CalendarCommandFactory {

  public CalendarCommand createCalendarCommand(String input) throws IllegalArgumentException {

    if (input == null || input.trim().isEmpty()) {
      throw new IllegalArgumentException("Invalid calendar event command: " + input);
    }

    // create CreateCalendarEvent command
    if (input.startsWith("create event")) {
      return new CreateCommandFactory().createCalendarCommand(input);
    }
    // create EditCalendarEvent command
    else if (input.startsWith("edit")) {
      return new EditCommandFactory().createCalendarCommand(input);
    }
    // create PrintCalendarEvent command
    else if (input.startsWith("print events")) {
      return new PrintCommandFactory().createCalendarCommand(input);
    }
    // create ShowStatus command
    else if (input.startsWith("show status")) {
      return new StatusCommandFactory().createCalendarCommand(input);
    }
    // throw exception for invalid calendar event command
    throw new IllegalArgumentException("Invalid calendar event command: " + input);

  }
}