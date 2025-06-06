package control;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import model.CalendarModel;
import model.CalendarModelImpl;
import view.CalendarView;
import view.CalendarViewImpl;

import static org.junit.Assert.assertEquals;


public class CalendarControllerImplTest {
  private CalendarController c;
  private CalendarModel m;
  private CalendarView v;
  private PrintStream s;
  private OutputStream os;
  private InputStream is;

  @Before
  public void setUp() {
    this.os = new ByteArrayOutputStream();
    this.v = new CalendarViewImpl(new PrintStream(this.os));
    this.m = new CalendarModelImpl();
  }

  // NOTE: editing separate series after splitting by start date creates duplicates (mutation issue)

  // test file input without exit command
  @Test
  public void testFileInputNoExit() throws FileNotFoundException {
    this.is = new FileInputStream("C:\\Users\\gauri\\Documents\\GitHub\\CS3500\\Calendar_Part1\\inputs\\commandsNoQuit");
    this.os = new ByteArrayOutputStream();
    this.s = new PrintStream(os);
    this.c = new CalendarControllerImpl(is);
    this.c.go(new CalendarModelImpl(), new CalendarViewImpl(s));
    String correctOutput = "Events from 2025-05-05T10:00 to 2025-05-21T11:00:" + System.lineSeparator() +
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

  @Test
  public void testGracefulFailure() throws FileNotFoundException{
    this.is = new FileInputStream("C:\\Users\\gauri\\Documents\\GitHub\\CS3500\\Calendar_Part1\\inputs\\gracefulFailure");
    this.os = new ByteArrayOutputStream();
    this.s = new PrintStream(os);
    this.c = new CalendarControllerImpl(is);
    this.c.go(new CalendarModelImpl(), new CalendarViewImpl(s));
    assertEquals("Invalid calendar event command: blah blah blah" + System.lineSeparator() +
            "Please enter a new command" + System.lineSeparator() +
            "File input must end with exit command" + System.lineSeparator() +
            "Invalid date provided: 2025" + System.lineSeparator() +
            "Please enter a new command" + System.lineSeparator() +
            "File input must end with exit command" + System.lineSeparator() +
            "Invalid date format." + System.lineSeparator() +
            "Please enter a new command" + System.lineSeparator() +
            "File input must end with exit command" + System.lineSeparator() +
            "Calendar command missing event subject" + System.lineSeparator() +
            "Please enter a new command" + System.lineSeparator() +
            "File input must end with exit command" + System.lineSeparator() +
            "Start date and end date must be the same for series event" + System.lineSeparator() +
            "Please enter a new command" + System.lineSeparator() +
            "File input must end with exit command" + System.lineSeparator() +
            "File input must end with exit command" + System.lineSeparator(), os.toString());

  }




}