package gbl.web.filter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.anno.ValidField;
import tk.gbl.constants.ResultType;
import tk.gbl.pojo.request.BaseRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.util.JsonUtil;
import tk.gbl.util.log.LoggerUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Date: 2014/8/11
 * Time: 13:30
 *
 * @author Tian.Dong
 */
@Component
@Aspect
public class ControllerValidFilter {
  /**
   * 异常
   */
  @AfterThrowing(pointcut = "execution(* tk.gbl.web.controller.*.*(..))", throwing = "ex")
  public void controllerHandleException(JoinPoint joinPoint, Exception ex) throws Exception {
    LoggerUtil.error("controller的某个方法抛出异常", ex);
  }

  /**
   * valid
   */
  @Around("execution(* tk.gbl.web.controller.*.*(..))")
  public Object controllerHandleResponse(ProceedingJoinPoint joinPoint) throws Throwable {
    Object target = joinPoint.getTarget();
    String methodName = joinPoint.getSignature().getName();
    Object[] methodArgs = joinPoint.getArgs();
    Method method = getMethod(joinPoint);
    if (!isValid(method)) {
      return joinPoint.proceed();
    }
    Errors errors = null;
    Object validObject = getValidObject(method, methodArgs);
    if (validObject != null) {
        errors = new BeanPropertyBindingResult(validObject, validObject
            .getClass().getSimpleName());
      new BaseRequest().validate(validObject, errors);
    }
    if (errors != null && errors.hasErrors()) {
      BaseResponse response = new BaseResponse(ResultType.VALID);
      response.setMessage(getErrMsg(errors));
      return JsonUtil.toJson(response);
    }
    return joinPoint.proceed();
  }

  /**
   * 获得参数验证错误信息
   *
   * @param errors Errors
   * @return 错误详细信息
   */
  public String getErrMsg(Errors errors) {
    List<ObjectError> errs = errors.getAllErrors();
    StringBuilder errInfo = new StringBuilder(200);
    for (ObjectError err : errs) {
      errInfo.append(err.getDefaultMessage());
    }
    return errInfo.toString();
  }

  /**
   * 判断是否需要对方法进行验证
   *
   * @param method 待验证方法
   * @return 是/否
   */
  private boolean isValid(Method method) {
    ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
    return responseBody != null;
  }

  /**
   * 找到方法参数中的errors对象
   *
   * @param methodArgs 方法传入参数
   * @return
   */
  private Errors getErrors(Object[] methodArgs) {
    for (Object arg : methodArgs) {
      if (arg != null && isOfErrors(arg.getClass())) {
        return (Errors) arg;
      }
    }
    return null;
  }

  private Object getValidObject(Method method, Object[] methodArgs) {
    Annotation[][] annotationList = method.getParameterAnnotations();
    for (int i = 0; i < annotationList.length; i++) {
      if (haveValidAnnotation(annotationList[i])) {
        return methodArgs[i];
      }
    }
    return null;
  }

  private boolean haveValidAnnotation(Annotation[] annotations) {
    for (Annotation annotation : annotations) {
      if (annotation.annotationType().equals(ValidField.class)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断一个类是否实现Errors接口
   */
  private boolean isOfErrors(Class clazz) {
    return Errors.class.isAssignableFrom(clazz);
  }

  /**
   * 找到被织入方法
   *
   * @param joinPoint 插入点
   * @return 被织入方法
   * @throws NoSuchMethodException
   * @throws NoSuchFieldException
   * @throws IllegalAccessException
   */
  private Method getMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException {
    Field methodInvocationField = joinPoint.getClass().getDeclaredField("methodInvocation");
    methodInvocationField.setAccessible(true);
    return ((ProxyMethodInvocation) methodInvocationField.get(joinPoint)).getMethod();
  }
}
