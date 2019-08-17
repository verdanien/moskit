package com.mos.moskit.common.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class MosDateUtils {
	private MosDateUtils() {
		throw new UnsupportedOperationException();
	}

	public static LocalTime getCurrentTime() {
		return LocalTime.now();
	}

	public static LocalDateTime getCurrentDateTime() {
		return LocalDateTime.now();
	}

	public static LocalDate getCurrentDate() {
		return LocalDate.now();
	}

}
