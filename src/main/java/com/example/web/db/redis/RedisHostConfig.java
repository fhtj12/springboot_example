package com.example.web.db.redis;

import com.example.web.util.PropertiesManager;
import lombok.Getter;

@Getter
public class RedisHostConfig {

    private final String host;
    private final int port;
    private final int dbNum;
    private final int maxConnection;
    private boolean isAuth = false;
    private String password;

    public RedisHostConfig(String host, int port, int dbNum, int maxConnection) {
        this.host = host;
        this.port = port;
        this.dbNum = dbNum;
        this.maxConnection = maxConnection;
    }

    public RedisHostConfig(String host, int port, int dbNum, int maxConnection, String password) {
        this(host, port, dbNum, maxConnection);
        this.isAuth = true;
        this.password = password;
    }

    public RedisHostConfig(String hostPropKey, String portPropKey, String dbNumPropKey, String maxConnPropKey) throws Exception {
        String host = PropertiesManager.getProperty(hostPropKey);
        if (host == null) {
            throw new Exception("REDIS host 설정 없음.");
        }

        String port = PropertiesManager.getProperty(portPropKey);
        if (port == null) {
            throw new Exception("REDIS port 설정 없음.");
        }

        String dbNum = PropertiesManager.getProperty(dbNumPropKey);
        if (dbNum == null) {
            throw new Exception("REDIS DB num 설정 없음.");
        }

        String maxConn = PropertiesManager.getProperty(maxConnPropKey);

        this.host = host;
        this.port = Integer.parseInt(port);
        this.dbNum = Integer.parseInt(dbNum);
        this.maxConnection = maxConn == null ? 100 : Integer.parseInt(maxConn); // default 100
    }

    public RedisHostConfig(String hostPropKey, String portPropKey, String dbNumPropKey, String maxConnPropKey
            , String passwordPropKey) throws Exception {
        this(hostPropKey, portPropKey, dbNumPropKey, maxConnPropKey);
        this.password = PropertiesManager.getProperty(passwordPropKey);
        this.isAuth = this.password != null;
    }

    public boolean isAuth() {
        return isAuth;
    }

    protected String getPassword() {
        return password;
    }

}
