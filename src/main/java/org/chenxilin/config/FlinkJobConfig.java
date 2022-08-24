package org.chenxilin.flink.config;

import lombok.Data;

/**
 * @author chenxilin
 */
@Data
public class FlinkJobConfig {
    /**
     * flink任务的slot数量
     */
    private int slots;

    /**
     * 分配给单个task manager的内存资源
     */
    private int taskManagerMemory;

    /**
     * 分配给job manager的内存资源
     */
    private int jobManagerMemory;
}
