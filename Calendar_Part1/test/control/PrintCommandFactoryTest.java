package control;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

/**
 * Test class for print commands.
 */
public class PrintCommandFactoryTest extends ACommandFactoryTest {

  @Override
  protected ACommandFactory makeFactory() {
    return new PrintCommandFactory();
  }

  @Test
  public void testValidPrintCommands() {
    ACommandFactory testFactory = makeFactory();

    String query1 = "print events on 2025-06-03";

    String query2 = "print events from 2025-06-03T10:00 to 2025-06-03T12:00";

    assertNotNull(testFactory.createCalendarCommand(query1));
    assertNotNull(testFactory.createCalendarCommand(query2));
  }

  @Test
  public void testInvalidPrintCommands() {
    ACommandFactory testFactory = makeFactory();

    String invalidQuery1 = "print event on 2025-06-03";
    String invalidQuery2 = "print events from 2025-06-03T10:00 to 2025-06-03T12:00 on 2025-06-03";
    String invalidQuery3 = "print events on 2025-06-31";
    String invalidQuery4 = "print events from 2025-06-03T10:00 to 2025-06-31T12:00";
    String invalidQuery5 = "print events from 2025-06-03T10:00 to 2025-06-01T12:00";

    // Method 1: Using assertThrows (preferred)
    assertThrows("Should reject 'print event' (missing s)",
            IllegalArgumentException.class,
            () -> testFactory.createCalendarCommand(invalidQuery1));

    assertThrows("Should reject conflicting keywords",
            IllegalArgumentException.class,
            () -> testFactory.createCalendarCommand(invalidQuery2));

    assertThrows("Should reject invalid date",
            IllegalArgumentException.class,
            () -> testFactory.createCalendarCommand(invalidQuery3));

    assertThrows("Should reject invalid end date",
            IllegalArgumentException.class,
            () -> testFactory.createCalendarCommand(invalidQuery4));

    assertThrows("Should reject end date before start date",
            IllegalArgumentException.class,
            () -> testFactory.createCalendarCommand(invalidQuery5));
  }
}
