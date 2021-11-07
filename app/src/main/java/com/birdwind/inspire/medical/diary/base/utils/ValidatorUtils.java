package com.birdwind.inspire.medical.diary.base.utils;

import android.util.Patterns;
import android.webkit.URLUtil;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class ValidatorUtils {

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+\\.*\\w+@(\\w+\\.){1,5}[a-zA-Z]{2,3}$");

    public static final Pattern TWPID_PATTERN = Pattern.compile("[ABCDEFGHJKLMNPQRSTUVXYWZIO][12]\\d{8}");

    public static String formatPhone(String phone, String regionCode) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phone, regionCode);
            return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164).replace(" ", "");
        } catch (NumberParseException e) {
            LogUtils.exception(e);
            return "";
        }
    }

    public static String getPhoneCountryCode(String phone) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            // phone must begin with '+'
            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phone, "");
            return phoneNumber.getPreferredDomesticCarrierCode();
        } catch (NumberParseException e) {
            LogUtils.exception(e);
            return "";
        }
    }

    public static String formatPhoneNational(String phone, String regionCode) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        if (!phone.isEmpty() && phone.charAt(0) == '+') {
            try {
                // phone must begin with '+'
                Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phone, "");
                int countryCode = phoneNumber.getCountryCode();
                return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
                    .replace("-", "").replace(" ", "");
            } catch (NumberParseException e) {
                LogUtils.exception(e);
            }
        } else {
            regionCode = "TW";
            try {
                Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phone, regionCode);
                return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL).replace("-", "")
                    .replace(" ", "");
            } catch (NumberParseException e) {
                LogUtils.exception(e);
            }
        }
        return "";
    }

    public static boolean isPhone(String phone, String regionCode) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        if (!phone.isEmpty() && phone.charAt(0) != '+') {
            regionCode = "TW";
        }
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phone, regionCode);
            return phoneNumberUtil.isValidNumber(phoneNumber);
        } catch (Exception e) {
            LogUtils.exception(e);
            return false;
        }
    }

    public static boolean isValidURL(String urlString) {
        return URLUtil.isValidUrl(urlString) && Patterns.WEB_URL.matcher(urlString).matches();
    }

    public static boolean isDate(String dttm, String format) {
        if (dttm == null || dttm.isEmpty() || format == null || format.isEmpty()) {
            return false;
        }

        if (!format.replaceAll("'.+?'", "").contains("y")) {
            format += "/yyyy";
            DateFormat formatter = new SimpleDateFormat("/yyyy");
            dttm += formatter.format(new Date());
        }

        DateFormat formatter = new SimpleDateFormat(format);
        formatter.setLenient(false);
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(dttm, pos);

        if (date == null || pos.getErrorIndex() > 0) {
            return false;
        }
        if (pos.getIndex() != dttm.length()) {
            return false;
        }

        return formatter.getCalendar().get(Calendar.YEAR) <= 9999;
    }

    public static String isChineseDate(String date) {
        if (date == null || date.length() < 9) {
            return null;
        } else {
            if (date.length() == 12) {
                date = DateTimeFormatUtils.convertFromChineseYear(date.substring(3, 12));
                if (!ValidatorUtils.isDate(date, "yyyy/MM/dd")) {
                    return null;
                }
            } else if (date.length() == 9) {
                date = DateTimeFormatUtils.convertFromChineseYear(date);
                if (!ValidatorUtils.isDate(date, "yyyy/MM/dd")) {
                    return null;
                }
            }
        }
        return date;
    }

    public static boolean isAgeOver(String birthdayString, int yearsOld) {
        Calendar nowCalendar = Calendar.getInstance();
        Calendar birthdayCalendar = Calendar.getInstance();
        Date birthdayDate = DateTimeFormatUtils.parseDate(birthdayString);
        if (birthdayDate != null) {
            birthdayCalendar.setTime(birthdayDate);
        }

        int yearNow = nowCalendar.get(Calendar.YEAR);
        int monthNow = nowCalendar.get(Calendar.MONTH);
        int dayNow = nowCalendar.get(Calendar.DAY_OF_MONTH);

        int yearBirth = birthdayCalendar.get(Calendar.YEAR);
        int monthBirth = birthdayCalendar.get(Calendar.MONTH);
        int dayBirth = birthdayCalendar.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;
        /* 如果出當前月小與出生月，或者當前月等於出生月但是當前日小於出生日，那麽年齡age就減一歲 */
        if (monthNow < monthBirth || (monthNow == monthBirth && dayNow < dayBirth)) {
            age--;
        }

        return age > yearsOld;
    }

    public static String isTWIdentityNumber(String idCard) {
        String regex = "[a-zA-Z]{1}[1-2]{1}\\d{8}";
        if (Pattern.matches(regex, idCard)) {
            return idCard.toUpperCase();
        } else {
            return null;
        }
    }

    public static boolean isEmail(String email) {
        boolean result = false;
        if (EMAIL_PATTERN.matcher(email).matches()) {
            result = true;
        }
        return result;
    }
}
