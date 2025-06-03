package control;

public interface CommandFactory {
  CalendarCommand createCalendarCommand(String input);

  //int searchKeyword(String input) throws IllegalArgumentException;
}
