package com.github.marschall.threeten;

import static org.junit.Assert.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import static java.time.temporal.ChronoUnit.SECONDS;

import org.junit.Test;

public class ApiDemoTest {

  @Test
  public void localTime() {
    LocalTime time1 = LocalTime.of(12, 15, 0);
    LocalTime time2 = LocalTime.parse("12:15:00");
    assertEquals(time1, time2);
  }
  
  @Test
  public void localTimeFormat() {
    LocalTime time = LocalTime.of(12, 15, 0);
    assertEquals("12:15:00", String.format("%tT", time));
  }
  
  @Test
  public void localDate() {
    LocalDate date1 = LocalDate.of(2014, 3, 30);
    date1 = LocalDate.of(2014, Month.MARCH, 30);
    LocalDate date2 = LocalDate.parse("2014-03-30");
    assertEquals(date1, date2);
  }
  
  @Test
  public void localDateTime() {
    LocalDate date = LocalDate.parse("2014-03-30");
    LocalTime time = LocalTime.parse("12:15:00");
    LocalDateTime dateTime = LocalDateTime.of(date, time);
    assertEquals(LocalDateTime.of(2014, 03, 30, 12, 15, 0), dateTime);
  }
  
  @Test
  public void truncate() {
    ZonedDateTime now = ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    assertEquals(0, now.getSecond());
    assertEquals(0, now.getNano());
  }
  
  @Test
  public void timeZones() {
    LocalDateTime dateTime = LocalDateTime.parse("2014-03-28T10:15:30");
    ZoneId zone = ZoneId.of("Europe/Zurich");
    ZoneOffset offset = ZoneOffset.of("+01:00");
    
    ZonedDateTime zonedDate = ZonedDateTime.of(dateTime, zone);
    OffsetDateTime offsetDate = OffsetDateTime.of(dateTime, offset);
    assertEquals(zonedDate.toInstant(), offsetDate.toInstant());
  }
  
  @Test
  public void parse() {
    LocalDate dateFromInts = LocalDate.of(2007, 12, 3);
    LocalDate dateFromString = LocalDate.parse("2007-12-03");
    DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
    LocalDate dateFromFormatter = LocalDate.parse("20071203", formatter);
    assertEquals(dateFromInts, dateFromString);
    assertEquals(dateFromInts, dateFromFormatter);
  }
  
  @Test
  public void format() {
    DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
    LocalDate date = LocalDate.parse("20071203", formatter);
    assertEquals("2007-12-03", date.toString());
    assertEquals("20071203", date.format(formatter));
  }
  
  @Test
  public void adjuster() {
    ZonedDateTime dateTime = ZonedDateTime.parse("2014-02-11T22:02:57.291+01:00[Europe/Zurich]");
    ZonedDateTime endOfMonth = dateTime.with(TemporalAdjusters.lastDayOfMonth()).truncatedTo(SECONDS);
    ZonedDateTime wednesday = dateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.WEDNESDAY)).truncatedTo(SECONDS);
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    assertEquals("2014-02-28T22:02:57", endOfMonth.format(formatter));
    assertEquals("2014-02-05T22:02:57", wednesday.format(formatter));
  }
  
}
