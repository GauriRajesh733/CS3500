package control.commands;

import java.time.LocalDateTime;

import model.CalendarModel;
import model.EventProperty;
import view.CalendarView;

class EditEvents implements CalendarCommand {
  private final EventProperty propertyToEdit;
  private final String subject;
  private final LocalDateTime startDate;
  private final String newProperty;

  public EditEvents(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty) {
    this.propertyToEdit = propertyToEdit;
    this.subject = subject;
    this.startDate = startDate;
    this.newProperty = newProperty;
  }

  @Override
  public void go(CalendarModel m, CalendarView v) {
    m.editEvents(propertyToEdit, subject, startDate, newProperty);
  }
}

