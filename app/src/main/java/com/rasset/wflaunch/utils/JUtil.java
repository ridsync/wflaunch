package com.rasset.wflaunch.utils;

import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andman on 2015-11-30.
 *  Each Views Static Utils
 */
public class JUtil {

    private static long	PREVIOUS_CLICKED_TIME = 0;
    private static long	PREVIOUS_CLICKED_TIME_2 = 0;
    private static long	LAST_CLICKED_VIEWID = -101;
    // 최초 1회 false
    public static boolean isInterValOver(int interval){
        long curTime = System.currentTimeMillis();
        if (curTime - PREVIOUS_CLICKED_TIME_2 > interval) {
            PREVIOUS_CLICKED_TIME_2 = curTime;
            return true;
        }
        return false;
    }

    public static boolean isDoubleClick(View view){
        return isDoubleClick(view,1000);
    }

    public static boolean isDoubleClick(View view, int interval){
        boolean result = false;
        if(view!=null){
            if (view.getId() == LAST_CLICKED_VIEWID) {
                result = isDoubleClick(interval);
            }else {
                LAST_CLICKED_VIEWID = view.getId();
            }
        } else {
            result = isDoubleClick(interval);
            LAST_CLICKED_VIEWID = -101;
        }
        return result;
    }

    private static boolean isDoubleClick(int interval){

        long curTime = System.currentTimeMillis();
        if (curTime - PREVIOUS_CLICKED_TIME < interval){
            return true;
        }

        PREVIOUS_CLICKED_TIME = curTime;
        return false;
    }

    public static boolean isEmptyString(String str) {
        boolean retVal = false;
        if (str != null) {
            if (str.isEmpty()) {
                retVal = true;
            } else if (str.equalsIgnoreCase("null")) {
                retVal = true;
            }
        } else {
            retVal = true;
        }
        return retVal;
    }

    public static boolean isNotNullStr(String str) {
        boolean retVal = true;
        if (str != null) {
            if (str.isEmpty()) {
                retVal = false;
            } else if (str.equalsIgnoreCase("null")) {
                retVal = false;
            }
        } else {
            retVal = false;
        }
        return retVal;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /** 더블클릭 방지
     * @return
     */

    /**
     *   getElapsedTimeStringFormat
     *   지난 시간표시 출력
     *   @param strDate 서버 날짜 포맷
     *   @return date format : now , 1m, 1h, 1d, 1w
     *   @see // TODO 불필요함 삭제해도됨.
      */
//    public static String getElapsedTimeStringFormat(String strDate) {
//        String strRet = null;
//        Calendar registCal = Calendar.getInstance();
//        // "2014-11-24T16:23:00.000Z"
//        SimpleDateFormat sdf = new SimpleDateFormat(AppConst.COMMON_DATE_FORMAT);
//        Date registDate = null;
//        TimeZone tz = TimeZone.getDefault();
//        TimeZone tzKR = TimeZone.getTimeZone("GMT+0900");
//        try {
//            registDate = sdf.parse(strDate);
//            registCal.setTime(registDate);
//            registCal.setTimeZone(TimeZone.getDefault());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (registDate != null) {
//            Calendar currentCal = Calendar.getInstance();
//            Long diffSec = ((currentCal.getTimeInMillis() - tz.getRawOffset()) - (registCal.getTimeInMillis() - tzKR.getRawOffset())) / 1000;
//            Long diffMin = diffSec / 60;
//            Long diffHour = diffMin / 60;
//            Long diffDay = diffHour / 24;
//            Long diffWeek = diffDay / 7;
//
//            if (diffWeek > 0) {
//                strRet = diffWeek + "w";
//            } else if (diffDay > 0) {
//                strRet = diffDay + "d";
//            } else if (diffHour > 0) {
//                strRet = diffHour + "h";
//            } else {
//                if (diffMin % 60 <= 0) {
//                    strRet = "now";
//                } else {
//                    strRet = diffMin % 60 + "m";
//                }
//            }
//        }
//        return strRet;
//    }
    public static String getElapsedTimeStringFormat(long msDate) {
        String strRet = null;
        Calendar registCal = Calendar.getInstance();
        // "2014-11-24T16:23:00.000Z"
        Date registDate = null;
        TimeZone tz = TimeZone.getDefault();
        TimeZone tzKR = TimeZone.getTimeZone("GMT+0900");
        try {
            registDate = new Date(msDate - tzKR.getRawOffset());
            registCal.setTime(registDate);
            registCal.setTimeZone(TimeZone.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (registDate != null) {
            Calendar currentCal = Calendar.getInstance();
            Long diffSec = ((currentCal.getTimeInMillis() - tz.getRawOffset()) - (registCal.getTimeInMillis() - tzKR.getRawOffset())) / 1000;
            Long diffMin = diffSec / 60;
            Long diffHour = diffMin / 60;
            Long diffDay = diffHour / 24;
            Long diffWeek = diffDay / 7;

            if (diffWeek > 0) {
                strRet = diffWeek + "w";
            } else if (diffDay > 0) {
                strRet = diffDay + "d";
            } else if (diffHour > 0) {
                strRet = diffHour + "h";
            } else {
                if (diffMin % 60 <= 0) {
                    strRet = "now";
                } else {
                    strRet = diffMin % 60 + "m";
                }
            }
        }
        return strRet;

    }

    /**
     * 시간차이 기준 timestamp 차이
     * @param msDate
     * @return
     * @see // timestamp가 GMT+9기준 한국시간으로 내려오므로 타임존 offset계산
     */
    public static String getElapsedTimeStringFormatForTimeLine(long msDate) {
        String strRet = null;
        Calendar registCal = Calendar.getInstance();
        // "2014-11-24T16:23:00.000Z"
        Date registDate = null;
        TimeZone tzKR = TimeZone.getTimeZone("GMT+0900");
        TimeZone tz = TimeZone.getDefault();
        try {
            registDate = new Date(msDate - tzKR.getRawOffset());
            registCal.setTime(registDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (registDate != null) {
            Calendar currentCal = Calendar.getInstance();
            Long diffSec = ((currentCal.getTimeInMillis() - tz.getRawOffset()) - (registCal.getTimeInMillis() - tzKR.getRawOffset())) / 1000;
            Long diffMin = diffSec / 60;
            Long diffHour = diffMin / 60;
            Long diffDay = diffHour / 24;
            Long diffMonth = diffDay / 30;
            Long diffWeek = diffDay / 7;

            if (diffMonth > 0){
                strRet = diffMonth + "개월 전";
            } else if (diffWeek > 0) {
                strRet = diffWeek + "주 전";
            } else if (diffDay > 0) {
                strRet = diffDay + "일 전";
            } else if (diffHour > 0) {
                strRet = diffHour + "시간 전";
            } else {
                if (diffMin % 60 <= 0) {
                    strRet = "조금전";
                } else {
                    strRet = diffMin % 60 + "분 전";
                }
            }
        }
        return strRet;

    }


    /**
     *   getElapsedTimeDaysCount
     *   지난 시간표시 출력 날짜기준
     *   @param lDate 서버 날짜 ms
     *   @return  elased days number
     *   @see // timestamp가 GMT+9기준 한국시간으로 내려오므로 타임존 offset계산
     */
    public static int getElapsedTimeDaysCount(long lDate) {
        int lElapsedDays = 0;
        Calendar registCal = Calendar.getInstance();
        // "2014-11-24T16:23:00.000Z"
        Date registDate = null;
        TimeZone tz = TimeZone.getDefault();
        TimeZone tzKR = TimeZone.getTimeZone("GMT+0900");
        try {
            registCal.setTimeInMillis(lDate);
            long tztime = registCal.getTimeInMillis() - tzKR.getRawOffset();
            registCal.setTimeInMillis(tztime);
            registDate = registCal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (registDate != null) {
            Calendar currentCal = Calendar.getInstance();

            int nYearOffset = currentCal.get(Calendar.YEAR) - registCal.get(Calendar.YEAR);

            if(nYearOffset > 0) {
                lElapsedDays = (currentCal.get(Calendar.DAY_OF_YEAR) + (365 * nYearOffset)) - registCal.get(Calendar.DAY_OF_YEAR);
            } else {
                lElapsedDays = currentCal.get(Calendar.DAY_OF_YEAR) - registCal.get(Calendar.DAY_OF_YEAR);
            }

        }

        return lElapsedDays;
    }


//    public static String getStringRemainTime() {
//        String date = SQApp.getsUserStat().getSpotExireDate();
//        SimpleDateFormat sdf = new SimpleDateFormat(AppConst.COMMON_DATE_FORMAT);
//        Calendar now = Calendar.getInstance();
//        Calendar expireCal = Calendar.getInstance();
//        try {
//            Date expireDate = sdf.parse(date);
//            expireCal.setTime(expireDate);
//            expireCal.setTimeZone(TimeZone.getDefault());
//            TimeZone tz = TimeZone.getDefault();
//            TimeZone tzKR = TimeZone.getTimeZone("GMT+0900");
//            Long diffSec = ((expireCal.getTimeInMillis() - tzKR.getRawOffset()) - (now.getTimeInMillis() - tz.getRawOffset()));
//            if (diffSec >0){
//                long hour = diffSec / (1000*60*60);
//                String strHour = String.valueOf(hour);
//                if (hour < 10) strHour = "0"+strHour;
//                long min = (diffSec / (1000*60)) % 60;
//                String strMin = String.valueOf(min);
//                if (min < 10) strMin = "0"+strMin;
//                return strHour + ":" + strMin +"분";
//            } else {
//                return "00:00분";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "00:00분";
//        }
//    }

    /**
     *   getDateFormatFromString
     *   원하는 시간타입 String반환
     *   @param strDate 서버 날짜 포맷
     *   @return  elased days number
     */
//    public static Date getDateFormatFromString(String strDate, String dateFormat) {
//        SimpleDateFormat sdf = new SimpleDateFormat(AppConst.COMMON_DATE_FORMAT);
//        Calendar registCal = Calendar.getInstance();
//        Calendar currentCal = Calendar.getInstance();
//
//        Date registDate = null;
//        Date currentDate = null;
//    }

    /**
     * 문자 뒤에서 2자리만 남기고 마스킹처리
     * @param oriText
     * @return  ***ex
     */
    public static String convertStringAsMasking(String oriText) {
//        if (oriText == null) return "";
//        String result = oriText;
//
//        if(oriText.length() > 2){
//            String preFix = oriText.substring(0, oriText.length() - 2);
//            String strEnd = oriText.substring(oriText.length() - 2, oriText.length());
//            String strMasking = preFix.replaceAll("[a-zA-Z0-9]", "*");
//            result = strMasking + strEnd;
//        }
//        return result;
        return oriText;
    }

    public static boolean isEmpty(Object s) {
        if (s == null) {
            return true;
        }
        if ((s instanceof String) && (((String)s).trim().length() == 0)) {
            return true;
        }
        if (s instanceof Map) {
            return ((Map<?, ?>)s).isEmpty();
        }
        if (s instanceof List) {
            return ((List<?>)s).isEmpty();
        }
        if (s instanceof Object[]) {
            return (((Object[])s).length == 0);
        }
        return false;
    }

    public static boolean isNumber(String str){
        boolean result = false;

        try{
            Double.parseDouble(str) ;
            result = true ;
        }catch(Exception e){}

        return result ;
    }

    public static String convertStringToDate(long indate, String format)
    {
        String dateString = "";
        SimpleDateFormat sdfr = new SimpleDateFormat(format,new Locale("ko", "KR"));
   /*you can also use DateFormat reference instead of SimpleDateFormat
    * like this: DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
    */
        try{
            Date date = new Date(indate);
            dateString = sdfr.format( date );
        }catch (Exception ex ){
            System.out.println(ex);
        }
        return dateString;
    }

    public static String convertStringToDate(long indate)
    {
        String format = "";
        Calendar today = Calendar.getInstance();
        Calendar regDate = Calendar.getInstance();
        regDate.setTimeInMillis(indate);
        if ( today.get(Calendar.YEAR) == regDate.get(Calendar.YEAR) &&
                today.get(Calendar.MONTH) == regDate.get(Calendar.MONTH) &&
                today.get(Calendar.DAY_OF_MONTH) == regDate.get(Calendar.DAY_OF_MONTH)){
            format = "a hh:mm";
        } else {
            format = "yy/MM/dd";
        }
        String dateString = "";
        SimpleDateFormat sdfr = new SimpleDateFormat(format,new Locale("ko", "KR"));
   /*you can also use DateFormat reference instead of SimpleDateFormat
    * like this: DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
    */
        try{
            Date date = new Date(indate);
            dateString = sdfr.format( date );
        }catch (Exception ex ){
            System.out.println(ex);
        }
        return dateString;
    }

}
