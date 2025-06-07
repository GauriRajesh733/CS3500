package model;

/**
 * Represents the location of an event.
 */
public enum Location {
  PHYSICAL, ONLINE;

  /**
   * Converts the given string input to location.
   * @param input represents user input.
   * @return location corresponding to given input.
   * @throws IllegalArgumentException if the input does not correspond to a location.
   */
  public static Location fromInput(String input) throws IllegalArgumentException {
    switch (input) {
      case "physical":
        return PHYSICAL;
      case "online":
        return ONLINE;
      default:
        throw new IllegalArgumentException("Invalid location provided: " + input);
    }
  }

  /**
   * Converts this location to string format.
   * @return string representation of this location.
   */
  public String toInput() {
    switch (this) {
      case PHYSICAL:
        return "physical";
      case ONLINE:
        return "online";
      default:
        throw new IllegalArgumentException("Invalid location provided: " + this);
    }
  }
}
