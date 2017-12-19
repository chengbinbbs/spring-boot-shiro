package com.chengbinbbs.springbootshiro.endpoint;

import org.springframework.boot.actuate.endpoint.Endpoint;

import java.util.Date;

/**
 * 监控
 *
 * @author zhangcb
 * @created 2017-07-19 15:50.
 */
public class MyEndPoint implements Endpoint<MemInfo> {

    /**
     * (1) getId是EndPoint的唯一标识，
     * (2)MVC接口对外暴露的路径:http://localhost:8080/myendpoint
     */
    @Override
    public String getId() {
        return "myendpoint";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public MemInfo invoke() {
        MemInfo memInfo = new MemInfo();
        Runtime runtime = Runtime.getRuntime();

        memInfo.setRecordTime(new Date());
        memInfo.setMaxMemory(runtime.maxMemory());
        memInfo.setTotalMemory(runtime.totalMemory());
        return memInfo;
    }

}
