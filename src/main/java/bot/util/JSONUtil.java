package bot.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * JSON工具类
 *
 * @author 梁振辉
 * @since 2023-05-27 15:21:02
 */
public class JSONUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        //忽略实体类未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 创建ObjectNode对象
     *
     * @return ObjectNode对象
     */
    public static ObjectNode create() {
        return objectMapper.createObjectNode();
    }

    public static ArrayNode array(int... items) {
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (int item : items) {
            arrayNode.add(item);
        }
        return arrayNode;
    }

    /**
     * JSON字符串转对象
     *
     * @param str  JSON字符串
     * @param type 对象类型
     * @param <T>  泛型
     * @return 对象
     */
    public static <T> T toBean(String str, Class<T> type) {
        try {
            return objectMapper.readValue(str, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JsonNode对象转POJO
     *
     * @param jsonNode JsonNode对象
     * @param type     对象类型
     * @param <T>      泛型
     * @return 对象
     */
    public static <T> T toBean(JsonNode jsonNode, Class<T> type) {
        return objectMapper.convertValue(jsonNode, type);
    }

    /**
     * 对象转JSON字符串
     *
     * @param obj 对象
     * @return JSON字符串
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
