import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import control.commands.CreateEventSeries;
import control.commands.CreateSingleEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CreateCommandTest {

  @Test
  public void testCreateEventSeries () {
    DayOfWeek[] days = {DayOfWeek.MONDAY};
    LocalDateTime start = LocalDateTime.of(2025,6,2,12,30);
    LocalDateTime end = LocalDateTime.of(2025,6,2,16,30);
    LocalDateTime nextStart = LocalDateTime.of(2025,6 ,9,12,30);
    LocalDateTime nextEnd = LocalDateTime.of(2025,6,9,16,30);
    CreateEventSeries cmd1 = new CreateEventSeries("hi", days, 2, start, end);
    CreateSingleEvent cmd2 = new CreateSingleEvent("hi", start, nextStart); // 7 day event
    /**

    SeriesEvent firstEvent = new SeriesEvent("hi", start, end);
    SeriesEvent secondEvent = new SeriesEvent("hi", nextStart, nextEnd);
    firstEvent.setNext(secondEvent);
    secondEvent.setPrev(firstEvent);**/

    //assertEquals(firstEvent, cmd.firstEvent);

    String input = "create event test from 2025-05-20-10-00 to 2025-05-20-12-00";

  }

  @Test
  public void testCreateSingleAllDayEvent() {
    LocalDateTime start = LocalDateTime.of(2025,6,2,12,30);
    CreateSingleEvent cmd = new CreateSingleEvent("test", start);

    LocalDateTime singleDayStartTime = LocalDateTime.of(2025,6,2,8,0);
    LocalDateTime singleDayEndTime = LocalDateTime.of(2025,6,2,17,0);

  }

  @Test
  public void testCreateSingleDayEvent() {
    LocalDateTime start = LocalDateTime.of(2025,6,2,12,30);
    LocalDateTime end = LocalDateTime.of(2025,6,2,14,30);
    CreateSingleEvent cmd = new CreateSingleEvent("test", start, end);
  }

  @Test
  public void testCreateMultipleDayEvent() {

  }
}
