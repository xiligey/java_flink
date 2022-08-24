package org.chenxilin.flink.cases.frauddetection;

import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;

/**
 * 欺诈检测器
 *
 * @author chenxilin
 */
public class FraudDetector extends KeyedProcessFunction<Long, Transaction, Alert> {

    @Override
    public void processElement(Transaction value, KeyedProcessFunction<Long, Transaction, Alert>.Context ctx, Collector<Alert> out) throws Exception {
        Alert alert = new Alert();
        alert.setId(value.getAccountId());

        out.collect(alert);
    }
}
