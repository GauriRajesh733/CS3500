package control.commands;

import org.junit.Before;
import org.junit.Test;

import model.EventProperty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


/**
 * This is an abstract test class for ACommandFactory.
 * It provides common tests for command factories that extend ACommandFactory.
 */

public abstract class ACommandFactoryTest {

  protected ACommandFactory commandFactory;

  /**
   * Factory method to create a specific command factory for testing.
   * Subclasses must implement this method to return an instance of their command factory.
   */

  public abstract ACommandFactory makeFactory();

  @Before
  public void setUp() {
    this.commandFactory = makeFactory();
  }

  @Test
  public void testSearchKeywordIndexValid() {
    String input = "create event test on 2025-05-05";
    String command = "event";// index of 'e' in "event"

    int actualIndex = commandFactory.searchKeywordIndex(input, command);

    assertEquals(7, actualIndex); // "create " has 7 characters
  }

  @Test
  public void testSearch() {
    String input = "create event test on 2025-05-05";
    int startIndex = 7; // index of 'e' in "event"
    int endIndex = 12; // index after "event"

    String expectedKeyword = "event";
    String actualKeyword = commandFactory.search(input, startIndex, endIndex, "Keyword not found");

    assertEquals(expectedKeyword, actualKeyword);
  }

  @Test
  public void testValidDateTime() {
    String validDateTime = "2025-05-05T10:00";
    String invalidDateTime = "2025-05-05 10:00"; // invalid format

    assertTrue(commandFactory.validDateTime(validDateTime));
    assertFalse(commandFactory.validDateTime(invalidDateTime));
  }

  @Test
  public void testValidDate() {
    String validDate = "2025-05-05";
    String invalidDate = "2025/05/05"; // invalid format

    assertTrue(commandFactory.validDate(validDate));
    assertFalse(commandFactory.validDate(invalidDate));
  }

  @Test
  public void testValidNewProperty() {
    String validSubject = "Meeting";
    String validLocation = "online";
    String invalidLocation = "hybrid"; // invalid location

    assertTrue(commandFactory.validNewProperty(validSubject, EventProperty.SUBJECT));
    assertTrue(commandFactory.validNewProperty(validLocation, EventProperty.LOCATION));
    assertFalse(commandFactory.validNewProperty(invalidLocation, EventProperty.LOCATION));
  }

  @Test
  public void testValidStartAndEndTime() {
    String validStart = "2025-05-05T10:00";
    String validEnd = "2025-05-05T12:00";
    String invalidStart = "2025-05-05T13:00";
    String invalidEnd = "2025-05-04T09:00"; // end before start

    assertTrue(commandFactory.validStartAndEndTime(validStart, validEnd));
    assertFalse(commandFactory.validStartAndEndTime(invalidStart, validEnd));
    assertFalse(commandFactory.validStartAndEndTime(validStart, invalidEnd));
  }

  @Test
  public void testValidateKeywords() {
    String validInput1 = "create event test on 2025-05-05";
    String validInput2 = "print events from 2025-05-05 to 2025-05-06";

    // Valid cases
    commandFactory.validateKeywords(validInput1);
    commandFactory.validateKeywords(validInput2);

    // Invalid cases
    assertThrows(IllegalArgumentException.class, () -> {
      commandFactory.validateKeywords("create event test on 2025-05-05 from 2025-05-06");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      commandFactory.validateKeywords("print events until 3 times");
    });
  }
}
