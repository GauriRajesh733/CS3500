import control.CalendarControllerImpl;
import model.CalendarModel;
import model.CalendarModelImpl;
import view.CalendarView;

public class Main {
  public static void main(String[] args) {
    try {
      // create calendar model and view
      CalendarModel model = new CalendarModelImpl();
      CalendarView view = new CalendarViewImpl();

      // run interactive mode
      if (args.length == 2 && args[0].equals("--mode") && args[1].equals("interactive")) {

        new CalendarControllerImpl( , ).go()
      }
      // run headless mode
      else if (args[0].equals("--mode") && args[1].equals("headless")) {
        String filePath = args[3];

      }
      // invalid command line args
      else {
        throw new IllegalArgumentException("Not enough arguments, specify mode and file with commands if running headless mode");
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Not enough arguments, specify mode and file with commands if running headless mode");
    }
  }
}
