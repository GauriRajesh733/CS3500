package control;

import java.util.Scanner;

import control.commands.Create;
import model.CalendarModel;
import model.CalendarModelImpl;

public class CalendarControllerImpl implements CalendarController {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    CalendarModel m = new CalendarModelImpl();
    while (s.hasNext()) {
      CalendarCommand cmd = null;
      String input = s.nextLine();


      // NOTE: fully abstract this to a calendar command factory that either creates a create, edit, etc. command
      // exit command
      if (input.equals("exit")) {
        return;
      }
      // create single event
      if (input.startsWith("create event")) {
        cmd = new Create(input);
      } else if (input.startsWith("edit event")) {
        // edit property of given event (single or part of series), Searching for an event requires
        // specifying enough of its properties to uniquely identify it. If multiple events have the
        // same properties that were specified, editing should not be possible.
        if (input.contains("from") && input.contains("to") && input.contains("with")) {
        }
        // edit events with given subject and if the event is in a series the events at the date
        // and after should be changed
        else if (input.contains("from") && input.contains("with")) {
        }
        throw new IllegalArgumentException("Invalid edit event command: " + input);
      }
      // edit all the events in the series
      else if (input.startsWith("edit series") && input.contains("from") && input.contains("with")) {

      } else if (input.startsWith("print events")) {
        // print bulleted list of events for given day
        if (input.contains("on")) {

        }
        // print bulleted list of events given start and end dates
        else if (input.contains("from")) {

        }
        throw new IllegalArgumentException("Invalid print event command: " + input);
      }
      // print busy status if user has events on given day and time, else available
      else if (input.startsWith("show status")) {

      }

      // error if unexpected command
      if (cmd == null) {
        throw new IllegalArgumentException("Invalid event command: " + input);
      }

      cmd.go(m);

    }
  }
}
