package control.commands;

import java.time.LocalDateTime;

import control.CalendarCommand;
import model.CalendarModel;

/**
 * Class to show the status of a calendar event on a specific date.
 */
public class ShowCalendarStatus implements CalendarCommand {

  LocalDateTime dateTime;
  /**
   * Constructor for ShowCalendarStatus.
   *
   * @param dateTime The input string containing the date and time to check the status.
   */
  public ShowCalendarStatus(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  private CalendarCommand showStatus(String input) {
    int onIndex = input.indexOf("on");

    String eventDateTime = input.substring(onIndex + 3);

    LocalDateTime date = LocalDateTime.parse(eventDateTime);

    return new ShowCalendarStatus(date);
  }

  //delete date if event is moved and there are no other events on that day
  //show busy if there is an event scheduled on that date, so checks if the date exists if not will return busy
  @Override
  public void go(CalendarModel m) {

  }
}
