package model;

import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class EventTests {
  AEvent single, multiday, series;

  @Before
  public void setUp() {
    this.single = new SingleEvent("class", LocalDateTime.of(2025, 9, 9, 8, 0),
            LocalDateTime.of(2025, 9, 9, 10, 0), null, null, null);
    this.multiday = new SingleEvent("vacation", LocalDateTime.of(2025, 9, 9, 0, 0),
            LocalDateTime.of(2025, 9, 16, 0, 0), null, null, null);
    DayOfWeek[] days = {DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY};
    this.series = new SeriesEvent("lab", days, 2, LocalDateTime.of(2025, 1, 6, 14, 0),
            LocalDateTime.of(2025, 1, 6, 16, 30), null, null, null);
  }

  @Test
  public void getStartDate() {
    assertEquals(LocalDateTime.of(2025, 9, 9, 8, 0), this.single.getStartDate());
    assertEquals(LocalDateTime.of(2025, 9, 9, 0, 0), this.multiday.getStartDate());
    assertEquals(LocalDateTime.of(2025, 1, 6, 14, 0), this.series.getStartDate());
  }

  @Test
  public void getEndDate() {
    assertEquals(LocalDateTime.of(2025, 9, 9, 10, 0), this.single.getEndDate());
    assertEquals(LocalDateTime.of(2025, 9, 16, 0, 0), this.multiday.getEndDate());
    assertEquals(LocalDateTime.of(2025, 1, 6, 16, 30), this.series.getEndDate());
  }

  @Test
  public void getSubject() {
    assertEquals("class", this.single.getSubject());
    assertEquals("vacation", this.multiday.getSubject());
    assertEquals("lab", this.series.getSubject());
  }

  @Test
  public void addToCalendar() {
    HashMap<LocalDate, ArrayList<AEvent>> calendar = new HashMap<>();

    // add events to calendar
    this.single.addToCalendar(calendar);
    this.multiday.addToCalendar(calendar);
    this.series.addToCalendar(calendar);

    // verify that events added to calendar
    assertTrue(calendar.get(LocalDate.of(2025, 9, 9)).contains(this.single));

    assertTrue(calendar.get(LocalDate.of(2025, 9, 9)).contains(this.multiday));
    assertTrue(calendar.get(LocalDate.of(2025, 9, 10)).contains(this.multiday));
    assertTrue(calendar.get(LocalDate.of(2025, 9, 11)).contains(this.multiday));
    assertTrue(calendar.get(LocalDate.of(2025, 9, 12)).contains(this.multiday));
    assertTrue(calendar.get(LocalDate.of(2025, 9, 13)).contains(this.multiday));
    assertTrue(calendar.get(LocalDate.of(2025, 9, 14)).contains(this.multiday));
    assertTrue(calendar.get(LocalDate.of(2025, 9, 15)).contains(this.multiday));
    assertTrue(calendar.get(LocalDate.of(2025, 9, 16)).contains(this.multiday));

    ArrayList<AEvent> seriesEvents = this.series.getEvents();

    assertTrue(calendar.get(LocalDate.of(2025, 1, 6)).contains(seriesEvents.get(0)));
    assertTrue(calendar.get(LocalDate.of(2025, 1, 8)).contains(seriesEvents.get(1)));
  }

  @Test
  public void sameEvent() {
    // true cases
    assertTrue(this.single.sameEvent("class", LocalDateTime.of(2025, 9, 9, 8, 0),
            LocalDateTime.of(2025, 9, 9, 10, 0)));

    // false cases
    assertFalse(this.single.sameEvent("class", LocalDateTime.of(2025, 9, 9, 8, 0),
            LocalDateTime.of(2026, 9, 9, 10, 0)));
    assertFalse(this.single.sameEvent("different", LocalDateTime.of(2026, 9, 9, 8, 0),
            LocalDateTime.of(2025, 9, 9, 10, 0)));
    assertFalse(this.single.sameEvent("class", LocalDateTime.of(2026, 9, 9, 8, 0),
            LocalDateTime.of(2025, 9, 9, 10, 0)));
  }

  // NOTE: this also tests methods to get start date, end date, status, location, etc.
  @Test
  public void editSingleEvent() {
    // edit single/multiday event 2025, 9, 9, 8, 0
    this.single.editSingleEvent(EventProperty.START, "2025-09-09T09:30");
    assertEquals("2025-09-09T09:30", this.single.getStartDate().toString());

    this.single.editSingleEvent(EventProperty.END, "2027-10-09T00:00");
    assertEquals("2027-10-09T00:00", this.single.getEndDate().toString());

    this.single.editSingleEvent(EventProperty.LOCATION, "online");
    assertEquals(Location.ONLINE, this.single.getLocation());

    this.single.editSingleEvent(EventProperty.DESCRIPTION, "at the airport");
    assertEquals("at the airport", this.single.getDescription());

    this.single.editSingleEvent(EventProperty.STATUS, "public");
    assertEquals(Status.PUBLIC, this.single.getStatus());

    this.single.editSingleEvent(EventProperty.SUBJECT, "shopping");
    assertEquals("shopping", this.single.getSubject());

    // edit series event
    this.series.editSingleEvent(EventProperty.START, "2027-10-09T00:00");
    assertEquals("2027-10-09T00:00", this.series.getStartDate().toString());
    // verify series event unlinked if changing start date (2 events to 1 event in series)
    assertEquals(1, this.series.getEvents().size());

    // verify series event not unlinked if not changing start date
    this.setUp();
    assertEquals(2, this.series.getEvents().size());
    this.series.editSingleEvent(EventProperty.END, "2027-10-09T00:00");
    assertEquals("2027-10-09T00:00", this.series.getEndDate().toString());
    assertEquals(2, this.series.getEvents().size());

    this.series.editSingleEvent(EventProperty.LOCATION, "online");
    assertEquals(Location.ONLINE, this.series.getLocation());
    assertEquals(2, this.series.getEvents().size());

    this.series.editSingleEvent(EventProperty.DESCRIPTION, "at the airport");
    assertEquals("at the airport", this.series.getDescription());
    assertEquals(2, this.series.getEvents().size());

    this.series.editSingleEvent(EventProperty.STATUS, "public");
    assertEquals(Status.PUBLIC, this.series.getStatus());
    assertEquals(2, this.series.getEvents().size());

    this.series.editSingleEvent(EventProperty.SUBJECT, "shopping");
    assertEquals("shopping", this.series.getSubject());
    assertEquals(2, this.series.getEvents().size());
  }

  @Test
  public void editSeriesEvent() {
    // edit single/multiday event (editSeriesEvent has same effect as editSingleEvent)
    this.single.editSeriesEvent(EventProperty.START, "2025-09-09T09:30");
    assertEquals("2025-09-09T09:30", this.single.getStartDate().toString());

    this.single.editSeriesEvent(EventProperty.END, "2027-10-09T00:00");
    assertEquals("2027-10-09T00:00", this.single.getEndDate().toString());

    this.single.editSeriesEvent(EventProperty.LOCATION, "online");
    assertEquals(Location.ONLINE, this.single.getLocation());

    this.single.editSeriesEvent(EventProperty.DESCRIPTION, "at the airport");
    assertEquals("at the airport", this.single.getDescription());

    this.single.editSeriesEvent(EventProperty.STATUS, "public");
    assertEquals(Status.PUBLIC, this.single.getStatus());

    this.single.editSeriesEvent(EventProperty.SUBJECT, "shopping");
    assertEquals("shopping", this.single.getSubject());

    // edit series event
    this.series.editSeriesEvent(EventProperty.START, "2027-10-09T00:00");
    assertEquals("2027-10-09T00:00", this.series.getStartDate().toString());
    // verify series event not unlinked since all series change start date
    assertEquals(2, this.series.getEvents().size());

    // verify series event not unlinked if not changing start date
    this.setUp();
    assertEquals(2, this.series.getEvents().size());
    this.series.editSeriesEvent(EventProperty.END, "2027-10-09T00:00");
    assertEquals("2027-10-09T00:00", this.series.getEndDate().toString());
    assertEquals(2, this.series.getEvents().size());

    this.series.editSeriesEvent(EventProperty.LOCATION, "online");
    assertEquals(Location.ONLINE, this.series.getLocation());
    assertEquals(2, this.series.getEvents().size());

    this.series.editSeriesEvent(EventProperty.DESCRIPTION, "at the airport");
    assertEquals("at the airport", this.series.getDescription());
    assertEquals(2, this.series.getEvents().size());

    this.series.editSeriesEvent(EventProperty.STATUS, "public");
    assertEquals(Status.PUBLIC, this.series.getStatus());
    assertEquals(2, this.series.getEvents().size());

    this.series.editSeriesEvent(EventProperty.SUBJECT, "shopping");
    assertEquals("shopping", this.series.getSubject());
    assertEquals(2, this.series.getEvents().size());
  }

  @Test
  public void editEvents() {
    // edit single/multiday event (editEvents has same effect as editSingleEvent)
    this.single.editEvents(EventProperty.START, "2027-10-09T00:00");
    assertEquals("2027-10-09T00:00", this.single.getStartDate().toString());

    this.single.editEvents(EventProperty.END, "2027-10-09T00:00");
    assertEquals("2027-10-09T00:00", this.single.getEndDate().toString());

    this.single.editEvents(EventProperty.LOCATION, "online");
    assertEquals(Location.ONLINE, this.single.getLocation());

    this.single.editEvents(EventProperty.DESCRIPTION, "at the airport");
    assertEquals("at the airport", this.single.getDescription());

    this.single.editEvents(EventProperty.STATUS, "public");
    assertEquals(Status.PUBLIC, this.single.getStatus());

    this.single.editEvents(EventProperty.SUBJECT, "shopping");
    assertEquals("shopping", this.single.getSubject());

    // edit series event
    this.series.editEvents(EventProperty.START, "2027-10-09T00:00");
    assertEquals("2027-10-09T00:00", this.series.getStartDate().toString());
    // verify series event not unlinked since first event in series
    assertEquals(2, this.series.getEvents().size());

    // verify series event not unlinked if not changing start date
    this.setUp();
    assertEquals(2, this.series.getEvents().size());
    this.series.editEvents(EventProperty.END, "2027-10-09T00:00");
    assertEquals("2027-10-09T00:00", this.series.getEndDate().toString());
    assertEquals(2, this.series.getEvents().size());

    this.series.editEvents(EventProperty.LOCATION, "online");
    assertEquals(Location.ONLINE, this.series.getLocation());
    assertEquals(2, this.series.getEvents().size());

    this.series.editEvents(EventProperty.DESCRIPTION, "at the airport");
    assertEquals("at the airport", this.series.getDescription());
    assertEquals(2, this.series.getEvents().size());

    this.series.editEvents(EventProperty.STATUS, "public");
    assertEquals(Status.PUBLIC, this.series.getStatus());
    assertEquals(2, this.series.getEvents().size());

    this.series.editEvents(EventProperty.SUBJECT, "shopping");
    assertEquals("shopping", this.series.getSubject());
    assertEquals(2, this.series.getEvents().size());
  }

  @Test
  public void sameSubjectAndStart() {
    // true cases
    assertTrue(this.single.sameSubjectAndStart("class", LocalDateTime.parse("2025-09-09T08:00")));

    // false cases
    assertFalse(this.single.sameSubjectAndStart("class2", LocalDateTime.parse("2025-09-09T08:00")));
    assertFalse(this.single.sameSubjectAndStart("class", LocalDateTime.parse("2025-09-09T09:00")));
    assertFalse(this.single.sameSubjectAndStart("class2", LocalDateTime.parse("2025-09-09T12:00")));
  }

  @Test
  public void testToString() {
    assertEquals("- class: 2025-09-09T08:00 to 2025-09-09T10:00", this.single.toString());
    assertEquals("- vacation: 2025-09-09T00:00 to 2025-09-16T00:00", this.multiday.toString());
    assertEquals("- lab: 2025-01-06T14:00 to 2025-01-06T16:30", this.series.toString());

    AEvent invalidSingle1 = new SingleEvent(
            null, LocalDateTime.parse("2025-09-09T08:00"), LocalDateTime.now(), null, null, null);
    try {
      String s = invalidSingle1.toString();
      fail("Should have thrown an exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Event is missing start date, end date, or subject", e.getMessage());
    }

    AEvent invalidSingle2 = new SingleEvent("subject", null, LocalDateTime.now(), null, null, null);
    try {
      String s = invalidSingle2.toString();
      fail("Should have thrown an exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Event is missing start date, end date, or subject", e.getMessage());
    }

    AEvent invalidSingle3 = new SingleEvent(
            "subject", LocalDateTime.parse("2025-09-09T08:00"), null, null, null, null);
    try {
      String s = invalidSingle3.toString();
      fail("Should have thrown an exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Event is missing start date, end date, or subject", e.getMessage());
    }
  }

  @Test
  public void getEvents() {
    // get events should return a list with only one event for single events
    ArrayList<AEvent> singleEvents = new ArrayList<AEvent>();
    singleEvents.add(this.single);
    assertEquals(singleEvents, this.single.getEvents());

    ArrayList<AEvent> multidayEvents = new ArrayList<AEvent>();
    multidayEvents.add(this.multiday);
    assertEquals(multidayEvents, this.multiday.getEvents());

    ArrayList<AEvent> seriesEvents = this.series.getEvents();

    // get events should return a list with all the events in a series
    assertTrue(
            seriesEvents.get(0).sameEvent(
                    "lab", LocalDateTime.of(2025, 1, 6, 14, 0),
                    LocalDateTime.of(2025, 1, 6, 16, 30)));
    assertTrue(
            seriesEvents.get(1).sameEvent(
                    "lab", LocalDateTime.of(2025, 1, 8, 14, 0),
                    LocalDateTime.of(2025, 1, 8, 16, 30)));
    // check for correct number of events in series
    assertEquals(2, seriesEvents.size());
  }
}
