package org.chenxilin.flink.function.flatmap;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将一行字符串切分为一个二维数组 key为切分后的值 value为1
 * 输入"a b a c d" 将得到 ((a, 1), (b, 1), (a, 1), (c, 1), (d, 1))
 *
 * @author chenxilin
 */
public class LineSplitterFlatMapFunction implements FlatMapFunction<String, Tuple2<String, Integer>> {

    private static final Logger LOG = LoggerFactory.getLogger(LineSplitterFlatMapFunction.class);

    @Override
    public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
        String[] tokens = s.toLowerCase().split("\\W+");

        for (String token : tokens) {
            if (token.length() > 0) {
                collector.collect(new Tuple2<>(token, 1));
            } else {
                LOG.error("输入的内容不能为空");
                // throw new Exception("");
            }
        }
    }
}
