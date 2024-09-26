package com.example.web.util;

import com.example.web.dao.ICommonDao;
import com.example.web.model.ProtoMap;
import com.example.web.model.QueryMap;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CUtil {

    /**
     * elapse millis 계산
     *
     * @param nanoStart nano millis
     * @return millis
     */
    public static long getElapseMillis(long nanoStart) {
        return ((System.nanoTime() - nanoStart) / 1000000L);
    }

    /**
     * 널을 빈 스트링으로 변환
     *
     * @param value 값이 null 혹은 문자열 "null" 일 경우 ""로 변환
     * @return 변환 결과
     */
    public static String nullToString(String value) {
        if (value == null || value.equals("null")) {
            return "";
        } else {
            return value;
        }
    }

    /**
     * Exception 을 String 으로 바꾸어주는 함수
     *
     * @param e Exception
     * @return Exception.printStackTrace 의 String 결과
     */
    public static String exceptionToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter o = new PrintWriter(sw);
        e.printStackTrace(o);
        return sw.toString();
    }

    /**
     * Exception 을 String 으로 바꾸어주는 함수
     *
     * @param e Exception
     * @return Exception.printStackTrace 의 String 결과 (최대 5라인)
     */
    public static String exceptionToStringShort(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter o = new PrintWriter(sw);

        o.print(e.getClass().getName() + ":" + e.getMessage());

        StackTraceElement[] exp = e.getStackTrace();
        int rowCnt = 0;
        for (StackTraceElement stackTraceElement : exp) {
            if (stackTraceElement.getClassName().startsWith("com.lotto")) {
                o.print("|" + stackTraceElement);
                rowCnt++;
            }

            if (rowCnt >= 5) {
                break;
            }
        }
        return sw.toString();
    }

    /**
     * @return yyyyMMddHHmmss String
     */
    public static String getSimpleDate() {
        return getSimpleDate("yyyyMMddHHmmss");
    }

    /**
     * @return yyyy/MM/dd-hh:mm:ss.SSS String
     */
    public static String getSimpleDate(String format) {
        if (format == null) format = "yyyy/MM/dd-HH:mm:ss.SSS";

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(format);
        return sdf.format(cal.getTime());
    }

    // 한글 포함 찾기 1 -----------------------------------------
    // 한글 정규 표현
    //private static String HAN_REQEX = ".*[ㄱ-ㅎㅏ-ㅣ가-R]+.*";
    public static String HAN_REQEX = "[ㄱ-ㅎㅏ-ㅣ가-R]";
    public static String ALPHA_REQEX = "\\p{Alpha}";// [a-zA-Z]
    public static String DIGIT_REQEX = "\\p{Digit}";// [0-9]
    public static String ALNUM_REQEX = "\\p{Alnum}";// [a-zA-Z0-9]
    public static String SPECIAL_REQEX = "[!-/:-@\\[-`{-~]";// "[!-/:-@\\[-`{-~]"

    /**
     * 패턴 포함 여부
     *
     * @param pattern Pattern
     * @param str     검사할 문자열
     * @return 패턴 포함시 true
     */
    private static boolean containsPattern(Pattern pattern, String str) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * 한글 포함 여부
     *
     * @param str 검사할 문자열
     * @return 한글 포함시 true
     */
    public static boolean containsHan(String str) {
        return containsPattern(Pattern.compile(".*" + HAN_REQEX + "+.*"), str);
    }

    /**
     * 알파벳 포함 여부
     *
     * @param str 검사할 문자열
     * @return 알파벳 포함시 true
     */
    public static boolean containsAlpha(String str) {
        return containsPattern(Pattern.compile(".*" + ALPHA_REQEX + "+.*"), str);
    }

    /**
     * 숫자 포함 여부
     *
     * @param str 검사할 문자열
     * @return 숫자 포함시 true
     */
    public static boolean containsDigit(String str) {
        return containsPattern(Pattern.compile(".*" + DIGIT_REQEX + "+.*"), str);
    }

    /**
     * 알파벳, 숫자 포함 여부
     *
     * @param str 검사할 문자열
     * @return 알파벳, 숫자 포함시 true
     */
    public static boolean containsAlnum(String str) {
        return containsPattern(Pattern.compile(".*" + ALNUM_REQEX + "+.*"), str);
    }

    /**
     * 특수문자 포함 여부
     *
     * @param str 검사할 문자열
     * @return 특수문자 포함시 true
     */
    public static boolean containsSpecialCharacter(String str) {
        return containsPattern(Pattern.compile(".*" + SPECIAL_REQEX + "+.*"), str);
    }

    /**
     * 알파벳으로만 이루어진 문자열인지 검사
     *
     * @param str 검사할 문자열
     * @return boolean
     */
    public static boolean isAlpha(String str) {
        if (containsDigit(str)) return false;
        if (containsHan(str)) return false;
        if (containsSpecialCharacter(str)) return false;
        // 알파벳이면 정상
        return containsAlpha(str); // 공백이거나 whitespace 란 뜻이다.
    }

    /**
     * 한글만 이루어진것인가?
     *
     * @param str 검사할 문자열
     * @return boolean
     */
    public static boolean isHan(String str) {
        if (containsSpecialCharacter(str)) return false;
        if (containsAlnum(str)) return false;
        // 한글이면 정상
        return containsHan(str); // 공백이거나 whitespace 란 뜻이다.
    }

    /**
     * 알파벳과 숫자로만 이루어진것인가?
     *
     * @param str 검사할 문자열
     * @return boolean
     */
    public static boolean isAlphaNumeric(String str) {
        if (containsHan(str)) return false;
        if (containsSpecialCharacter(str)) return false;
        // 알파 뉴메릭이면 정상
        return containsAlnum(str); // 공백이거나 whitespace 란 뜻이다.
    }

    /**
     * 숫자로만 이루어진것인가?
     *
     * @param str 검사할 문자열
     * @return boolean
     */
    public static boolean isDigit(String str) {
        if (containsHan(str)) return false;
        if (containsSpecialCharacter(str)) return false;
        if (containsAlpha(str)) return false;
        // 숫자만이면 정상
        return containsDigit(str); // 공백이거나 whitespace 란 뜻이다.
    }


    /**
     * 공백제거 - 앞,가운데,뒤 모두 제거함
     *
     * @param str 공백 제거할 문자열
     * @return String
     */
    public static String eraseSpace(String str) {
        return str.replaceAll(" ", "");
    }

    /**
     * 가운데 공백이 있는가?
     *
     * @param str 검사할 문자열
     * @return boolean
     */
    public static boolean isCenterSpace(String str) {
        return str.indexOf(" ") > 0;
    }

    /**
     * utf8 decode
     *
     * @param enc 인코딩된 문자열
     * @return 디코딩된 문자열
     */
    public static String decodeFromUtf(String enc) {
        return URLDecoder.decode(enc, StandardCharsets.UTF_8);
    }

    /**
     * Map 을 Json 타입으로 파싱(특정 키만) - 2019/08/19 ikho
     *
     * @param map Map
     * @return JSONObject
     */
    public static JSONObject mapToJsonString(Map<String, Object> map) {
        // 비어있는지 검사
        if (isEmpty(map)) {
            return null;
        }

        JSONObject jsonObject = new JSONObject();

        try {
            for (String key : map.keySet()) {
                jsonObject.put(key, map.get(key));
            }
        } catch (JSONException e) {
            return null;
        }

        return jsonObject;
    }

    public static boolean isEmpty(Object object) {
        if (object == null) return true;
        if ((object instanceof String) && (((String) object).trim().isEmpty())) {
            return true;
        }
        if (object instanceof Map) {
            return ((Map) object).isEmpty();
        }
        if (object instanceof List) {
            return ((List) object).isEmpty();
        }
        if (object instanceof Object[]) {
            return (((Object[]) object).length == 0);
        }

        return false;
    }

    public static boolean isEmpty(Object... objects) {
        for (Object object : objects) {
            if (!isEmpty(object)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 시스템 정의값 가져오기
     *
     * @param ICommonDao ICommonDao
     * @param propCd RFSYS_PROP 테이블의 PROP_CD
     * @return String
     */
    public static String getSysPropValue(ICommonDao ICommonDao, String propCd) {
        return ICommonDao.getSysPropValue(propCd);
    }

    /**
     * 시스템 정의값 가져오기
     *
     * @param ICommonDao ICommonDao
     * @param propCd RFSYS_PROP 테이블의 PROP_CD
     * @return String
     */
    public static String getSysProp(ICommonDao ICommonDao, String propCd) {
        QueryMap tmap = new QueryMap();
        tmap.put("PROP_CD", propCd);
        ProtoMap protoMap = ICommonDao.getSysProp(tmap);
        if (protoMap != null)
            return protoMap.getString("PROP_VAL");
        else
            return null;
    }

}


