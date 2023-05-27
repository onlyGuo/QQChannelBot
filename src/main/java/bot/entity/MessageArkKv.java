package bot.entity;

import java.util.List;

/**
 * @author 梁振辉
 * @since 2023-05-26 16:03:20
 */
public class MessageArkKv {
    /**
     * key
     */
    private String key;
    /**
     * value
     */
    private String value;
    /**
     * ark obj类型的列表
     */
    private List<MessageArkObj> obj;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<MessageArkObj> getObj() {
        return obj;
    }

    public void setObj(List<MessageArkObj> obj) {
        this.obj = obj;
    }
}
