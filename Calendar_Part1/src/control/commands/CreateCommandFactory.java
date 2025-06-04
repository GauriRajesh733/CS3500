package control.commands;

import java.time.LocalDateTime;
import java.time.DayOfWeek;

import control.ACommandFactory;
import control.CalendarCommand;
import control.CommandFactory;

// NOTE: ADD TRY CATCH BLOCKS TO PRIVATE METHODS!
// ADD HELPER METHOD TO CHECK INDEX OF WORD AND THROW EXCEPTION THERE!  MAYBE TAKE IN ANY STRING
// ADD HELPER METHOD ALSO FOR SUBSTRINGS TAKE IN INDICES AND THROW EXCEPTION IF NEEDED
public final class CreateCommandFactory extends ACommandFactory {

  public CalendarCommand createCalendarCommand(String input) {
    // create single event
    if (input.contains("from") && !input.contains("repeats")) {
      return this.createSingleEvent(input);
    }
    // create event series on specific weekdays that repeats N times
    else if (input.contains("from") && input.contains("repeats")
            && input.contains("times")) {
      return this.createNEventSeries(input);
    }
    // create event series on specific weekdays until specific end date and time
    else if (input.contains("from") && input.contains("repeats")
            && input.contains("until")) {
      return this.createEventsWithEndDate(input);
    }
    // create a single all day event
    else if (input.contains("on")) {
      return this.createAllDayEvent(input);
    }
    // create a series of all day events that repeat N times on specific weekdays
    else if (input.contains("on") && input.contains("repeats")
            && input.contains("for")) {
      return this.createAllDayEvents(input);
    }
    // create a series of all day events until a specific date (inclusive)
    else if (input.contains("on") && input.contains("repeats")
            && input.contains("until")) {
      return this.createAllDayEventsWithEndDate(input);
    }
    throw new IllegalArgumentException("Invalid create event command: " + input);
  }

  private CreateCommandBuilder builder() {
    return new CreateCommandBuilder();
  }

  private CalendarCommand createSingleEvent(String input) {
    int fromIndex = input.indexOf("from");
    int toIndex = input.indexOf("to");
    // add try catch blocks to all!

    String eventSubject = input.substring(13, fromIndex - 1);
    String startDateTime = input.substring(fromIndex + 5, toIndex - 1);
    String endDateTime = input.substring(toIndex + 3);

    return this.builder().subject(eventSubject).startDateTime(startDateTime)
            .endDateTime(endDateTime).build();
  }

  //"create event <eventSubject> from <dateStringTtimeString> to <dateStringTtimeString> repeats
  // <weekdays> for <N> times"
  private CalendarCommand createNEventSeries(String input) {
    int fromIndex = input.indexOf("from");
    int repeatIndex = input.indexOf("repeats");
    int toIndex = input.indexOf("to");
    int forIndex = input.indexOf("for");
    int timesIndex = input.indexOf("times");

    String eventSubject = input.substring(13, fromIndex - 1);
    String startDateTime = input.substring(fromIndex + 5, toIndex - 1);
    String endDateTime = input.substring(toIndex + 3, repeatIndex - 1);
    String weekDays = input.substring(repeatIndex + 8, forIndex - 1);
    String occurrences = input.substring(forIndex + 4, timesIndex - 1);

    return this.builder().subject(eventSubject).startDateTime(startDateTime)
            .endDateTime(endDateTime).daysOfWeek(weekDays).occurrences(occurrences).build();
  }

  private CalendarCommand createEventsWithEndDate(String input) {
    int fromIndex = input.indexOf("from");
    int toIndex = input.indexOf("to");
    int repeatIndex = input.indexOf("repeats");
    int forIndex = input.indexOf("for");
    int untilIndex = input.indexOf("until");

    String eventSubject = input.substring(13, fromIndex - 1);
    String startDateTime = input.substring(fromIndex + 5, toIndex - 1);
    String endDateTime = input.substring(toIndex + 3, repeatIndex - 1);
    String weekDays = input.substring(repeatIndex + 8, forIndex - 1);
    String endDate = input.substring(untilIndex + 6);

    return this.builder().subject(eventSubject).startDateTime(startDateTime)
            .endDateTime(endDateTime).daysOfWeek(weekDays).endDate(endDate).build();
  }

  private CalendarCommand createAllDayEvents(String input) {
    int onIndex = input.indexOf("on");
    int repeatIndex = input.indexOf("repeats");
    int forIndex = input.indexOf("for");
    int timesIndex = input.indexOf("times");

    String eventSubject = input.substring(13, onIndex - 1);
    String startDate = input.substring(onIndex + 3, repeatIndex - 1);
    String weekdays = input.substring(repeatIndex + 8, forIndex - 1);
    String occurrences = input.substring(forIndex + 4, timesIndex - 1);

    return this.builder().subject(eventSubject).startDate(startDate)
            .daysOfWeek(weekdays).occurrences(occurrences).build();
  }

  private CalendarCommand createAllDayEventsWithEndDate(String input) {
    int onIndex = input.indexOf("on");
    int repeatIndex = input.indexOf("repeats");
    int untilIndex = input.indexOf("until");

    String eventSubject = input.substring(13, onIndex - 1);
    String startDate = input.substring(onIndex + 3, repeatIndex - 1);
    String daysOfWeek = input.substring(repeatIndex + 8, untilIndex - 1);
    String endDate = input.substring(untilIndex + 6);

    return this.builder().subject(eventSubject).startDate(startDate).endDate(endDate)
            .daysOfWeek(daysOfWeek).build();
  }

  private CalendarCommand createAllDayEvent(String input) {
    int onIndex = input.indexOf("on");

    String eventSubject = input.substring(13, onIndex - 1);
    String startDate = input.substring(onIndex + 2);

    return this.builder().subject(eventSubject).startDate(startDate).build();
  }

  private static class CreateCommandBuilder {
    private String subject;
    private LocalDateTime startDateTime; // YYYY-MM-DD-HH-MM
    private LocalDateTime endDateTime; // YYYY-MM-DD-HH-MM
    private LocalDateTime startDate; // YYYY-MM-DD
    private LocalDateTime endDate; // YYYY-MM-DD
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
        int year = Integer.parseInt(dateTime.substring(0, 4));
        int month = Integer.parseInt(dateTime.substring(5, 7));
        int day = Integer.parseInt(dateTime.substring(8, 10));
        int hour = Integer.parseInt(dateTime.substring(11, 13));
        int minute = Integer.parseInt(dateTime.substring(15));

        if (isStartDate) {
          this.startDateTime = LocalDateTime.of(year, month, day, hour, minute);
        } else {
          this.endDateTime = LocalDateTime.of(year, month, day, hour, minute);
        }
      }
      // ADD CASCADING EXCEPTIONS! (numberformat, illegalarg, etc.)
      catch (Exception e) {
        throw new IllegalArgumentException("Invalid start date: " + startDateTime);
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
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));

        if (isStartDate) {
          this.startDate = LocalDateTime.of(year, month, day, 8, 0);
        } else {
          this.endDate = LocalDateTime.of(year, month, day, 17, 0);
        }
      } catch (Exception e) {
        throw new IllegalArgumentException("Invalid start date: " + startDateTime);
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
        this.daysOfWeek[i] = DayOfWeek.valueOf(weekdays[i]);
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

      // create event series on specific weekdays that repeats N times
      if (occurrences != null && endDate == null) {
        return new CreateEventSeries(subject, daysOfWeek, occurrences, startDateTime, endDateTime);
      }

      // create event series on specific weekdays until specific end date and time
      if (endDate != null && startDate == null) {
        return new CreateEventSeries(subject, daysOfWeek, startDateTime, endDateTime, endDate);
      }

      // create a series of all day events that repeat N times on specific weekdays
      if (startDate != null && endDate == null) {
        return new CreateEventSeries(subject, daysOfWeek, occurrences, startDate);
      }

      // create a series of all day events until a specific date (inclusive)
      if (startDate != null) {
        return new CreateEventSeries(subject, daysOfWeek, startDate, endDate);
      }

      throw new IllegalArgumentException("Invalid inputs to create event series.");
    }
  }
}