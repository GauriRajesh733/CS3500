package view;

import java.io.PrintStream;
import java.util.ArrayList;

import model.AEvent;

public class CalendarViewImpl implements CalendarView {
  private PrintStream out;

  public CalendarViewImpl(PrintStream out) {
    this.out = out;
  }

  @Override
  public void showEventsOnDate(ArrayList<AEvent> events) {

  }

  @Override
  public void showEventsInRange(ArrayList<AEvent> events) {

  }

  @Override
  public void showStatus(boolean status) {

  }
}
