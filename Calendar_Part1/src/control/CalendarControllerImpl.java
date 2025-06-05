package control;

import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import control.commands.CalendarCommand;
import control.commands.CalendarCommandFactory;
import model.CalendarModel;
import view.CalendarView;

/**
 * CalendarController implementation that takes input, processes it, and sends it to the model.
 */

public class CalendarControllerImpl implements CalendarController {
  private final InputStream in;

  /**
   * Constructs a CalendarControllerImpl with the given InputStream.
   *
   * @param in the InputStream to read commands from
   */

  public CalendarControllerImpl(InputStream in) {
    this.in = in;
  }

  /**
   * Starts the controller to process commands from the input stream.
   *
   * @param m the CalendarModel to interact with
   * @param v the CalendarView to display results and errors
   */

  public void go(CalendarModel m, CalendarView v) {
    Objects.requireNonNull(m);
    Scanner s = new Scanner(this.in);
    while (s.hasNext()) {
      String input = s.nextLine();

      if (input.equals("exit")) {
        System.exit(0);
      }

      try {
        CalendarCommand cmd = new CalendarCommandFactory().createCalendarCommand(input);

        cmd.go(m, v);
      } catch (Exception e) {
        v.showErrorMessage(e.getMessage());
        this.go(m, v);
      }
    }

    // display error message if finished parsing commands in file input without exit command
     v.showErrorMessage("File input must end with exit command");
  }
}

