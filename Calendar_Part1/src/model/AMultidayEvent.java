package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

// T needs to be event?
public abstract class AMultidayEvent<T extends AMultidayEvent<T>> extends AEvent implements MultidayEvent<T> {
  protected T next;
  protected T prev;

  public AMultidayEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    super(subject, startDateTime, endDateTime, null, null, null);
    this.next = null;
    this.prev = null;
  }

  public abstract void addToCalendar(Map<LocalDateTime, ArrayList<AEvent>> calendar, Map<String,
          ArrayList<LocalDateTime>> recurringEvents);

  public void setPrev(T seriesEvent) {
    this.prev = seriesEvent;
  }

  public void setNext(T seriesEvent) {
    this.next = seriesEvent;
  }

  public boolean hasNext() {
    return this.next != null;
  }

  public boolean hasPrev() {
    return this.prev != null;
  }

  public T next() {
    if (this.hasNext()) {
      return this.next;
    }
    throw new IllegalArgumentException("There is no event after this");
  }

  public T prev() {
    if (this.hasPrev()) {
      return this.prev;
    }
    throw new IllegalArgumentException("There is no event before this");
  }
}
