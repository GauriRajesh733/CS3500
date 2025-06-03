package control.commands;


import control.CalendarCommand;
import control.CommandFactory;

public final class EditCommandFactory implements CommandFactory {

  @Override
  public CalendarCommand createCalendarCommand(String input) {

    /**
     // edit property of given event (single or part of series), Searching for an event requires
     // specifying enough of its properties to uniquely identify it. If multiple events have the
     // same properties that were specified, editing should not be possible.
     //edit event <property> <eventSubject> from <dateStringTtimeString> to <dateStringTtimeString> with <NewPropertyValue>
     if (input.contains("from") && input.contains("to") && input.contains("with")) {
     this.editSingleEvent(input);
     }
     //edit events <property> <eventSubject> from <dateStringTtimeString> with <NewPropertyValue>
     // edit events with given subject and if the event is in a series the events at the date
     // and after should be changed
     else if (input.contains("from") && input.contains("with")) {

     }

     //edit series <property> <eventSubject> from <dateStringTtimeString> with <NewPropertyValue>
     else if (true) {
     this.editSeries(input);
     }

     throw new IllegalArgumentException("Invalid edit event command: " + input);
     }

     private editSingleEvent(String input) {

     }

     private searchC**/
    return null;


  }
}

