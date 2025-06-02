package control.commands;

import control.CalendarCommand;
import model.CalendarModel;
import model.Date;
import model.DayOfWeek;

public class CreateEventSeries implements CalendarCommand {
  private String subject;
  private Date startDateTime; // YYYY-MM-DD-HH-MM
  private Date endDateTime; // YYYY-MM-DD-HH-MM
  private Date startDate; // YYYY-MM-DD
  private Date endDate; // YYYY-MM-DD
  private Integer occurrences; // repeats N times
  private DayOfWeek[] daysOfWeek;

  public CreateEventSeries(String subject, Date startDateTime, Date endDateTime, Date startDate, Date endDate, int occurrences, DayOfWeek[] daysOfWeek) {
    this.subject = subject;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.startDate = startDate;
    this.endDate = endDate;
    this.occurrences = occurrences;
    this.daysOfWeek = daysOfWeek;
  }

  @Override
  public void go(CalendarModel m) {
    //m.add to calendar me
  }
}
