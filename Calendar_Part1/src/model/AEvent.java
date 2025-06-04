package model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

// NOTE: add builder pattern!
public abstract class AEvent implements Event {
  protected LocalDateTime startDateTime;
  protected LocalDateTime endDateTime;
  protected String subject;
  protected String description;
  protected Location location;
  protected Status status;

  protected AEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    this.subject = subject;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.description = null;
    this.location = null;
    this.status = null;
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
  public abstract void addToCalendar(Map<LocalDate, ArrayList<AEvent>> calendar);

  public boolean sameEvent(String subject, LocalDateTime startDate, LocalDateTime endDate) {
    return this.subject.equals(subject) && this.startDateTime.equals(startDate) && this.endDateTime.equals(endDate);
  }

  public void editSingleEvent(EventProperty propertyToEdit, String newProperty) {
    // only edit property if it is not start date
    switch (propertyToEdit) {
      case SUBJECT:
        this.subject = newProperty;
      case DESCRIPTION:
        this.description = newProperty;
      case LOCATION:
        this.location = Location.fromInput(newProperty);
      case STATUS:
        this.status = Status.fromInput(newProperty);
      case END:
        this.endDateTime = LocalDateTime.parse(newProperty);
    }
  }

  public abstract void editSeriesEvent(EventProperty propertyToEdit, String newProperty);

  public abstract void editEvents(EventProperty propertyToEdit, String newProperty);

  public boolean sameSubjectAndStart(String subject, LocalDateTime startDate) {
    return this.subject.equals(subject) && this.startDateTime.equals(startDate);
  }


}


