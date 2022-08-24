package org.chenxilin.flink.cases.wordcount;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.chenxilin.flink.function.flatmap.LineSplitterFlatMapFunction;


/**
 * Flink示例——本地文件WordCount批处理
 *
 * @author chenxilin
 */
public class FileWordCountBatch {
    public static void main(String[] args) throws Exception {
        // 创建flink环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // 读取数据
        String inputPath = "/Users/chenxilin/Code/all_about_java/flink/src/main/resources/hello.txt";
        DataSource<String> text = env.readTextFile(inputPath);

        // 数据转换和处理
        DataSet<Tuple2<String, Integer>> ds = text
            .flatMap(new LineSplitterFlatMapFunction())
            .groupBy(0)
            .sum(1);

        // 打印结果(print方法内部已经调用execute方法，所以不用再调用)
        ds.print();
    }
}
