package control.commands.print;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

import control.commands.PrintEvents;
import model.CalendarModel;
import model.CalendarModelImpl;
import view.CalendarView;
import view.CalendarViewImpl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Test class for PrintEvents command.
 */

public class PrintEventsTest {
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
    LocalDate testDate = LocalDate.of(2025, 5, 5);
    PrintEvents command = new PrintEvents(testDate);
    assertNotNull(command);
  }

  @Test
  public void testPrintDaysWithNoEvents() {
    LocalDate testDate = LocalDate.of(2025, 5, 5);
    PrintEvents command = new PrintEvents(testDate);

    command.run(mockModel, mockView);

    String expectedOutput = "No events on 2025-05-05" + System.lineSeparator();
    assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  public void testPrintDaysWithEvents() {
    LocalDate testDate = LocalDate.of(2025, 5, 5);

    mockModel.addSingleEvent(
            "Test Event", LocalDateTime.of(2025, 5, 5, 5, 5), LocalDateTime.of(2025, 5, 5, 5, 5));

    PrintEvents command = new PrintEvents(testDate);
    command.run(mockModel, mockView);

    String expectedOutput = "Events on 2025-05-05:" + System.lineSeparator()
            + "- Test Event: 2025-05-05T05:05 to 2025-05-05T05:05" + System.lineSeparator();
    assertEquals(expectedOutput, outputStream.toString());

    mockModel.addSingleEvent(
            "Test Event2", LocalDateTime.of(2025, 5, 5, 5, 10),
            LocalDateTime.of(2025, 5, 5, 5, 15));

    command.run(mockModel, mockView);

    expectedOutput = "Events on 2025-05-05:" + System.lineSeparator()
            + "- Test Event: 2025-05-05T05:05 to 2025-05-05T05:05" + System.lineSeparator()
            + "Events on 2025-05-05:" + System.lineSeparator()
            + "- Test Event: 2025-05-05T05:05 to 2025-05-05T05:05" + System.lineSeparator()
            + "- Test Event2: 2025-05-05T05:10 to 2025-05-05T05:15" + System.lineSeparator();
    assertEquals(expectedOutput, outputStream.toString());
  }
}
