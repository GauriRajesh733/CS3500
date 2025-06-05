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
            && input.contains(" until ")) {
      return this.createEventsWithEndDate(input);
    }
    // create a series of all day events until a specific date (inclusive)
    else if (input.contains(" on ") && input.contains("repeats")
            && input.contains(" until ")) {
      return this.createAllDayEventsWithEndDate(input);
    }
    // create a series of all day events that repeat N times on specific weekdays
    else if (input.contains("on") && input.contains("repeats")
            && input.contains("for")) {
      return this.createAllDayEvents(input);
    }
    // create a single all day event
    else if (input.contains("on")) {
      return this.createAllDayEvent(input);
    }
    throw new IllegalArgumentException("Invalid create event command: " + input);
  }

  private CreateCommandBuilder builder() {
    return new CreateCommandBuilder();
  }

  private CalendarCommand createSingleEvent(String input) {
    int fromIndex = this.searchKeywordIndex(input, "from");
    int toIndex = this.searchKeywordIndex(input, "to");

    String eventSubject = this.search(input, 13, fromIndex - 1, "Calendar command missing event subject");
    String startDateTime = this.search(input, fromIndex + 5, toIndex - 1, "Calendar command missing start date");
    String endDateTime = this.search(input, toIndex + 3, input.length(), "Calendar command missing end date");

    return this.builder().subject(eventSubject).startDateTime(startDateTime)
            .endDateTime(endDateTime).build();
  }

  //"create event <eventSubject> from <dateStringTtimeString> to <dateStringTtimeString> repeats
  // <weekdays> for <N> times"
  private CalendarCommand createNEventSeries(String input) {
    int fromIndex = searchKeywordIndex(input, "from");
    int repeatIndex = searchKeywordIndex(input, "repeats");
    int toIndex = searchKeywordIndex(input, "to");
    int forIndex = searchKeywordIndex(input, "for");
    int timesIndex = searchKeywordIndex(input, "times");

    String eventSubject = this.search(input, 13, fromIndex - 1, "Calendar command missing event subject");
    String startDateTime = this.search(input, fromIndex + 5, toIndex - 1, "Calendar command missing start date");
    String endDateTime = this.search(input, toIndex + 3, repeatIndex - 1, "Calendar command missing end date");
    String weekDays = this.search(input, repeatIndex + 8, forIndex - 1, "Calendar command missing week days");
    String occurrences = this.search(input, forIndex + 4, timesIndex - 1, "Calendar command missing occurences");

    return this.builder().subject(eventSubject).startDateTime(startDateTime)
            .endDateTime(endDateTime).daysOfWeek(weekDays).occurrences(occurrences).build();
  }

  private CalendarCommand createEventsWithEndDate(String input) {
    int fromIndex = this.searchKeywordIndex(input, "from");
    int toIndex = this.searchKeywordIndex(input, "to");
    ;
    int repeatIndex = this.searchKeywordIndex(input, "repeats");
    ;
    int forIndex = this.searchKeywordIndex(input, "for");
    ;
    int untilIndex = this.searchKeywordIndex(input, "until");
    ;

    String eventSubject = this.search(input, 13, fromIndex - 1, "Calendar command missing event subject");
    String startDateTime = this.search(input, fromIndex + 5, toIndex - 1, "Calendar command missing start date");
    String endDateTime = this.search(input, toIndex + 3, repeatIndex - 1, "Calendar command missing end date");
    String weekDays = this.search(input, repeatIndex + 8, untilIndex - 1, "Calendar command missing week days");
    String endDate = this.search(input, untilIndex + 6, input.length(), "Calendar command missing end date");

    return this.builder().subject(eventSubject).startDateTime(startDateTime)
            .endDateTime(endDateTime).daysOfWeek(weekDays).endDate(endDate).build();
  }

  private CalendarCommand createAllDayEvents(String input) {
    int onIndex = this.searchKeywordIndex(input, "on");
    int repeatIndex = this.searchKeywordIndex(input, "repeats");
    int forIndex = this.searchKeywordIndex(input, "for");
    int timesIndex = this.searchKeywordIndex(input, "times");

    String eventSubject = this.search(input, 13, onIndex - 1, "Calendar command missing event subject");
    String startDate = this.search(input, onIndex + 3, repeatIndex - 1, "Calendar command missing start date");
    String weekdays = this.search(input, repeatIndex + 8, forIndex - 1, "Calendar command missing week days");
    String occurrences = this.search(input, forIndex + 4, timesIndex - 1, "Calendar command missing occurences");

    return this.builder().subject(eventSubject).startDate(startDate)
            .daysOfWeek(weekdays).occurrences(occurrences).build();
  }

  private CalendarCommand createAllDayEventsWithEndDate(String input) {
    int onIndex = this.searchKeywordIndex(input, "on");
    int repeatIndex = this.searchKeywordIndex(input, "repeats");
    int untilIndex = this.searchKeywordIndex(input, "until");

    String eventSubject = this.search(input, 13, onIndex - 1, "Calendar command missing event subject");
    String startDate = this.search(input, onIndex + 3, repeatIndex - 1, "Calendar command start date");
    String daysOfWeek = this.search(input, repeatIndex + 8, untilIndex - 1, "Calendar command missing week days");
    String endDate = this.search(input, untilIndex + 6, input.length(), "Calendar command missing end date");

    return this.builder().subject(eventSubject).startDate(startDate).endDate(endDate)
            .daysOfWeek(daysOfWeek).build();
  }

  private CalendarCommand createAllDayEvent(String input) {
    int onIndex = this.searchKeywordIndex(input, "on");

    String eventSubject = this.search(input, 13, onIndex - 1, "Calendar command missing event subject");
    String startDate = this.search(input, onIndex + 3, input.length(), "Calendar command missing start date");

    return this.builder().subject(eventSubject).startDate(startDate).build();
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
        }
        else {
          this.endDateTime = LocalDateTime.parse(dateTime);
        }
      }
      catch (DateTimeParseException e) {
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
        this.daysOfWeek[i] = toWeekday(weekdays[i]);
      }

      return this;
    }

    private static DayOfWeek toWeekday(String input) {
      switch(input) {
        case "M": return DayOfWeek.MONDAY;
        case "T": return DayOfWeek.TUESDAY;
        case "W": return DayOfWeek.WEDNESDAY;
        case "R": return DayOfWeek.THURSDAY;
        case "F": return DayOfWeek.FRIDAY;
        case "S": return DayOfWeek.SATURDAY;
        case "U": return DayOfWeek.SUNDAY;
        default:
          throw new IllegalArgumentException("Invalid weekday: " + input);
      }
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