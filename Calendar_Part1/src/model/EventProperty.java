package model;

/**
 * Represents a property of an event that can be updated.
 */
public enum EventProperty {
  SUBJECT, START, END, DESCRIPTION, LOCATION, STATUS;

  /**
   * Converts the given string input to the event property.
   * @param input represents user input.
   * @return the corresponding event property to edit.
   * @throws IllegalArgumentException if the input does not match any of the event properties.
   */
  public static EventProperty fromInput(String input) throws IllegalArgumentException {
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
