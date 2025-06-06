package control;

import org.junit.Test;

import control.commands.CalendarCommand;

import static org.junit.Assert.fail;

public class EditCommandFactoryTest extends ACommandFactoryTest {

  @Override
  public ACommandFactory makeFactory() {
    return new EditCommandFactory();
  }

  // test invalid edit event command with missing input and one valid command with all inputs
  @Test
  public void testInvalidEditEventCommand() {
    CalendarCommand command;
    String invalidEditCommand1 = "edit event";
    String invalidEditCommand2 = "edit event location";
    String invalidEditCommand3 = "edit event location title";
    String invalidEditCommand4 = "edit event location title from";
    String invalidEditCommand5 = "edit event location title from 2025-06-04T21:19:00";
    String invalidEditCommand6 = "edit event location title from 2025-06-04T21:19:00 to";
    String invalidEditCommand7 = "edit event location title from 2025-06-04T21:19:00 " +
            "to 2025-06-04T21:19:00";
    String invalidEditCommand8 = "edit event location title from 2025-06-04T21:19:00 " +
            "to 2025-06-04T21:19:00 with";
    String validEditCommand = "edit event location title from 2025-06-04T21:19:00 " +
            "to 2025-06-04T21:19:00 with physical";

    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand1);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }

    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand2);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand3);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand4);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand5);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand6);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand7);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand8);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }

    try {
      command = this.makeFactory().createCalendarCommand(validEditCommand);
    } catch (IllegalArgumentException e) {
      fail("Should not have thrown");
    }
  }

  // test invalid edit events command with missing input and one valid command with all inputs
  @Test
  public void testInvalidEditEventsCommand() {
    CalendarCommand command;
    String invalidEditCommand1 = "edit events";
    String invalidEditCommand2 = "edit events location";
    String invalidEditCommand3 = "edit event location title";
    String invalidEditCommand4 = "edit event location title from";
    String invalidEditCommand5 = "edit event location title from 2025-06-04T21:19:00";
    String invalidEditCommand6 = "edit event location title from 2025-06-04T21:19:00 to";
    String invalidEditCommand7 = "edit event location title from 2025-06-04T21:19:00 with" +
            "invalid";
    String invalidEditCommand8 = "edit event location title from 2025-06-04T21:19:00 " +
            "to 2025-06-04T21:19:00 with";
    String validEditCommand = "edit event location title from 2025-06-04T21:19:00 " +
            "to 2025-06-04T21:19:00 with physical";

    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand1);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }

    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand2);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand3);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand4);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand5);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand6);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand7);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand8);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }

    try {
      command = this.makeFactory().createCalendarCommand(validEditCommand);
    } catch (IllegalArgumentException e) {
      fail("Should not have thrown");
    }
  }

  // test invalid edit series command with missing input and one valid command with all inputs
  @Test
  public void testInvalidEditSeriesCommand() {
    CalendarCommand command;
    String invalidEditCommand1 = "edit series";
    String invalidEditCommand2 = "edit series description";
    String invalidEditCommand3 = "edit series description title";
    String invalidEditCommand4 = "edit series description title from";
    String invalidEditCommand5 = "edit series description title from 2025-06-04T21:19:00";
    String invalidEditCommand6 = "edit series description title from 2025-06-04T21:19:00 to";
    String invalidEditCommand7 = "edit series description title from 2025-06-04T21:19:00 with";
    String validEditCommand = "edit series location title from 2025-06-04T21:19:00 " +
            "with new description";

    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand1);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand2);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand3);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand4);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand5);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand6);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(invalidEditCommand7);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    try {
      command = this.makeFactory().createCalendarCommand(validEditCommand);
    } catch (IllegalArgumentException e) {
      fail("Should not have thrown");
    }
  }
}