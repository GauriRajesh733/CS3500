package control.commands;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import control.CalendarControllerImpl;
import model.CalendarModel;
import model.CalendarModelImpl;
import view.CalendarView;
import view.CalendarViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * This is a test class for the CalendarControllerImpl.
 */
public class CalendarControllerImplTest {
  ByteArrayOutputStream outputStream;
  ByteArrayInputStream inputStream;
  CalendarControllerImpl controller;
  CalendarModel mockModel;
  CalendarView mockView;

  private void resetStream() {
    this.outputStream = new ByteArrayOutputStream();
    this.mockView = new CalendarViewImpl(new PrintStream(this.outputStream));
  }

  private void setInput(String input) {
    this.inputStream = new ByteArrayInputStream(input.getBytes());
    this.controller = new CalendarControllerImpl(this.inputStream);
  }

  @Before
  public void setUp() {
    this.outputStream = new ByteArrayOutputStream();
    this.mockView = new CalendarViewImpl(new PrintStream(this.outputStream));
    this.mockModel = new CalendarModelImpl();
  }

  @Test
  public void testSingleValidCommand() {
    String input = "create event test on 2025-05-05" + System.lineSeparator() +
            "print events on 2025-05-05" + System.lineSeparator() +
            "exit" + System.lineSeparator();

    this.setInput(input);
    controller.go(mockModel, mockView);

    String output = outputStream.toString();

    /**
    assertEquals("""
            Events on 2025-05-05:
            - test: 2025-05-05T08:00 to 2025-05-05T17:00
            """, output);**/
  }

}