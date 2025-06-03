package model;

public enum EventProperty {
  SUBJECT, START, END, DESCRIPTION, LOCATION, STATUS;

  public static EventProperty fromInput(String input) {
    switch (input) {
      case "subject":
        return SUBJECT;
      case "start":
        return START;
      case "end":
        return END;
      case "description":
        return DESCRIPTION;
      case "location":
        return LOCATION;
      case "status":
        return STATUS;
      default:
        throw new IllegalArgumentException("Invalid property: " + input);
    }
  }
}
