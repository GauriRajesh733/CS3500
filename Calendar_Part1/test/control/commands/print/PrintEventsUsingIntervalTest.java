package control.commands.print;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import control.commands.PrintEvents;
import model.CalendarModel;
import model.CalendarModelImpl;
import view.CalendarView;
import view.CalendarViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PrintEventsUsingIntervalTest {
  private ByteArrayOutputStream outputStream;
  private PrintStream printStream;
  private CalendarModel mockModel;
  private CalendarView mockView;

  @Before
  public void setUp() {
    outputStream = new ByteArrayOutputStream();
    printStream = new PrintStream(outputStream);
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
  public void testPrintEventsModelAndView() {
    LocalDate testDate = LocalDate.of(2025, 5, 5);
    PrintEvents command = new PrintEvents(testDate);

    command.go(mockModel, mockView);

    assertNotNull(mockModel.printEventsForDate(testDate));
    assertNotNull(mockView);

    // Check that the output stream has been populated
    String output = outputStream.toString();
    assertEquals("No events on 2025-05-05" + System.lineSeparator(), output);
  }
}