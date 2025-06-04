package control;

public interface CommandFactory {
  CalendarCommand createCalendarCommand(String input);
}
