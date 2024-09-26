package com.example.web.model;

import java.util.Map;

public class QueryMap extends ProtoMap {

    public QueryMap() {}

    public QueryMap(ProtoMap protoMap) {
        super(protoMap);
    }

    public QueryMap(String usid) {
        super();
        this.put("USID", usid);
    }

    public QueryMap append(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public QueryMap append(Map map, String key) {
        super.put(key, map.get(key));
        return this;
    }

    public QueryMap appendUSID(Map map) {
        this.put("USID", map.get("USID"));
        return this;
    }

    public QueryMap appendUSID(String usid) {
        this.put("USID", usid);
        return this;
    }

}
