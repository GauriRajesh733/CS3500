package control.commands;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import control.CalendarCommand;

import static org.junit.Assert.assertThrows;

/**
 * Test class for CalendarCommandFactory.
 * This class tests the creation of calendar commands and handles invalid command inputs.
 */

public class CalendarCommandFactoryTest extends TestCase {

  private CalendarCommandFactory factory;
  ByteArrayOutputStream outputStream;
  ByteArrayOutputStream errorStream;
  PrintStream originalOut;
  PrintStream originalErr;

  private void resetStreams() {
    this.outputStream = new ByteArrayOutputStream();
    this.errorStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(this.outputStream));
    System.setErr(new PrintStream(this.errorStream));
  }

  @Before
  public void setUp() {
    this.originalOut = System.out;
    this.originalErr = System.err;

    this.outputStream = new ByteArrayOutputStream();
    this.errorStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(this.outputStream));
    System.setErr(new PrintStream(this.errorStream));

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
            "show" // incomplete
    };

    for (String command : invalidCommands) {
      try {
        factory.createCalendarCommand(command);
        fail("Should have thrown exception for command: '" + command + "'");
      } catch (IllegalArgumentException e) {
        assertTrue("Invalid calendar event command: " + command,
                e.getMessage().contains("Invalid calendar event command: " + command));
        assertTrue("Exception should include the invalid command",
                e.getMessage().contains(command));
      }
    }

    this.resetStreams();

    assertThrows(
            IllegalArgumentException.class, () ->
            {
              factory.createCalendarCommand("invalidCommand");
            });

    this.resetStreams();

    //null input
    assertThrows(
            NullPointerException.class, () ->

            {
              factory.createCalendarCommand(null);
            });

    this.resetStreams();

    //empty input
    assertThrows(IllegalArgumentException.class, () -> {
      factory.createCalendarCommand("");
    });

    this.resetStreams();
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
    //check if errorStream is empty
    assertEquals("", this.errorStream.toString());

    this.resetStreams();

    validCommand = "print events on 2025-06-03";
    command = factory.createCalendarCommand(validCommand);
    assertNotNull(command);
    assertEquals("", this.errorStream.toString());

    this.resetStreams();

    validCommand = "show status on 2025-06-03T10:00";
    command = factory.createCalendarCommand(validCommand);
    assertNotNull(command);
    assertEquals("", this.errorStream.toString());
  }

}