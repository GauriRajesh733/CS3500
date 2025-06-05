package control.commands;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * This is a test class for the CreateCommandFactory.
 * It extends ACommandFactoryTest to test the specific functionality of CreateCommandFactory.
 */

public class CreateCommandFactoryTest extends ACommandFactoryTest {

  @Override
  public ACommandFactory makeFactory() {
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
            "create event Standup from 2025-06-04T09:00 to 2025-06-04T09:30 repeats M for 5 times");
    assertNotNull(cmd1);

    // Multiple weekdays, N times
    CalendarCommand cmd2 = commandFactory.createCalendarCommand(
            "create event Team Meeting from 2025-06-04T10:00 to 2025-06-04T11:00 repeats MW for 10 times");
    assertNotNull(cmd2);

    // All weekdays
    CalendarCommand cmd3 = commandFactory.createCalendarCommand(
            "create event Workout from 2025-06-04T07:00 to 2025-06-04T08:00 repeats MTWRFSU for 2 times");
    assertNotNull(cmd3);

    // Various weekday combinations
    CalendarCommand cmd4 = commandFactory.createCalendarCommand(
            "create event Class from 2025-06-04T14:00 to 2025-06-04T15:30 repeats TR for 15 times");
    assertNotNull(cmd4);
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
