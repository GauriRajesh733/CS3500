package model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public abstract class AEvent implements Event {
  protected final LocalDateTime startDateTime;
  protected final LocalDateTime endDateTime;
  protected final String subject;
  protected final String description;
  protected final Location location;
  protected final Status status;

  protected AEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime,
                   String description, Location location, Status status) {
    this.subject = subject;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.description = description;
    this.location = location;
    this.status = status;
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

  public abstract AEvent editSingleEvent(EventProperty propertyToEdit, String newProperty);

  public abstract AEvent editSeriesEvent(EventProperty propertyToEdit, String newProperty);

  public abstract AEvent editEvents(EventProperty propertyToEdit, String newProperty);

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


