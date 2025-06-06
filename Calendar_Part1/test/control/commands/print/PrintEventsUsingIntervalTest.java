package control.commands.print;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.DayOfWeek;

import model.CalendarModel;
import control.commands.PrintEventsUsingInterval;
import model.CalendarModelImpl;
import view.CalendarView;
import view.CalendarViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for PrintEventUsingInterval command.
 */

public class PrintEventsUsingIntervalTest {
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
  public void testPrintEventsConstructor() {
    LocalDateTime startDateTime =  LocalDateTime.of(2025, 5, 5, 10,0);
    LocalDateTime endDateTime =  LocalDateTime.of(2025, 5, 5, 15,0);
    PrintEventsUsingInterval command = new PrintEventsUsingInterval(startDateTime, endDateTime);
    assertNotNull(command);
  }

  @Test
  public void testPrintDaysWithNoEvents() {
    LocalDateTime startDateTime =  LocalDateTime.of(2025, 5, 5, 10,0);
    LocalDateTime endDateTime =  LocalDateTime.of(2025, 5, 5, 15,0);
    PrintEventsUsingInterval command = new PrintEventsUsingInterval(startDateTime, endDateTime);

    command.run(mockModel, mockView);

    String expectedOutput = "No events from 2025-05-05T10:00 to 2025-05-05T15:00"
            + System.lineSeparator();
    assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  public void testPrintDaysWithOneEvent() {
    LocalDateTime startDateTime =  LocalDateTime.of(2025, 5, 5, 5,0);
    LocalDateTime endDateTime =  LocalDateTime.of(2025, 5, 5, 15,0);

    mockModel.addSingleEvent(
            "Test Event", LocalDateTime.of(2025, 5, 5, 5, 5), LocalDateTime.of(2025, 5, 5, 5, 5));

    PrintEventsUsingInterval command = new PrintEventsUsingInterval(startDateTime, endDateTime);
    command.run(mockModel, mockView);

    String expectedOutput = "Events from 2025-05-05T05:00 to 2025-05-05T15:00:"
            + System.lineSeparator() +
            "- Test Event: 2025-05-05T05:05 to 2025-05-05T05:05" + System.lineSeparator();
    assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  public void testPrintDaysWithEventSeries() {
    LocalDateTime startDateTime = LocalDateTime.of(
            2025, 5, 5, 5, 0);
    LocalDateTime endDateTime = LocalDateTime.of(
            2025, 5, 19, 15, 0);

    DayOfWeek[] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY};

    mockModel.addSeriesEvent("Test Series Event", daysOfWeek, 10,
            LocalDateTime.of(2025, 5, 5, 5, 5),
            LocalDateTime.of(2025, 5, 5, 6, 0));

    PrintEventsUsingInterval command = new PrintEventsUsingInterval(startDateTime, endDateTime);
    command.run(mockModel, mockView);

    String expectedOutput = "Events from 2025-05-05T05:00 to 2025-05-19T15:00:" +
            System.lineSeparator() +
            "- Test Series Event: 2025-05-14T05:05 to 2025-05-14T06:00" +
            System.lineSeparator() +
            "- Test Series Event: 2025-05-12T05:05 to 2025-05-12T06:00" +
            System.lineSeparator() +
            "- Test Series Event: 2025-05-09T05:05 to 2025-05-09T06:00" +
            System.lineSeparator() +
            "- Test Series Event: 2025-05-07T05:05 to 2025-05-07T06:00" +
            System.lineSeparator() +
            "- Test Series Event: 2025-05-05T05:05 to 2025-05-05T06:00" +
            System.lineSeparator() +
            "- Test Series Event: 2025-05-19T05:05 to 2025-05-19T06:00" +
            System.lineSeparator() +
            "- Test Series Event: 2025-05-16T05:05 to 2025-05-16T06:00" +
            System.lineSeparator();

    assertEquals(expectedOutput, outputStream.toString());
  }
}
