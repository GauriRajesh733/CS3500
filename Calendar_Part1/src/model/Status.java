package model;

/**
 * Represents the status of an event.
 */
public enum Status {
  PUBLIC, PRIVATE;

  /**
   * Converts the input to status.
   * @param input represents user input.
   * @return status based on given input.
   * @throws IllegalArgumentException if the input does not correspond to a status.
   */
  public static Status fromInput(String input) throws IllegalArgumentException {
    switch (input) {
      case "public":
        return PUBLIC;
      case "private":
        return PRIVATE;
      default:
        throw new IllegalArgumentException("Invalid status provided: " + input);
    }
  }
}
