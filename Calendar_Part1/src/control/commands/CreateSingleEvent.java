package control.commands;

import java.time.LocalDateTime;

import control.CalendarCommand;
import model.CalendarModel;
import model.SingleEvent;

public class CreateSingleEvent implements CalendarCommand {
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private final String subject;

  // create all day event
  public CreateSingleEvent(String subject, LocalDateTime startDate) {
    this.subject = subject;
    this.startDate = startDate;
    this.endDate = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 17, 0);
  }

  // create single day event
  public CreateSingleEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    this.subject = subject;
    this.startDate = startDateTime;
    this.endDate = endDateTime;
  }

  @Override
  public void go(CalendarModel m) {
    m.addEvent(new SingleEvent(this.subject, this.startDate, this.endDate));
  }
}
