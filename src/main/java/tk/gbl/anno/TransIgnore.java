package tk.gbl.anno;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
@Documented
public @interface TransIgnore {
  String value() default "";

  String regex() default "";
}