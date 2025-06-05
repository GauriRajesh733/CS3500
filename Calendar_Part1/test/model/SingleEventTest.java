package model;

import java.time.LocalDateTime;

public class SingleEventTest extends AEventTest{


  @Override
  public AEvent createEvent() {
    LocalDateTime startDateTime = LocalDateTime.of(2023, 12, 1, 10, 0);
    LocalDateTime endDateTime  = LocalDateTime.of(2023, 12, 1, 11, 0);
    return new SingleEvent("Test Subject",
            startDateTime, endDateTime);
  }
}