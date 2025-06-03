package model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

// NOTE: add builder pattern!
public abstract class AEvent implements Event {
  protected final LocalDateTime startDateTime;
  protected final LocalDateTime endDateTime;
  protected final String subject;
  protected final String description;
  protected final Location location;
  protected final Status status;

  protected AEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime, String description, Location location, Status status) {
    this.subject = subject;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.description = description;
    this.location = location;
    this.status = status;
  }

  public LocalDateTime getStartDate() {
    return LocalDateTime.of(startDateTime.getYear(), startDateTime.getMonthValue(),
            startDateTime.getDayOfMonth(), startDateTime.getHour(), startDateTime.getMinute());
  }

  public LocalDateTime getEndDate() {
    return LocalDateTime.of(endDateTime.getYear(), endDateTime.getMonthValue(),
            endDateTime.getDayOfMonth(), endDateTime.getHour(), endDateTime.getMinute());
  }

  @Override
  public abstract void addToCalendar(Map<LocalDateTime, ArrayList<AEvent>> calendar,
                                     Map<String, ArrayList<LocalDateTime>> recurringEvents);

}


