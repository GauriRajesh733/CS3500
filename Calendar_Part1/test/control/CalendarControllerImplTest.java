package control;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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

  private void resetStream() {
    this.os = new ByteArrayOutputStream();
    this.v = new CalendarViewImpl(new PrintStream(this.os));
  }

  private void setInput(String input) {
    this.is = new ByteArrayInputStream(input.getBytes());
    this.c = new CalendarControllerImpl(this.is);
  }

  @Test
  public void testSingleValidCommand() {
    String input = "create event test on 2025-05-05" + System.lineSeparator() +
            "print events on 2025-05-05" + System.lineSeparator() +
            "exit" + System.lineSeparator();

    this.setInput(input);
    c.go(m, v);

    String output = os.toString();

    /**
     assertEquals("""
     Events on 2025-05-05:
     - test: 2025-05-05T08:00 to 2025-05-05T17:00
     """, output);**/
  }

  // test invalid file input without exit command
  @Test
  public void testFileInputNoExit() throws FileNotFoundException {
    this.is = new FileInputStream("C:\\Users\\gauri\\Documents\\GitHub\\CS3500\\Calendar_Part1\\inputs\\commandsNoQuit");
    this.os = new ByteArrayOutputStream();
    this.s = new PrintStream(os);
    this.c = new CalendarControllerImpl(is);
    this.c.go(new CalendarModelImpl(), new CalendarViewImpl(s));
    assertEquals("", os.toString());
  }

  // test valid file input with exit command




}