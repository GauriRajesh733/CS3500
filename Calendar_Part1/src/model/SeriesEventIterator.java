package model;

import java.util.Iterator;

public class SeriesEventIterator implements Iterator<SeriesEvent> {
  private final SeriesEvent seriesEvent;

  public SeriesEventIterator(SeriesEvent seriesEvent) {
    this.seriesEvent = seriesEvent;
  }

  @Override
  public boolean hasNext() {
    return seriesEvent.hasNext();
  }

  @Override
  public SeriesEvent next() {
    if (this.seriesEvent.hasNext()) {
      return this.seriesEvent.next();
    }
    throw new IllegalArgumentException("No events after this event in series");
  }

  public boolean hasPrev() {
    return this.seriesEvent.hasPrev();
  }

  public SeriesEvent prev() {
    if (this.seriesEvent.hasPrev()) {
      return this.seriesEvent.prev();
    }
    throw new IllegalArgumentException("No events before this event in series");
  }
}
