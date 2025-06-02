package control.commands;


import control.CalendarCommand;
import model.CalendarModel;

public class Edit implements CalendarCommand {
  CalendarCommand editCommand;

  public Edit(String input) {

  }
  @Override
  public void go(CalendarModel m) {this.editCommand.go(m);}
}
