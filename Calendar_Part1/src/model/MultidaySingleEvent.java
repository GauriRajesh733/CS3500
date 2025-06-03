package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class MultidaySingleEvent extends AMultidayEvent<MultidaySingleEvent> {

  public MultidaySingleEvent(String subject, LocalDateTime startDate, LocalDateTime endDate) {
    super(subject, startDate, endDate);
  }

  @Override
  public void addToCalendar(Map<LocalDateTime, ArrayList<AEvent>> calendar, Map<String, ArrayList<LocalDateTime>> recurringEvents) {
    MultidaySingleEvent currentDay = new MultidaySingleEvent(this.subject, this.startDateTime,
            this.endDateTime);
    LocalDateTime currentDate = this.startDateTime;

    while (!currentDate.isAfter(this.endDateTime)) {
      if (!calendar.containsKey(currentDate)) {
        calendar.put(currentDate, new ArrayList<AEvent>());
      }
      calendar.get(currentDate).add(this);

      currentDate = currentDate.plusDays(1);
    }
  }


}
