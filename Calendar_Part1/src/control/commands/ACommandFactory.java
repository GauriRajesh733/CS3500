package control.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import model.EventProperty;

abstract public class ACommandFactory implements CommandFactory {

  @Override
  abstract public CalendarCommand createCalendarCommand(String input);

  protected int searchKeywordIndex(String input, String command) throws IllegalArgumentException {
    int index;
    try {
      if (command.equals(" ")) {
        index = input.indexOf(command);
      }
      else {
        index = input.lastIndexOf(command);
      }
    } catch (StringIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Calendar command missing keyword " + command);
    }
    return index;
  }

  protected String search(String input, int startIndex, int endIndex, String errorMessage) throws IllegalArgumentException {
    String keyword;
    try {
      keyword = input.substring(startIndex, endIndex);
    } catch (StringIndexOutOfBoundsException e) {
      throw new IllegalArgumentException(errorMessage);
    }

    return keyword;
  }

  protected boolean validDateTime(String input) throws IllegalArgumentException {
    try {
      LocalDateTime.parse(input);
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }

  protected boolean validDate(String input) throws IllegalArgumentException {
    try {
      LocalDate.parse(input);
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }

  protected boolean validNewProperty(String input, EventProperty propertyToEdit) {
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
