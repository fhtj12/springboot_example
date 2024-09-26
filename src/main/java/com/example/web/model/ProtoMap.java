package com.example.web.model;

import com.example.web.exception.ServiceException;
import com.example.web.util.CUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProtoMap extends HashMap<String, Object> {
    public ProtoMap() {}

    public ProtoMap(Map<String, Object> map) {
        super(map);
    }

    /**
     * key 변경. (이전 key value 삭제 후 새로운 key 로 value 가 이동.)
     * @param srcKey 원래 key
     * @param changeKey 변경할 key
     */
    public void keySwap(String srcKey, String changeKey) {
        super.put(changeKey, super.get(srcKey));
        super.remove(srcKey);
    }

    public void toLowerCaseAllKey() {
        HashMap<String, Object> copyMap = new HashMap<>();
        for (String key : super.keySet()) {
            copyMap.put(key.toLowerCase(), super.get(key));
        }
        super.clear();
        super.putAll(copyMap);
    }

    public void toUpperCaseAllKey() {
        HashMap<String, Object> copyMap = new HashMap<>();
        for (String key : super.keySet()) {
            copyMap.put(key.toUpperCase(), super.get(key));
        }
        super.clear();
        super.putAll(copyMap);
    }

    public boolean isEmptyValue(String key) {
        return CUtil.isEmpty(this.get(key));
    }

    public void checkEmptyValue(String key) {
        if (isEmptyValue(key)) throw new ServiceException("CM0001", new Object[] {key});
    }

    public void checkEmptyValue(String ... keys) {
        for (String key : keys) {
            if (isEmptyValue(key)) throw new ServiceException("CM0001", new Object[] {key});
        }
    }

    public ProtoMap append(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public ProtoMap append(Map map, String key) {
        super.put(key, map.get(key));
        return this;
    }

    /**
     * value 복사 및 key 유지
     * @param map 복제할 원본 Map
     * @param key key
     */
    public void copy(Map map, String key) {
        this.put(key, map.get(key));
    }

    /**
     * value 복사 및 key 는 변경
     * @param map 복제할 원본 Map
     * @param srcKey 복제할 원본 Map의 key
     * @param destKey 복제대상 Map의 key
     */
    public void copy(Map map, String srcKey, String destKey) {
        this.put(destKey, map.get(srcKey));
    }

    @Override
    public ProtoMap clone() {
        return (ProtoMap) super.clone();
    }

    public String getString(String key) {
        Object value = super.get(key);
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public int getInt(String key) {
        Object value = super.get(key);
        if (value == null) {
            return 0;
        }
        return new BigDecimal(value.toString()).intValue();
    }

    public long getLong(String key) {
        Object value = super.get(key);
        if (value == null) {
            return 0L;
        }
        return new BigDecimal(value.toString()).longValue();
    }

    public double getDouble(String key) {
        Object value = super.get(key);
        if (value == null) {
            return 0.0d;
        }
        return new BigDecimal(value.toString()).doubleValue();
    }

    public float getFloat(String key) {
        Object value = super.get(key);
        if (value == null) {
            return 0.0f;
        }
        return new BigDecimal(value.toString()).floatValue();
    }

}
