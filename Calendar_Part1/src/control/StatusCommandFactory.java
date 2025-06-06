package control;

import java.time.LocalDateTime;

import control.commands.CalendarCommand;
import model.CalendarModel;
import view.CalendarView;

/**
 * Class to show the status of a calendar event on a specific date.
 */
class StatusCommandFactory extends ACommandFactory {

  @Override
  public CalendarCommand createCalendarCommand(String input) {
    if (input.contains("on") && input.contains(" show status")) {
      return this.showStatus(input);
    } else {
      throw new IllegalArgumentException("Invalid show status command" + input);
    }
  }

  private CalendarCommand showStatus(String input) {
    int onIndex = searchKeywordIndex(input, "on");

    String eventDateTime = search(
            input, onIndex + 3, input.length(),
            "CalendarCommand ShowCalendarStatus input eventDateTime wrong indexing.");

    if (!validDateTime(eventDateTime)) {
      throw new IllegalArgumentException("Invalid date format.");
    }

    LocalDateTime date = LocalDateTime.parse(eventDateTime);

    return new ShowStatus(date);
  }

  private static class ShowStatus implements CalendarCommand {
    LocalDateTime dateTime;

    public ShowStatus(LocalDateTime dateTime) {
      this.dateTime = dateTime;
    }

    @Override
    public void go(CalendarModel m, CalendarView v) {
      v.showStatus(m.showCalendarStatus(this.dateTime), this.dateTime);
    }
  }
}
