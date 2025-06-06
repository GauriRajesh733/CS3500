package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

/**
 * Represents an event in a calendar.
 */
interface Event {
  /**
   * Add this event to the given calendar.
   * @param calendar represents the calendar, which maps dates to list of events.
   */
  void addToCalendar(Map<LocalDate, ArrayList<AEvent>> calendar);

  /**
   * Add this event to the given calendar.
   * @param calendar represents the calendar, which maps dates to list of events.
   */
  void addSingleEventToCalendar(Map<LocalDate, ArrayList<AEvent>> calendar);

  /**
   * Get subject of this event.
   * @return the subject.
   */
  String getSubject();

  /**
   * Get location of this event.
   * @return the location.
   */
  Location getLocation();

  /**
   * Get description of this event.
   * @return the description.
   */
  String getDescription();

  /**
   * Get status of this event.
   * @return the status.
   */
  Status getStatus();

  /**
   * Get start date of this event.
   * @return the start date.
   */
  LocalDateTime getStartDate();

  /**
   * Get end date of this event.
   * @return the end date.
   */
  LocalDateTime getEndDate();

  /**
   * Edit this event based on the given property to edit.
   * @param propertyToEdit represents type of property to edit.
   * @param newProperty represents new property.
   * @return new event based on this event but with new property.
   */
  AEvent editSingleEvent(EventProperty propertyToEdit, String newProperty);

  /**
   * Edit all events in this series or this event if single based on the given property to edit.
   * @param propertyToEdit represents type of property to edit.
   * @param newProperty represents new property.
   * @return new event or start of new series based on this event but with new property.
   */
  AEvent editSeriesEvent(EventProperty propertyToEdit, String newProperty);

  /**
   * Edit this event and any linked following events based on the given property to edit.
   * @param propertyToEdit represents type of property to edit.
   * @param newProperty represents new property.
   * @return new event based on this event but with new property.
   */
  AEvent editEvents(EventProperty propertyToEdit, String newProperty);

  /**
   * Determines if this is the same event as another based on the given information.
   * @param subject represents the subject of the other event.
   * @param startDate represents the start date of the other event.
   * @param endDate represents the end date of the other event.
   * @return true if the properties for each event match, false otherwise.
   */
  boolean sameEvent(String subject, LocalDateTime startDate, LocalDateTime endDate);

  /**
   * Determine if this is the same event as another based on the given information.
   * @param subject represents the subject of the other event.
   * @param startDate represents the start date of the other event.
   * @return true if the properties for each event match, false otherwise.
   */
  boolean sameSubjectAndStart(String subject, LocalDateTime startDate);

  /**
   * Get all events that are related to this event.
   * @return list with one event if single event or multiple events if multiday or series event.
   */
  ArrayList<AEvent> getEvents();
}
