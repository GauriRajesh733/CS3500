package control;

import org.junit.Test;

public class EditCommandFactoryTest extends ACommandFactoryTest {

  @Override
  public ACommandFactory makeFactory() {
    return new EditCommandFactory();
  }

  @Test
  public void searchKeywordIndex() {

  }

  @Test
  public void search() {
  }



  @Test
  public void createCalendarCommand() {
    String validInput1 = "edit event subject oldSubject from 2019-09-08T00:00 to 2019-09-08T50:00 with newSubject";
    String validInput2 = "edit events location errands from 2017-08-08T08:00 with physical";
    String validInput3 = "edit series description meeting from 2022-05-21T08:00 with new meeting description";
  }
}