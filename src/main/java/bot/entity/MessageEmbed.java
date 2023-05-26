package bot.entity;

import lombok.Data;

import java.util.List;

/**
 * @author 梁振辉
 * @since 2023-05-26 15:50:58
 */
@Data
public class MessageEmbed {
    /**
     * 标题
     */
    private String title;
    /**
     * 消息弹窗内容
     */
    private String prompt;
    /**
     * 缩略图
     */
    private MessageEmbedThumbnail thumbnail;
    /**
     * embed字段数据
     */
    private List<MessageEmbedField> fields;
}
