package control;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

import control.commands.CalendarCommandFactory;
import model.CalendarModel;
import view.CalendarView;

public class CalendarControllerImpl implements CalendarController {
  private final InputStream in;

  public CalendarControllerImpl(InputStream in) {
    this.in = in;
  }

  public void go(CalendarModel m, CalendarView v) {
    Objects.requireNonNull(m);
    Scanner s = new Scanner(this.in);

    while (s.hasNext()) {
      String input = s.nextLine();

      if (input.equals("exit")) {
        return;
      }

      CalendarCommand cmd = new CalendarCommandFactory().createCalendarCommand(input);
      cmd.go(m);
    }
  }

  // add methods here for displaying view output or does view do that??
}

