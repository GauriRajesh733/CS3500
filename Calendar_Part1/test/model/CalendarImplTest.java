package model;

import org.junit.Test;

import java.time.ZoneId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for CalendarImpl.
 */
public class CalendarImplTest {

  @Test
  public void testCalendarImpl() {
    Calendar calendar = new CalendarImpl("Test Calendar", ZoneId.of("EST"));

    assertEquals("Test Calendar", calendar.getName());
    assertEquals(ZoneId.of("EST"), calendar.getTimeZone());
    assertNotNull(calendar.getModel());
  }

  @Test
  public void testSetNameAndTimeZone() {
    Calendar calendar = new CalendarImpl("Initial Calendar", ZoneId.of("UTC"));

    calendar.setName("Updated Calendar");
    calendar.setTimeZone(ZoneId.of("PST"));

    assertEquals("Updated Calendar", calendar.getName());
    assertEquals(ZoneId.of("PST"), calendar.getTimeZone());
  }

  @Test
  public void invalidZoneIdTest() {
    Calendar calendar = new CalendarImpl("Invalid Zone Test", ZoneId.of("UTC"));

    try {
      calendar.setTimeZone(ZoneId.of("INVALID_ZONE"));
    } catch (Exception e) {
      assertEquals("Invalid time zone ID: INVALID_ZONE", e.getMessage());
    }
  }
}