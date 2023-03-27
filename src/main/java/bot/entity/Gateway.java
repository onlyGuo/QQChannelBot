package bot.entity;

/**
 * WebSocket接入地址
 *
 * @author 梁振辉
 * @since 2023-03-28 00:12:02
 */
public class Gateway {

    /**
     * 一个用于连接websocket的地址。
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
