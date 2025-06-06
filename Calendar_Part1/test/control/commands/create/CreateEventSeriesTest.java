package control.commands.create;

import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import control.commands.CreateEventSeries;
import control.commands.PrintEvents;
import model.CalendarModel;
import model.CalendarModelImpl;
import view.CalendarView;
import view.CalendarViewImpl;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Test class for CreateEventSeries command.
 */

public class CreateEventSeriesTest {
  private ByteArrayOutputStream outputStream;
  private CalendarModel mockModel;
  private CalendarView mockView;

  @Before
  public void setUp() {
    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    mockModel = new CalendarModelImpl();
    mockView = new CalendarViewImpl(printStream);
  }

  @Test
  public void testCreateEventSeriesRepeatingNTimesOnSpecificWeekdays() {
    String subject = "Test Event";
    LocalDateTime startDateTime = LocalDateTime.of(
            2025, 5, 5, 10, 0);
    LocalDateTime endDateTime = LocalDateTime.of(
            2025, 5, 5, 11, 0);
    DayOfWeek[] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY};
    int occurrences = 10;

    CreateEventSeries command = new CreateEventSeries(
            subject, daysOfWeek, occurrences, startDateTime, endDateTime);

    command.run(mockModel, mockView);

    LocalDate[] expectedDates = {
            LocalDate.of(2025, 5, 5),   // Mon
            LocalDate.of(2025, 5, 7),   // Wed
            LocalDate.of(2025, 5, 9),   // Fri
            LocalDate.of(2025, 5, 12),  // Mon
            LocalDate.of(2025, 5, 14),  // Wed
            LocalDate.of(2025, 5, 16),   // Fri
            LocalDate.of(2025, 5, 19),  // Mon
            LocalDate.of(2025, 5, 21),  // Wed
            LocalDate.of(2025, 5, 23),   // Fri
            LocalDate.of(2025, 5, 26),  // Mon
    };

    for (LocalDate date : expectedDates) {
      ArrayList<String> events = mockModel.printEventsForDate(date);
      assertFalse("Should have event on " + date, events.isEmpty());
      assertTrue("Event should contain subject on " + date,
              events.get(0).contains(subject));
      assertTrue("Event should have correct time on " + date,
              events.get(0).contains("10:00") && events.get(0).contains("11:00"));
    }
  }

  @Test
  public void testCreateEventSeriesRepeatingNTimesUntilEndDate() {
    String subject = "Test Event";
    LocalDateTime startDateTime = LocalDateTime.of(
            2025, 5, 5, 10, 0);
    LocalDateTime endDateTime = LocalDateTime.of(
            2025, 5, 5, 11, 0);
    DayOfWeek[] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY};
    LocalDate endDate = LocalDate.of(2025, 5, 26); // last occurrence

    CreateEventSeries command = new CreateEventSeries(
            subject, daysOfWeek, startDateTime, endDateTime, endDate);

    command.run(mockModel, mockView);

    LocalDate[] expectedDates = {
            LocalDate.of(2025, 5, 5),   // Mon
            LocalDate.of(2025, 5, 7),   // Wed
            LocalDate.of(2025, 5, 9),   // Fri
            LocalDate.of(2025, 5, 12),  // Mon
            LocalDate.of(2025, 5, 14),  // Wed
            LocalDate.of(2025, 5, 16),   // Fri
            LocalDate.of(2025, 5, 19),  // Mon
            LocalDate.of(2025, 5, 21),  // Wed
            LocalDate.of(2025, 5, 23),   // Fri
            LocalDate.of(2025, 5, 26),  // Mon
    };

    for (LocalDate date : expectedDates) {
      ArrayList<String> events = mockModel.printEventsForDate(date);
      assertFalse("Should have event on " + date, events.isEmpty());
      assertTrue("Event should contain subject on " + date,
              events.get(0).contains(subject));
      assertTrue("Event should have correct time on " + date,
              events.get(0).contains("10:00") && events.get(0).contains("11:00"));
    }
  }

  @Test
  public void testCreateAllDayEventsSeriesRepeatingNTimesOnSpecificWeekdays() {
    String subject = "Test Event";
    LocalDate startDate = LocalDate.of(2025, 5, 5);
    DayOfWeek[] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY};
    int occurrences = 10;

    CreateEventSeries command = new CreateEventSeries(
            subject, daysOfWeek, occurrences, startDate);

    command.run(mockModel, mockView);

    LocalDate[] expectedDates = {
            LocalDate.of(2025, 5, 5),   // Mon
            LocalDate.of(2025, 5, 7),   // Wed
            LocalDate.of(2025, 5, 9),   // Fri
            LocalDate.of(2025, 5, 12),  // Mon
            LocalDate.of(2025, 5, 14),  // Wed
            LocalDate.of(2025, 5, 16),   // Fri
            LocalDate.of(2025, 5, 19),  // Mon
            LocalDate.of(2025, 5, 21),  // Wed
            LocalDate.of(2025, 5, 23),   // Fri
            LocalDate.of(2025, 5, 26),  // Mon
    };

    for (LocalDate date : expectedDates) {
      ArrayList<String> events = mockModel.printEventsForDate(date);
      assertFalse("Should have event on " + date, events.isEmpty());
      assertTrue("Event should contain subject on " + date,
              events.get(0).contains(subject));
      // Check for all-day event times (8:00 AM to 5:00 PM)
      assertTrue("Event should be all day (8:00-17:00) on " + date,
              events.get(0).contains("T08:00") && events.get(0).contains("T17:00"));
    }
  }

  @Test
  public void testCreateAllDayEventsSeriesUntilEndDate() {
    String subject = "Test Event";
    LocalDate startDate = LocalDate.of(2025, 5, 5);
    DayOfWeek[] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY};
    LocalDate endDate = LocalDate.of(2025, 5, 26); // last occurrence

    CreateEventSeries command = new CreateEventSeries(
            subject, daysOfWeek, startDate, endDate);

    command.run(mockModel, mockView);

    LocalDate[] expectedDates = {
            LocalDate.of(2025, 5, 5),   // Mon
            LocalDate.of(2025, 5, 7),   // Wed
            LocalDate.of(2025, 5, 9),   // Fri
            LocalDate.of(2025, 5, 12),  // Mon
            LocalDate.of(2025, 5, 14),  // Wed
            LocalDate.of(2025, 5, 16),   // Fri
            LocalDate.of(2025, 5, 19),  // Mon
            LocalDate.of(2025, 5, 21),  // Wed
            LocalDate.of(2025, 5, 23),   // Fri
            LocalDate.of(2025, 5, 26),  // Mon
    };

    for (LocalDate date : expectedDates) {
      ArrayList<String> events = mockModel.printEventsForDate(date);
      assertFalse("Should have event on " + date, events.isEmpty());
      assertTrue("Event should contain subject on " + date,
              events.get(0).contains(subject));
      // Check for all-day event times (8:00 AM to 5:00 PM)
      assertTrue("Event should be all day (8:00-17:00) on " + date,
              events.get(0).contains("T08:00") && events.get(0).contains("T17:00"));
    }
  }

  @Test
  public void invalidTests() {
    String subject = "Test Event";
    LocalDateTime startDateTime = LocalDateTime.of(
            2025, 5, 5, 10, 0);
    LocalDateTime endDateTime = LocalDateTime.of(
            2025, 5, 6, 11, 0);
    DayOfWeek[] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY};
    int occurrences = 10;

    assertThrows(IllegalArgumentException.class, () -> {
      new CreateEventSeries(
              subject, daysOfWeek, occurrences, startDateTime, endDateTime);
    });
  }

  @Test
  public void testVerifySeriesWithPrintCommand() {
    String subject = "Print";
    LocalDateTime startDateTime = LocalDateTime.of(2025, 8, 4, 16, 0); // Monday
    LocalDateTime endDateTime = LocalDateTime.of(2025, 8, 4, 17, 0);
    DayOfWeek[] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.FRIDAY};
    int occurrences = 4;

    CreateEventSeries command = new CreateEventSeries(
            subject, daysOfWeek, occurrences, startDateTime, endDateTime);
    command.run(mockModel, mockView);

    PrintEvents printCommand = new PrintEvents(LocalDate.of(2025, 8, 4)); // First Monday
    printCommand.run(mockModel, mockView);

    String output = outputStream.toString();
    assertTrue("Should show events on the date", output.contains("Events on 2025-08-04:"));
    assertTrue("Should contain the event subject", output.contains(subject));
    assertTrue("Should show correct times", output.contains("16:00") && output.contains("17:00"));
  }


}