package model;

import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CalendarModelImplTest {
  private CalendarModel m;
  private AEvent single, multiday, series;
  private DayOfWeek[] days;

  @Before
  public void setUp() {
    this.m = new CalendarModelImpl();
    this.single = new SingleEvent("single", LocalDateTime.of(2025, 6, 3, 8, 0), LocalDateTime.of(2025, 6, 3, 17, 0), null, null, null);
    this.multiday = new SingleEvent("multiday", LocalDateTime.of(2025, 7, 3, 8, 0), LocalDateTime.of(2025, 7, 5, 17, 0), null, null, null);
    this.days = new DayOfWeek[]{DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
    this.series = new SeriesEvent("series", days, 4, LocalDateTime.of(2025, 8, 1, 5, 0), LocalDateTime.of(2025, 8, 1, 6, 0), null, null, null);
  }

  // NOTE: ADD REMOVE TESTS!
  @Test
  public void testAddEvent() {
    // add single event
    m.addSingleEvent("single", LocalDateTime.of(2025, 6, 3, 8, 0), LocalDateTime.of(2025, 6, 3, 17, 0));

    // verify changes
    assertTrue(m.printEventsForDate(single.getStartDate().toLocalDate()).contains(single.toString()));

    // add multiday event
    m.addSingleEvent("multiday", LocalDateTime.of(2025, 7, 3, 8, 0), LocalDateTime.of(2025, 7, 5, 17, 0));

    // verify changes
    LocalDate currentDate = multiday.getStartDate().toLocalDate();
    while (!currentDate.isAfter(multiday.getEndDate().toLocalDate())) {
      assertTrue(m.printEventsForDate(single.getStartDate().toLocalDate()).contains(single.toString()));
      currentDate = currentDate.plusDays(1);
    }

    // add series event
    m.addSeriesEvent("series", days, 4, LocalDateTime.of(2025, 8, 1, 5, 0), LocalDateTime.of(2025, 8, 1, 6, 0));

    // verify changes
    for (AEvent date : series.getEvents()) {
      assertTrue(m.printEventsForDate(date.getStartDate().toLocalDate()).contains(date.toString()));
    }

    // check that duplicate event with same start date, end date, and subject cannot be added
    try {
      m.addSingleEvent("single", LocalDateTime.of(2025, 6, 3, 8, 0), LocalDateTime.of(2025, 6, 3, 17, 0));
      fail("Should have thrown");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Given event with start date, end date, and subject already exists in calendar",
              e.getMessage());
    }

  }

  @Test
  public void testPrintEventsForDate() {
    // print events for date with no events
    assertTrue(this.m.printEventsForDate(single.getStartDate().toLocalDate()).isEmpty());

    // print events for date with events
    this.m.addSingleEvent("single", LocalDateTime.of(2025, 6, 3, 8, 0), LocalDateTime.of(2025, 6, 3, 17, 0));

    assertEquals(1, this.m.printEventsForDate(single.getStartDate().toLocalDate()).size());

    this.m.addSingleEvent("multiday", LocalDateTime.of(2025, 7, 3, 8, 0), LocalDateTime.of(2025, 7, 5, 17, 0));
    this.m.addSingleEvent("multiday", LocalDateTime.of(2025, 7, 3, 8, 0), LocalDateTime.of(2025, 7, 5, 17, 0).plusHours(2));
    this.m.addSingleEvent("multiday", LocalDateTime.of(2025, 7, 3, 8, 0), LocalDateTime.of(2025, 7, 5, 17, 0).plusHours(4));

    assertEquals(3, this.m.printEventsForDate(multiday.getStartDate().toLocalDate()).size());
  }

  @Test
  public void printEventsUsingInterval() {
    // print events for date range with no events
    assertEquals(new ArrayList<String>(), this.m.printEventsUsingInterval(LocalDateTime.of(2025, 6, 3, 8, 0), LocalDateTime.of(2025, 6, 3, 17, 0)));

    // print events for date range with events
    this.m.addSingleEvent("single", LocalDateTime.of(2025, 6, 3, 8, 0), LocalDateTime.of(2025, 6, 3, 17, 0));
    this.m.addSingleEvent("multiday", LocalDateTime.of(2025, 7, 3, 8, 0), LocalDateTime.of(2025, 7, 5, 17, 0));
    this.m.addSeriesEvent("series", days, 4, LocalDateTime.of(2025, 8, 1, 5, 0), LocalDateTime.of(2025, 8, 1, 6, 0));

    // verify changes (1 single day event + 1 multiday event + 4 events in series = 6 events)
    assertEquals(6, this.m.printEventsUsingInterval(LocalDateTime.of(2022, 6, 3, 8, 0), LocalDateTime.of(2028, 6, 3, 17, 0)).size());
  }

  @Test
  public void editSingleEvent() {
    // no event in calendar to edit
    try {
      m.editSingleEvent(EventProperty.LOCATION, LocalDateTime.of(2025,5,6,0,0), LocalDateTime.of(2025, 5, 6, 5, 0), "name", "online");
      fail("Should have thrown");
    }
    catch(IllegalArgumentException e) {
      assertEquals("No events in calendar with given start date, end date, and subject", e.getMessage());
    }

    // add events to calendar model
    m.addSingleEvent("single", LocalDateTime.of(2025, 6, 3, 8, 0), LocalDateTime.of(2025, 6, 3, 17, 0));
    m.addSingleEvent("multiday", LocalDateTime.of(2025, 7, 3, 8, 0), LocalDateTime.of(2025, 7, 5, 17, 0));
    m.addSeriesEvent("series", days, 4, LocalDateTime.of(2025, 8, 1, 5, 0), LocalDateTime.of(2025, 8, 1, 6, 0));

    // edit single event based on date and subject
    assertNull(single.getDescription());
    m.editSingleEvent(EventProperty.DESCRIPTION, single.getStartDate(), single.getEndDate(), single.getSubject(), "new description");
    // verify changes to event
    assertEquals("new description", single.getDescription());

    // edit multiday event based on date and subject
    m.editSingleEvent(EventProperty.START, multiday.getStartDate(), multiday.getEndDate(),
            multiday.getSubject(), multiday.getStartDate().plusDays(3).toString());
    // verify changes to event
    assertEquals(LocalDateTime.parse("2025-07-06T08:00"), multiday.getStartDate());

    // edit series
    ArrayList<AEvent> seriesEvents = this.series.getEvents();

    m.editSingleEvent(EventProperty.STATUS, series.getStartDate(), series.getEndDate(), series.getSubject(), "public");

    // verify only single event in series changed
    assertEquals(Status.PUBLIC, seriesEvents.get(0).getStatus());

    for (int i = 1; i < seriesEvents.size(); i++) {
      assertNull(seriesEvents.get(i).getStatus());
    }
  }

  @Test
  public void editEvents() {
    // add events to calendar model

    // edit single event based on date and subject

    // verify changes to event

    // no events in calendar to edit

  }

  @Test
  public void editSeries() {
    // add events to calendar model

    // edit single event based on date and subject

    // verify changes to event

    // no event series in calendar to edit

  }

  @Test
  public void showCalendarStatus() {
    // available status if no events on given date

    // add events to calendar

    // busy status if events on given date
  }
}