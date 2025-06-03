package control.commands;

import control.CalendarCommand;
import model.CalendarModel;

public class ShowCalendarStatus implements CalendarCommand {

  public ShowCalendarStatus(String input) {

  }

  //delete date if event is moved and there are no other events on that day
  //show busy if there is an event scheduled on that date, so checks if the date exists if not will return busy
  @Override
  public void go(CalendarModel m) {

  }
}
