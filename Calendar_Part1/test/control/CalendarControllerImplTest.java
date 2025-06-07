package control;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import model.CalendarModelImpl;
import view.CalendarViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test class for calendar controller.
 */
public class CalendarControllerImplTest {
  private CalendarController c;
  private PrintStream s;
  private OutputStream os;
  private InputStream is;

  @Before
  public void setUp() {
    this.os = new ByteArrayOutputStream();
    this.s = new PrintStream(os);
  }

  // test list of valid file commands (based on example provided in assignment)
  // NOTE: no exit command so that test output can be seen
  @Test
  public void testValidFileInputsNoExit() throws FileNotFoundException {
    this.is = new FileInputStream(
            "C:\\Users\\gauri\\Documents\\GitHub\\CS3500\\Calendar_Part1\\res\\validCommands");
    this.c = new CalendarControllerImpl(is);
    this.c.run(new CalendarModelImpl(), new CalendarViewImpl(s));
    String correctOutput = "Events from 2025-05-05T10:00 to 2025-05-21T11:00:"
            + System.lineSeparator() +
            "- First: 2025-05-14T10:00 to 2025-05-14T11:00" + System.lineSeparator() +
            "- First: 2025-05-12T10:00 to 2025-05-12T11:00" + System.lineSeparator() +
            "- First: 2025-05-07T10:00 to 2025-05-07T11:00" + System.lineSeparator() +
            "- First: 2025-05-05T10:00 to 2025-05-05T11:00" + System.lineSeparator() +
            "- First: 2025-05-21T10:00 to 2025-05-21T11:00" + System.lineSeparator() +
            "- First: 2025-05-19T10:00 to 2025-05-19T11:00" + System.lineSeparator() +
            "Events from 2025-05-05T10:00 to 2025-05-21T11:00:" + System.lineSeparator() +
            "- Second: 2025-05-14T10:00 to 2025-05-14T11:00" + System.lineSeparator() +
            "- Second: 2025-05-12T10:00 to 2025-05-12T11:00" + System.lineSeparator() +
            "- First: 2025-05-07T10:00 to 2025-05-07T11:00" + System.lineSeparator() +
            "- First: 2025-05-05T10:00 to 2025-05-05T11:00" + System.lineSeparator() +
            "- Second: 2025-05-21T10:00 to 2025-05-21T11:00" + System.lineSeparator() +
            "- Second: 2025-05-19T10:00 to 2025-05-19T11:00" + System.lineSeparator() +
            "Events from 2025-05-05T10:00 to 2025-05-21T11:00:" + System.lineSeparator() +
            "- Third: 2025-05-14T10:00 to 2025-05-14T11:00" + System.lineSeparator() +
            "- Third: 2025-05-12T10:00 to 2025-05-12T11:00" + System.lineSeparator() +
            "- Third: 2025-05-07T10:00 to 2025-05-07T11:00" + System.lineSeparator() +
            "- Third: 2025-05-05T10:00 to 2025-05-05T11:00" + System.lineSeparator() +
            "- Third: 2025-05-21T10:00 to 2025-05-21T11:00" + System.lineSeparator() +
            "- Third: 2025-05-19T10:00 to 2025-05-19T11:00" + System.lineSeparator() +
            "Events from 2025-05-05T10:00 to 2025-05-21T11:00:" + System.lineSeparator() +
            "- Third: 2025-05-14T10:30 to 2025-05-14T11:00" + System.lineSeparator() +
            "- Third: 2025-05-12T10:30 to 2025-05-12T11:00" + System.lineSeparator() +
            "- Third: 2025-05-07T10:00 to 2025-05-07T11:00" + System.lineSeparator() +
            "- Third: 2025-05-05T10:00 to 2025-05-05T11:00" + System.lineSeparator() +
            "- Third: 2025-05-21T10:30 to 2025-05-21T11:00" + System.lineSeparator() +
            "- Third: 2025-05-19T10:30 to 2025-05-19T11:00" + System.lineSeparator() +
            "Events from 2025-05-05T10:00 to 2025-05-21T11:00:" + System.lineSeparator() +
            "- Third: 2025-05-14T10:30 to 2025-05-14T11:00" + System.lineSeparator() +
            "- Third: 2025-05-12T10:30 to 2025-05-12T11:00" + System.lineSeparator() +
            "- Fourth: 2025-05-07T10:00 to 2025-05-07T11:00" + System.lineSeparator() +
            "- Third: 2025-05-21T10:30 to 2025-05-21T11:00" + System.lineSeparator() +
            "- Fourth: 2025-05-05T10:00 to 2025-05-05T11:00" + System.lineSeparator() +
            "- Third: 2025-05-19T10:30 to 2025-05-19T11:00" + System.lineSeparator() +
            "Events from 2025-05-05T10:00 to 2025-05-21T11:00:" + System.lineSeparator() +
            "- Fifth: 2025-05-14T10:30 to 2025-05-14T11:00" + System.lineSeparator() +
            "- Fifth: 2025-05-12T10:30 to 2025-05-12T11:00" + System.lineSeparator() +
            "- Fourth: 2025-05-07T10:00 to 2025-05-07T11:00" + System.lineSeparator() +
            "- Fourth: 2025-05-05T10:00 to 2025-05-05T11:00" + System.lineSeparator() +
            "- Fifth: 2025-05-21T10:30 to 2025-05-21T11:00" + System.lineSeparator() +
            "- Fifth: 2025-05-19T10:30 to 2025-05-19T11:00" + System.lineSeparator() +
            "File input must end with exit command" + System.lineSeparator();
    assertEquals(correctOutput, os.toString());
  }

  // test list of invalid file input commands and check that controller handles them gracefully
  // NOTE: no exit command so that test output can be seen
  @Test
  public void testInvalidFileInputsNoExit() throws FileNotFoundException {
    this.is = new FileInputStream(
            "C:\\Users\\gauri\\Documents\\GitHub\\CS3500\\Calendar_Part1\\res\\invalidCommands");
    this.c = new CalendarControllerImpl(is);
    this.c.run(new CalendarModelImpl(), new CalendarViewImpl(s));
    assertEquals("Invalid calendar event command: blah blah blah" + System.lineSeparator() +
            "Please enter a new command" + System.lineSeparator() +
            "File input must end with exit command" + System.lineSeparator() +
            "Invalid date provided: 2025" + System.lineSeparator() +
            "Please enter a new command" + System.lineSeparator() +
            "File input must end with exit command" + System.lineSeparator() +
            "Invalid date format." + System.lineSeparator() +
            "Please enter a new command" + System.lineSeparator() +
            "File input must end with exit command" + System.lineSeparator() +
            "Missing time specification (expected 'from DATETIME' or 'on DATE')"
            + System.lineSeparator() +
            "Please enter a new command" + System.lineSeparator() +
            "File input must end with exit command" + System.lineSeparator() +
            "Start date and end date must be the same for series event" + System.lineSeparator() +
            "Please enter a new command" + System.lineSeparator() +
            "File input must end with exit command" + System.lineSeparator() +
            "File input must end with exit command" + System.lineSeparator(), os.toString());
  }

  // test list of valid file commands
  // NOTE: did not add exit command because exits test itself
  @Test
  public void testValidFileInputsNoExit2() throws FileNotFoundException {
    this.is = new FileInputStream(
            "C:\\Users\\gauri\\Documents\\GitHub\\CS3500\\Calendar_Part1\\res\\validCommands2.txt");
    this.c = new CalendarControllerImpl(is);
    this.c.run(new CalendarModelImpl(), new CalendarViewImpl(s));
    assertEquals("Events from 2025-06-02T09:50 to 2025-06-30T11:20:" + System.lineSeparator() +
            "- lab: 2025-06-30T09:50 to 2025-06-30T11:20" + System.lineSeparator() +
            "- lab: 2025-06-11T09:50 to 2025-06-11T11:20" + System.lineSeparator() +
            "- lab: 2025-06-09T09:50 to 2025-06-09T11:20" + System.lineSeparator() +
            "- lab: 2025-06-25T09:50 to 2025-06-25T11:20" + System.lineSeparator() +
            "- lab: 2025-06-23T09:50 to 2025-06-23T11:20" + System.lineSeparator() +
            "- lab: 2025-06-04T09:50 to 2025-06-04T11:20" + System.lineSeparator() +
            "- lab: 2025-06-02T09:50 to 2025-06-02T11:20" + System.lineSeparator() +
            "- lab: 2025-06-18T09:50 to 2025-06-18T11:20" + System.lineSeparator() +
            "- lab: 2025-06-16T09:50 to 2025-06-16T11:20" + System.lineSeparator() +
            "Busy on 2025-06-02T09:50" + System.lineSeparator() +
            "Available on 2025-06-01T09:50" + System.lineSeparator() +
            "Events on 2025-06-05:" + System.lineSeparator() +
            "- allDay: 2025-06-05T08:00 to 2025-06-05T17:00" + System.lineSeparator() +
            "Events from 2025-06-02T09:50 to 2025-06-30T11:20:" + System.lineSeparator() +
            "- oodLab: 2025-06-30T09:50 to 2025-06-30T11:20" + System.lineSeparator() +
            "- oodLab: 2025-06-11T09:50 to 2025-06-11T11:20" + System.lineSeparator() +
            "- oodLab: 2025-06-09T09:50 to 2025-06-09T11:20" + System.lineSeparator() +
            "- oodLab: 2025-06-25T09:50 to 2025-06-25T11:20" + System.lineSeparator() +
            "- oodLab: 2025-06-23T09:50 to 2025-06-23T11:20" + System.lineSeparator() +
            "- allDay: 2025-06-05T08:00 to 2025-06-05T17:00" + System.lineSeparator() +
            "- oodLab: 2025-06-04T09:50 to 2025-06-04T11:20" + System.lineSeparator() +
            "- oodLab: 2025-06-02T09:50 to 2025-06-02T11:20" + System.lineSeparator() +
            "- oodLab: 2025-06-18T09:50 to 2025-06-18T11:20" + System.lineSeparator() +
            "- oodLab: 2025-06-16T09:50 to 2025-06-16T11:20" + System.lineSeparator() +
            "Events from 2025-06-02T09:50 to 2025-06-30T11:20:" + System.lineSeparator() +
            "- oodLab (physical): 2025-06-30T09:50 to 2025-06-30T11:20" + System.lineSeparator() +
            "- oodLab: 2025-06-11T09:50 to 2025-06-11T11:20" + System.lineSeparator() +
            "- oodLab: 2025-06-09T09:50 to 2025-06-09T11:20" + System.lineSeparator() +
            "- oodLab (physical): 2025-06-25T09:50 to 2025-06-25T11:20" + System.lineSeparator() +
            "- oodLab (physical): 2025-06-23T09:50 to 2025-06-23T11:20" + System.lineSeparator() +
            "- allDay: 2025-06-05T08:00 to 2025-06-05T17:00" + System.lineSeparator() +
            "- oodLab: 2025-06-04T09:50 to 2025-06-04T11:20" + System.lineSeparator() +
            "- oodLab: 2025-06-02T09:50 to 2025-06-02T11:20" + System.lineSeparator() +
            "- oodLab (physical): 2025-06-18T09:50 to 2025-06-18T11:20" + System.lineSeparator() +
            "- oodLab: 2025-06-16T09:50 to 2025-06-16T11:20" + System.lineSeparator() +
            "File input must end with exit command" + System.lineSeparator(), os.toString());
  }

  // // test exit command
  // // NOTE: exits test itself so commented out
  // @Test
  // public void testFileExit() throws FileNotFoundException {
  //   this.is = new FileInputStream(
  //           "C:\\Users\\gauri\\Documents\\GitHub\\CS3500\\Calendar_Part1\\res\\exitCommand");
  //   this.c = new CalendarControllerImpl(is);
  //   this.c.run(new CalendarModelImpl(), new CalendarViewImpl(s));
  //   assertNotNull(this.c);
  // }
}