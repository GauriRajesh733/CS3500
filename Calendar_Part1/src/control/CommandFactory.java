package control;

public interface CommandFactory {
  public CalendarCommand createCalendarCommand(String input);
}
