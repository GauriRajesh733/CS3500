package control.commands;

import control.CalendarCommand;
import model.CalendarModel;
import model.Date;
import model.DayOfWeek;

class CreateCalendarCommand implements CalendarCommand {
  CalendarCommand calendarCommand;

  private CreateCalendarCommand(String input) {
    if (input.contains("from") && !input.contains("repeats")) {
      this.createSingleEvent(input);
    }
    // create event series on specific weekdays that repeats N times
    else if (input.contains("from") && input.contains("repeats")
            && input.contains("times")) {
      this.createNEventSeries(input);
    }
    // create event series on specific weekdays until specific end date and time
    else if (input.contains("from") && input.contains("repeats")
            && input.contains("until")) {
      this.createEventsWithEndDate(input);
    }
    // create a single all day event
    else if (input.contains("on")) {
      this.createAllDayEvent(input);
    }
    // create a series of all day events that repeat N times on specific weekdays
    else if (input.contains("on") && input.contains("repeats")
            && input.contains("for")) {
      this.createAllDayEvents(input);
    }
    // create a series of all day events until a specific date (inclusive)
    else if (input.contains("on") && input.contains("repeats")
            && input.contains("until")) {
      this.createAllDayEventsWithEndDate(input);
    }
    throw new IllegalArgumentException("Invalid create event command: " + input);
  }

  private CreateCommandBuilder builder() {
    return new CreateCommandBuilder();
  }

  @Override
  public void go(CalendarModel m) {
    this.calendarCommand.go(m);
  }

  private void createSingleEvent(String input) {
    int fromIndex = input.indexOf("from");
    int toIndex = input.indexOf("to");
 // add try catch blocks to all!

    String eventSubject = input.substring(13, fromIndex - 1);
    String startDateTime = input.substring(fromIndex + 5, toIndex - 1);
    String endDateTime = input.substring(toIndex + 3);

    this.calendarCommand = this.builder().subject(eventSubject).startDateTime(startDateTime)
            .endDateTime(endDateTime).build();
  }

  //"create event <eventSubject> from <dateStringTtimeString> to <dateStringTtimeString> repeats
  // <weekdays> for <N> times"
  private void createNEventSeries(String input) {
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

    this.calendarCommand = this.builder().subject(eventSubject).startDateTime(startDateTime)
            .endDateTime(endDateTime).daysOfWeek(weekDays).occurrences(occurrences).build();
  }

  private void createEventsWithEndDate(String input) {
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

    this.calendarCommand = this.builder().subject(eventSubject).startDateTime(startDateTime)
            .endDateTime(endDateTime).daysOfWeek(weekDays).endDate(endDate).build();
  }

  private void createAllDayEvents(String input) {
    int onIndex = input.indexOf("on");
    int repeatIndex = input.indexOf("repeats");
    int forIndex = input.indexOf("for");
    int timesIndex = input.indexOf("times");

    String eventSubject = input.substring(13, onIndex - 1);
    String startDate = input.substring(onIndex + 3, repeatIndex - 1);
    String weekdays = input.substring(repeatIndex + 8, forIndex - 1);
    String occurrences = input.substring(forIndex + 4, timesIndex - 1);

    this.calendarCommand = this.builder().subject(eventSubject).startDate(startDate)
            .daysOfWeek(weekdays).occurrences(occurrences).build();
  }

  private void createAllDayEventsWithEndDate(String input) {
    int onIndex = input.indexOf("on");
    int repeatIndex = input.indexOf("repeats");
    int untilIndex = input.indexOf("until");

    String eventSubject = input.substring(13, onIndex - 1);
    String startDate = input.substring(onIndex + 3, repeatIndex - 1);
    String daysOfWeek = input.substring(repeatIndex + 8, untilIndex - 1);
    String endDate = input.substring(untilIndex + 6);

    this.calendarCommand = this.builder().subject(eventSubject).startDate(startDate).endDate(endDate)
            .daysOfWeek(daysOfWeek).build();
  }

  private void createAllDayEvent(String input) {
    int onIndex = input.indexOf("on");

    String eventSubject = input.substring(13, onIndex - 1);
    String startDate = input.substring(onIndex + 2);

    this.calendarCommand = this.builder().subject(eventSubject).startDate(startDate).build();
  }
// make private?
  private static class CreateCommandBuilder {
    private String subject;
    private Date startDateTime; // YYYY-MM-DD-HH-MM
    private Date endDateTime; // YYYY-MM-DD-HH-MM
    private Date startDate; // YYYY-MM-DD
    private Date endDate; // YYYY-MM-DD
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
          this.startDateTime = new Date(year, month, day, hour, minute);
        } else {
          this.endDateTime = new Date(year, month, day, hour, minute);
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
          this.startDate = new Date(year, month, day, null, null);
        } else {
          this.endDate = new Date(year, month, day, null, null);
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
      if (this.occurrences == null && this.daysOfWeek == null) {
        return new CreateSingleEvent(subject, startDateTime, endDateTime, startDate);
      }
      return new CreateEventSeries(
              subject, startDateTime, endDateTime, startDate, endDate, occurrences, daysOfWeek);
    }
  }
}