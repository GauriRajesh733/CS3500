package control.commands;

import java.time.LocalDateTime;

import model.CalendarModel;
import model.EventProperty;
import view.CalendarView;

/**
 * Command to edit events in the calendar.
 * This command allows modification of specific properties of events
 * based on the provided parameters.
 */
public class EditEvents implements CalendarCommand {
  private final EventProperty propertyToEdit;
  private final String subject;
  private final LocalDateTime startDate;
  private final String newProperty;

  /**
   * Constructor to edit events in the calendar.
   * @param propertyToEdit the property to be edited (e.g., SUBJECT, START_DATE, END_DATE)
   * @param subject the subject of the event to be edited
   * @param startDate the start date and time of the event to be edited
   * @param newProperty the new value for the property being edited
   */
  public EditEvents(
          EventProperty propertyToEdit, String subject,
          LocalDateTime startDate, String newProperty) {
    this.propertyToEdit = propertyToEdit;
    this.subject = subject;
    this.startDate = startDate;
    this.newProperty = newProperty;
  }

  @Override
  public void run(CalendarModel m, CalendarView v) {
    m.editEvents(propertyToEdit, subject, startDate, newProperty);
  }
}

