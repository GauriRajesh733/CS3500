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
   * Constructor to create an EditEvents command.
   * @param propertyToEdit the property of the event to edit (e.g., SUBJECT, LOCATION, etc.)
   * @param subject the subject of the event to match for editing
   * @param startDate the start date and time of the events to edit
   * @param newProperty the new value for the specified property
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

