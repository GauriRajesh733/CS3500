package model;

public class SingleEvent extends AEvent  {
  String subject;
  Date startDate;
  Date endDate;

  public SingleEvent(String subject, Date startDate, Date endDate) {
    super(subject, startDate, null, null, null, endDate);
  }
}
