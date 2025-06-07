package view;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Test class for calendar view.
 */
public class CalendarViewImplTest {
  ByteArrayOutputStream stream;
  CalendarView view;

  private void resetStream() {
    this.stream = new ByteArrayOutputStream();
    this.view = new CalendarViewImpl(new PrintStream(this.stream));
  }

  @Before
  public void setUp() {
    this.stream = new ByteArrayOutputStream();
    this.view = new CalendarViewImpl(new PrintStream(this.stream));
  }

  // test if view displays events for a single day
  @Test
  public void showEventsOnDate() {
    ArrayList<String> singleDayEvents = new ArrayList<>();

    // empty list of dates
    view.showEventsOnDate(singleDayEvents, LocalDate.of(2025, 6, 2));
    assertEquals("No events on 2025-06-02" + System.lineSeparator(), this.stream.toString());

    // list of dates with 1 single event
    this.resetStream();
    singleDayEvents.add("- Gym: 2025-05-02T11:20 to 2025-05-02T12:20");
    view.showEventsOnDate(singleDayEvents, LocalDate.of(2025, 5, 2));
    assertEquals("Events on 2025-05-02:" + System.lineSeparator() +
            "- Gym: 2025-05-02T11:20 to 2025-05-02T12:20" +
            System.lineSeparator(), this.stream.toString());

    // list of dates with 1 single multiday event
    this.resetStream();
    singleDayEvents = new ArrayList<>();
    singleDayEvents.add("- Project: 2025-05-03T11:20 to 2025-05-04T11:20");
    view.showEventsOnDate(singleDayEvents, LocalDate.of(2025, 5, 3));
    assertEquals("Events on 2025-05-03:" + System.lineSeparator() +
            "- Project: 2025-05-03T11:20 to 2025-05-04T11:20" +
            System.lineSeparator(), this.stream.toString());

    // list of dates with 1 single series event
    this.resetStream();
    singleDayEvents = new ArrayList<>();
    singleDayEvents.add("- Class: 2025-05-04T11:20 to 2025-05-04T02:20");
    view.showEventsOnDate(singleDayEvents, LocalDate.of(2025, 5, 3));
    assertEquals("Events on 2025-05-03:" + System.lineSeparator() +
            "- Class: 2025-05-04T11:20 to 2025-05-04T02:20" +
            System.lineSeparator(), this.stream.toString());

    // list of dates with locations
    this.resetStream();
    singleDayEvents = new ArrayList<>();
    singleDayEvents.add("- Gym (physical): 2025-05-02T11:20 to 2025-05-02T12:20");
    singleDayEvents.add("- Project (online): 2025-05-03T11:20 to 2025-05-04T11:20");
    view.showEventsOnDate(singleDayEvents, LocalDate.of(2025, 5, 3));
    assertEquals("Events on 2025-05-03:" + System.lineSeparator() +
            "- Gym (physical): 2025-05-02T11:20 to 2025-05-02T12:20" + System.lineSeparator() +
            "- Project (online): 2025-05-03T11:20 to 2025-05-04T11:20" +
            System.lineSeparator(), stream.toString());
  }

  // test if view displays event in range
  @Test
  public void showEventsInRange() {
    // no events in range
    ArrayList<String> eventsInRange = new ArrayList<>();
    view.showEventsInRange(
            eventsInRange, LocalDateTime.of(2025, 5, 2, 0, 0), LocalDateTime.of(2025, 5, 4, 0, 0));
    assertEquals("No events from 2025-05-02T00:00 to 2025-05-04T00:00" +
            System.lineSeparator(), this.stream.toString());

    // multiple events without locations
    this.resetStream();
    eventsInRange.add("- Gym: 2025-05-02T11:20 to 2025-05-02T12:20");
    eventsInRange.add("- Project: 2025-05-03T11:20 to 2025-05-04T11:20");
    eventsInRange.add("- Class: 2025-05-04T11:20 to 2025-05-04T02:20");
    view.showEventsInRange(
            eventsInRange, LocalDateTime.of(2025, 5, 2, 0, 0), LocalDateTime.of(2025, 5, 4, 0, 0));
    assertEquals("Events from 2025-05-02T00:00 to 2025-05-04T00:00:" + System.lineSeparator()
            + "- Gym: 2025-05-02T11:20 to 2025-05-02T12:20" + System.lineSeparator()
            + "- Project: 2025-05-03T11:20 to 2025-05-04T11:20" + System.lineSeparator()
            + "- Class: 2025-05-04T11:20 to 2025-05-04T02:20" +
            System.lineSeparator(), this.stream.toString());

    // multiple events with locations
    this.resetStream();
    eventsInRange = new ArrayList<>();
    eventsInRange.add("- Gym (physical): 2025-05-02T11:20 to 2025-05-02T12:20");
    eventsInRange.add("- Project (online): 2025-05-03T11:20 to 2025-05-04T11:20");
    eventsInRange.add("- Class (physical): 2025-05-04T11:20 to 2025-05-04T02:20");
    view.showEventsInRange(
            eventsInRange, LocalDateTime.of(2025, 5, 2, 0, 0), LocalDateTime.of(2025, 5, 4, 0, 0));
    assertEquals("Events from 2025-05-02T00:00 to 2025-05-04T00:00:" + System.lineSeparator()
            + "- Gym (physical): 2025-05-02T11:20 to 2025-05-02T12:20" + System.lineSeparator()
            + "- Project (online): 2025-05-03T11:20 to 2025-05-04T11:20" + System.lineSeparator()
            + "- Class (physical): 2025-05-04T11:20 to 2025-05-04T02:20" +
            System.lineSeparator(), this.stream.toString());


  }

  // test if view displays status on date
  @Test
  public void showStatus() {
    // available status
    view.showStatus(false, LocalDateTime.of(2025, 5, 2, 0, 0));
    assertEquals("Available on 2025-05-02T00:00" + System.lineSeparator(), this.stream.toString());

    // busy status
    this.resetStream();
    view.showStatus(true, LocalDateTime.of(2025, 5, 2, 0, 0));
    assertEquals("Busy on 2025-05-02T00:00" + System.lineSeparator(), this.stream.toString());
  }

  // test if view displays error message
  @Test
  public void showErrorMessage() {
    // error message
    view.showErrorMessage("Something went wrong");
    assertEquals("Something went wrong" + System.lineSeparator()
            + "Please enter a new command" + System.lineSeparator(), this.stream.toString());
  }
}