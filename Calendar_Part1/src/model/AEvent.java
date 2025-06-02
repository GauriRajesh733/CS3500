package model;


// NOTE: add builder pattern!
public class AEvent implements Event {
  protected String subject;
  protected Date startDate; //YYYY-MM-DD-HH-MM
  protected Date endDate; //YYYY-MM-DD-HH-MM
  protected String description;
  protected Location location;
  protected Status status;

  protected AEvent(String subject, Date startDate, String description,
                   Location location, Status status, Date endDate) {
    this.subject = subject;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
    this.location = location;
    this.status = status;
  }
}


