package com.changyi.chy.common.support;

import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalQuery;
import java.util.*;

/**
 * 日期工具类
 *
 */
@UtilityClass
public class DateUtil {

	public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_TIME = "HH:mm:ss";
	/**
	 * 老 date 格式化
	 */
	public static final ConcurrentDateFormat DATETIME_FORMAT = ConcurrentDateFormat.of(PATTERN_DATETIME);
	public static final ConcurrentDateFormat DATE_FORMAT = ConcurrentDateFormat.of(PATTERN_DATE);
	public static final ConcurrentDateFormat TIME_FORMAT = ConcurrentDateFormat.of(PATTERN_TIME);
	/**
	 * java 8 时间格式化
	 */
	public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DateUtil.PATTERN_DATETIME);
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DateUtil.PATTERN_DATE);
	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(DateUtil.PATTERN_TIME);

	/**
	 * 获取当前日期
	 *
	 * @return 当前日期
	 */
	public static Date now() {
		return new Date();
	}

	/**
	 * 添加年
	 *
	 * @param date       时间
	 * @param yearsToAdd 添加的年数
	 * @return 设置后的时间
	 */
	public static Date plusYears(Date date, int yearsToAdd) {
		return set(date, Calendar.YEAR, yearsToAdd);
	}

	/**
	 * 添加月
	 *
	 * @param date        时间
	 * @param monthsToAdd 添加的月数
	 * @return 设置后的时间
	 */
	public static Date plusMonths(Date date, int monthsToAdd) {
		return set(date, Calendar.MONTH, monthsToAdd);
	}

	/**
	 * 添加周
	 *
	 * @param date       时间
	 * @param weeksToAdd 添加的周数
	 * @return 设置后的时间
	 */
	public static Date plusWeeks(Date date, int weeksToAdd) {
		return DateUtil.plusDays(date, weeksToAdd * 7);
	}

	/**
	 * 添加天
	 *
	 * @param date      时间
	 * @param daysToAdd 添加的天数
	 * @return 设置后的时间
	 */
	public static Date plusDays(Date date, long daysToAdd) {
		return set(date, Calendar.DATE, (int) daysToAdd);
	}

	/**
	 * 添加小时
	 *
	 * @param date       时间
	 * @param hoursToAdd 添加的小时数
	 * @return 设置后的时间
	 */
	public static Date plusHours(Date date, long hoursToAdd) {
		return set(date, Calendar.HOUR_OF_DAY, (int) hoursToAdd);
	}

	/**
	 * 添加分钟
	 *
	 * @param date         时间
	 * @param minutesToAdd 添加的分钟数
	 * @return 设置后的时间
	 */
	public static Date plusMinutes(Date date, long minutesToAdd) {
		return set(date, Calendar.MINUTE, (int) minutesToAdd);
	}

	/**
	 * 添加秒
	 *
	 * @param date         时间
	 * @param secondsToAdd 添加的秒数
	 * @return 设置后的时间
	 */
	public static Date plusSeconds(Date date, long secondsToAdd) {
		return set(date, Calendar.SECOND, (int) secondsToAdd);
	}

	/**
	 * 添加毫秒
	 *
	 * @param date        时间
	 * @param millisToAdd 添加的毫秒数
	 * @return 设置后的时间
	 */
	public static Date plusMillis(Date date, long millisToAdd) {
		return set(date, Calendar.MILLISECOND, (int) millisToAdd);
	}

	/**
	 * 添加纳秒
	 *
	 * @param date       时间
	 * @param nanosToAdd 添加的纳秒数
	 * @return 设置后的时间
	 */
	public static Date plusNanos(Date date, long nanosToAdd) {
		return DateUtil.plusMillis(date, nanosToAdd / 1000000);
	}

	/**
	 * 日期添加
	 *
	 * @param date   时间
	 * @param amount 添加的时间
	 * @return 设置后的时间
	 */
	public static Date plus(Date date, TemporalAmount amount) {
		Instant instant = date.toInstant();
		return Date.from(instant.plus(amount));
	}

	/**
	 * 减少年
	 *
	 * @param date  时间
	 * @param years 减少的年数
	 * @return 设置后的时间
	 */
	public static Date minusYears(Date date, int years) {
		return DateUtil.plusYears(date, -years);
	}

	/**
	 * 减少月
	 *
	 * @param date   时间
	 * @param months 减少的月数
	 * @return 设置后的时间
	 */
	public static Date minusMonths(Date date, int months) {
		return DateUtil.plusMonths(date, -months);
	}

	/**
	 * 减少周
	 *
	 * @param date  时间
	 * @param weeks 减少的周数
	 * @return 设置后的时间
	 */
	public static Date minusWeeks(Date date, int weeks) {
		return DateUtil.plusWeeks(date, -weeks);
	}

	/**
	 * 减少天
	 *
	 * @param date 时间
	 * @param days 减少的天数
	 * @return 设置后的时间
	 */
	public static Date minusDays(Date date, long days) {
		return DateUtil.plusDays(date, -days);
	}

	/**
	 * 减少小时
	 *
	 * @param date  时间
	 * @param hours 减少的小时数
	 * @return 设置后的时间
	 */
	public static Date minusHours(Date date, long hours) {
		return DateUtil.plusHours(date, -hours);
	}

	/**
	 * 减少分钟
	 *
	 * @param date    时间
	 * @param minutes 减少的分钟数
	 * @return 设置后的时间
	 */
	public static Date minusMinutes(Date date, long minutes) {
		return DateUtil.plusMinutes(date, -minutes);
	}

	/**
	 * 减少秒
	 *
	 * @param date    时间
	 * @param seconds 减少的秒数
	 * @return 设置后的时间
	 */
	public static Date minusSeconds(Date date, long seconds) {
		return DateUtil.plusSeconds(date, -seconds);
	}

	/**
	 * 减少毫秒
	 *
	 * @param date   时间
	 * @param millis 减少的毫秒数
	 * @return 设置后的时间
	 */
	public static Date minusMillis(Date date, long millis) {
		return DateUtil.plusMillis(date, -millis);
	}

	/**
	 * 减少纳秒
	 *
	 * @param date  时间
	 * @param nanos 减少的纳秒数
	 * @return 设置后的时间
	 */
	public static Date minusNanos(Date date, long nanos) {
		return DateUtil.plusNanos(date, -nanos);
	}

	/**
	 * 日期减少
	 *
	 * @param date   时间
	 * @param amount 减少的时间
	 * @return 设置后的时间
	 */
	public static Date minus(Date date, TemporalAmount amount) {
		Instant instant = date.toInstant();
		return Date.from(instant.minus(amount));
	}

	/**
	 * 设置日期属性
	 *
	 * @param date          时间
	 * @param calendarField 更改的属性
	 * @param amount        更改数，-1表示减少
	 * @return 设置后的时间
	 */
	private static Date set(Date date, int calendarField, int amount) {
		Assert.notNull(date, "The date must not be null");
		Calendar c = Calendar.getInstance();
		c.setLenient(false);
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	/**
	 * 日期时间格式化
	 *
	 * @param date 时间
	 * @return 格式化后的时间
	 */
	public static String formatDateTime(Date date) {
		return DATETIME_FORMAT.format(date);
	}

	/**
	 * 日期格式化
	 *
	 * @param date 时间
	 * @return 格式化后的时间
	 */
	public static String formatDate(Date date) {
		return DATE_FORMAT.format(date);
	}

	/**
	 * 时间格式化
	 *
	 * @param date 时间
	 * @return 格式化后的时间
	 */
	public static String formatTime(Date date) {
		return TIME_FORMAT.format(date);
	}

	/**
	 * 日期格式化
	 *
	 * @param date    时间
	 * @param pattern 表达式
	 * @return 格式化后的时间
	 */
	public static String format(Date date, String pattern) {
		return ConcurrentDateFormat.of(pattern).format(date);
	}

	/**
	 * 日期时间格式化
	 *
	 * @param temporal 时间
	 * @return 格式化后的时间
	 */
	public static String formatDateTime(TemporalAccessor temporal) {
		return DATETIME_FORMATTER.format(temporal);
	}

	/**
	 * 日期格式化
	 *
	 * @param temporal 时间
	 * @return 格式化后的时间
	 */
	public static String formatDate(TemporalAccessor temporal) {
		return DATE_FORMATTER.format(temporal);
	}

	/**
	 * 时间格式化
	 *
	 * @param temporal 时间
	 * @return 格式化后的时间
	 */
	public static String formatTime(TemporalAccessor temporal) {
		return TIME_FORMATTER.format(temporal);
	}

	/**
	 * 日期格式化
	 *
	 * @param temporal 时间
	 * @param pattern  表达式
	 * @return 格式化后的时间
	 */
	public static String format(TemporalAccessor temporal, String pattern) {
		return DateTimeFormatter.ofPattern(pattern).format(temporal);
	}

	/**
	 * 将字符串转换为时间
	 *
	 * @param dateStr 时间字符串
	 * @param pattern 表达式
	 * @return 时间
	 */
	public static Date parse(String dateStr, String pattern) {
		ConcurrentDateFormat format = ConcurrentDateFormat.of(pattern);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 将字符串转换为时间
	 *
	 * @param dateStr 时间字符串
	 * @param format  ConcurrentDateFormat
	 * @return 时间
	 */
	public static Date parse(String dateStr, ConcurrentDateFormat format) {
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 将字符串转换为时间
	 *
	 * @param dateStr 时间字符串
	 * @param pattern 表达式
	 * @param query   查询条件
	 * @return 时间
	 */
	public static <T> T parse(String dateStr, String pattern, TemporalQuery<T> query) {
		return DateTimeFormatter.ofPattern(pattern).parse(dateStr, query);
	}

	/**
	 * 将字符串转换为时间
	 *
	 * @param dateStr 时间字符串
	 * @param format  表达式
	 * @return 时间
	 */
	public static Long parseToTimeMills(String dateStr, String format) {
		Date date = parse(dateStr, format);
		return date.getTime();
	}

	/**
	 * 时间转 Instant
	 *
	 * @param dateTime 时间
	 * @return Instant
	 */
	public static Instant toInstant(LocalDateTime dateTime) {
		return dateTime.atZone(ZoneId.systemDefault()).toInstant();
	}

	/**
	 * Instant 转 时间
	 *
	 * @param instant Instant
	 * @return Instant
	 */
	public static LocalDateTime toDateTime(Instant instant) {
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

	/**
	 * 转换成 date
	 *
	 * @param dateTime LocalDateTime
	 * @return Date
	 */
	public static Date toDate(LocalDateTime dateTime) {
		return Date.from(DateUtil.toInstant(dateTime));
	}

	/**
	 * 转换成 date
	 *
	 * @param localDate LocalDate
	 * @return Date
	 */
	public static Date toDate(final LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Converts local date time to Calendar.
	 */
	public static Calendar toCalendar(final LocalDateTime localDateTime) {
		return GregorianCalendar.from(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));
	}

	/**
	 * localDateTime 转换成毫秒数
	 *
	 * @param localDateTime LocalDateTime
	 * @return long
	 */
	public static long toMilliseconds(final LocalDateTime localDateTime) {
		return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	/**
	 * localDate 转换成毫秒数
	 *
	 * @param localDate LocalDate
	 * @return long
	 */
	public static long toMilliseconds(LocalDate localDate) {
		return toMilliseconds(localDate.atStartOfDay());
	}

	/**
	 * 转换成java8 时间
	 *
	 * @param calendar 日历
	 * @return LocalDateTime
	 */
	public static LocalDateTime fromCalendar(final Calendar calendar) {
		TimeZone tz = calendar.getTimeZone();
		ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
		return LocalDateTime.ofInstant(calendar.toInstant(), zid);
	}

	/**
	 * 转换成java8 时间
	 *
	 * @param instant Instant
	 * @return LocalDateTime
	 */
	public static LocalDateTime fromInstant(final Instant instant) {
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

	/**
	 * 转换成java8 时间
	 *
	 * @param date Date
	 * @return LocalDateTime
	 */
	public static LocalDateTime fromDate(final Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * 转换成java8 时间
	 *
	 * @param milliseconds 毫秒数
	 * @return LocalDateTime
	 */
	public static LocalDateTime fromMilliseconds(final long milliseconds) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
	}

	/**
	 * 比较2个时间差，跨度比较小
	 *
	 * @param startInclusive 开始时间
	 * @param endExclusive   结束时间
	 * @return 时间间隔
	 */
	public static Duration between(Temporal startInclusive, Temporal endExclusive) {
		return Duration.between(startInclusive, endExclusive);
	}

	/**
	 * 比较2个时间差，跨度比较大，年月日为单位
	 *
	 * @param startDate 开始时间
	 * @param endDate   结束时间
	 * @return 时间间隔
	 */
	public static Period between(LocalDate startDate, LocalDate endDate) {
		return Period.between(startDate, endDate);
	}

	/**
	 * 比较2个 时间差
	 *
	 * @param startDate 开始时间
	 * @param endDate   结束时间
	 * @return 时间间隔
	 */
	public static Duration between(Date startDate, Date endDate) {
		return Duration.between(startDate.toInstant(), endDate.toInstant());
	}

	/**
	 * 将秒数转换为日时分秒
	 *
	 * @param second 秒数
	 * @return 时间
	 */
	public static String secondToTime(Long second) {
		if (second == null || second < 0) {
			return StringPool.EMPTY;
		}

		long days = second / 86400;
		second = second % 86400;
		long hours = second / 3600;
		second = second % 3600;
		long minutes = second / 60;
		second = second % 60;
		if (days > 0) {
			return StringUtil.format("{}天{}小时{}分{}秒", days, hours, minutes, second);
		} else {
			return StringUtil.format("{}小时{}分{}秒", hours, minutes, second);
		}
	}

	/**
	 * 获取今天的日期
	 *
	 * @return 时间
	 */
	public static String today() {
		return formatDate(new Date());
	}

	/**
	 * 获取今天的日期时间
	 *
	 * @return 时间
	 */
	public static String getDateTime() {
		return formatDateTime(new Date());
	}

	/**
	 * 获取当前时间秒数
	 *
	 * @return 秒数
	 */
	public static Long getCurrentTimeMills() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取当前时间秒数
	 *
	 * @return 秒数
	 */
	public static Long getCurrentTimeSecond() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 获取当前时间格式化时间
	 *
	 * @param fmt 时间格式化
	 * @return 格式化时间
	 */
	public static String getDateTimeFormat(String fmt) {
		return format(new Date(), fmt);
	}

} 