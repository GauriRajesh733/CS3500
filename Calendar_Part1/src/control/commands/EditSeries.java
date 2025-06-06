package control.commands;

import java.time.LocalDateTime;

import model.CalendarModel;
import model.EventProperty;
import view.CalendarView;

/**
 * Command to edit series of events in the calendar.
 * This command allows modification of specific properties of events series
 * based on the provided parameters.
 */
public class EditSeries implements CalendarCommand {
  private final EventProperty propertyToEdit;
  private final String subject;
  private final LocalDateTime startDate;
  private final String newProperty;

  /**
   * Constructor to create an EditSeries command.
   * @param propertyToEdit the property of the event series to edit (e.g., SUBJECT, LOCATION, etc.)
   * @param subject the subject of the event series to match for editing
   * @param startDate the start date and time of the series events to edit
   * @param newProperty the new value for the specified property
   */
  public EditSeries(
          EventProperty propertyToEdit, String subject,
          LocalDateTime startDate, String newProperty) {
    this.propertyToEdit = propertyToEdit;
    this.subject = subject;
    this.startDate = startDate;
    this.newProperty = newProperty;
  }

  @Override
  public void run(CalendarModel m, CalendarView v) {
    m.editSeries(this.propertyToEdit, this.subject, this.startDate, this.newProperty);
  }
}
