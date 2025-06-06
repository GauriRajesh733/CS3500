package control;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import control.commands.CalendarCommand;
import model.CalendarModel;
import model.CalendarModelImpl;
import view.CalendarView;
import view.CalendarViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EditCalendarTest {
  private ByteArrayOutputStream stream;
  private CommandFactory editCommandFactory;
  private CalendarModel model;
  private CalendarView view;

  @Before
  public void setup() {
    this.stream = new ByteArrayOutputStream();
    this.editCommandFactory = new EditCommandFactory();
    this.model = new CalendarModelImpl();
    this.view = new CalendarViewImpl(new PrintStream(stream));
  }

  private void resetStream() {
    this.stream = new ByteArrayOutputStream();
    this.view = new CalendarViewImpl(new PrintStream(stream));
  }

  // SINGLE EVENT TESTS
  // NOTE: all edit commands produce same outputs for single events

  // edit event
  @Test
  public void testSingleEventEditEventCommand() {
    // add event to calendar
    String input1 = "create event title from 2025-06-04T21:19 to 2025-06-04T21:19";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.go(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events on 2025-06-04";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.go(this.model, this.view);
    assertEquals("Events on 2025-06-04:" + System.lineSeparator()
                    + "- title: 2025-06-04T21:19 to 2025-06-04T21:19" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    // edit event command
    String input3 = "edit event location title from 2025-06-04T21:19:00 to 2025-06-04T21:19:00 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.go(this.model, this.view);

    // verify event edited in calendar
    this.resetStream();
    String input4 = "print events on 2025-06-04";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.go(this.model, this.view);
    assertEquals("Events on 2025-06-04:" + System.lineSeparator()
                    + "- title (physical): 2025-06-04T21:19 to 2025-06-04T21:19" + System.lineSeparator(),
            this.stream.toString());
  }

  // edit events
  @Test
  public void testSingleEventEditEventsCommand() {
    // add event to calendar
    String input1 = "create event title from 2025-06-04T21:19 to 2025-06-04T21:19";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.go(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events on 2025-06-04";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.go(this.model, this.view);
    assertEquals("Events on 2025-06-04:" + System.lineSeparator()
                    + "- title: 2025-06-04T21:19 to 2025-06-04T21:19" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    // edit events command
    String input3 = "edit events location title from 2025-06-04T21:19 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.go(this.model, this.view);

    // verify event edited in calendar
    this.resetStream();
    String input4 = "print events on 2025-06-04";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.go(this.model, this.view);
    assertEquals("Events on 2025-06-04:" + System.lineSeparator()
                    + "- title (physical): 2025-06-04T21:19 to 2025-06-04T21:19" + System.lineSeparator(),
            this.stream.toString());
  }

  // edit series
  @Test
  public void testSingleEventEditSeriesCommand() {
    // add event to calendar
    String input1 = "create event title from 2025-06-04T21:19 to 2025-06-04T21:19";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.go(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events on 2025-06-04";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.go(this.model, this.view);
    assertEquals("Events on 2025-06-04:" + System.lineSeparator()
                    + "- title: 2025-06-04T21:19 to 2025-06-04T21:19" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    // edit series command
    String input3 = "edit series location title from 2025-06-04T21:19 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.go(this.model, this.view);

    // verify event edited in calendar
    this.resetStream();
    String input4 = "print events on 2025-06-04";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.go(this.model, this.view);
    assertEquals("Events on 2025-06-04:" + System.lineSeparator()
                    + "- title (physical): 2025-06-04T21:19 to 2025-06-04T21:19" + System.lineSeparator(),
            this.stream.toString());
  }

  // MULTIDAY SINGLE EVENT TESTS
  // NOTE: all edit commands produce same outputs for single events, even for multiday events

  // edit event
  @Test
  public void testMultidayEventEditEventCommand() {
    // add event to calendar
    String input1 = "create event title from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.go(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.go(this.model, this.view);
    assertEquals("Events from 2025-06-04T21:19 to 2025-06-07T21:19:" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    // edit event command
    String input3 = "edit event location title from 2025-06-04T21:19 to 2025-06-07T21:19 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.go(this.model, this.view);

    // verify event edited in calendar
    this.resetStream();
    String input4 = "print events from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.go(this.model, this.view);
    assertEquals("Events from 2025-06-04T21:19 to 2025-06-07T21:19:" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator(),
            this.stream.toString());
  }

  // edit events
  @Test
  public void testMultidayEventEditEventsCommand() {
    // add event to calendar
    String input1 = "create event title from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.go(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.go(this.model, this.view);
    assertEquals("Events from 2025-06-04T21:19 to 2025-06-07T21:19:" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    // edit events command
    String input3 = "edit events location title from 2025-06-04T21:19 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.go(this.model, this.view);

    // verify event edited in calendar
    this.resetStream();
    String input4 = "print events from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.go(this.model, this.view);
    assertEquals("Events from 2025-06-04T21:19 to 2025-06-07T21:19:" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator(),
            this.stream.toString());
  }

  // edit series
  @Test
  public void testMultidayEventEditSeriesCommand() {
    // add event to calendar
    String input1 = "create event title from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.go(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.go(this.model, this.view);
    assertEquals("Events from 2025-06-04T21:19 to 2025-06-07T21:19:" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    // edit series command
    String input3 = "edit series location title from 2025-06-04T21:19:00 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.go(this.model, this.view);

    // verify event edited in calendar
    this.resetStream();
    String input4 = "print events from 2025-06-04T21:19:00 to 2025-06-07T21:19";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.go(this.model, this.view);
    assertEquals("Events from 2025-06-04T21:19 to 2025-06-07T21:19:" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator(),
            this.stream.toString());
  }


  // SERIES EVENT TESTS

  @Test
  public void testSeriesEventEditEventCommand() {
    // add event to calendar
    String input1 = "create event title on 2019-09-09 repeats MW for 6 times";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.go(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.go(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title: 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title: 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title: 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title: 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title: 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());;
    this.resetStream();

    // edit event command for event in middle of series
    String input3 = "edit event location title from 2019-09-11T08:00 to 2019-09-11T17:00 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.go(this.model, this.view);

    // verify only single event in series edited in calendar
    this.resetStream();
    String input4 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.go(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" +  System.lineSeparator() +
                    "- title (physical): 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title: 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title: 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title: 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title: 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());
  }

  // edit events
  @Test
  public void testSeriesEventEditEventsCommand() {
    // add event to calendar
    String input1 = "create event title on 2019-09-09 repeats MW for 6 times";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.go(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.go(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title: 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title: 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title: 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title: 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title: 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());

    // edit event command for event in middle of series
    String input3 = "edit events location title from 2019-09-25T08:00 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.go(this.model, this.view);

    // verify event on or after date in series edited in calendar
    this.resetStream();
    String input4 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.go(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title (physical): 2019-09-30T08:00 to 2019-09-30T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title (physical): 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title: 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title: 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title: 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());
  }

  // edit series
  @Test
  public void testSeriesEventEditSeriesCommand() {
    // add event to calendar
    String input1 = "create event title on 2019-09-09 repeats MW for 6 times";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.go(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.go(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title: 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title: 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title: 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title: 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title: 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    // edit series command for all events in series
    String input3 = "edit series location title from 2019-09-25T08:00 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.go(this.model, this.view);

    // verify all events in series updated
    this.resetStream();
    String input4 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.go(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" +  System.lineSeparator() +
                    "- title (physical): 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title (physical): 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title (physical): 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title (physical): 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title (physical): 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title (physical): 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());
  }

  // invalid edit command
  @Test
  public void testInvalidEditEventCommand() {
    CalendarCommand command;
    String invalidEditCommand1 = "edit event";
    String invalidEditCommand2 = "edit event location";
    String invalidEditCommand3 = "edit event location title";
    String invalidEditCommand4 = "edit event location title from";
    String invalidEditCommand5 = "edit event location title from 2025-06-04T21:19:00";
    String invalidEditCommand6 = "edit event location title from 2025-06-04T21:19:00 to";
    String invalidEditCommand7 = "edit event location title from 2025-06-04T21:19:00 to 2025-06-04T21:19:00";
    String invalidEditCommand8 = "edit event location title from 2025-06-04T21:19:00 to 2025-06-04T21:19:00 with";
    String validEditCommand = "edit event location title from 2025-06-04T21:19:00 to 2025-06-04T21:19:00 with physical";

    // missing event property
    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand1);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }

    // missing event subject
    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand2);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    // missing from
    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand3);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    // missing start date
    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand4);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    // missing to
    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand5);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    // missing end date
    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand6);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    // missing with
    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand7);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
    // missing new property
    try {
      command = this.editCommandFactory.createCalendarCommand(invalidEditCommand8);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }

    // correct example with all keywords and queries
    try {
      command = this.editCommandFactory.createCalendarCommand(validEditCommand);
    } catch (IllegalArgumentException e) {
      fail("Should not have thrown");
    }
  }


  public void testInvalidEditEventsCommand() {

  }


  public void testInvalidEditSeriesCommand() {

  }
}