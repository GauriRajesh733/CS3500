package control.commands;


import control.ACommandFactory;
import control.CalendarCommand;

public final class CalendarCommandFactory extends ACommandFactory {

  public CalendarCommand createCalendarCommand(String input) {
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
      return new ShowCalendarStatus().createCalendarCommand(input);
    }
    // throw exception for invalid calendar event command
    throw new IllegalArgumentException("Invalid calendar event command: " + input);

  }
}