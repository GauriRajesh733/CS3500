package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class SingleEvent extends AEvent {

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

  public void editSingleEvent(EventProperty propertyToEdit, String newProperty) {
    switch (propertyToEdit) {
      // if not editing start or end date call super method
      case SUBJECT:
      case DESCRIPTION:
      case LOCATION:
      case STATUS:
      case END:
        super.editSingleEvent(propertyToEdit, newProperty);
        break;
        // if editing start or end date update this event
      case START:
        this.startDateTime = LocalDateTime.parse(newProperty);
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


}

