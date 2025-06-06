package control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.DayOfWeek;

import control.commands.CalendarCommand;
import control.commands.CreateEventSeries;
import control.commands.CreateSingleEvent;

/**
 * Factory for creating calendar commands to add events to the calendar.
 * This factory handles various types of event creation commands.
 */

final class CreateCommandFactory extends ACommandFactory {

  @Override
  public CalendarCommand createCalendarCommand(String input) throws IllegalArgumentException {

    try {
      String remaining = input.substring(13); // Remove "create event "
      SubjectAndRest result = extractSubject(remaining);
      String subject = result.subject;
      String commandStructure = result.remaining;

      String[] parts = commandStructure.trim().split("\\s+");

      return createCommandFromPattern(subject, parts);

    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid date/time format in command: " + input, e);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid number format in command: " + input, e);
    } catch (StringIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Invalid command format: " + input, e);
    }
  }

  private CalendarCommand createCommandFromPattern(String subject, String[] parts) {
    int length = parts.length;

    if (length == 0) {
      throw new IllegalArgumentException("Create command must have at least an event subject.");
    }

    // Single event creation (from/to keywords stripped by extractSubject)
    if (length == 3 && parts[1].equals("to")) {
      return createSingleEvent(subject, parts[0], parts[2]);
    }

    // N-event series creation (from/to keywords stripped)
    if (length == 8 && parts[1].equals("to")
            && parts[3].equals("repeats") && parts[5].equals("for") && parts[7].equals("times")) {
      return createNEventSeries(subject, parts[0], parts[2], parts[4], parts[6]);
    }

    // Events with end date creation (from/to keywords stripped)
    if (length == 7 && parts[1].equals("to")
            && parts[3].equals("repeats") && parts[5].equals("until")) {
      return createEventsWithEndDate(subject, parts[0], parts[2], parts[4], parts[6]);
    }

    // All day event creation (on keyword stripped by extractSubject)
    if (length == 1) {
      return createAllDayEvent(subject, parts[0]);
    }

    // All day events repeats N times (on keyword stripped)
    if (length == 6 && parts[1].equals("repeats")
            && parts[3].equals("for") && parts[5].equals("times")) {
      return createAllDayEvents(subject, parts[0], parts[2], parts[4]);
    }

    // All day events with end date (on keyword stripped)
    if (length == 5 && parts[1].equals("repeats")
            && parts[3].equals("until")) {
      return createAllDayEventsWithEndDate(subject, parts[0], parts[2], parts[4]);
    }

    throw new IllegalArgumentException("Invalid create command format. Expected one of: " +
            "'from DATETIME to DATETIME', " +
            "'on DATE', " +
            "'from DATETIME to DATETIME repeats DAYS for N times', " +
            "'from DATETIME to DATETIME repeats DAYS until DATE', " +
            "'on DATE repeats DAYS for N times', " +
            "'on DATE repeats DAYS until DATE'");
  }

  private CreateCommandBuilder builder() {
    return new CreateCommandBuilder();
  }


  private CalendarCommand createSingleEvent(String subject, String startDateTime, String endDateTime) {
    validDateTimeRange(startDateTime, endDateTime);

    return this.builder().subject(subject).startDateTime(startDateTime)
            .endDateTime(endDateTime).build();
  }

  //"create event <eventSubject> from <dateStringTtimeString> to <dateStringTtimeString> repeats
  // <weekdays> for <N> times"
  private CalendarCommand createNEventSeries(
          String subject, String startDateTime, String endDateTime,
          String weekdays, String occurrences) {

    validDateTimeRange(startDateTime, endDateTime);

    if (!validOccurrencesNumber(occurrences)) {
      throw new IllegalArgumentException("Invalid occurrences number: " + occurrences);
    }

    return this.builder().subject(subject).startDateTime(startDateTime)
            .endDateTime(endDateTime).daysOfWeek(weekdays).occurrences(occurrences).build();
  }

  private CalendarCommand createEventsWithEndDate(
          String subject, String startDateTime, String endDateTime,
          String weekdays, String endDate) {

    validDateTimeRange(startDateTime, endDateTime);

    if (!validDate(endDate)) {
      throw new IllegalArgumentException("Invalid end date provided: " + endDate);
    }

    return this.builder().subject(subject).startDateTime(startDateTime)
            .endDateTime(endDateTime).daysOfWeek(weekdays).endDate(endDate).build();
  }

  private CalendarCommand createAllDayEvents(
          String subject, String startDate, String weekdays, String occurrences) {
    if (!validDate(startDate)) {
      throw new IllegalArgumentException("Invalid start date provided: " + startDate);
    }

    if (!validOccurrencesNumber(occurrences)) {
      throw new IllegalArgumentException("Invalid occurrences number: " + occurrences);
    }

    return this.builder().subject(subject).startDate(startDate)
            .daysOfWeek(weekdays).occurrences(occurrences).build();
  }

  private CalendarCommand createAllDayEventsWithEndDate(
          String subject, String startDate, String weekdays, String endDate) {
    if (!validDate(startDate)) {
      throw new IllegalArgumentException("Invalid start date provided: " + startDate);
    }

    if (!validDate(endDate)) {
      throw new IllegalArgumentException("Invalid end date provided: " + endDate);
    }

    if (LocalDate.parse(endDate).isBefore(LocalDate.parse(startDate))) {
      throw new IllegalArgumentException("End date cannot be before start date");
    }

    return this.builder().subject(subject).startDate(startDate).endDate(endDate)
            .daysOfWeek(weekdays).build();
  }

  private CalendarCommand createAllDayEvent(String subject, String startDate) {
    if (!validDate(startDate)) {
      throw new IllegalArgumentException("Invalid start date provided: " + startDate);
    }

    return this.builder().subject(subject).startDate(startDate).build();
  }

  private static class CreateCommandBuilder {
    private String subject;
    private LocalDateTime startDateTime; // YYYY-MM-DD-HH-MM
    private LocalDateTime endDateTime; // YYYY-MM-DD-HH-MM
    private LocalDate startDate; // YYYY-MM-DD
    private LocalDate endDate; // YYYY-MM-DD
    private Integer occurrences; // repeats N times
    private DayOfWeek[] daysOfWeek;

    private CreateCommandBuilder() {
      this.subject = null;
      this.startDateTime = null;
      this.endDateTime = null;
      this.startDate = null;
      this.endDate = null;
      this.occurrences = null;
      this.daysOfWeek = null;
    }

    private CreateCommandBuilder subject(String subject) {
      this.subject = subject;
      return this;
    }

    private void dateTime(String dateTime, boolean isStartDate) {
      try {
        if (isStartDate) {
          this.startDateTime = LocalDateTime.parse(dateTime);
        } else {
          this.endDateTime = LocalDateTime.parse(dateTime);
        }
      } catch (DateTimeParseException e) {
        throw new IllegalArgumentException("Invalid date: " + dateTime);
      }
    }

    private CreateCommandBuilder startDateTime(String startDateTime) {
      this.dateTime(startDateTime, true);
      return this;
    }

    private CreateCommandBuilder endDateTime(String endDateTime) {
      this.dateTime(endDateTime, false);
      return this;
    }

    private void date(String date, boolean isStartDate) {
      try {
        if (isStartDate) {
          this.startDate = LocalDate.parse(date);
        } else {
          this.endDate = LocalDate.parse(date);
        }
      } catch (DateTimeParseException e) {
        throw new IllegalArgumentException("Invalid date: " + date);
      }
    }

    private CreateCommandBuilder startDate(String startDate) {
      this.date(startDate, true);
      return this;
    }

    private CreateCommandBuilder endDate(String endDate) {
      this.date(endDate, false);
      return this;
    }

    // NOTE: check with TAs if okay to do general exception with custom error message?
    private CreateCommandBuilder occurrences(String occurrences) {
      try {
        int n = Integer.parseInt(occurrences);

        if (n < 0) {
          throw new IllegalArgumentException("Invalid occurrences: " + occurrences);
        }

        this.occurrences = n;
      } catch (Exception e) {
        throw new IllegalArgumentException("Invalid occurrences: " + occurrences);
      }

      return this;
    }

    private CreateCommandBuilder daysOfWeek(String dayOfWeek) {
      String[] weekdays = dayOfWeek.split("");
      this.daysOfWeek = new DayOfWeek[dayOfWeek.length()];

      for (int i = 0; i < weekdays.length; i++) {
        this.daysOfWeek[i] = charToWeekday(weekdays[i].charAt(0));
      }

      return this;
    }

    private CalendarCommand build() {

      // create single all day event
      if (this.daysOfWeek == null && this.startDate != null &&
              this.startDateTime == null && this.endDateTime == null) {
        return new CreateSingleEvent(subject, startDate);
      }
      // error if no start or end dates provided
      if (this.daysOfWeek == null && (this.startDateTime == null || this.endDateTime == null)) {
        throw new IllegalArgumentException("No start and/or end date provided");
      }

      // create single event (single day or multiple days)
      if (this.daysOfWeek == null) {
        return new CreateSingleEvent(subject, startDateTime, endDateTime);
      }

      if (occurrences != null && startDate != null && endDate == null) {
        // create a series of all day events that repeat N times on specific weekdays
        return new CreateEventSeries(subject, daysOfWeek, occurrences, startDate);
      }
      // endDate == null
      if (occurrences != null && startDate == null && endDate == null) {
        // create event series on specific weekdays that repeats N times
        return new CreateEventSeries(subject, daysOfWeek, occurrences, startDateTime, endDateTime);

      }

      // create event series on specific weekdays until specific end date and time
      if (endDate != null && startDate == null) {
        return new CreateEventSeries(subject, daysOfWeek, startDateTime, endDateTime, endDate);
      }


      // create a series of all day events until a specific date (inclusive)
      if (startDate != null && endDate != null) {
        return new CreateEventSeries(subject, daysOfWeek, startDate, endDate);
      }

      throw new IllegalArgumentException("Invalid inputs to create event series.");
    }
  }
}
