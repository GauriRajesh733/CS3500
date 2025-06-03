package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class SeriesEvent extends AMultidayEvent<SeriesEvent> {

  public SeriesEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    super(subject, startDateTime, endDateTime);
  }

  @Override
  public void addToCalendar(Map<LocalDateTime, ArrayList<AEvent>> calendar, Map<String, ArrayList<LocalDateTime>> recurringEvents) {
    // add this single event to calendar
    if (!calendar.containsKey(this.startDateTime)) {
      calendar.put(this.startDateTime, new ArrayList<AEvent>());
    }
    calendar.get(this.startDateTime).add(this);

    // add other events in this series to calendar
    while (this.hasNext()) {
      this.next.addToCalendar(calendar, recurringEvents);
    }
  }
}