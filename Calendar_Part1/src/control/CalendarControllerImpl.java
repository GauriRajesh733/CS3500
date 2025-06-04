package control;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;

import control.commands.CalendarCommandFactory;
import model.CalendarModel;
import model.CalendarModelImpl;

public class CalendarControllerImpl implements CalendarController {
  private final InputStream in;
  private final PrintStream out;

  public CalendarControllerImpl(InputStream in, PrintStream out) {
    this.in = in;
    this.out = out;
  }

  public void go(CalendarModel m) {
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

