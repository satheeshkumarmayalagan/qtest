package com.mycorp.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class LocalDateConverter {

	public static LocalDate toLocalDate(XMLGregorianCalendar xmlDate) {
		LocalDate localDate = null;
		try {
			if (xmlDate != null)
				localDate = xmlDate.toGregorianCalendar().toZonedDateTime().toLocalDate();
		} catch (Exception e) {
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

}
