package view;

import java.util.ArrayList;

import model.AEvent;

public interface CalendarView {
  void showEventsOnDate(ArrayList<AEvent> events);

  void showEventsInRange(ArrayList<AEvent> events);

  void showStatus(boolean status);
}
