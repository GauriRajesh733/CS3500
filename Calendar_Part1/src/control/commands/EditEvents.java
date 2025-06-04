package control.commands;

import java.time.LocalDateTime;

import control.CalendarCommand;
import model.CalendarModel;
import model.EventProperty;

public class EditEvents implements CalendarCommand {
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
  public void go(CalendarModel m) {
    m.editEvents(propertyToEdit, subject, startDate, newProperty);
  }
}

