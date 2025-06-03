package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SeriesEvent extends AEvent implements Iterable<SeriesEvent> {
  private SeriesEvent next;
  private SeriesEvent prev;
  private final List<SeriesEvent> seriesEvents = new ArrayList<>();

  public SeriesEvent(String subject, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    super(subject, startDateTime, endDateTime, null, null, null);
    this.next = null;
    this.prev = null;
  }

  public void setPrev(SeriesEvent seriesEvent) {
    this.prev = seriesEvent;
  }

  public void setNext(SeriesEvent seriesEvent) {
    this.next = seriesEvent;
  }

  @Override
  public void addToCalendar(Map<LocalDateTime, ArrayList<AEvent>> calendar, Map<String, ArrayList<LocalDateTime>> recurringEvents) {
    // add this single event to calendar
    if (!calendar.containsKey(this.startDateTime)) {
      calendar.put(this.startDateTime, new ArrayList<AEvent>());
    }
    calendar.get(this.startDateTime).add(this);

    // add other events in this series to calendar
    while (this.hasNext()) {
      this.next.addToCalendar(calendar, recurringEvents);

    }
  }

  @Override
  public LocalDateTime getStartDate() {
    return this.startDateTime;
  }

  @Override
  public LocalDateTime getEndDate() {
    return this.endDateTime;
  }

  @Override
  public Iterator<SeriesEvent> iterator() {
    return new SeriesEventIterator(next); //?
  }

  public SeriesEvent next() {
    return this.next;
  }

  public SeriesEvent prev() {
    return this.prev;
  }

  public boolean hasNext() {
    return this.next != null;
  }

  public boolean hasPrev() {
    return this.prev != null;
  }

  public int length() {
    SeriesEvent currNode = this;
    int i = 0;
    while (currNode.hasNext()) {
      currNode = currNode.next();
      i++;
    }
    return i;
  }


}