package control.commands;

import control.CalendarCommand;
import control.CommandFactory;

public class PrintCommandFactory implements CommandFactory {
  @Override
  public CalendarCommand createCalendarCommand(String input) {
    if (input.contains("on")) {
      return this.printEvents(input);
    } else if (input.contains("from") && input.contains("to")) {
      return this.printEventsWithInterval(input);
    } else {
      throw new IllegalArgumentException("Invalid print command" + input);
    }
  }

  private CalendarCommand printEvents(String input) {
    int onIndex = input.indexOf("on");

    String eventDate = input.substring(onIndex + 3);

    //get the lists of events along with their start time, end time, and location (if any)


  }

  private CalendarCommand printEventsWithInterval(String input) {
    int fromIndex = input.indexOf("from");
    int toIndex = input.indexOf("to");

    String eventStartDateTime = input.substring(fromIndex + 5, toIndex - 1);
    String eventEndDateTime = input.substring(toIndex + 3);

    //gets list of all events in the given interval including their start time and end time and location

    return null;
  }



}
