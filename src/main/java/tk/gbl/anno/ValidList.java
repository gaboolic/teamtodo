package tk.gbl.anno;

import java.lang.annotation.*;

/**
 * Date: 2015/4/24
 * Time: 21:00
 *
 * @author Tian.Dong
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
@Documented
public @interface ValidList {
  String value() default "";

  String regex() default "";
}