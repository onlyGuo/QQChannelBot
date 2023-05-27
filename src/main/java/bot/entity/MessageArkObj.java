package bot.entity;

import java.util.List;

/**
 * @author 梁振辉
 * @since 2023-05-26 16:03:44
 */
public class MessageArkObj {
    /**
     * ark objkv列表
     */
    private List<MessageArkObjKv> obj_kv;

    public List<MessageArkObjKv> getObj_kv() {
        return obj_kv;
    }

    public void setObj_kv(List<MessageArkObjKv> obj_kv) {
        this.obj_kv = obj_kv;
    }
}
