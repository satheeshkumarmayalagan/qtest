package com.mycorp.crm.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateConverter {

	public static LocalDate toLocalDate(XMLGregorianCalendar xmlDate) {
		LocalDate localDate = null;
		try {
			if (xmlDate != null)
				localDate = xmlDate.toGregorianCalendar().toZonedDateTime().toLocalDate();
		} catch (Exception e) {
			// consume and return null
		}
		return localDate;
	}

	public static XMLGregorianCalendar toXmlDate(LocalDate localDate) {
		XMLGregorianCalendar xmlDate = null;
		try {
			if (localDate != null) {
				GregorianCalendar gcal = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
				xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);

			}
		} catch (Exception e) {
			// consume and return null
		}
		return xmlDate;
	}

	public static XMLGregorianCalendar toXmlDate(java.sql.Date date) {
		XMLGregorianCalendar xmlDate = null;
		try {
			if (date != null) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(date);
				xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			}
		} catch (Exception e) {
			// consume and return null
		}
		return xmlDate;
	}

	public static java.sql.Date toSqlDate(LocalDate date) {
		java.sql.Date sqlDate = null;
		try {
			if (date != null)
				sqlDate = java.sql.Date.valueOf(date);
		} catch (Exception e) {
			// consume and return null
		}
		return sqlDate;
	}

	public static java.sql.Date toSqlDate(XMLGregorianCalendar xcal) {
		java.sql.Date sqlDate = null;
		try {
			if (xcal != null) {
				Date dt = xcal.toGregorianCalendar().getTime();
				sqlDate = new java.sql.Date(dt.getTime());
			}
		} catch (Exception e) {
			// consume and return null
		}
		return sqlDate;
	}
}
