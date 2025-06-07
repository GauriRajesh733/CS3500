package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

/**
 * Represents an event in a calendar.
 */
public abstract class AEvent implements Event {
  protected final LocalDateTime startDateTime;
  protected final LocalDateTime endDateTime;
  protected final String subject;
  protected final String description;
  protected final Location location;
  protected final Status status;

  /**
   * Constructs an event.
   * @param subject represents subject of event.
   * @param startDateTime represents start date of event.
   * @param endDateTime  represents end date of event.
   * @param description  represents description of event.
   * @param location  represents location of event.
   * @param status  represents status of event.
   */
  protected AEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime,
                   String description, Location location, Status status) {
    this.subject = subject;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.description = description;
    this.location = location;
    this.status = status;
  }

  @Override
  public LocalDateTime getStartDate() {
    return this.startDateTime;
  }

  @Override
  public LocalDateTime getEndDate() {
    return this.endDateTime;
  }

  @Override
  public String getSubject() {
    return this.subject;
  }

  @Override
  public abstract void addToCalendar(Map<LocalDate, ArrayList<AEvent>> calendar);

  @Override
  public boolean sameEvent(String subject, LocalDateTime startDate, LocalDateTime endDate) {
    return this.subject.equals(subject) && this.startDateTime.equals(startDate) &&
            this.endDateTime.equals(endDate);
  }

  @Override
  public abstract AEvent editSingleEvent(EventProperty propertyToEdit, String newProperty);

  @Override
  public abstract AEvent editSeriesEvent(EventProperty propertyToEdit, String newProperty);

  @Override
  public abstract AEvent editEvents(EventProperty propertyToEdit, String newProperty);

  @Override
  public boolean sameSubjectAndStart(String subject, LocalDateTime startDate) {
    return this.subject.equals(subject) && this.startDateTime.equals(startDate);
  }

  @Override
  public String toString() {
    if (this.startDateTime == null || this.endDateTime == null || this.subject == null) {
      throw new IllegalArgumentException("Event is missing start date, end date, or subject");
    }

    return "- " + this.subject + this.formatLocation() + this.startDateTime + " to "
            + this.endDateTime;
  }

  /**
   * Formats the location of the event for display in view.
   * @return String representation of location part of display output.
   */
  private String formatLocation() {
    if (this.location == null) {
      return ": ";
    }
    return " (" + this.location.toInput() + "): ";
  }

  @Override
  public abstract ArrayList<AEvent> getEvents();

  @Override
  public String getDescription() {
    return this.description;
  }

  @Override
  public Location getLocation() {
    return this.location;
  }

  @Override
  public Status getStatus() {
    return this.status;
  }
}


