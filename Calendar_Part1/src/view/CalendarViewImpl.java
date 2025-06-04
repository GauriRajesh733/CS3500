package view;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.AEvent;

public class CalendarViewImpl implements CalendarView {
  private final PrintStream out;

  public CalendarViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void showEventsOnDate(ArrayList<AEvent> events, LocalDate day) {
    if (events.isEmpty()) {
      out.println("No events on " + day);
      return;
    }

    out.println("Events on " + day + ":");
    for (AEvent event : events) {
      out.println(event);
    }
  }

  @Override
  public void showEventsInRange(ArrayList<AEvent> events, LocalDateTime start, LocalDateTime end) {
    if (events.isEmpty()) {
      out.println("No events from " + start + " to " + end + "");
      return;
    }

    out.println("Events from " + start + " to " + end + ":");

    for (AEvent event : events) {
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
    out.println(errorMessage);
    out.println("Try again");
  }
}
