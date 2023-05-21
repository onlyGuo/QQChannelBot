package bot.entity;

/**
 * 统一返回值类型
 *
 * @author 梁振辉
 * @since 2023-04-15 09:41:19
 */
public class Result {

    /**
     * 返回码
     */
    public Integer code;
    /**
     * 返回信息
     */
    public String msg;
    /**
     * 返回数据
     */
    public Object data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}