import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import control.CalendarControllerImpl;
import model.CalendarModel;
import model.CalendarModelImpl;
import view.CalendarView;
import view.CalendarViewImpl;

/**
 * Main runner class for calendar application.
 */
public class Main {
  /**
   * Run calendar application.
   * @param args represents command line arguments.
   */
  public static void main(String[] args) {
    try {
      // create calendar model and view
      CalendarModel model = new CalendarModelImpl();
      CalendarView view = new CalendarViewImpl(System.out);

      // run interactive mode
      if (args[0].equals("--mode") && args[1].equals("interactive")) {
        new CalendarControllerImpl(System.in).run(model, view);
      }
      // run headless mode
      else if (args[0].equals("--mode") && args[1].equals("headless")) {
        File file = new File(args[3]);
        try (InputStream stream = new FileInputStream(file)) {
          new CalendarControllerImpl(stream).run(model, view);
        } catch (FileNotFoundException e) {
          throw new IllegalArgumentException("File not found");
        } catch (IOException e) {
          throw new IllegalArgumentException("Failed to read file");
        }
      }
      // invalid command line args
      else {
        throw new IllegalArgumentException(
                "Not enough arguments, specify mode and " +
                        "file with commands if running headless mode");
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException(
              "Not enough arguments, specify mode and file with commands if running headless mode");
    }
  }
}
