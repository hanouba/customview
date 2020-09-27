package retrofit2.adapter.http.http;

/**
 * Description: <ResponseThrowable><br>
 * Author:      gxl<br>
 * Date:        2019/3/18<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class ResponseException extends Exception {
    public int code;
    public String message;

    public ResponseException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResponseThrowable{" + "code=" + code + ", message='" + message + '\'' + '}';
    }
}
