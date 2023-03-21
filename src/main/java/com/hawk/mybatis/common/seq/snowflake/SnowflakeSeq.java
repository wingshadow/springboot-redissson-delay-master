package com.hawk.mybatis.common.seq.snowflake;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * <pre>
 * 序列生成器
 * 
 * 雪花算法，SnowFlake算法生成的ID大致上是按照时间递增的，用在分布式系统中时，
 * 需要注意数据中心标识和机器标识必须唯一，这样就能保证每个节点生成的ID都是唯一的。
 * 
 * A snowflake is a source of k-ordered unique 64-bit integers.
 * 
 * 原理：
 * SnowFlake算法产生的ID是一个64位的整型，结构如下（每一部分用“-”符号分隔）：
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 * 
 * 1) 1位标识部分，在java中由于long的最高位是符号位，正数是0，负数是1，一般生成的ID为正数，所以为0；
 * 2) 41位时间戳部分，这个是毫秒级的时间，一般实现上不会存储当前的时间戳，而是时间戳的差值（当前时间-固定的开始时间），
 * 这样可以使产生的ID从更小值开始；41位的时间戳可以使用69年，(1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69年；
 * 3) 10位节点部分，Twitter实现中使用前5位作为数据中心标识，后5位作为机器标识，可以部署1024个节点；
 * 4) 12位序列号部分，支持同一毫秒内同一个节点可以生成4096个ID；
 * </pre>
 * 
 * @version 1.0.0 2018-03-21 16:42:37 初始创建
 * @version 2.1.2 2018-08-20 09:22:17 增加属性：startStmp, 移除事态常量：START_STMP，修改构造方法。
 */
public class SnowflakeSeq {
    
    /**
     * 序列号占用的位数
     */
    private static final long SEQUENCE_BIT = 12;
    
    /**
     * 机器标识占用的位数
     */
    private static final long MACHINE_BIT = 5;
    
    /**
     * 数据中心占用的位数
     */
    private static final long DATACENTER_BIT = 5;
    
    /**
     * 每一部分的最大值
     */
    private static final long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    
    private static final long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    
    private static final long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    
    /**
     * 每一部分向左的位移
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    
    private static final long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    
    private static final long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;
    
    /**
     * 数据中心
     */
    private long datacenterId;
    
    /**
     * 机器标识
     */
    private long machineId;
    
    /**
     * 时间戳相对起始点
     */
    private long startStmp;
    
    /**
     * 序列号
     */
    private long sequence = 0L;
    
    /**
     * 上一次时间戳
     */
    private long lastStmp = -1L;
    
    /**
     * 构造器
     * 
     * @param snowflakeProp 序列生成器配置参数
     */
    public SnowflakeSeq(SnowflakeProp snowflakeProp) {
        if (snowflakeProp.getDatacenter() > MAX_DATACENTER_NUM || snowflakeProp.getDatacenter() < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (snowflakeProp.getMachine() > MAX_MACHINE_NUM || snowflakeProp.getMachine() < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        
        String startStmpStr = snowflakeProp.getStartStmp();
        LocalDateTime ldt = LocalDateTime.parse(startStmpStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        long ldtLong = ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        
        this.datacenterId = snowflakeProp.getDatacenter();
        this.machineId = snowflakeProp.getMachine();
        this.startStmp = ldtLong;
    }
    
    /**
     * 产生下一个ID
     *
     * @return 下一个ID
     */
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new SnowflakeException("Clock moved backwards.  Refusing to generate id");
        }
        
        if (currStmp == lastStmp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }
        
        lastStmp = currStmp;
        
        return (currStmp - startStmp) << TIMESTMP_LEFT // 时间戳部分
                | datacenterId << DATACENTER_LEFT       // 数据中心部分
                | machineId << MACHINE_LEFT             // 机器标识部分
                | sequence;                             // 序列号部分
    }
    
    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }
    
    private long getNewstmp() {
        return System.currentTimeMillis();
    }
    
}
