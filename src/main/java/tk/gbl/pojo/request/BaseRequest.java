package tk.gbl.pojo.request;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import tk.gbl.anno.ValidField;
import tk.gbl.util.log.LoggerUtil;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Date: 2014/8/6
 * Time: 14:22
 *
 * @author Tian.Dong
 */
public class BaseRequest implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    return this.getClass().equals(clazz);
  }

  @Override
  public void validate(Object object, Errors errors) {
    try {
      for (Field field : object.getClass().getDeclaredFields()) {
        validateNotNull(object, field, errors);
      }
    } catch (Exception e) {
      LoggerUtil.error("validate", e);
    }
  }

  private void validateNotNull(Object object, Field field, Errors errors) throws IllegalAccessException {
    field.setAccessible(true);
    ValidField anno = field.getAnnotation(ValidField.class);
    if (anno != null) {
      Object fieldObject = field.get(object);
      if (fieldObject == null) {
        ValidationUtils.rejectIfEmpty(errors, field.getName(), null, field.getName()
            + "不能为空.");
        return;
      }
      if (anno.regex() != null && anno.regex().length() > 0) {
        String val = fieldObject.toString();
        if(!val.matches(anno.regex())) {
          errors.rejectValue(field.getName(), null, field.getName()
              + "不符合规范.");
          return;
        }
      }
      if (fieldObject.getClass().equals(Integer.class)
          || fieldObject.getClass().equals(Double.class)
          || fieldObject.getClass().equals(String.class)
          || fieldObject.getClass().equals(Date.class)) {
        //简单类型

      } else {
        validate(fieldObject, errors);
      }

    }
  }
}
