package control.commands;

import model.CalendarModel;
import view.CalendarView;

public interface CalendarCommand {
  void go(CalendarModel m, CalendarView v);
}
