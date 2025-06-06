package control.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.CalendarModel;
import view.CalendarView;

public class CreateSingleEvent implements CalendarCommand {
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private final String subject;

  // create all day event
  public CreateSingleEvent(String subject, LocalDate startDate) {
    this.subject = subject;
    this.startDate = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
            startDate.getDayOfMonth(), 8, 0);
    ;
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
  public void run(CalendarModel m, CalendarView v) {
    m.addSingleEvent(this.subject, this.startDate, this.endDate);
  }
}
