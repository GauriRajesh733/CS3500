package control.commands;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CalendarCommandFactoryTest extends TestCase {

  private CalendarCommandFactory factory;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @Before
  public void setUp() {
    factory = new CalendarCommandFactory();

    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @Test(expected =  IllegalArgumentException.class)
  public void testInvalidCommand() {
      factory.createCalendarCommand("invalidCommand");

  }

}