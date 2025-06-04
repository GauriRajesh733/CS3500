package control.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;

import control.ACommandFactory;
import control.CalendarCommand;
import control.CommandFactory;

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
    int onIndex = input.indexOf("on");

    String eventDate = input.substring(onIndex + 3);
    if (!validDateTime(eventDate)) {
      throw new IllegalArgumentException("Invalid date provided: " + eventDate);
    }

    LocalDate date = LocalDate.parse(eventDate);
    //get the lists of events along with their start time, end time, and location (if any)
    return new PrintEvents(date);
  }

  private CalendarCommand printEventsWithInterval(String input) {
    int fromIndex = input.indexOf("from");
    int toIndex = input.indexOf("to");

    String eventStartDateTime = input.substring(fromIndex + 5, toIndex - 1);
    String eventEndDateTime = input.substring(toIndex + 3);

    if (!validDateTime(eventStartDateTime) || !validDateTime(eventEndDateTime)) {
      throw new IllegalArgumentException(
              "Invalid date provided: " + eventStartDateTime + " or " + eventEndDateTime);
    }

    LocalDateTime startDateTime = LocalDateTime.parse(eventStartDateTime);
    LocalDateTime endDateTime = LocalDateTime.parse(eventEndDateTime);

    if (startDateTime.isAfter(endDateTime)) {
      throw new IllegalArgumentException("Start date cannot be after end date");
    }

    //gets list of all events in the given interval including their start time and end time and
    // location

    return new PrintEventsUsingInterval(startDateTime, endDateTime);
  }



}
