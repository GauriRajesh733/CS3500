package control;


import java.time.LocalDateTime;

import control.commands.CalendarCommand;
import control.commands.EditEvents;
import control.commands.EditSeries;
import control.commands.EditSingleEvent;
import model.EventProperty;

final class EditCommandFactory extends ACommandFactory {

  @Override
  public CalendarCommand createCalendarCommand(String input) {

    if (input.length() < 12) {
      throw new IllegalArgumentException("Edit calendar command missing inputs: " + input);
    }

    // Change the property of the given event (irrespective of whether it is single or part of a series).
    if (input.contains("event") && input.contains("from") && input.contains("to") && input.contains("with")) {
      return this.editSingleEvent(input);
    }

    int fromIndex = searchKeywordIndex(input, "from");
    int withIndex = searchKeywordIndex(input, "with");

    // get property to edit
    int propertyToEditEndIndex = searchKeywordIndex(input.substring(12), " ") + 12;
    EventProperty propertyToEdit = EventProperty.fromInput(search(input, 12, propertyToEditEndIndex, "Calendar command missing property to edit"));

    // get event subject
    String subject = search(input, propertyToEditEndIndex + 1, fromIndex - 1, "Calendar command missing event subject");

    // get start date
    String startDateStr = search(input, fromIndex + 5, withIndex - 1, "Calendar command missing start date");
    if (!validDateTime(startDateStr)) {
      throw new IllegalArgumentException("Invalid start date provided: " + startDateStr);
    }
    LocalDateTime startDate = LocalDateTime.parse(startDateStr);

    // get new property
    String newProperty = search(input, withIndex + 5, input.length(), "Calendar command missing new property");

    // Identify the event/portion of series based on the subject and start date and edit the given property
    if (input.contains("events") && input.contains("from") && input.contains("with")) {
      return new EditEvents(propertyToEdit, subject, startDate, newProperty);
    }

    // Identify the event/whole series based on the subject and start date and edit the given property
    else if (input.contains("series") && input.contains("from") && input.contains("with")) {
      return new EditSeries(propertyToEdit, subject, startDate, newProperty);

    }

    throw new IllegalArgumentException("Invalid edit event command: " + input);
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
    String startDateStr = search(input, fromIndex + 5, toIndex - 1, "Calendar command missing start date");
    if (!validDateTime(startDateStr)) {
      throw new IllegalArgumentException("Invalid start date provided: " + startDateStr);
    }
    LocalDateTime startDate = LocalDateTime.parse(startDateStr);
    // get end date and verify
    String endDateStr = search(input, toIndex + 3, withIndex - 1, "Calendar command missing end date");
    if (!validDateTime(endDateStr)) {
      throw new IllegalArgumentException("Invalid end date provided: " + endDateStr);
    }
    LocalDateTime endDate = LocalDateTime.parse(endDateStr);
    // get new property value and check if it matches the property to edit type
    String newProperty = search(input, withIndex + 5, input.length(), "Calendar command missing new property value");
    if (!validNewProperty(newProperty, propertyToEdit)) {
      throw new IllegalArgumentException("Type of new property does not match property to edit");
    }

    return new EditSingleEvent(propertyToEdit, startDate, endDate, subject, newProperty);
  }
}

