package control.commands;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrintCommandFactoryTest {

  @Test
  public void createCalendarCommand() {
    PrintCommandFactory testFactory = new PrintCommandFactory();

    String query1 = "print events on 2025-06-03";

    String query2 = "print events from 2025-06-03-10-00 to 2025-06-03-12-00";

    testFactory.createCalendarCommand(query1);

  }
}