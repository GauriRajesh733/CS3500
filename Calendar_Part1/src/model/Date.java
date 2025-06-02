package model;

public class Date {
  private final Integer year;
  private final Integer month;
  private final Integer day;
  private final Integer hour;
  private final Integer minute;

  public Date(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
    if (year < 0 || month < 1 || day < 1) {
      throw new IllegalArgumentException("year,month,day must be non-negative");
    }
    if (!isValidDate()) {
      throw new IllegalArgumentException("months and dates do not correspond");
    }

    this.year = year;
    this.month = month;
    this.day = day;
    this.hour = hour;
    this.minute = minute;
  }

  private boolean isValidDate() {
    return day <= getMaxDaysInMonths();
  }

  private int getMaxDaysInMonths() {
    switch (month) {
      case 2:
        if (isLeapYear()) {
          return 29;
        } else {
          return 28;
        }
      case 4:
      case 6:
      case 9:
      case 11:
        return 30;
      default:
        return 31;
    }
  }

  private boolean isLeapYear() {
    int divisibleByFour = year % 4;
    int divisibleByHundred = year % 100;
    int divisibleByFourHundred = year % 400;
    return divisibleByFour == 0 && (divisibleByHundred != 0 || divisibleByFourHundred == 0);
  }

  public int getYear() {
    return this.year;
  }

  public int getMonth() {
    return this.month;
  }

  public int getDay() {
    return this.day;
  }

  public int getHour() {
    return this.hour;
  }

  public int getMinute() {
    return this.minute;
  }

}
