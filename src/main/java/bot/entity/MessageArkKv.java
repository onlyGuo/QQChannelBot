package bot.entity;

import lombok.Data;

import java.util.List;

/**
 * @author 梁振辉
 * @since 2023-05-26 16:03:20
 */
@Data
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
}
