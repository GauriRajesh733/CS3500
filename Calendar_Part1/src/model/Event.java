package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public interface Event {
  void addToCalendar(Map<LocalDateTime, ArrayList<AEvent>> calendar);

  LocalDateTime getStartDate();

  LocalDateTime getEndDate();
}
