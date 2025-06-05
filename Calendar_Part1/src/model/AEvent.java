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
    return this.startDateTime;
  }

  public LocalDateTime getEndDate() {
    return this.endDateTime;
  }

  public String getSubject() {
    return this.subject;
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
        break;
      case DESCRIPTION:
        this.description = newProperty;
        break;
      case LOCATION:
        this.location = Location.fromInput(newProperty);
        break;
      case STATUS:
        this.status = Status.fromInput(newProperty);
        break;
      case END:
        this.endDateTime = LocalDateTime.parse(newProperty);
        break;
      case START:
        this.startDateTime = LocalDateTime.parse(newProperty);
        break;
    }
  }

  public abstract void editSeriesEvent(EventProperty propertyToEdit, String newProperty);

  public abstract void editEvents(EventProperty propertyToEdit, String newProperty);

  public boolean sameSubjectAndStart(String subject, LocalDateTime startDate) {
    return this.subject.equals(subject) && this.startDateTime.equals(startDate);
  }

  @Override
  public String toString() {
    if (this.startDateTime == null || this.endDateTime == null || this.subject == null) {
      throw new IllegalArgumentException("Event is missing start date, end date, or subject");
    }

    return "- " + this.subject + this.formatLocation() + this.startDateTime + " to " + this.endDateTime;
  }

  private String formatLocation() {
    if (this.location == null) {
      return ": ";
    }
    return " (" + this.location.toInput() + "): ";
  }

  public abstract ArrayList<AEvent> getEvents();

  public String getDescription() {
    return this.description;
  }

  public Location getLocation() {
    return this.location;
  }

  public Status getStatus() {
    return this.status;
  }

}


