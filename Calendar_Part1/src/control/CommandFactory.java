package control;

import control.commands.CalendarCommand;

interface CommandFactory {
  CalendarCommand createCalendarCommand(String input);
}
