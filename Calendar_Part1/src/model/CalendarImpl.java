package model;

import java.time.ZoneId;

public class CalendarImpl implements Calendar {
  private String name;
  private ZoneId timeZone;

  public CalendarImpl(String name, ZoneId timeZone) {
    this.name = name;
    this.timeZone = timeZone;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public ZoneId getTimeZone() {
    return timeZone;
  }

  @Override
  public void setTimeZone(ZoneId zoneId) throws IllegalArgumentException {
    if (zoneId == null) {
      throw new IllegalArgumentException("Time zone cannot be null");
    }
    try {
      zoneId.getId(); // This will throw an exception if the zoneId is invalid
      this.timeZone = zoneId;
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid time zone ID: " + zoneId.getId(), e);
    }

  }

  @Override
  public void setName(String name) throws IllegalArgumentException {
  if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Calendar name cannot be null or empty");
    }

    this.name = name;
  }

  @Override
  public CalendarModel getModel() {
    return null;
  }

  // Other calendar-specific methods can be added here
}
