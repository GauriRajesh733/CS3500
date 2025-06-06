package view;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CalendarViewImpl implements CalendarView {
  private final PrintStream out;

  public CalendarViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void showEventsOnDate(ArrayList<String> events, LocalDate day) {
    if (events.isEmpty()) {
      out.println("No events on " + day);
      return;
    }

    out.println("Events on " + day + ":");
    for (String event : events) {
      out.println(event);
    }
  }

  @Override
  public void showEventsInRange(ArrayList<String> events, LocalDateTime start, LocalDateTime end) {
    if (events.isEmpty()) {
      out.println("No events from " + start + " to " + end + "");
      return;
    }

    out.println("Events from " + start + " to " + end + ":");

    for (String event : events) {
      out.println(event);
    }
  }

  @Override
  public void showStatus(boolean status, LocalDateTime dateTime) {
    if (status) {
      out.println("Busy on " + dateTime);
      return;
    }
    out.println("Available on " + dateTime);
  }

  @Override
  public void showErrorMessage(String errorMessage) {
    if (errorMessage == null) {
      out.println("Something went wrong, please enter a new command");
      return;
    }
    out.println(errorMessage);
    if (!errorMessage.equals("File input must end with exit command")) {
      out.println("Please enter a new command");
    }
  }
}
