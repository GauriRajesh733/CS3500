package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

class SingleEvent extends AEvent {

  public SingleEvent(String subject, LocalDateTime startDate, LocalDateTime endDate,
                     String description, Location location, Status status) {
    super(subject, startDate, endDate, description, location, status);
  }

  @Override
  public void addToCalendar(Map<LocalDate, ArrayList<AEvent>> calendar) {
    LocalDate currentDate = this.startDateTime.toLocalDate();

    // add event to all dates in between if multiday event
    while (!currentDate.isAfter(this.endDateTime.toLocalDate())) {
      if (!calendar.containsKey(currentDate)) {
        calendar.put(currentDate, new ArrayList<>());
      }
      calendar.get(currentDate).add(this);
      currentDate = currentDate.plusDays(1);
    }
  }

  @Override
  public void addSingleEventToCalendar(Map<LocalDate, ArrayList<AEvent>> calendar) {
    this.addToCalendar(calendar);
  }

  public AEvent editSingleEvent(EventProperty propertyToEdit, String newProperty) {
    switch (propertyToEdit) {
      case START:
        LocalDateTime newStartDateTime = LocalDateTime.parse(newProperty);

        if (newStartDateTime.isAfter(this.endDateTime)) {
          throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return new SingleEvent(
                this.subject, newStartDateTime, this.endDateTime,
                this.description, this.location, this.status);
      case END:
        LocalDateTime newEndDate = LocalDateTime.parse(newProperty);

        if (newEndDate.isBefore(this.startDateTime)) {
          throw new IllegalArgumentException("End date cannot be before end date.");
        }

        return new SingleEvent(
                this.subject, this.startDateTime, newEndDate,
                this.description, this.location, this.status);
      case DESCRIPTION:
        return new SingleEvent(
                this.subject, this.startDateTime, this.endDateTime,
                newProperty, this.location, this.status);
      case LOCATION:
        return new SingleEvent(
                this.subject, this.startDateTime, this.endDateTime,
                this.description, Location.fromInput(newProperty), this.status);
      case STATUS:
        return new SingleEvent(
                this.subject, this.startDateTime, this.endDateTime,
                this.description, this.location, Status.fromInput(newProperty));
      case SUBJECT:
        return new SingleEvent(
                newProperty, this.startDateTime, this.endDateTime,
                this.description, this.location, this.status);
      default:
        throw new IllegalArgumentException("Invalid property: " + propertyToEdit);
    }
  }

  @Override
  public AEvent editSeriesEvent(EventProperty propertyToEdit, String newProperty) {
    return this.editSingleEvent(propertyToEdit, newProperty);
  }

  @Override
  public AEvent editEvents(EventProperty propertyToEdit, String newProperty) {
    return this.editSingleEvent(propertyToEdit, newProperty);
  }

  @Override
  public ArrayList<AEvent> getEvents() {
    ArrayList<AEvent> events = new ArrayList<>();
    events.add(this);

    return events;
  }


}

