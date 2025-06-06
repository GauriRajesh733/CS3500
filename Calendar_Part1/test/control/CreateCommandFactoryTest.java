package control;

import org.junit.Test;

import control.ACommandFactory;
import control.ACommandFactoryTest;
import control.commands.CalendarCommand;
import control.commands.CreateCommandFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

/**
 * This is a test class for the CreateCommandFactory.
 * It extends ACommandFactoryTest to test the specific functionality of CreateCommandFactory.
 */

public class CreateCommandFactoryTest extends ACommandFactoryTest {

  @Override
  protected ACommandFactory makeFactory() {
    return new CreateCommandFactory();
  }

  @Test
  public void testCreateCommand() {
    // Test to ensure that the CreateCommandFactory can create a valid command
    ACommandFactory factory = makeFactory();
    String input = "create event test on 2025-05-05";
    CalendarCommand cmd = factory.createCalendarCommand(input);

    assertNotNull(cmd); // Ensure command is created

    // Multi-word subject with quotes
    CalendarCommand cmd2 = commandFactory.createCalendarCommand(
            "create event \"Team Standup\" from 2025-06-04T09:00 to 2025-06-04T09:30");
    assertNotNull(cmd2);

    // Multi-day event
    CalendarCommand cmd3 = commandFactory.createCalendarCommand(
            "create event Conference from 2025-06-04T08:00 to 2025-06-06T17:00");
    assertNotNull(cmd3);
  }

  @Test
  public void testCreateEventSeriesWithTimes() {
    // Single weekday, N times
    CalendarCommand cmd1 = commandFactory.createCalendarCommand(
            "create event Standup from 2025-06-04T09:00 to 2025-06-04T09:30 repeats M for " +
                    "5 times");
    assertNotNull(cmd1);

    // Multiple weekdays, N times
    CalendarCommand cmd2 = commandFactory.createCalendarCommand(
            "create event Team Meeting from 2025-06-04T10:00 to 2025-06-04T11:00 repeats " +
                    "MW for 10 times");
    assertNotNull(cmd2);

    // All weekdays
    CalendarCommand cmd3 = commandFactory.createCalendarCommand(
            "create event Workout from 2025-06-04T07:00 to 2025-06-04T08:00 repeats " +
                    "MTWRFSU for 2 times");
    assertNotNull(cmd3);

    // Various weekday combinations
    CalendarCommand cmd4 = commandFactory.createCalendarCommand(
            "create event Class from 2025-06-04T14:00 to 2025-06-04T15:30 repeats " +
                    "TR for 15 times");
    assertNotNull(cmd4);
  }

  @Test
  public void testCreateEventSeriesUntilDate() {
    // Single weekday until a specific date
    CalendarCommand cmd1 = commandFactory.createCalendarCommand(
            "create event Standup from 2025-06-04T09:00 to 2025-06-04T09:30 repeats " +
                    "M until 2025-07-01");
    assertNotNull(cmd1);

    // Multiple weekdays until a specific date
    CalendarCommand cmd2 = commandFactory.createCalendarCommand(
            "create event Team Meeting from 2025-06-04T10:00 to 2025-06-04T11:00 repeats " +
                    "MW until 2025-07-01");
    assertNotNull(cmd2);

    // All weekdays until a specific date
    CalendarCommand cmd3 = commandFactory.createCalendarCommand(
            "create event Workout from 2025-06-04T07:00 to 2025-06-04T08:00 repeats " +
                    "MTWRFSU until 2025-07-01");
    assertNotNull(cmd3);
  }

  @Test
  public void testCreatSingleAllDayEvent() {
    // Single word subject
    CalendarCommand cmd1 = commandFactory.createCalendarCommand(
            "create event Holiday on 2025-07-04");
    assertNotNull(cmd1);

    // Multiple word subject
    CalendarCommand cmd2 = commandFactory.createCalendarCommand(
            "create event Summer Vacation Yay on 2025-07-04");
    assertNotNull(cmd2);

    // Multi-word subject with quotes
    CalendarCommand cmd3 = commandFactory.createCalendarCommand(
            "create event Independence Day on 2025-07-04");
    assertNotNull(cmd3);

    //using keyword 'on' for all-day event
    CalendarCommand cmd4 = commandFactory.createCalendarCommand(
            "create event Exam on Monday on 2025-07-04");
    assertNotNull(cmd4);
  }

  @Test
  public void testCreateAllDayEventSeriesForNTimes() {
    //Single weekday
    CalendarCommand cmd1 = commandFactory.createCalendarCommand(
            "create event All Day Event on 2025-07-04 repeats M for 8 times");
    assertNotNull(cmd1);

    //Multiple weekdays
    CalendarCommand cmd2 = commandFactory.createCalendarCommand(
            "create event Weekly Meeting on 2025-07-04 repeats MW for 6 times");
    assertNotNull(cmd2);

    //All weekdays
    CalendarCommand cmd3 = commandFactory.createCalendarCommand(
            "create event Team Retreat on 2025-07-04 repeats MTWRFSU for 4 times");
    assertNotNull(cmd3);
  }

  @Test
  public void testCreateAllDayEventSeriesUntilDate() {
    // Single weekday until a specific date
    CalendarCommand cmd1 = commandFactory.createCalendarCommand(
            "create event All Day Event on 2025-07-04 repeats M until 2025-08-01");
    assertNotNull(cmd1);

    // Multiple weekdays until a specific date
    CalendarCommand cmd2 = commandFactory.createCalendarCommand(
            "create event Weekly Meeting on 2025-07-04 repeats MW until 2025-08-01");
    assertNotNull(cmd2);

    // All weekdays until a specific date
    CalendarCommand cmd3 = commandFactory.createCalendarCommand(
            "create event Team Retreat on 2025-07-04 repeats MTWRFSU until 2025-08-01");
    assertNotNull(cmd3);
  }

  @Test
  public void testValidEdgeCases() {
    // Minimum repetition count
    CalendarCommand cmd1 = commandFactory.createCalendarCommand(
            "create event Test from 2025-06-04T10:00 to 2025-06-04T11:00 repeats M for 1 " +
                    "times");
    assertNotNull(cmd1);
  }

  @Test
  public void testInvalidCreateCommandSingleEvent() {
    // Invalid command formats for single event
    String[] invalidCommands = {
            "create event on 2025-05-05", // missing subject
            "create event test on 2025-13-05", // invalid month
            "create event test on 2025-05-32", // invalid day
            "create event test on 2025-05-05T25:00", // invalid hour
            "create event test on 2025-05-05T10:60" // invalid minute
    };

    for (String command : invalidCommands) {
      assertThrows("Should throw exception for: " + command,
              IllegalArgumentException.class,
              () -> commandFactory.createCalendarCommand(command));
    }
  }

  @Test
  public void testInvalidCreateCommandSeriesNTimes() {
    String[] invalidCommands = {
            "create event test from 2025-05-05 to 2025-05-06 repeats M for 0 times", // zero times
            "create event test from 2025-05-05 to 2025-05-06 repeats M for -1" +
                    " times", // negative times
            "create event test from 2025-05-05 to 2025-05-06 repeats M for 1", // missing 'times'
            "create event test from 2025-05-05 to 2025-05-06 repeats M", // missing repeat count
            "create event test from 2025-05-05 to 2025-05-06 repeats", // missing repeat type
            "create event test from 2025-05-05 to 2025-05-06", // missing repeats
            "create event test from 2025-05-05 to 2025-05-06 repeats M for 1 times " +
                    "extra", // extra text
            "create event test from 2025-05-05 to 2025-05-06 repeats M for 1 times until " +
                    "2025-04-01", // until date before start date
            "create event test from 2025-05-05 to 2025-04-06 repeats M for 1 times until " +
                    "2025-06-01", // to date before start date
    };

    for (String command : invalidCommands) {
      assertThrows("Should throw exception for: " + command,
              IllegalArgumentException.class,
              () -> commandFactory.createCalendarCommand(command));
    }
  }

  @Test
  public void testInvalidCreateCommandSeriesUntilDate() {
    String[] invalidCommands = {
            "create event test on 2025-05-05 repeats M until " +
                    "2025-04-01", // until date before start date
            "create event test on 2025-05-05 repeats M until 2025-06-01 for 1 " +
                    "times", // invalid format
            "create event test on 2025-05-05 repeats M until 2025-13-01", // invalid month
            "create event test on 2025-05-05 repeats M until 2025-05-32", // invalid day
            "create event test on 2025-05-05 repeats M until 2025-05-05T25:00", // invalid hour
            "create event test on 2025-05-05 repeats M until 2025-05-05T10:60", // invalid minute
            "create event test on 2025-05-05 repeats M until", // missing until date
            "create event test on 2025-05-05 repeats M", // missing until
            "create event test on 2025-05-05 repeats", // missing repeat type

    };

    for (String command : invalidCommands) {
      assertThrows("Should throw exception for: " + command,
              IllegalArgumentException.class,
              () -> commandFactory.createCalendarCommand(command));
    }
  }

  @Test
  public void testSingleAllDayEventInvalid() {
    // Invalid command formats for single all-day event
    String[] invalidCommands = {
            "create event on 2025-05-05", // missing subject
            "create event test on 2025-13-05", // invalid month
            "create event test on 2025-05-32", // invalid day
            "create event test on 2025-05-05T25:00", // invalid hour
            "create event test on 2025-05-05T10:60", // invalid minute
            "create", // incomplete command
            "creat event test" // incomplete command
    };

    for (String command : invalidCommands) {
      assertThrows("Should throw exception for: " + command,
              IllegalArgumentException.class,
              () -> commandFactory.createCalendarCommand(command));
    }
  }

  @Test
  public void testAllDayEventSeriesInvalidN() {
    // Invalid command formats for all-day event series
    String[] invalidCommands = {
            "create event on 2025-05-05 repeats M for 0 times", // zero times
            "create event on 2025-05-05 repeats M for -1 times", // negative times
            "create event on 2025-05-05 repeats M until 2025-04-01", // until date before start date
            "create event on 2025-05-05 repeats M until 2025-06-01 for 1 times", // invalid format
            "create event on 2025-05-05 repeats M until", // missing until date
            "create event on 2025-05-05 repeats", // missing repeat type
    };

    for (String command : invalidCommands) {
      assertThrows("Should throw exception for: " + command,
              IllegalArgumentException.class,
              () -> commandFactory.createCalendarCommand(command));
    }
  }

  @Test
  public void testAllDayEventSeriesInvalidUntilDate() {
    // Invalid command formats for all-day event series until date
    String[] invalidCommands = {
            "create event on 2025-05-05 repeats M until 2025-04-01", // until date before start date
            "create event on 2025-05-05 repeats M until 2025-13-01", // invalid month
            "create event on 2025-05-05 repeats M until 2025-05-32", // invalid day
            "create event on 2025-05-05 repeats M until 2025-05-05T25:00", // invalid hour
            "create event on 2025-05-05 repeats M until 2025-05-05T10:60", // invalid minute
            "create event on 2025-05-05 repeats M until", // missing until date
            "create event on 2025-05-05 repeats M", // missing until
            "create event on 2025-05-05 repeats", // missing repeat type
            "create event on 2025-05-05 repeats M until 2025-06-01 for 1 times extra" // extra text

    };

    for (String command : invalidCommands) {
      assertThrows("Should throw exception for: " + command,
              IllegalArgumentException.class,
              () -> commandFactory.createCalendarCommand(command));
    }
  }

  @Test
  public void testInvalidEdgeCases() {
    assertThrows(NullPointerException.class,
            () -> commandFactory.createCalendarCommand(null));

    assertThrows(IllegalArgumentException.class,
            () -> commandFactory.createCalendarCommand(
                    "create event Test from 2025-06-04T10:00 to 2025-06-04T11:00 " +
                            "repeats X for 5 times"));

    assertThrows(IllegalArgumentException.class,
            () -> commandFactory.createCalendarCommand(""));

    // Multiple keywords in command
    assertThrows(IllegalArgumentException.class, () -> {
      commandFactory.createCalendarCommand(
              "create event Future on 2030-01-01 from 2030-01-01T00:00 to 2030-01-02T00:00");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      commandFactory.createCalendarCommand(
              "create event Test on 2025-01-01 from 2025-01-01T10:00 to 2025-01-01T11:00 " +
                      "repeats M for 5 times");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      commandFactory.createCalendarCommand(
              "create event Test on 2025-01-01 from 2025-01-01T10:00 to 2025-01-01T11:00 " +
                      "repeats M until 2025-02-01");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      commandFactory.createCalendarCommand(
              "create event Test from 2025-01-01T10:00 to 2025-01-01T11:00 repeats M for 5 " +
                      "times until 2025-02-01")
      ;});

  assertThrows(IllegalArgumentException.class, () -> {
      commandFactory.createCalendarCommand(
              "create event Test on 2025-01-01 repeats M for 5 times until 2025-02-01");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      commandFactory.createCalendarCommand(
              "create event Test on 2025-01-01 repeats M for 5 times until 2025-02-01");});

  }

  @Test
  public void testKeywordsInSubjects() {
    // These should be VALID - keywords are part of subject
    String[] validCommands = {
            "create event Meeting on Monday from 2025-06-04T10:00 to 2025-06-04T11:00",
            "create event Conference from Boston on 2025-06-04",
            "create event Training for beginners on 2025-06-04",
            "create event Talk to parents from 2025-06-04T19:00 to 2025-06-04T20:00"
    };

    for (String command : validCommands) {
      CalendarCommand cmd = commandFactory.createCalendarCommand(command);
      assertNotNull("Should accept keyword in subject: " + command, cmd);
    }

    // These should be INVALID - actual structural conflicts
    String[] invalidCommands = {
            "create event Test on 2025-06-04 from 2025-06-04T10:00 to 2025-06-04T11:00",
            "create event Test from 2025-06-04T10:00 to 2025-06-04T11:00 repeats M for 5 times until 2025-08-01"
    };

    for (String command : invalidCommands) {
      assertThrows("Should reject structural conflicts: " + command,
              IllegalArgumentException.class,
              () -> commandFactory.createCalendarCommand(command));
    }
  }

}
