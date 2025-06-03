package model;

public interface MultidayEvent<T extends AMultidayEvent<T>> {
  void setPrev(T iterableEvent);

  void setNext(T iterableEvent);

  boolean hasNext();

  boolean hasPrev();

  T next();

  T prev();
}
