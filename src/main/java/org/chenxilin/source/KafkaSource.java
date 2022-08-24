package org.chenxilin.flink.source;

import lombok.Data;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.chenxilin.basic.kafka.KafkaSourceConfig;
import org.chenxilin.flink.deserialize.CommonDeserializationSchema;

/**
 * Kafka源
 *
 * @author chenxilin
 */
@Data
public class KafkaSource<T> {

    private KafkaSourceConfig kafkaSourceConfig;

    /**
     * 反序列化操作类
     */
    private DeserializationSchema<T> deserializer;


    /**
     * 构造函数
     *
     * @param kafkaSourceConfig {@link KafkaSourceConfig}
     * @param deserializer      反序列化Schema操作类
     */
    public KafkaSource(KafkaSourceConfig kafkaSourceConfig, DeserializationSchema<T> deserializer) {
        this.kafkaSourceConfig = kafkaSourceConfig;
        this.deserializer = deserializer;
    }

    /**
     * 构造函数
     *
     * @param kafkaSourceConfig {@link KafkaSourceConfig}
     * @param outputClass       将kafka消息反序列化成什么类
     */
    public KafkaSource(KafkaSourceConfig kafkaSourceConfig, Class<T> outputClass) {
        this.kafkaSourceConfig = kafkaSourceConfig;
        this.deserializer = new CommonDeserializationSchema<>(outputClass);
    }

    /**
     * 创建kafkaConsumer {@link FlinkKafkaConsumer< T >}
     */
    public FlinkKafkaConsumer<T> buildConsumer() {
        return new FlinkKafkaConsumer<>(kafkaSourceConfig.getTopics(), deserializer, kafkaSourceConfig.toProperties());
    }
}
