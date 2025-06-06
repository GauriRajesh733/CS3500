package control;

import org.junit.Before;
import org.junit.Test;

import control.commands.CalendarCommand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for CalendarCommandFactory.
 * This class tests the creation of calendar commands and handles invalid command inputs.
 */

public class CalendarCommandFactoryTest {

  private CalendarCommandFactory factory;

  @Before
  public void setUp() {
    this.factory = new CalendarCommandFactory();
  }

  @Test
  public void testInvalidCommands() {

    String[] invalidCommands = {
            "delete event Meeting",
            "update event subject",
            "calendar show",
            "remove event",
            "CREATE EVENT Test", // wrong case
            "PRINT EVENTS ON 2025-06-03", // wrong case
            "EDIT EVENT Test", // wrong case
            "print event on date", // missing 's'
            "SHOW STATUS ON 2025-06-03", // wrong case
            "show state on time", // wrong keyword
            "create", // incomplete
            "print", // incomplete
            "show",// incomplete
    };

    for (String command : invalidCommands) {
      try {
        factory.createCalendarCommand(command);
        fail("Should have thrown exception for command: '" + command + "'");
      } catch (IllegalArgumentException e) {
        assertTrue("Invalid calendar event command: " + command,
                e.getMessage().contains("Invalid calendar event command: " + command));
      }
    }

    assertThrows(
            IllegalArgumentException.class, () -> {
              factory.createCalendarCommand("invalidCommand");
            });


    //null input
    assertThrows(
            IllegalArgumentException.class, () -> {
              factory.createCalendarCommand(null);
            });

    //empty input
    assertThrows(IllegalArgumentException.class, () -> {
      factory.createCalendarCommand("");
    });
  }

  @Test
  public void testInvalidCommandMessage() {
    String input = "invalid command here";

    try {
      factory.createCalendarCommand(input);
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid calendar event command: " + input, e.getMessage());
    }
  }

  @Test
  public void testValidCommandCreation() {
    // Test valid command creation
    String validCommand = "create event Test Event on 2025-06-03";
    CalendarCommand command = factory.createCalendarCommand(validCommand);
    assertNotNull(command);

    validCommand = "print events on 2025-06-03";
    command = factory.createCalendarCommand(validCommand);
    assertNotNull(command);

    validCommand = "show status on 2025-06-03T10:00";
    command = factory.createCalendarCommand(validCommand);
    assertNotNull(command);
  }
}