package model;

import java.time.ZoneId;

/**
 * Represents a calendar interface for different types of calendar implementations.
 * This interface defines the basic operations that can be performed on a calendar.
 */
public interface Calendar{

  /**
   * Gets the name of the calendar.
   */
  String getName();

  /**
   * Gets the time zone of the calendar.
   */
  ZoneId getTimeZone();

  /**
   * Sets the time zone for the calendar.
   * @param zoneId the ZoneId to set
   */
  void setTimeZone(ZoneId zoneId) throws IllegalArgumentException;

  /**
   * Sets the name of the calendar.
   */

  void setName(String name) throws IllegalArgumentException;

  /**
   * Gets the underlying model for this calendar.
   */
  CalendarModel getModel();

}
