package bot.entity;

/**
 * WebSocket网关
 *
 * @author 梁振辉
 * @since 2023-05-26 11:14:37
 */
public class Gateway {
    /**
     * WebSocket网关地址
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
