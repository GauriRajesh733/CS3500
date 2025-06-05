package control.commands.edit;

import org.junit.Before;
import org.junit.Test;

import control.CalendarCommand;
import control.CommandFactory;
import control.commands.EditCommandFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EditCommandFactoryTest {
  private CommandFactory editCommandFactory;

  @Before
  public void setup() {
    this.editCommandFactory = new EditCommandFactory();
  }

  // edit event
  @Test
  public void testCreateEditEventCommand() {

  }

  // edit events
  @Test
  public void testCreateEditEventsCommand() {

  }

  // edit series
  @Test
  public void testCreateEditSeriesCommand() {

  }

  // invalid edit command
  @Test
  public void testInvalidEditEventCommand() {
    CalendarCommand command;
    String invalidEditCommand1 = "edit event blah blah blah";
    String invalidEditCommand2 = "edit event location blah blah";
    String invalidEditCommand3 = "edit event location blah blah";
    String invalidEditCommand4 = "edit event location from blah";
    String invalidEditCommand5 = "edit event location from 2025-06-04T21:19:00";
    String invalidEditCommand6 = "edit event location from 2025-06-04T21:19:00 to";
    String invalidEditCommand7 = "edit event location from 2025-06-04T21:19:00 to 2025-06-04T21:19:00";
    String invalidEditCommand8 = "edit event location from 2025-06-04T21:19:00 to 2025-06-04T21:19:00 with";
    String invalidEditCommand9 = "edit event location from 2025-06-04T21:19:00 to 2025-06-04T21:19:00 with physical";

    // missing edit property
    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand1);
      fail("Should have thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Calendar command missing property to edit", e.getMessage());
    }

    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand2);
      fail("Should have thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Calendar command missing property to edit", e.getMessage());
    }

    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand3);
      fail("Should have thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Calendar command missing property to edit", e.getMessage());
    }

    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand4);
      fail("Should have thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Calendar command missing property to edit", e.getMessage());
    }

    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand5);
      fail("Should have thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Calendar command missing property to edit", e.getMessage());
    }

    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand6);
      fail("Should have thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Calendar command missing property to edit", e.getMessage());
    }

    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand7);
      fail("Should have thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Calendar command missing property to edit", e.getMessage());
    }

    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand8);
      fail("Should have thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Calendar command missing property to edit", e.getMessage());
    }

    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand9);
      fail("Should have thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Calendar command missing property to edit", e.getMessage());
    }


  }


  public void testInvalidEditEventsCommand() {

  }


  public void testInvalidEditSeriesCommand() {

  }
}