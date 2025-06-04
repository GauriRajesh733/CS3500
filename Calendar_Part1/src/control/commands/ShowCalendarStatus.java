package control.commands;

import java.time.LocalDateTime;

import control.ACommandFactory;
import control.CalendarCommand;
import model.CalendarModel;

/**
 * Class to show the status of a calendar event on a specific date.
 */
public class ShowCalendarStatus extends ACommandFactory implements CalendarCommand {

  private final LocalDateTime dateTime;
  /**
   * Constructor for ShowCalendarStatus.
   *
   * @param dateTime The input string containing the date and time to check the status.
   */
  public ShowCalendarStatus(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  @Override
  public CalendarCommand createCalendarCommand(String input) {
    int onIndex = input.indexOf("on");

    String eventDateTime = input.substring(onIndex + 3);

    if (!validDateTime(eventDateTime)) {
      throw new IllegalArgumentException("Invalid date format.");
    }

    LocalDateTime date = LocalDateTime.parse(eventDateTime);

    return new ShowCalendarStatus(date);
  }

  @Override
  public void go(CalendarModel m) {
    m.showCalendarStatus(this.dateTime);
  }
}
