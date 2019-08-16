package tattool.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate asLocalDate(Date date) {
		LocalDate data = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		data.format(format);
		System.out.println(data);
		return data;
	}

	public static Date asLocalDateTimetoDate(LocalDateTime time) {
		Date out = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
		return out;
	}

	public static LocalDateTime asLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static Calendar convertStringToCalendar(String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(data));
			return c;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String DateToString(Date dateSession) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return df.format(dateSession);
	}

	public static Date StringToDate(String dataStr) {

		try {
			SimpleDateFormat  format = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
			java.util.Date data = format.parse(dataStr);
			return data;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
