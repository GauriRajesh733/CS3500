package control;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import control.commands.CalendarCommand;
import model.EventProperty;

abstract public class ACommandFactory implements CommandFactory {

  @Override
  abstract public CalendarCommand createCalendarCommand(String input);

  protected int searchKeywordIndex(String input, String command) throws IllegalArgumentException {
    int index;
    try {
      index = input.lastIndexOf(command);

      if (command.equals(" ")) {
        index = input.indexOf(command);
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

  protected SubjectAndRest extractSubject(String remaining) {
    remaining = remaining.trim();

    if (remaining.isEmpty()) {
      throw new IllegalArgumentException("Event subject cannot be empty");
    }

    int structuralFromIndex = findStructuralKeyword(remaining, " from ", "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}");
    int structuralOnIndex = findStructuralKeyword(remaining, " on ", "\\d{4}-\\d{2}-\\d{2}");

    int firstKeywordIndex;
    String keyword;


    if (structuralFromIndex != -1 && structuralOnIndex != -1) {
      if (structuralFromIndex < structuralOnIndex) {
        firstKeywordIndex = structuralFromIndex;
        keyword = " from ";
      } else {
        firstKeywordIndex = structuralOnIndex;
        keyword = " on ";
      }
    } else if (structuralFromIndex != -1) {
      firstKeywordIndex = structuralFromIndex;
      keyword = " from ";
    } else if (structuralOnIndex != -1) {
      firstKeywordIndex = structuralOnIndex;
      keyword = " on ";
    } else {
      throw new IllegalArgumentException("Missing time specification (expected 'from DATETIME' or 'on DATE')");
    }

    String subject = remaining.substring(0, firstKeywordIndex).trim();
    if (subject.isEmpty()) {
      throw new IllegalArgumentException("Subject cannot be empty");
    }

    String commandStructure = remaining.substring(firstKeywordIndex + keyword.length()).trim();

    return new SubjectAndRest(subject, commandStructure);
  }

  private int findStructuralKeyword(String input, String keyword, String dateTimePattern) {
    int index = 0;
    while ((index = input.indexOf(keyword, index)) != -1) {
      int afterKeyword = index + keyword.length();
      if (afterKeyword < input.length()) {
        String remaining = input.substring(afterKeyword);
        String[] tokens = remaining.split("\\s+");
        if (tokens.length > 0 && tokens[0].matches(dateTimePattern)) {
          return index;
        }
      }
      index++;
    }
    return -1;
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

  protected void validDateTimeRange(String startDateTime, String endDateTime) throws IllegalArgumentException {
    if (!validDateTime(startDateTime)) {
      throw new IllegalArgumentException("Invalid start date and time: " + startDateTime);
    }
    if (!validDateTime(endDateTime)) {
      throw new IllegalArgumentException("Invalid end date and time: " + endDateTime);
    }
    if (!startDateIsBeforeEndDate(startDateTime, endDateTime)) {
      throw new IllegalArgumentException("Start date and time must be before end date and time");
    }
  }

  private boolean startDateIsBeforeEndDate(
          String startInput, String endInput) throws IllegalArgumentException {
    if (!validDateTime(startInput) || !validDateTime(endInput)) {
      return false;
    }
    LocalDateTime startDateTime = LocalDateTime.parse(startInput);
    LocalDateTime endDateTime = LocalDateTime.parse(endInput);
    return startDateTime.isBefore(endDateTime);
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

  protected boolean validOccurrencesNumber(String input) {
    try {
      int occurrences = Integer.parseInt(input);
      return occurrences > 0; // occurrences must be a positive integer
    } catch (NumberFormatException e) {
      return false; // not a valid integer
    }
  }

  protected static class SubjectAndRest {
    protected final String subject;
    protected final String remaining;

    SubjectAndRest(String subject, String remaining) {
      this.subject = subject;
      this.remaining = remaining.trim();
    }
  }

  protected DayOfWeek[] parseWeekdays(String weekdays) {
    if (weekdays == null || weekdays.isEmpty()) {
      throw new IllegalArgumentException("Weekdays cannot be empty");
    }

    DayOfWeek[] result = new DayOfWeek[weekdays.length()];
    for (int i = 0; i < weekdays.length(); i++) {
      result[i] = charToWeekday(weekdays.charAt(i));
    }
    return result;
  }

  protected static DayOfWeek charToWeekday(char c) {
    switch (c) {
      case 'M':
        return DayOfWeek.MONDAY;
      case 'T':
        return DayOfWeek.TUESDAY;
      case 'W':
        return DayOfWeek.WEDNESDAY;
      case 'R':
        return DayOfWeek.THURSDAY;
      case 'F':
        return DayOfWeek.FRIDAY;
      case 'S':
        return DayOfWeek.SATURDAY;
      case 'U':
        return DayOfWeek.SUNDAY;
      default:
        throw new IllegalArgumentException("Invalid weekday: " + c);
    }
  }
}
