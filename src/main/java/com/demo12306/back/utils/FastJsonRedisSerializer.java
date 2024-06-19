package com.demo12306.back.utils;
import java.nio.charset.Charset;
import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    private final static ParserConfig defaultRedisConfig = new ParserConfig();
    static { defaultRedisConfig.setAutoTypeSupport(true);}

    /**
     * DEFAULT_CHARSET <br>
     */
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * clazz 反序列化类<br>
     */
    private Class<T> clazz;

    /**
     * <Description> 构造器<br>
     *
     * @param clazz 反序列化目标类
     */
    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     * Description: 序列化<br>
     *
     * @author xubin<br>
     * @taskId <br>
     * @param t
     * @return
     * @throws SerializationException <br>
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }


    /**
     * Description: 反序列化<br>
     *
     * @author xubin<br>
     * @taskId <br>
     * @param bytes
     * @return
     * @throws SerializationException <br>
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return (T) JSON.parseObject(str, clazz, defaultRedisConfig);
    }

}
