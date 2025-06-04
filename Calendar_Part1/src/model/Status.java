package model;

public enum Status {
  PUBLIC, PRIVATE;

  public static Status fromInput(String input) {
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
