package model;

public enum Location {
  PHYSICAL, ONLINE;

  public static Location fromInput(String input) {
    switch (input) {
      case "physical":
        return PHYSICAL;
      case "online":
        return ONLINE;
      default:
        throw new IllegalArgumentException("Invalid location provided: " + input);
    }
  }

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
