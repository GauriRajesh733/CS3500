package control.commands;

import model.CalendarModel;
import view.CalendarView;

public interface CalendarCommand {
  void run(CalendarModel m, CalendarView v);
}
