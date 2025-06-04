package control.commands;

import java.time.LocalDateTime;

import control.CalendarCommand;
import model.CalendarModel;
import model.EventProperty;
import view.CalendarView;

public class EditSeries implements CalendarCommand {
  private final EventProperty propertyToEdit;
  private final String subject;
  private final LocalDateTime startDate;
  private final String newProperty;

  public EditSeries(EventProperty propertyToEdit, String subject, LocalDateTime startDate, String newProperty) {
    this.propertyToEdit = propertyToEdit;
    this.subject = subject;
    this.startDate = startDate;
    this.newProperty = newProperty;
  }

  @Override
  public void go(CalendarModel m, CalendarView v) {
    m.editSeries(this.propertyToEdit, this.subject, this.startDate, this.newProperty);
  }
}
