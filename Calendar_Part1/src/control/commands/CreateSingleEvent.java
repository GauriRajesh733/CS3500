package control.commands;

import control.CalendarCommand;
import model.CalendarModel;
import model.Date;
import model.SingleEvent;

public class CreateSingleEvent implements CalendarCommand {
  private final Date startDate;
  private final Date endDate;
  private final String subject;

  public CreateSingleEvent(String subject, Date startDateTime, Date endDateTime, Date startDate) {

    if (startDateTime == null && startDate == null) {
      throw new IllegalArgumentException("Start date cannot be null");
    }

    if (endDateTime == null) {
      this.endDate = new Date(startDate.getYear(), startDate.getMonth(), startDate.getDay(), 17, 0);
    }
    else {
      this.endDate = endDateTime;
    }

    if (startDate == null) {
      this.startDate = startDateTime;
    }
    else {
      this.startDate = new Date(startDate.getYear(), startDate.getMonth(), startDate.getDay(), 8, 0);
    }

    this.subject = subject;
  }

  @Override
  public void go(CalendarModel m) {
    m.addEvent(new SingleEvent(this.subject, this.startDate, this.endDate));
  }
}
