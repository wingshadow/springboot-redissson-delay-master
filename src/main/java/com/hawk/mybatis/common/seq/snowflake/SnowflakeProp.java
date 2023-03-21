package com.hawk.mybatis.common.seq.snowflake;


import lombok.ToString;

import java.io.Serializable;

/**
 * 序列化参数配置
 * 
 */
@ToString
public class SnowflakeProp implements Serializable {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8598854142503663549L;
    
    /**
     * 数据中心标识
     */
    private int datacenter = 1;
    
    /**
     * 机器标识
     */
    private int machine = 1;
    
    /**
     * 时间戳相对起始点
     */
    private String startStmp = "2018-10-01 00:00:00";
    
    /**
     * @return 数据中心标识
     */
    public long getDatacenter() {
        return this.datacenter;
    }
    
    /**
     * @param datacenter 数据中心标识
     */
    public void setDatacenter(int datacenter) {
        this.datacenter = datacenter;
    }
    
    /**
     * @return 机器标识
     */
    public int getMachine() {
        return this.machine;
    }
    
    /**
     * @param machine 机器标识
     */
    public void setMachine(int machine) {
        this.machine = machine;
    }

    /**
     * @return 时间戳相对起始点
     */
    public String getStartStmp() {
        return this.startStmp;
    }

    /**
     * @param startStmp 时间戳相对起始点
     */
    public void setStartStmp(String startStmp) {
        this.startStmp = startStmp;
    }
    
}
