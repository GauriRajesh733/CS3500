package control.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Factory class to create print commands based on user input.
 */
public class PrintCommandFactory extends ACommandFactory {
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
    int onIndex = searchKeywordIndex(input, "on");

    String eventDate = search(
            input, onIndex + 3, input.length(),
            "Calendar Command printEvents input eventDate wrong indexing.");
    if (!validDate(eventDate)) {
      throw new IllegalArgumentException("Invalid date provided: " + eventDate);
    }

    LocalDate date = LocalDate.parse(eventDate);
    return new PrintEvents(date);
  }

  private CalendarCommand printEventsWithInterval(String input) {
    int fromIndex = searchKeywordIndex(input, "from");
    int toIndex = searchKeywordIndex(input, "to");

    String eventStartDateTime = search(
            input, fromIndex + 5, toIndex - 1,
            "Calendar Command printEventsWithIntervals input eventStartDateTime wrong indexing.");
    String eventEndDateTime =search(
            input, toIndex + 3, input.length(),
            "Calendar Command printEventsWithIntervals input eventEndDateTime wrong indexing.");

    if (!validDateTime(eventStartDateTime)) {
      throw new IllegalArgumentException(
              "Invalid date provided: " + eventStartDateTime);
    }

    if (!validDateTime(eventEndDateTime)) {
      throw new IllegalArgumentException(
              "Invalid date provided: " + eventEndDateTime);
    }

    LocalDateTime startDateTime = LocalDateTime.parse(eventStartDateTime);
    LocalDateTime endDateTime = LocalDateTime.parse(eventEndDateTime);

    if (startDateTime.isAfter(endDateTime)) {
      throw new IllegalArgumentException("Start date cannot be after end date");
    }

    return new PrintEventsUsingInterval(startDateTime, endDateTime);
  }



}
