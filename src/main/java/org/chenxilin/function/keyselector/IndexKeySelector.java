package org.chenxilin.flink.function.keyselector;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;

/**
 * 按照索引来选择Key
 *
 * @author chenxilin
 */
@Data
@AllArgsConstructor
public class IndexKeySelector implements KeySelector<Tuple2<String, Integer>, String> {

    /**
     * 索引（按照第几个索引来选择key）
     */
    private Integer index;

    @Override
    public String getKey(Tuple2<String, Integer> value) {
        return value.getField(index);
    }
}
