package control;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import control.commands.CalendarCommand;
import model.CalendarModel;
import model.CalendarModelImpl;
import view.CalendarView;
import view.CalendarViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

/**
 * Test class for status commands.
 */
public class StatusCommandFactoryTest extends ACommandFactoryTest {

  private ByteArrayOutputStream outputStream;
  private CalendarModel mockModel;
  private CalendarView mockView;

  @Override
  public ACommandFactory makeFactory() {
    return new StatusCommandFactory();
  }

  @Before
  public void setUp() {
    super.setUp();
    outputStream = new ByteArrayOutputStream();
    mockModel = new CalendarModelImpl();
    mockView = new CalendarViewImpl(new PrintStream(outputStream));
  }

  @Test
  public void testCreateShowStatus() {
    String input = "show status on 2025-05-05T10:00";

    CalendarCommand command = commandFactory.createCalendarCommand(input);
    assertNotNull("Command should be created", command);
  }

  @Test
  public void testShowStatusDifferentTimes() {
    String[] validInputs = {
            "show status on 2025-01-01T00:00",
            "show status on 2025-12-31T23:59",
            "show status on 2025-06-15T12:30",
            "show status on 2030-02-28T14:45"
    };

    for (String input : validInputs) {
      CalendarCommand command = commandFactory.createCalendarCommand(input);
      assertNotNull("Should create command for: " + input, command);
    }
  }

  @Test
  public void testShowStatusBusyExecution() {
    LocalDateTime testDateTime = LocalDateTime.of(2025, 5, 5, 10, 0);

    String input = "show status on 2025-05-05T10:00";

    mockModel.addSingleEvent("Test Event", testDateTime, testDateTime.plusHours(1));

    CalendarCommand cmd = commandFactory.createCalendarCommand(input);
    cmd.run(mockModel, mockView);

    assertEquals("Busy on 2025-05-05T10:00" + System.lineSeparator(), outputStream.toString());
  }

  @Test
  public void testShowStatusAvailableExecution() {
    String input = "show status on 2025-05-05T10:00";

    CalendarCommand cmd = commandFactory.createCalendarCommand(input);
    cmd.run(mockModel, mockView);

    assertEquals("Available on 2025-05-05T10:00" + System.lineSeparator(), outputStream.toString());
  }

  @Test
  public void testInvalidCommands() {
    String[] invalidInputs = {
            "show",
            "show status",
            "show on 2025-05-05T10:00",
            "status on 2025-05-05T10:00",
            "show status on 2025-05-05",

    };

    for (String command : invalidInputs) {
      assertThrows("Should throw exception for: '" + command + "'",
              IllegalArgumentException.class,
              () -> commandFactory.createCalendarCommand(command));
    }
  }

  @Test
  public void testCaseSensitivity() {
    // Test if your commands are case-sensitive (they should be)
    String[] wrongCaseCommands = {
            "SHOW STATUS ON 2025-05-05T10:00",
            "Show Status On 2025-05-05T10:00"
    };

    for (String command : wrongCaseCommands) {
      assertThrows("Should be case-sensitive: " + command,
              IllegalArgumentException.class,
              () -> commandFactory.createCalendarCommand(command));
    }
  }

  @Test
  public void testValidateKeywordsMethod() {
    // Test that validateKeywords catches conflicts
    // (if your validateKeywords checks for keyword conflicts)

    String[] conflictingCommands = {
            "show status on 2025-05-05T10:00 from 2025-05-05T11:00", // mixing keywords
            // Add other conflicts based on your validateKeywords implementation
    };

    for (String command : conflictingCommands) {
      assertThrows("Should reject conflicting keywords: " + command,
              IllegalArgumentException.class,
              () -> commandFactory.createCalendarCommand(command));
    }
  }

  @Test
  public void testNullInput() {
    assertThrows("Should throw exception for null input",
            NullPointerException.class,
            () -> commandFactory.createCalendarCommand(null));
  }

}