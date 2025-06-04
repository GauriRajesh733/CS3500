package control.commands;


import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import control.CalendarCommand;
import control.CommandFactory;
import model.EventProperty;

public final class EditCommandFactory implements CommandFactory {

  @Override
  public CalendarCommand createCalendarCommand(String input) {

    // Change the property of the given event (irrespective of whether it is single or part of a series).
    if (input.contains("event") && input.contains("from") && input.contains("to") && input.contains("with")) {
      return this.editSingleEvent(input);
    }

    // Identify the event/portion of series based on the subject and start date and edit the given property
    if (input.contains("events") && input.contains("from") && input.contains("with")) {
      return this.editEvents(input);
    }

    // Identify the event/whole series based on the subject and start date and edit the given property
    else if (input.contains("series") && input.contains("from") && input.contains("with")) {
      return this.editSeries(input);
    }

    throw new IllegalArgumentException("Invalid edit event command: " + input);
  }

  private CalendarCommand editSeries(String input) {
    int fromIndex = searchKeywordIndex(input, "from");
    int withIndex = searchKeywordIndex(input, "with");

    // get property to edit
    int propertyToEditEndIndex = searchKeywordIndex(input.substring(12), " ");
    EventProperty propertyToEdit = EventProperty.fromInput(search(input, 12, propertyToEditEndIndex, "Calendar command missing property to edit"));

    // get event subject
    String subject = search(input, propertyToEditEndIndex + 1, fromIndex - 1, "Calendar command missing event subject");

    // get start date
    String startDateStr = search(input, fromIndex + 5, withIndex - 1, "Calendar command missing start date");
    if (validDateTime(startDateStr)) {
      throw new IllegalArgumentException("Invalid start date provided: " + startDateStr);
    }
    LocalDateTime startDate = LocalDateTime.parse(startDateStr);

    // get new property
    String newProperty = search(input, withIndex + 5, input.length() - 1, "Calendar command missing new property");

    return new EditSeries(propertyToEdit, subject, startDate, newProperty);
  }

  private CalendarCommand editEvents(String input) {
    int fromIndex = searchKeywordIndex(input, "from");
    int withIndex = searchKeywordIndex(input, "with");

    // get property to edit
    int propertyToEditEndIndex = searchKeywordIndex(input.substring(12), " ");
    EventProperty propertyToEdit = EventProperty.fromInput(search(input, 12, propertyToEditEndIndex, "Calendar command missing property to edit"));

    // get event subject
    String subject = search(input, propertyToEditEndIndex + 1, fromIndex - 1, "Calendar command missing event subject");

    // get start date
    String startDateStr = search(input, fromIndex + 5, withIndex - 1, "Calendar command missing start date");
    if (validDateTime(startDateStr)) {
      throw new IllegalArgumentException("Invalid start date provided: " + startDateStr);
    }
    LocalDateTime startDate = LocalDateTime.parse(startDateStr);

    // get new property
    String newProperty = search(input, withIndex + 5, input.length() - 1, "Calendar command missing new property");

    return new EditEvents(propertyToEdit, subject, startDate, newProperty);
  }


  private CalendarCommand editSingleEvent(String input) {
    int fromIndex = searchKeywordIndex(input, "from");
    int toIndex = searchKeywordIndex(input, "to");
    int withIndex = searchKeywordIndex(input, "with");

    // get property to edit
    int eventSubjectIndex = searchKeywordIndex(input.substring(11), " ") + 12;
    EventProperty propertyToEdit = EventProperty.fromInput(search(input, 11, eventSubjectIndex - 1, "Calendar command missing property"));
    // get subject to edit
    String subject = search(input, eventSubjectIndex, fromIndex - 1, "Calendar command missing event subject");
    // get start date and verify
    String startDateStr = search(input, fromIndex + 2, toIndex - 1, "Calendar command missing start date");
    if (validDateTime(startDateStr)) {
      throw new IllegalArgumentException("Invalid start date provided: " + startDateStr);
    }
    LocalDateTime startDate = LocalDateTime.parse(startDateStr);
    // get end date and verify
    String endDateStr = search(input, toIndex + 3, withIndex - 1, "Calendar command missing end date");
    if (validDateTime(endDateStr)) {
      throw new IllegalArgumentException("Invalid end date provided: " + endDateStr);
    }
    LocalDateTime endDate = LocalDateTime.parse(endDateStr);
    // get new property value and check if it matches the property to edit type
    String newProperty = search(input, withIndex + 5, input.length() - 1, "Calendar command missing new property value");
    if (!validNewProperty(newProperty, propertyToEdit)) {
      throw new IllegalArgumentException("Type of new property does not match property to edit");
    }

    return new EditSingleEvent(propertyToEdit, startDate, endDate, subject, newProperty);
  }

  private int searchKeywordIndex(String input, String command) throws IllegalArgumentException {
    int index;
    try {
      index = input.indexOf(command);
    } catch (StringIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Calendar command missing keyword " + command);
    }
    return index;
  }

  private String search(String input, int startIndex, int endIndex, String errorMessage) throws IllegalArgumentException {
    String keyword;
    try {
      keyword = input.substring(startIndex, endIndex);
    } catch (StringIndexOutOfBoundsException e) {
      throw new IllegalArgumentException(errorMessage);
    }

    return keyword;
  }

  private boolean validDateTime(String input) throws IllegalArgumentException {
    try {
      LocalDateTime.parse(input);
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }

  private boolean validNewProperty(String input, EventProperty propertyToEdit) {
    switch (propertyToEdit) {
      case SUBJECT:
      case DESCRIPTION:
        return true;
      case LOCATION:
        return input.equals("physical") || input.equals("online");
      case START:
      case END:
        return validDateTime(input);
      case STATUS:
        return input.equals("public") || input.equals("private");
      default:
        throw new IllegalArgumentException("Invalid property to edit for event");
    }
  }


}

