 package org.chenxilin.flink.deserialize;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 通用消息反序列化Schema
 * 使用场景：消费kafka时需要将kafka消息转化为特定的实体类
 *
 * @author chenxilin
 */
public class CommonDeserializationSchema<OUTPUT> implements DeserializationSchema<OUTPUT> {

    private final Class<OUTPUT> outputClass;
    private final ObjectMapper MAPPER = new ObjectMapper();

    public CommonDeserializationSchema(Class<OUTPUT> outputClass) {
        this.outputClass = outputClass;
    }

    /**
     * 反序列化操作（将byte类型数据转为{@link OUTPUT}对象）
     */
    @Override
    public OUTPUT deserialize(byte[] message) throws IOException {
        return MAPPER.readValue(message, outputClass);
    }

    /**
     * 是否为流的终点
     */
    @Override
    public boolean isEndOfStream(Object nextElement) {
        return false;
    }

    /**
     * 获取输出的类型信息
     */
    @Override
    public TypeInformation<OUTPUT> getProducedType() {
        return TypeInformation.of(outputClass);
    }
}
