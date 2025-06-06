package control.commands;

import java.time.LocalDateTime;

import model.CalendarModel;
import model.EventProperty;
import view.CalendarView;

/**
 * Command to edit a single event in the calendar.
 * This command allows modification of specific properties of events
 * based on the provided parameters.
 */
public class EditSingleEvent implements CalendarCommand {
  private final EventProperty propertyToEdit;
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private final String subject;
  private final String newProperty;

  /**
   * Constructor to create an EditSingleEvent command.
   * @param propertyToEdit the property of the event to edit (e.g., SUBJECT, LOCATION, etc.)
   * @param startDate the start date and time of the event to edit
   * @param endDate the end date and time of the event to edit
   * @param subject the subject of the event to match for editing
   * @param newProperty the new value for the specified property
   */
  public EditSingleEvent(EventProperty propertyToEdit, LocalDateTime startDate,
                         LocalDateTime endDate, String subject, String newProperty) {
    this.propertyToEdit = propertyToEdit;
    this.startDate = startDate;
    this.endDate = endDate;
    this.subject = subject;
    this.newProperty = newProperty;
  }

  @Override
  public void run(CalendarModel m, CalendarView v) {
    m.editSingleEvent(
            this.propertyToEdit, this.startDate, this.endDate, this.subject, this.newProperty);
  }
}
