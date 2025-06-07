package control.commands.create;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import control.commands.CreateSingleEvent;
import control.commands.PrintEvents;
import model.CalendarModel;
import model.CalendarModelImpl;
import view.CalendarView;
import view.CalendarViewImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for CreateSingleEvent command.
 */

public class CreateSingleEventTest {
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
  public void testCreateSingleDayEvent() {
    String subject = "Test Event";
    LocalDateTime startDateTime = LocalDateTime.of(
            2025, 5, 1, 10, 0);
    LocalDateTime endDateTime = LocalDateTime.of(
            2025, 5, 1, 11, 0);

    CreateSingleEvent command = new CreateSingleEvent(subject, startDateTime, endDateTime);

    command.run(mockModel, mockView);

    ArrayList<String> events = mockModel.printEventsForDate(
            LocalDate.of(2025, 5, 1));

    assertFalse("Event should be added to calendar", events.isEmpty());

    assertTrue("Event should contain the subject",
            events.get(0).contains(subject));
    assertTrue("Event should contain start time",
            events.get(0).contains("2025-05-01T10:00"));
    assertTrue("Event should contain end time",
            events.get(0).contains("2025-05-01T11:00"));
  }

  @Test
  public void testCreateSingleAllDayEvent() {
    String subject = "Test event";
    LocalDate startDate = LocalDate.of(2025, 5, 2);

    CreateSingleEvent command = new CreateSingleEvent(subject, startDate);

    command.run(mockModel, mockView);

    ArrayList<String> events = mockModel.printEventsForDate(startDate);

    assertFalse("Event should be added to calendar", events.isEmpty());

    assertTrue("Event should contain the subject",
            events.get(0).contains(subject));
    assertTrue("All-day event should start at 08:00",
            events.get(0).contains("T08:00"));
    assertTrue("All-day event should end at 17:00",
            events.get(0).contains("T17:00"));
  }

  @Test
  public void testCreateMultiDayEvent() {
    String subject = "Multi Day Event";
    LocalDateTime startDateTime = LocalDateTime.of(
            2025, 5, 1, 14, 0);
    LocalDateTime endDateTime = LocalDateTime.of(
            2025, 5, 3, 16, 0);

    CreateSingleEvent command = new CreateSingleEvent(subject, startDateTime, endDateTime);
    command.run(mockModel, mockView);

    LocalDate[] testDates = {
            LocalDate.of(2025, 5, 1),
            LocalDate.of(2025, 5, 2),
            LocalDate.of(2025, 5, 3)
    };

    for (LocalDate date : testDates) {
      ArrayList<String> events = mockModel.printEventsForDate(date);
      assertFalse("Event should appear on " + date, events.isEmpty());
      assertTrue("Event should contain subject on " + date,
              events.get(0).contains(subject));
    }
  }

  @Test
  public void testCreateDuplicateEventThrowsException() {
    String subject = "Duplicate Event";
    LocalDateTime startDateTime = LocalDateTime.of(
            2025, 5, 1, 10, 0);
    LocalDateTime endDateTime = LocalDateTime.of(
            2025, 5, 1, 11, 0);

    CreateSingleEvent command1 = new CreateSingleEvent(subject, startDateTime, endDateTime);
    CreateSingleEvent command2 = new CreateSingleEvent(subject, startDateTime, endDateTime);

    command1.run(mockModel, mockView);

    try {
      command2.run(mockModel, mockView);
      fail("Should have thrown exception for duplicate event");
    } catch (IllegalArgumentException e) {
      assertTrue("Should mention duplicate event",
              e.getMessage().contains("already exists"));
    }
  }

  @Test
  public void testCreateEventAndVerifyWithPrint() {
    String subject = "Verification Event";
    LocalDate eventDate = LocalDate.of(2025, 5, 5);
    LocalDateTime startDateTime = LocalDateTime.of(
            2025, 5, 5, 9, 30);
    LocalDateTime endDateTime = LocalDateTime.of(
            2025, 5, 5, 10, 30);

    CreateSingleEvent createCommand = new CreateSingleEvent(subject, startDateTime, endDateTime);
    createCommand.run(mockModel, mockView);

    PrintEvents printCommand = new PrintEvents(eventDate);
    printCommand.run(mockModel, mockView);

    String output = outputStream.toString();
    assertTrue("Output should contain events header",
            output.contains("Events on 2025-05-05:"));
    assertTrue("Output should contain event subject",
            output.contains(subject));
    assertTrue("Output should contain start time",
            output.contains("09:30"));
    assertTrue("Output should contain end time",
            output.contains("10:30"));
  }
}