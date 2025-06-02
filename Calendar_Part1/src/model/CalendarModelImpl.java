package model;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;

public class CalendarModelImpl implements CalendarModel {
  Map<Date, ArrayList<AEvent>> calendar;
  // NOTE: check if subject date repeated!
  // events need to be in order?
  public CalendarModelImpl() {
    // add comparator to treemap!
    this.calendar = new TreeMap<Date, ArrayList<AEvent>>();
  }

}
