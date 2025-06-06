package model;

import org.junit.Test;

import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public abstract class AEventTest {

  public abstract AEvent createEvent();

  @Test
  public void testEditSingleEvent() {
    AEvent event = createEvent();
    //edit new subject
    event.editSingleEvent(EventProperty.SUBJECT, "New Subject");

    assertEquals("New Subject", event.getSubject());

    //edit new description
    event.editSingleEvent(EventProperty.DESCRIPTION, "New Description");

    //edit new location
    event.editSingleEvent(EventProperty.LOCATION, "online");

    //edit new status
    event.editSingleEvent(EventProperty.STATUS, "public");

    //edit end date
    event.editSingleEvent(EventProperty.END, "2023-12-31T23:59:59");

    assertEquals(
            "- New Subject (online): "
                    + event.getStartDate() + " to 2023-12-31T23:59:59", event.toString());
  }

  @Test
  public void testEditSingleEventMultipleTimes() {
    AEvent event = createEvent();

    // edit subject multiple times
    event.editSingleEvent(EventProperty.SUBJECT, "First Subject");
    assertEquals("First Subject", event.getSubject());

    event.editSingleEvent(EventProperty.SUBJECT, "Second Subject");
    assertEquals("Second Subject", event.getSubject());

    // edit description multiple times
    event.editSingleEvent(EventProperty.DESCRIPTION, "First Description");
    event.editSingleEvent(EventProperty.DESCRIPTION, "Second Description");

    //edit location multiple times
    event.editSingleEvent(EventProperty.LOCATION, "physical");
    event.editSingleEvent(EventProperty.LOCATION, "online");

    //edit status multiple times
    event.editSingleEvent(EventProperty.STATUS, "private");
    event.editSingleEvent(EventProperty.STATUS, "public");

    //edit end date multiple times
    event.editSingleEvent(EventProperty.END, "2023-12-31T23:59");
    event.editSingleEvent(EventProperty.END, "2024-01-01T00:00");

    assertEquals("- Second Subject (online): "
            + event.getStartDate() + " to 2024-01-01T00:00", event.toString());

  }

  @Test
  public void testInvalidInputsForEditSingleEvent() {
    AEvent event = createEvent();

    assertThrows(DateTimeParseException.class, () -> {
      event.editSingleEvent(EventProperty.START, "invalid-date");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      event.editSingleEvent(EventProperty.STATUS, "invalid-status");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      event.editSingleEvent(EventProperty.LOCATION, "invalid-location");
    });
  }

  @Test
  public void testNullEditSingleEvent() {
    AEvent event = createEvent();

    event.editSingleEvent(EventProperty.SUBJECT, "New Subject");
    event.editSingleEvent(EventProperty.DESCRIPTION, null);
    event.editSingleEvent(EventProperty.LOCATION, "online");
    event.editSingleEvent(EventProperty.STATUS, "private");
    event.editSingleEvent(EventProperty.END, "2023-12-31T23:59:59");

    try {
      String string = event.toString();
    } catch (IllegalArgumentException e) {
      assertEquals("Event is missing start date, end date, or subject", e.getMessage());
    }
  }

  @Test
  public void testNullFormatLocation() {
    AEvent event = createEvent();
    event.editSingleEvent(EventProperty.SUBJECT, "New Subject");
    event.editSingleEvent(EventProperty.DESCRIPTION, "description here");
    event.editSingleEvent(EventProperty.STATUS, "private");
    event.editSingleEvent(EventProperty.END, "2023-12-31T23:59:59");

    assertEquals("- New Subject: "
            + event.getStartDate() + " to 2023-12-31T23:59:59", event.toString());
  }

  @Test
  public void testEditSeriesEvent() {
    AEvent event = createEvent();
    event.editSeriesEvent(EventProperty.SUBJECT, "New Series Subject");
    assertEquals("New Series Subject", event.getSubject());

    event.editSeriesEvent(EventProperty.DESCRIPTION, "New Series Description");
    event.editSeriesEvent(EventProperty.LOCATION, "online");
    event.editSeriesEvent(EventProperty.STATUS, "public");
    event.editSeriesEvent(EventProperty.END, "2023-12-31T23:59:59");

    assertEquals("- New Series Subject (online): "
            + event.getStartDate() + " to 2023-12-31T23:59:59", event.toString());
  }
}
