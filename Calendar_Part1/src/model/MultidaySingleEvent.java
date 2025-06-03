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
    // add first event to calendar with end time as 5pm?

    // for each "in between" day event keep same start and end date as whole event

    // add last event to calendar with start time as 8am?
  }


}
