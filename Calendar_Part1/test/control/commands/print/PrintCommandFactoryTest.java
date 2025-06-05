package control.commands;
import org.junit.Test;

<<<<<<< HEAD
=======
import control.ACommandFactoryTest;
import control.commands.PrintCommandFactory;

>>>>>>> f21da3497da71b175d0e1dcc0fd521c33e21df77
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PrintCommandFactoryTest extends ACommandFactoryTest {

  @Override
  public PrintCommandFactory makeFactory() {
    return new PrintCommandFactory();
  }

  @Test
  public void testValidPrintCommands() {
    PrintCommandFactory testFactory = makeFactory();

    String query1 = "print events on 2025-06-03";

    String query2 = "print events from 2025-06-03T10:00 to 2025-06-03T12:00";

    assertNotNull(testFactory.createCalendarCommand(query1));
    assertNotNull(testFactory.createCalendarCommand(query2));
  }

  @Test
  public void testInvalidPrintCommands() {
    PrintCommandFactory testFactory = makeFactory();

    String invalidQuery1 = "print event on 2025-06-03";
    String invalidQuery2 = "print events from 2025-06-03T10:00 to 2025-06-03T12:00 on 2025-06-03";
    String invalidQuery3 = "print events on 2025-06-31";
    String invalidQuery4 = "print events from 2025-06-03T10:00 to 2025-06-31T12:00";
    String invalidQuery5 = "print events from 2025-06-03T10:00 to 2025-06-01T12:00";

    try {
      testFactory.createCalendarCommand(invalidQuery1);
    } catch (IllegalArgumentException e) {
      assert e.getMessage().contains("Invalid print command");
    }

    try {
      testFactory.createCalendarCommand(invalidQuery2);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Invalid print command"));
    }

    try {
      testFactory.createCalendarCommand(invalidQuery3);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Invalid date provided: 2025-06-31"));
    }

    try {
      testFactory.createCalendarCommand(invalidQuery4);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Invalid date provided: 2025-06-31"));
    }

    try {
      testFactory.createCalendarCommand(invalidQuery5);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Start date cannot be after end date"));
    }

  }
}