package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalendarModelImpl implements CalendarModel {
  private final Map<LocalDateTime, ArrayList<AEvent>> calendar;

  public CalendarModelImpl() {
    // add comparator to treemap!
    this.calendar = new HashMap<>();
  }

  @Override
  public void addEvent(AEvent event) {
    // check for repeated date??
    event.addToCalendar(this.calendar);
  }

  @Override
  public void editSingleEvent(EventProperty propertyToEdit, LocalDateTime startDate, LocalDateTime endDate, String subject, String newProperty) {

  }

  @Override
  public void editEvents(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty) {

  }

  @Override
  public void editSeries(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty) {

  }

  private AEvent findEvent();

  // find date

  // find subject

  // if it exists then update

  // if editing non-date property just mutate or create new event

  // if editing date property

}
