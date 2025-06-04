package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;

public class CalendarModelImpl implements CalendarModel {
  private Map<LocalDateTime, ArrayList<AEvent>> calendar;
  private Map<String, ArrayList<LocalDateTime>> events;


  // NOTE: check if subject date repeated!
  // events need to be in order?
  public CalendarModelImpl() {
    // add comparator to treemap!
    this.calendar = new TreeMap<LocalDateTime, ArrayList<AEvent>>();
    this.recurringEvents = new HashMap<String, ArrayList<LocalDateTime>>();
  }

  @Override
  public void addEvent(AEvent event) {
    event.addToCalendar(this.calendar, this.recurringEvents);
  }

  //addSingleEvent
  //addSeriesEvent

  @Override
  public List<AEvent> printEventsForDate(LocalDate date) {
    List<AEvent> events = new ArrayList<>();
    for (Map.Entry<LocalDateTime, ArrayList<AEvent>> entry : this.calendar.entrySet()) {
      if (entry.getKey().toLocalDate().equals(date)) {
        events.addAll(entry.getValue());
      }
    }
    return events;
  }

  @Override
  public List<AEvent> printEventsUsingInterval(LocalDateTime start, LocalDateTime end) {
    List<AEvent> events = new ArrayList<>();
    for (Map.Entry<LocalDateTime, ArrayList<AEvent>> entry : this.calendar.entrySet()) {
      LocalDateTime eventStart = entry.getKey();
      if ((eventStart.isAfter(start) || eventStart.isEqual(start)) &&
              (eventStart.isBefore(end) || eventStart.isEqual(end))) {
        events.addAll(entry.getValue());
      }
    }
    return events;
  }
}
