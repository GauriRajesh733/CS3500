package control.commands;

import java.time.LocalDateTime;

import model.CalendarModel;
import model.EventProperty;
import view.CalendarView;

class EditSingleEvent implements CalendarCommand {
  private final EventProperty propertyToEdit;
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private final String subject;
  private final String newProperty;

  public EditSingleEvent(EventProperty propertyToEdit, LocalDateTime startDate,
                         LocalDateTime endDate, String subject, String newProperty) {
    this.propertyToEdit = propertyToEdit;
    this.startDate = startDate;
    this.endDate = endDate;
    this.subject = subject;
    this.newProperty = newProperty;
  }

  @Override
  public void go(CalendarModel m, CalendarView v) {
    m.editSingleEvent(this.propertyToEdit, this.startDate, this.endDate, this.subject, this.newProperty);
  }
}
