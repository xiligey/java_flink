package org.chenxilin.cases.kafkaproducetool;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.chenxilin.basic.kafka.KafkaSinkConfig;
import org.chenxilin.common.file.PropertiesHelper;
import org.chenxilin.common.file.TextHelper;
import org.chenxilin.flink.sink.KafkaSink;
import org.chenxilin.flink.source.TextCycleSource;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

/**
 * @author chenxilin
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesHelper.readPropertiesFile(args[0]);
        ProduceConfig myKafkaProducerConfig = new ProduceConfig(properties);
        int parallelism = myKafkaProducerConfig.getJobParallelism();
        StreamExecutionEnvironment streamEnv = StreamExecutionEnvironment
            .getExecutionEnvironment()
            .setParallelism(parallelism);

        ArrayList<String> jsonLines = TextHelper.readFile(myKafkaProducerConfig.getJsonFile());

        KafkaSinkConfig kafkaSinkConfig = new KafkaSinkConfig(
            myKafkaProducerConfig.getTopic(), myKafkaProducerConfig.getBootstrapServers()
        );

        switch (myKafkaProducerConfig.getProducerStyle()) {
            case "cycle":
                TextCycleSource textCycleSource = new TextCycleSource(jsonLines, myKafkaProducerConfig);
                DataStream<Map<String, Object>> sourceStream = streamEnv.addSource(textCycleSource);
                sourceStream
                    .addSink(new KafkaSink<Map<String, Object>>(kafkaSinkConfig).buildProducer())
                    .setParallelism(parallelism);
                break;
            case "once":
                DataStream<String> text = streamEnv.readTextFile(myKafkaProducerConfig.getJsonFile());
                text.addSink(new KafkaSink<String>(kafkaSinkConfig).buildProducer()).setParallelism(parallelism);
                break;
            default:
                // do nothing
        }

        streamEnv.setParallelism(parallelism).execute("mlops-flink-sink-tool");

    }
}
