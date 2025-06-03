package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class SingleEvent extends AEvent {

  public SingleEvent(String subject, LocalDateTime startDate, LocalDateTime endDate) {
    super(subject, startDate, endDate, null, null, null);
  }

  // NOTE: should we just put the same start and end date but for different dates if it spans multiple days?
  // OR should it be all day event for in between days??
  @Override
  public void addToCalendar(Map<LocalDateTime, ArrayList<AEvent>> calendar, Map<String, ArrayList<LocalDateTime>> recurringEvents) {
    LocalDateTime currentDate = this.startDateTime;

    // multi spanning event!
    while(!currentDate.isAfter(this.endDateTime)) {
      LocalDateTime nextDate = currentDate.plusDays(1);
      calendar.get(nextDate).add(this);
    }
  }


}
