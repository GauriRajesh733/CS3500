package control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import control.commands.CalendarCommand;
import model.EventProperty;

abstract public class ACommandFactory implements CommandFactory {

  @Override
  abstract public CalendarCommand createCalendarCommand(String input);

  protected int searchKeywordIndex(String input, String command) throws IllegalArgumentException {
    int index;
    try {
      index = input.lastIndexOf(command);
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

  protected String searchDateTimeWithRegex(String input) {
    Pattern pattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2})");
    Matcher matcher = pattern.matcher(input);
    if (matcher.find()) {
      return matcher.group(0); // returns the first match
    } else {
      throw new IllegalArgumentException("Invalid date-time format in input: " + input);
    }
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

  protected boolean validStartAndEndTime(String startInput, String endInput) throws IllegalArgumentException {
    if (!validDateTime(startInput) || !validDateTime(endInput)) {
      return false;
    }
    LocalDateTime startDateTime = LocalDateTime.parse(startInput);
    LocalDateTime endDateTime = LocalDateTime.parse(endInput);
    return !startDateTime.isAfter(endDateTime); // start time must be before end time
  }

  protected void validateKeywords(String input) {
    if (input.contains("on") && input.contains("from")) {
      throw new IllegalArgumentException("Cannot mix 'on' and 'from/to' keywords: " + input);
    }
    if (input.contains("times") && input.contains("until")) {
      throw new IllegalArgumentException("Cannot mix 'for X times' and 'until date' keywords: " + input);
    }
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
