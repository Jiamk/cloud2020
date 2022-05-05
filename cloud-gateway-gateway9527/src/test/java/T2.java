import java.time.ZonedDateTime;

/**
 * author admin
 * date 2022/5/4 - 11:21
 * description
 */
public class T2 {

    public static void main(String[] args) {
        ZonedDateTime zbj = ZonedDateTime.now(); //默认时区
        System.out.println(zbj);
        //2022-05-04T11:22:16.028+08:00[Asia/Shanghai]
    }
}

