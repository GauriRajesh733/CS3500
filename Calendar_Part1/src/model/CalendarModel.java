package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a calendar that stores single events and series of events.
 */
public interface CalendarModel {

  /**
   * Add a single event with the given information to this calendar.
   * @param subject represents the subject of the event.
   * @param startDateTime represents the start date and time of the event.
   * @param endDateTime represents the end date and time of the event.
   */
  void addSingleEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime);

  /**
   * Add a series of events with the given information to this calendar.
   * @param subject represents the subject of the event.
   * @param daysOfWeek represents days of weeks this event repeats on.
   * @param occurrences represents number of occurrences of this event.
   * @param startDateTime represents the start date and time of this event.
   * @param endDateTime represents the end date and time of this event.
   */
  void addSeriesEvent(String subject, DayOfWeek[] daysOfWeek, int occurrences,
                      LocalDateTime startDateTime, LocalDateTime endDateTime);

  /**
   * Creates a list of string descriptions of events on a given date.
   * @param date represents date in calendar.
   * @return list of descriptions of events on given date in this calendar.
   */
  ArrayList<String> printEventsForDate(LocalDate date);

  /**
   * Creates a list of string descriptions of events in a given range of dates.
   * @param start represents start date of range.
   * @param end represents end date of range.
   * @return lit of descriptions of events in given range in this calendar.
   */
  ArrayList<String> printEventsUsingInterval(LocalDateTime start, LocalDateTime end);

  /**
   * Edits single event in this calendar based on given information on the event and the property
   * to update.
   * @param propertyToEdit represents the property of the event to edit.
   * @param startDate represents the start date of the event to edit.
   * @param endDate represents the end date of the event to edit.
   * @param subject represents the start date of the event to edit.
   * @param newProperty represents the string representation of the new property.
   * @throws IllegalArgumentException if the event does not exist.
   */
  void editSingleEvent(
          EventProperty propertyToEdit, LocalDateTime startDate, LocalDateTime endDate,
          String subject, String newProperty) throws IllegalArgumentException;

  /**
   * Edits events on or after the given date with the same subject with the given new property.
   * @param propertyToEdit represents the property to edit.
   * @param subject represents the subject of the event to edit.
   * @param startDate represents the start date of the event to edit.
   * @param newProperty represents the string representation of the new property.
   * @throws IllegalArgumentException if the event does not exist.
   */
  void editEvents(
          EventProperty propertyToEdit, String subject,
          LocalDateTime startDate, String newProperty) throws IllegalArgumentException;

  /**
   * Edits all events in a series based on the given start date and subject with the given property.
   * @param propertyToEdit represents the property to edit.
   * @param subject represents the subject of the event to edit.
   * @param startDate represents the start date of the event.
   * @param newProperty represents the string representation of the property to edit.
   * @throws IllegalArgumentException if the series or single event cannot be found.
   */
  void editSeries(
          EventProperty propertyToEdit, String subject,
          LocalDateTime startDate, String newProperty) throws IllegalArgumentException;

  /**
   * Determine if there are events in the calendar on the given date.
   * @param date represents the date to check for.
   * @return true if there are events on the given date, false otherwise.
   */
  boolean showCalendarStatus(LocalDateTime date);

  /**
   * Remove the events in the given list of events from this calendar.
   * @param eventsToRemove represents the events to remove from this calendar.
   * @param removeSeries is true if you are removing a series of events from the calendar, false
   *                     otherwise.
   */
  void removeEvents(ArrayList<AEvent> eventsToRemove, boolean removeSeries);
}
