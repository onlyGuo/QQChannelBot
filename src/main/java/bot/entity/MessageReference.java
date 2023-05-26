package bot.entity;

import lombok.Data;

/**
 * @author 梁振辉
 * @since 2023-05-26 16:05:58
 */
@Data
public class MessageReference {
    /**
     * 需要引用回复的消息 id
     */
    private String message_id;
    /**
     * 是否忽略获取引用消息详情错误，默认否
     */
    private Boolean ignore_get_message_error;
}
