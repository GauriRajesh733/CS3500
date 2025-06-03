package control.commands;

import java.time.LocalDateTime;

import control.CalendarCommand;
import model.CalendarModel;
import model.MultidaySingleEvent;

public class CreateMultidayEvent implements CalendarCommand {
  private final MultidaySingleEvent firstDay;

  public CreateMultidayEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    this.firstDay = this.createMultidayEvent(subject, startDateTime, endDateTime);
  }

  @Override
  public void go(CalendarModel m) {
    m.addEvent(this.firstDay);
  }

  private MultidaySingleEvent createMultidayEvent(
          String subject, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    MultidaySingleEvent currentDay = new MultidaySingleEvent(subject, startDateTime, endDateTime);
    MultidaySingleEvent prevDay = null;
    MultidaySingleEvent nextDay = null;
    LocalDateTime currStartDate = startDateTime;

    while (currStartDate.isBefore(endDateTime)) {
      nextDay = new MultidaySingleEvent(subject, startDateTime, endDateTime);

      currentDay.setNext(nextDay);
      nextDay.setPrev(currentDay);
      currentDay.setPrev(prevDay);
      if (prevDay != null) {
        prevDay.setNext(currentDay);
      }

      prevDay = currentDay;
      currentDay = nextDay;

      currStartDate = currStartDate.plusDays(1);
    }

    MultidaySingleEvent firstDay = nextDay;

    // traverse backwards to get first day in multiday event
    while (firstDay.hasPrev()) {
      firstDay = firstDay.prev();
    }

    return firstDay;
  }
}

