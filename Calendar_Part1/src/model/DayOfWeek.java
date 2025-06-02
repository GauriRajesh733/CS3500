package model;

public enum DayOfWeek {
  MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

  //MRU. 'M' is Monday, 'T' is Tuesday, 'W' is Wednesday, 'R' is Thursday, 'F' is Friday,
  // 'S' is Saturday, and 'U' is Sunday.
  public DayOfWeek fromInput(String input) {
    switch (input) {
      case "M":
        return MONDAY;
      case "T":
        return TUESDAY;
      case "W":
        return WEDNESDAY;
      case "R":
        return THURSDAY;
      case "F":
        return FRIDAY;
      case "S":
        return SATURDAY;
      case "U":
        return SUNDAY;
      default:
        throw new IllegalArgumentException("Invalid day of week: " + input);
    }
  }
}
