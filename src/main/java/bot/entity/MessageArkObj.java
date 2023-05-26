package bot.entity;

import lombok.Data;

import java.util.List;

/**
 * @author 梁振辉
 * @since 2023-05-26 16:03:44
 */
@Data
public class MessageArkObj {
    /**
     * ark objkv列表
     */
    private List<MessageArkObjKv> obj_kv;
}
