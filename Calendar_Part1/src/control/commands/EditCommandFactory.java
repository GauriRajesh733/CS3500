package control.commands;


import control.CalendarCommand;
import control.CommandFactory;
import model.EventProperty;

public final class EditCommandFactory implements CommandFactory {
  EventProperty propertyToEdit;

  @Override
  public CalendarCommand createCalendarCommand(String input) {

    // Changes the property of the given event (irrespective of whether it is single or part of a series).
    if (input.contains("from") && input.contains("to") && input.contains("with")) {
      this.editSingleEvent(input);
    }

    //edit for change start date which means it will separate the series


    /**
     // edit property of given event (single or part of series), Searching for an event requires
     // specifying enough of its properties to uniquely identify it. If multiple events have the
     // same properties that were specified, editing should not be possible.
     //edit event <property> <eventSubject> from <dateStringTtimeString> to <dateStringTtimeString> with <NewPropertyValue>

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


  private CalendarCommand editSingleEvent(String input) {
    int fromIndex = searchKeywordIndex(input, "from", "");
    int toIndex = searchKeywordIndex(input, "to", "");
    int withIndex = searchKeywordIndex(input, "with", "");
    int eventsIndex = searchKeywordIndex(input, "events", "");

    // get property to edit
    int eventSubjectIndex = searchKeywordIndex(input.substring(11), " ", "event subject") + 12;
    String property = search(input, 11, eventSubjectIndex - 1, "Calendar command missing property");
    this.propertyToEdit = EventProperty.fromInput(property);

    // get subject to edit
    String eventSubject = searchEventSubject(input, eventSubjectIndex, fromIndex - 1);

    // get start date


    // get end date


    // get new property value









  }

  private int searchKeywordIndex(String input, String command, String errorMessage) {
    int index;
    try {
      index = input.indexOf(command);
    }
    catch (StringIndexOutOfBoundsException e){
      throw new IllegalArgumentException("Calendar edit command missing keyword " + command + errorMessage);
    }
    return index;
  }

  private String search(String input, int startIndex, int endIndex, String errorMessage) {
    String keyword;
    try {
      keyword = input.substring(startIndex, endIndex);
    }
    catch (StringIndexOutOfBoundsException e) {
      throw new IllegalArgumentException(errorMessage);
    }

    return keyword;
  }

  private String searchEventSubject(String input, int startIndex, int endIndex) {
    String subject;
    try {
      subject = input.substring(startIndex, endIndex);
    }
    catch (StringIndexOutOfBoundsException e){
      throw new IllegalArgumentException("No event subject provided");
    }
    return subject;
  }
}

