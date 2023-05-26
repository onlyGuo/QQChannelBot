package bot.entity;

import lombok.Data;

import java.util.List;

/**
 * @author 梁振辉
 * @since 2023-05-26 16:03:01
 */
@Data
public class MessageArk {
    /**
     * ark模板id（需要先申请）
     */
    private Integer template_id;
    /**
     * kv值列表
     */
    private List<MessageArkKv> kv;
}
