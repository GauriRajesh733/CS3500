package control;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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


  // test invalid file input without exit command
  @Test
  public void testFileInputNoExit() throws FileNotFoundException {
    os = new ByteArrayOutputStream();
    s = new PrintStream(os);
    FileInputStream invalidFileInput = new FileInputStream("C:\\Users\\gauri\\Documents\\GitHub\\CS3500\\Calendar_Part1\\inputs\\commandsNoQuit");
    c = new CalendarControllerImpl(invalidFileInput);
    c.go(new CalendarModelImpl(), new CalendarViewImpl(s));
    assertEquals("", os.toString());
  }

  // test valid file input with exit command
}