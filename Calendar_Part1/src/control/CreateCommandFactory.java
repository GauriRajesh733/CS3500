package control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.DayOfWeek;

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
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid create event command: " + input, e);
    }
  }

  private CalendarCommand createCommandFromPattern(String subject, String[] parts) {
    int length = parts.length;

    if (length == 0) {
      throw new IllegalArgumentException("Create command must have at least an event subject.");
    }

    // Single event creation
    if (length == 4 && parts[0].equals("from") && parts[2].equals("to")) {
      return createSingleEvent(subject, parts[1], parts[3]);
    }

    // N-event series creation
    if (length == 9 && parts[0].equals("from") && parts[2].equals("to")
            && parts[4].equals("repeats") && parts[6].equals("for") && parts[8].equals("times")) {
      return createNEventSeries(subject, parts[1], parts[3], parts[5], parts[7]);
    }

    // Events with end date creation
    if (length == 8 && parts[0].equals("from") && parts[2].equals("to")
            && parts[4].equals("repeats") && parts[6].equals("until")) {
      return createEventsWithEndDate(subject, parts[1], parts[3], parts[5], parts[7]);
    }

    //All day event creation
    if (length == 2 && parts[0].equals("on")) {
      return createAllDayEvent(subject, parts[1]);
    }

    //All day events repeats N times
    if (length == 7 && parts[0].equals("on") && parts[2].equals("repeats")
            && parts[4].equals("for") && parts[6].equals("times")) {
      return createAllDayEvents(subject, parts[1], parts[3], parts[5]);
    }

    // All day events with end date
    if (length == 6 && parts[0].equals("on") && parts[2].equals("repeats")
            && parts[4].equals("until")) {
      return createAllDayEventsWithEndDate(subject, parts[1], parts[3], parts[5]);
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
