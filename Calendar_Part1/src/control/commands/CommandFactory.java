package control.commands;

public interface CommandFactory {
  CalendarCommand createCalendarCommand(String input);
}
