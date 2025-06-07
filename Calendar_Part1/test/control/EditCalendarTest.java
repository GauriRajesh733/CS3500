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

/**
 * Tests for edit calendar commands.
 */
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
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events on 2025-06-04";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
    assertEquals("Events on 2025-06-04:" + System.lineSeparator()
                    + "- title: 2025-06-04T21:19 to 2025-06-04T21:19" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    // edit event command
    String input3 = "edit event location title from 2025-06-04T21:19:00 " +
            "to 2025-06-04T21:19:00 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.run(this.model, this.view);

    // verify event edited in calendar
    this.resetStream();
    String input4 = "print events on 2025-06-04";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.run(this.model, this.view);
    assertEquals("Events on 2025-06-04:" + System.lineSeparator()
                    + "- title (physical): 2025-06-04T21:19 to 2025-06-04T21:19"
                    + System.lineSeparator(),
            this.stream.toString());
  }

  // edit events
  @Test
  public void testSingleEventEditEventsCommand() {
    // add event to calendar
    String input1 = "create event title from 2025-06-04T21:19 to 2025-06-04T21:19";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events on 2025-06-04";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
    assertEquals("Events on 2025-06-04:" + System.lineSeparator()
                    + "- title: 2025-06-04T21:19 to 2025-06-04T21:19" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    // edit events command
    String input3 = "edit events location title from 2025-06-04T21:19 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.run(this.model, this.view);

    // verify event edited in calendar
    this.resetStream();
    String input4 = "print events on 2025-06-04";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.run(this.model, this.view);
    assertEquals("Events on 2025-06-04:" + System.lineSeparator()
                    + "- title (physical): 2025-06-04T21:19 to 2025-06-04T21:19"
                    + System.lineSeparator(),
            this.stream.toString());
  }

  // edit series
  @Test
  public void testSingleEventEditSeriesCommand() {
    // add event to calendar
    String input1 = "create event title from 2025-06-04T21:19 to 2025-06-04T21:19";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events on 2025-06-04";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
    assertEquals("Events on 2025-06-04:" + System.lineSeparator()
                    + "- title: 2025-06-04T21:19 to 2025-06-04T21:19" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    // edit series command
    String input3 = "edit series location title from 2025-06-04T21:19 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.run(this.model, this.view);

    // verify event edited in calendar
    this.resetStream();
    String input4 = "print events on 2025-06-04";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.run(this.model, this.view);
    assertEquals("Events on 2025-06-04:" + System.lineSeparator()
                    + "- title (physical): 2025-06-04T21:19 to 2025-06-04T21:19"
                    + System.lineSeparator(),
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
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
    assertEquals("Events from 2025-06-04T21:19 to 2025-06-07T21:19:" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator() +
                    "- title: 2025-06-04T21:19 to 2025-06-07T21:19" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    // edit event command
    String input3 = "edit event location title from 2025-06-04T21:19 " +
            "to 2025-06-07T21:19 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.run(this.model, this.view);

    // verify event edited in calendar
    this.resetStream();
    String input4 = "print events from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.run(this.model, this.view);
    assertEquals("Events from 2025-06-04T21:19 to 2025-06-07T21:19:" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19"
                    + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19"
                    + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19"
                    + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19"
                    + System.lineSeparator(),
            this.stream.toString());
  }

  // edit events
  @Test
  public void testMultidayEventEditEventsCommand() {
    // add event to calendar
    String input1 = "create event title from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
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
    cmd.run(this.model, this.view);

    // verify event edited in calendar
    this.resetStream();
    String input4 = "print events from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.run(this.model, this.view);
    assertEquals("Events from 2025-06-04T21:19 to 2025-06-07T21:19:" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19"
                    + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19"
                    + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19"
                    + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19"
                    + System.lineSeparator(),
            this.stream.toString());
  }

  // edit series
  @Test
  public void testMultidayEventEditSeriesCommand() {
    // add event to calendar
    String input1 = "create event title from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2025-06-04T21:19 to 2025-06-07T21:19";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
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
    cmd.run(this.model, this.view);

    // verify event edited in calendar
    this.resetStream();
    String input4 = "print events from 2025-06-04T21:19:00 to 2025-06-07T21:19";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.run(this.model, this.view);
    assertEquals("Events from 2025-06-04T21:19 to 2025-06-07T21:19:" + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19"
                    + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19"
                    + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19"
                    + System.lineSeparator() +
                    "- title (physical): 2025-06-04T21:19 to 2025-06-07T21:19"
                    + System.lineSeparator(),
            this.stream.toString());
  }


  // SERIES EVENT TESTS

  @Test
  public void testSeriesEventEditEventCommand() {
    // add event to calendar
    String input1 = "create event title on 2019-09-09 repeats MW for 6 times";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title: 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title: 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title: 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title: 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title: 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());
    ;
    this.resetStream();

    // edit event command for event in middle of series
    String input3 = "edit event location title from 2019-09-11T08:00 " +
            "to 2019-09-11T17:00 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.run(this.model, this.view);

    // verify only single event in series edited in calendar
    this.resetStream();
    String input4 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.run(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title (physical): 2019-09-11T08:00 to 2019-09-11T17:00"
                    + System.lineSeparator() +
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
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title: 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title: 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title: 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title: 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title: 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());

    // edit event command for event in middle of series
    String input3 = "edit events location title from 2019-09-23T08:00 with physical";
    CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
    cmd.run(this.model, this.view);

    // verify event on or after date in series edited in calendar
    this.resetStream();
    String input4 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.run(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title: 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title (physical): 2019-09-25T08:00 to 2019-09-25T17:00"
                    + System.lineSeparator() +
                    "- title (physical): 2019-09-23T08:00 to 2019-09-23T17:00"
                    + System.lineSeparator() +
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
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
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
    cmd.run(this.model, this.view);

    // verify all events in series updated
    this.resetStream();
    String input4 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd2 = new PrintCommandFactory().createCalendarCommand(input4);
    printCmd2.run(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title (physical): 2019-09-11T08:00 to 2019-09-11T17:00"
                    + System.lineSeparator() +
                    "- title (physical): 2019-09-09T08:00 to 2019-09-09T17:00"
                    + System.lineSeparator() +
                    "- title (physical): 2019-09-25T08:00 to 2019-09-25T17:00"
                    + System.lineSeparator() +
                    "- title (physical): 2019-09-23T08:00 to 2019-09-23T17:00"
                    + System.lineSeparator() +
                    "- title (physical): 2019-09-18T08:00 to 2019-09-18T17:00"
                    + System.lineSeparator() +
                    "- title (physical): 2019-09-16T08:00 to 2019-09-16T17:00"
                    + System.lineSeparator(),
            this.stream.toString());
  }

  // test trying to edit start date of event to be after end date
  @Test
  public void testStartDateAfterEndDateError() {
    // add event to calendar
    String input1 = "create event title on 2019-09-09 repeats MW for 6 times";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title: 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title: 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title: 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title: 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title: 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    try {
      // edit series command for all events in series
      String input3 = "edit series end title from 2019-09-09T08:00 with 2019-09-09T19:00";
      CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
      cmd.run(this.model, this.view);
      fail("Should have thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Start date time must be before end date time", e.getMessage());
    }
  }

  // test trying to edit end date of event to be before start date
  @Test
  public void testEditEndDateBeforeStartDateError() {
    // add event to calendar
    String input1 = "create event title on 2019-09-09 repeats MW for 6 times";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title: 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title: 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title: 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title: 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title: 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    try {
      // edit series command for all events in series
      String input3 = "edit series end title from 2019-09-09T08:00 with 2019-09-08T06:00";
      CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
      cmd.run(this.model, this.view);
      fail("Should have thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Start date time must be before end date time", e.getMessage());
    }
  }

  // test trying to edit series to span multiple days
  @Test
  public void testEditSeriesMultipleDaysError() {
    // add event to calendar
    String input1 = "create event title on 2019-09-09 repeats MW for 6 times";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title: 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title: 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title: 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title: 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title: 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    try {
      // edit series command for all events in series
      String input3 = "edit series start title from 2019-09-09T08:00 with 2019-09-07T06:00";
      CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
      cmd.run(this.model, this.view);
      fail("Should have thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("Series event cannot span multiple days", e.getMessage());
    }
  }

  // test trying to edit start date invalid
  @Test
  public void testStartDateInvalidError() {
    // add event to calendar
    String input1 = "create event title on 2019-09-09 repeats MW for 6 times";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title: 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title: 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title: 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title: 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title: 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    try {
      // edit series command for all events in series
      String input3 = "edit event start title from 2019-09-09T08:00 to 2019-09-09T17:00 with 2019-09-09";
      CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
      cmd.run(this.model, this.view);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
  }

  // test trying to edit end date invalid
  @Test
  public void testEditDateInvalidError() {
    // add event to calendar
    String input1 = "create event title on 2019-09-09 repeats MW for 6 times";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title: 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title: 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title: 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title: 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title: 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    try {
      // edit series command for all events in series
      String input3 = "edit event end title from 2019-09-09T08:00 to 2019-09-09T17:00 with blah blah blah";
      CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
      cmd.run(this.model, this.view);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
  }

  // test trying to edit location invalid
  @Test
  public void testEditLocationInvalid() {
    // add event to calendar
    String input1 = "create event title on 2019-09-09 repeats MW for 6 times";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title: 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title: 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title: 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title: 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title: 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    try {
      // edit series command for all events in series
      String input3 = "edit event location title from 2019-09-09T08:00 to 2019-09-09T17:00 with blah blah blah";
      CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
      cmd.run(this.model, this.view);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
  }

  // test trying to edit status invalid
  @Test
  public void testEditStatusInvalid() {
    // add event to calendar
    String input1 = "create event title on 2019-09-09 repeats MW for 6 times";
    CalendarCommand createCmd = new CalendarCommandFactory().createCalendarCommand(input1);
    createCmd.run(this.model, this.view);

    // verify event added to calendar
    String input2 = "print events from 2019-09-09T00:00 to 2019-11-09T00:00";
    CalendarCommand printCmd1 = new PrintCommandFactory().createCalendarCommand(input2);
    printCmd1.run(this.model, this.view);
    assertEquals("Events from 2019-09-09T00:00 to 2019-11-09T00:00:" + System.lineSeparator() +
                    "- title: 2019-09-11T08:00 to 2019-09-11T17:00" + System.lineSeparator() +
                    "- title: 2019-09-09T08:00 to 2019-09-09T17:00" + System.lineSeparator() +
                    "- title: 2019-09-25T08:00 to 2019-09-25T17:00" + System.lineSeparator() +
                    "- title: 2019-09-23T08:00 to 2019-09-23T17:00" + System.lineSeparator() +
                    "- title: 2019-09-18T08:00 to 2019-09-18T17:00" + System.lineSeparator() +
                    "- title: 2019-09-16T08:00 to 2019-09-16T17:00" + System.lineSeparator(),
            this.stream.toString());
    this.resetStream();

    try {
      // edit series command for all events in series
      String input3 = "edit event end title from 2019-09-09T08:00 to 2019-09-09T17:00 with blah blah blah";
      CalendarCommand cmd = this.editCommandFactory.createCalendarCommand(input3);
      cmd.run(this.model, this.view);
      fail("Should have thrown");
    } catch (IllegalArgumentException ignored) {
    }
  }

}