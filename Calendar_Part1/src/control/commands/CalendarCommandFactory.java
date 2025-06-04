package control.commands;


import control.CalendarCommand;
import control.CommandFactory;

public final class CalendarCommandFactory implements CommandFactory {

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
      return new ShowCalendarStatus(input);
    }
    // throw exception for invalid calendar event command
    else {
      throw new IllegalArgumentException("Invalid calendar event command: " + input);
    }
  }
}