package org.chenxilin.flink.cases.wordcount;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.chenxilin.flink.function.flatmap.LineSplitterFlatMapFunction;
import org.chenxilin.flink.function.keyselector.IndexKeySelector;

/**
 * Flink示例——Socket文本流处理
 *
 * @author chenxilin
 */
public class SocketTextStreamWordCountStream {
    public static void main(String[] args) throws Exception {
        String hostName = "localhost";
        int port = 9000;

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> stream = env.socketTextStream(hostName, port);

        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = stream
            .flatMap(new LineSplitterFlatMapFunction())
            .keyBy(new IndexKeySelector(0))
            .sum(1);

        sum.print();

        env.execute("SocketTextStreamWordCount");
    }
}
