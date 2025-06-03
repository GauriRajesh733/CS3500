package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;

public class CalendarModelImpl implements CalendarModel {
  private Map<LocalDateTime, ArrayList<AEvent>> calendar;
  private Map<String, ArrayList<LocalDateTime>> recurringEvents;


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
}
