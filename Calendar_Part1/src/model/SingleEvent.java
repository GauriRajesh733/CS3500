package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

class SingleEvent extends AEvent {

  public SingleEvent(String subject, LocalDateTime startDate, LocalDateTime endDate) {
    super(subject, startDate, endDate);
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
  public void editSeriesEvent(EventProperty propertyToEdit, String newProperty) {
    this.editSingleEvent(propertyToEdit, newProperty);
  }

  @Override
  public void editEvents(EventProperty propertyToEdit, String newProperty) {
    this.editSingleEvent(propertyToEdit, newProperty);
  }

  @Override
  public ArrayList<AEvent> getEvents() {
    ArrayList<AEvent> events = new ArrayList<>();
    events.add(this);

    return events;
  }


}

