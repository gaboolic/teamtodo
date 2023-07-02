package tk.gbl.anno;

import java.lang.annotation.*;

/**
 * Date: 2015/4/25
 * Time: 16:37
 *
 * @author Tian.Dong
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
@Documented
public @interface ValidJump {
}
