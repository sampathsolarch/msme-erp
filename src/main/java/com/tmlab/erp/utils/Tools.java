package com.tmlab.erp.utils;

import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Tools
 *
 * @author SD
 */
public class Tools {
	/**
	 * Get a 32-digit unique serial number
	 *
	 * @return 32 is the ID string
	 */
	public static String getUUID_32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * Get the time of the day, the format is yyyy-MM-dd
	 *
	 * @return formatted date format
	 */
	public static String getNow() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * Get current month yyyy-MM
	 *
	 * @return
	 */
	public static String getCurrentMonth() {
		return new SimpleDateFormat("yyyy-MM").format(new Date());
	}

	/**
	 * Get the specified date format yyyy-MM-dd
	 *
	 * @return
	 */
	public static String parseDateToStr(Date date) {
		if (date != null) {
			return new SimpleDateFormat("yyyy-MM-dd").format(date);
		} else {
			return "";
		}
	}

	/**
	 * Get the time of the day, the format is yyyyMMddHHmmss
	 *
	 * @return formatted date format
	 */
	public static String getNow2(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}

	/**
	 * Get the time of the day, the format is yyyy-MM-dd HH:mm:ss
	 *
	 * @return formatted date format
	 */
	public static String getNow3() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * Get the specified time, the format is yyyy-MM-dd HH:mm:ss
	 *
	 * @return formatted date format
	 */
	public static String getCenternTime(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static String parseDayToTime(String day, String timeStr) {
		if (StringUtil.isNotEmpty(day)) {
			return day + timeStr;
		} else {
			return null;
		}
	}

	/**
	 * Get the specified time, the format is mm:ss
	 *
	 * @return formatted date format
	 */
	public static String getTimeInfo(Date date) {
		return new SimpleDateFormat("mm:ss").format(date);
	}

	/**
	 * Get the current day of the week return day of the week
	 */
	public static String getWeekDay() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(new Date());
		int day = c.get(Calendar.DAY_OF_WEEK);
		String weekDay = "";
		switch (day) {
		case 1:
			weekDay = "Sunday";
			break;
		case 2:
			weekDay = "Monday";
			break;
		case 3:
			weekDay = "Tuesday";
			break;
		case 4:
			weekDay = "Wednesday";
			break;
		case 5:
			weekDay = "Thursday";
			break;
		case 6:
			weekDay = "Friday";
			break;
		case 7:
			weekDay = "Saturday";
			break;
		default:
			break;
		}
		return weekDay;
	}

	/**
	 * Determine if the string is all numbers
	 *
	 * @param checkStr
	 * @return boolean value
	 */
	public static boolean checkStrIsNum(String checkStr) {
		if (checkStr == null || checkStr.length() == 0)
			return false;
		return Pattern.compile("^[0-9]*.{1}[0-9]*$").matcher(checkStr).matches();
// return Pattern.compile(":^[0-9]+(.[0-9])*$").matcher(checkStr).matches();
	}

	/**
	 * Get the time of the previous day
	 *
	 * @return the previous day's date
	 */
	public static String getPreviousDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return new SimpleDateFormat("yyyy-MM").format(cal.getTime());
	}

	/**
	 * Get the first 6 months of the current month (including the current month)
	 * 
	 * @param size number of months
	 * @return
	 */
	public static List<String> getLastMonths(int size) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		List<String> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			c.setTime(new Date());
			c.add(Calendar.MONTH, -i);
			Date m = c.getTime();
			list.add(sdf.format(m));
		}
		Collections.reverse(list);
		return list;
	}

	/**
	 * intercept string length
	 *
	 * @param beforeStr
	 * @param cutLeng
	 * @return the truncated string
	 */
	public static String subStr(String beforeStr, int cutLeng) {
		if (beforeStr.length() > cutLeng)
			return beforeStr.substring(0, cutLeng) + "...";
		return beforeStr;
	}

	/**
	 * Generate random strings, mixed letters and numbers
	 *
	 * @return the combined string ^[0-9a-zA-Z]
	 */
	public static String getRandomChar() {
		// generate a random number of 0, 1, 2
		int rand = (int) Math.round(Math.random() * 1);
		long itmp = 0;
		char ctmp = '\u0000';
		switch (rand) {
		// Generate uppercase letters + numbers within 1000
		case 1:
			itmp = Math.round(Math.random() * 25 + 65);
			ctmp = (char) itmp;
			return String.valueOf(ctmp) + (int) Math.random() * 1000;
		// generate lowercase letters
		case 2:
			itmp = Math.round(Math.random() * 25 + 97);
			ctmp = (char) itmp;
			return String.valueOf(ctmp) + (int) Math.random() * 1000;
		// generate numbers
		default:
			itmp = Math.round(Math.random() * 1000);
			return itmp + "";
		}
	}

	/**
	 * Judging that the first letter starts with a number, the string includes
	 * numbers, letters % and spaces
	 *
	 * @param str check string whether @return starts with a number
	 */
	public static boolean CheckIsStartWithNum(String str) {
		return Pattern.compile("^[0-9][a-zA-Z0-9%,\\s]*$").matcher(str).matches();
	}

	/**
	 * Judging that the first letter starts with ",", the string includes numbers,
	 * letters % and spaces
	 *
	 * @param str check string whether @return starts with a number
	 */
	public static boolean CheckIsStartWithSpec(String str) {
		return Pattern.compile("^[,][a-zA-Z0-9%,\\s]*$").matcher(str).matches();
	}

	/**
	 * Character transcoding
	 *
	 * @param aValue
	 * @return
	 * @see the transcoded string
	 */
	public static String encodeValue(String aValue) {
		if (aValue.trim().length() == 0) {
			return "";
		}
		String valueAfterTransCode = null;
		try {
			valueAfterTransCode = URLEncoder.encode(aValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.getMessage();
		}
		return valueAfterTransCode;
	}

	/**
	 * Character transcoding
	 *
	 * @param aValue
	 * @return
	 * @see the transcoded string
	 */
	public static String decodeValue(String aValue) {
		if (aValue.trim().length() == 0) {
			return "";
		}
		String valueAfterTransCode = null;
		try {
			valueAfterTransCode = URLDecoder.decode(aValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.getMessage();
		}
		return valueAfterTransCode;
	}

	/**
	 * remove the ' in str
	 *
	 * @param str
	 * @return remove the string after '
	 * @see [class, class#method, class#member]
	 */
	public static String afterDealStr(String str) {
		return str.replace("'", "");
	}

	/**
	 * Get user IP address (disabled)
	 *
	 * @return user IP
	 * @see [class, class#method, class#member]
	 */
	public static String getCurrentUserIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "127.0.0.1";
		}
	}

	/**
	 * Obtain the client IP from the Request object, process the HTTP proxy server
	 * and the Nginx reverse proxy to intercept the IP
	 *
	 * @param request
	 * @return ip
	 */
	public static String getLocalIp(HttpServletRequest request) {
		String remoteAddr = getIpAddr(request);
		String forwarded = request.getHeader("X-Forwarded-For");
		String realIp = request.getHeader("X-Real-IP");

		String ip = null;
		if (realIp == null) {
			if (forwarded == null) {
				ip = remoteAddr;
			} else {
				ip = remoteAddr + "/" + forwarded.split(",")[0];
			}
		} else {
			if (realIp.equals(forwarded)) {
				ip = realIp;
			} else {
				if (forwarded != null) {
					forwarded = forwarded.split(",")[0];
				}
				ip = realIp + "/" + forwarded;
			}
		}
		return ip;
	}

	/**
	 * Get visitor IP
	 *
	 * In general, you can use Request.getRemoteAddr(), but after reverse proxy
	 * software such as nginx, this method will fail.
	 *
	 * This method first obtains the X-Real-IP from the Header, and if it does not
	 * exist, then obtains the first IP (use, split) from X-Forwarded-For, Call
	 * Request.getRemoteAddr() if it doesn't exist yet.
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// After multiple reverse proxies, there will be multiple IP values, the first
			// one is the real IP.
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}

	/**
	 * Convert the ID value passed in batches in the foreground
	 *
	 * @param data
	 * @return converted ID value array
	 */
	public static int[] changeDataForm(String data) {
		String[] dataStr = data.split(",");
		int[] dataInt = new int[dataStr.length];
		for (int i = 0; i < dataStr.length; i++)
			dataInt[i] = Integer.parseInt(dataStr[i]);
		return dataInt;
	}

	/**
	 * Solve the problem of Chinese garbled characters in exported files under
	 * firefox and ie
	 */
	public static String changeUnicode(String fileName, String browserType) {
		String returnFileName = "";
		try {
			if (browserType.equalsIgnoreCase("MSIE")) {
				returnFileName = URLEncoder.encode(fileName, "ISO8859-1");
				returnFileName = returnFileName.replace(" ", "%20");
				if (returnFileName.length() > 150) {
					returnFileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
					returnFileName = returnFileName.replace(" ", "%20");
				}
			} else if (browserType.equalsIgnoreCase("Firefox")) {
				returnFileName = new String(fileName.getBytes("ISO8859-1"), "ISO8859-1");
				returnFileName = returnFileName.replace(" ", "%20");
			} else {
				returnFileName = URLEncoder.encode(fileName, "ISO8859-1");
				returnFileName = returnFileName.replace(" ", "%20");
				if (returnFileName.length() > 150) {

					returnFileName = new String(returnFileName.getBytes("GB2312"), "ISO8859-1");
					returnFileName = returnFileName.replace(" ", "%20");
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return returnFileName;
	}

	/**
	 * Write the content of the financial journal to convert special characters
	 *
	 * @param str the character to be converted
	 * @return converted character
	 */
	public static String htmlspecialchars(String str) {
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}

	/**
	 * Get consumption month according to consumption date
	 *
	 * @param consumeDate consumption date
	 * @return returns consumption month information
	 */
	public static String getConsumeMonth(String consumeDate) {
		return consumeDate.substring(0, 7);
	}

	/**
	 * Get the first XX months of the current date
	 *
	 * @param beforeMonth
	 * @return previous XX month string
	 */
	public static String getBeforeMonth(int beforeMonth) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -beforeMonth);
		return new SimpleDateFormat("yyyy-MM").format(c.getTime());
	}

	/**
	 * Get the first day of the month according to the month
	 * 
	 * @param monthTime
	 * @return
	 * @throws ParseException
	 */
	public static String firstDayOfMonth(String monthTime) throws ParseException {
		return monthTime + "-01";
	}

	/**
	 * Get the last day of the month according to the month
	 * 
	 * @param monthTime
	 * @return
	 * @throws ParseException
	 */
	public static String lastDayOfMonth(String monthTime) throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM").parse(monthTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	/**
	 * Get email user name
	 *
	 * @param emailAddress
	 */
	public static String getEmailUserName(String emailAddress) {
		return emailAddress.substring(0, emailAddress.lastIndexOf("@"));
	}

	/**
	 * Get the Chinese code, solve the problem of garbled email attachments
	 *
	 * @param emailAttchmentTitle
	 * @return
	 */
	public static String getChineseString(String emailAttchmentTitle) {
		if (emailAttchmentTitle != null && !emailAttchmentTitle.equals("")) {
			try {
				return new String(emailAttchmentTitle.getBytes(), "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return emailAttchmentTitle;
	}

	/**
	 * Determine whether userTel is legal, userTel can only be a number
	 *
	 * @param userTel
	 * @return true legal false illegal
	 */
	public static boolean isTelNumber(String userTel) {
		String reg_phone = "^(\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}$";
		String reg_tel = "^(1[0-9][0-9]|1[0-9][0|3|6|8|9])\\d{8}$";
		boolean b_phpne = Pattern.compile(reg_phone).matcher(userTel).matches();
		boolean b_tel = Pattern.compile(reg_tel).matcher(userTel).matches();
		return (b_phpne || b_tel);
	}

	/**
	 * Fuzzy judgment of whether the phone number is legal, only numbers
	 *
	 * @param userTel
	 * @return
	 */
	public static boolean isTelNumberBySlur(String userTel) {
		return Pattern.compile("^([\\s0-9]{0,12}$)").matcher(userTel).matches();
	}

	/**
	 * Get the string type of the current time
	 *
	 * @return processed string type
	 */
	public static String getNowTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
	}

	/**
	 * Open the specified file
	 *
	 * @param filePath absolute path to the file
	 */
	public static void openFile(String filePath) {
		String viewFilePath = filePath.replace("\\", "/");
		// Runtime.getRuntime().exec("cmd /c start "+filePath);
		// Solve the problem with spaces in the path
		Runtime r = Runtime.getRuntime();
		String[] cmdArray = new String[] { "cmd.exe", "/c", viewFilePath };
		try {
			r.exec(cmdArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isContainsChinese(String str) {
		return Pattern.compile("[\u4e00-\u9fa5]").matcher(str).matches();
	}

	/**
	 * filter text in html file
	 *
	 * @param content
	 * @return filtered text
	 */
	public static String filterText(String content) {
		return content.replace("/<(?:.|\\s)*?>/g", "");
	}

	/**
	 * Remove all symbols from the string, whether it is full-width, half-width,
	 * currency symbols or spaces, etc.
	 *
	 * @params
	 * @return
	 */
	public static String removeSymbolForString(String s) {
		StringBuffer buffer = new StringBuffer();
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if ((chars[i] >= 19968 && chars[i] <= 40869) || (chars[i] >= 97 && chars[i] <= 122)
					|| (chars[i] >= 65 && chars[i] <= 90)) {
				buffer.append(chars[i]);
			}
		}
		return buffer.toString();
	}

	/**
	 * Get the MD5 of a string
	 *
	 * @param msg
	 * @return encrypted MD5 string
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5Encryp(String msg) throws NoSuchAlgorithmException {
		// Generate an MD5 encrypted calculation digest
		MessageDigest md = MessageDigest.getInstance("MD5");
		// Calculate the md5 function
		md.update(msg.getBytes());
		return new BigInteger(1, md.digest()).toString(16);
	}

	/**
	 * Determine whether the plugin URL
	 *
	 * @return
	 */
	public static boolean isPluginUrl(String url) {
		if (url != null && (url.startsWith("/plugin"))) {
			return true;
		}
		return false;
	}

	/**
	 * handle string null value
	 *
	 * @param beforeStr String before processing
	 * @return the processed string
	 */
	public static String dealNullStr(String beforeStr) {
		if (null == beforeStr || beforeStr.length() == 0)
			return "";
		return beforeStr;
	}

	/**
	 * Intercept tenant id according to token
	 * 
	 * @param token
	 * @return
	 */
	public static Long getTenantIdByToken(String token) {
		Long tenantId = 0L;
		if (StringUtil.isNotEmpty(token) && token.indexOf("_") > -1) {
			String[] tokenArr = token.split("_");
			if (tokenArr.length == 2) {
				tenantId = Long.parseLong(tokenArr[1]);
			}
		}
		return tenantId;
	}

	/**
	 * Use the parameter Format to convert the string to Date
	 *
	 * @param strDate
	 * @param pattern
	 * @return
	 * @throws ParseException
	 * 
	 */
	public static Date parse(String strDate, String pattern) throws ParseException {
		return new SimpleDateFormat(pattern).parse(strDate);
	}

	public static Date addDays(Date date, int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // The date data needs to be transferred to the Calender object for operation
		calendar.add(calendar.DATE, num);// Increase the date by n days. Positive numbers are pushed back, and negative
											// numbers are moved forward
		date = calendar.getTime(); // This time is the result of pushing the date back one day
		return date;
	}

	/**
	 * Generate random numbers and letter combinations
	 * 
	 * @param length
	 * @return
	 */
	public static String getCharAndNum(int length) {
		Random random = new Random();
		StringBuffer valSb = new StringBuffer();
		String charStr = "0123456789abcdefghijklmnopqrstuvwxyz";
		int charLength = charStr.length();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(charLength);
			valSb.append(charStr.charAt(index));
		}
		return valSb.toString();
	}

	public static void main(String[] args) {
		String aa = "The statement of the payment against the law";
		char[] bb = aa.toCharArray();
		for (char c : bb) {
			System.out.println(c);
		}
		System.out.println(getBeforeMonth(1));

		try {
			System.out.println(md5Encryp("guest"));
			System.out.println(md5Encryp("admin"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String value = "2333";
		System.out.println(checkStrIsNum(value));

		for (int i = 0; i < 100; i++) {
			System.out.print(getRandomChar() + " || ");
		}
	}
}
