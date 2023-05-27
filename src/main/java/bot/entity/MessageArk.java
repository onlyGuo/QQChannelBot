package bot.entity;

import java.util.List;

/**
 * @author 梁振辉
 * @since 2023-05-26 16:03:01
 */
public class MessageArk {
    /**
     * ark模板id（需要先申请）
     */
    private Integer template_id;
    /**
     * kv值列表
     */
    private List<MessageArkKv> kv;

    public Integer getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(Integer template_id) {
        this.template_id = template_id;
    }

    public List<MessageArkKv> getKv() {
        return kv;
    }

    public void setKv(List<MessageArkKv> kv) {
        this.kv = kv;
    }
}
