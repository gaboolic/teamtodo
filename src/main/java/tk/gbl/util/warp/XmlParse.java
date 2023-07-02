package tk.gbl.util.warp;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import tk.gbl.anno.ValidList;
import tk.gbl.util.log.LoggerUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2014/12/3
 * Time: 18:32
 *
 * @author Tian.Dong
 */
public class XmlParse {
  public static <T> T parse(String xml, Class<T> target) throws Exception {
    Object obj = target.newInstance();

    Document doc = null;
    try {
      doc = DocumentHelper.parseText(xml);
    } catch (Exception e) {
      LoggerUtil.error("解析xml文档失败" + xml, e);
      return null;
    }
    Element root = doc.getRootElement();

    Element bodyElement = root;
    for (Object element : bodyElement.elements()) {
      Element item = (Element) element;
      Field field = obj.getClass().getDeclaredField(item.getName());
      field.setAccessible(true);
      setField(obj, field, item);
    }
    return (T) obj;
  }

  /* 用xml的一个节点给obj的某个field赋值*/
  private static void setField(Object obj, Field rootField, Element root) throws Exception {
    if (rootField.getType().equals(String.class)) {
            /* 基本类型 */
      rootField.set(obj, root.getTextTrim());
    } else {
            /* 复杂对象*/
      Class fieldClass = rootField.getType();
      Object fieldObject = null;
            /* 如果field的类是List类型*/
      if (fieldClass.equals(CustomArrayList.class) ||
          fieldClass.isAssignableFrom(List.class) ||
          fieldClass.isAssignableFrom(ArrayList.class)) {
        fieldObject = new CustomArrayList();
        for (Object attr : root.attributes()) {
          Attribute attribute = (Attribute) attr;
          ((CustomArrayList) fieldObject).addAttribute(attribute.getName(), attribute.getText());
        }
        for (Object element : root.elements()) {
          Element item = (Element) element;
          if (item.isTextOnly()) {
            ((CustomArrayList) fieldObject).add(item.getTextTrim());

          } else {
            //Object itemObj = Class.forName(getClassName(item.getName())).newInstance();
            Object itemObj = null;
            Type type = rootField.getGenericType();
            if (type instanceof ParameterizedType) {
              ParameterizedType paramType = (ParameterizedType) type;
              Type[] actualTypes = paramType.getActualTypeArguments();
              itemObj = ((Class) actualTypes[0]).newInstance();
            }

            ((CustomArrayList) fieldObject).add(itemObj);

            setDefaultField(itemObj, item);
          }
        }
      } else {
                /* 如果不是List而是普通对象，则new一个实例，递归赋值*/
        fieldObject = fieldClass.newInstance();
        setDefaultField(fieldObject, root);
      }
            /* 将创建好的fieldObject 赋值给rootField*/
      rootField.set(obj, fieldObject);
    }
  }

  /*用一个节点赋值给Field */
  private static void setDefaultField(Object fieldObject, Element root) throws Exception {

    for (Object attr : root.attributes()) {
      Attribute item = (Attribute) attr;
      try {
        Field field = fieldObject.getClass().getDeclaredField(item.getName());
        field.setAccessible(true);
        field.set(fieldObject, item.getText());
      }catch (Exception e){
        e.printStackTrace();
      }
    }
    if (root.isTextOnly() && root.getText()!=null && root.getText().length()>0) {
      Field field = fieldObject.getClass().getDeclaredField("data");
      field.setAccessible(true);
      field.set(fieldObject, root.getTextTrim());
      return;
    }
    for (Field field : fieldObject.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      if (field.getAnnotation(ValidList.class) != null) {
        ValidList validList = field.getAnnotation(ValidList.class);
        String name = validList.value();
        List list = root.elements(name);
        setListField(fieldObject, field, list);
      }
      if (root.element(field.getName()) != null) {
        setField(fieldObject, field, root.element(field.getName()));
      } else if (root.attribute(field.getName()) != null) {
        setField(fieldObject, field, root.attribute(field.getName()));

      }

    }
//    for (Object element : root.elements()) {
//      Element item = (Element) element;
//      try {
//        Field field = fieldObject.getClass().getDeclaredField(item.getName());
//        field.setAccessible(true);
//        setField(fieldObject, field, item);
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
//    }
  }

  private static void setListField(Object fieldObject, Field rootField, List elementList) throws Exception {
    CustomArrayList list = new CustomArrayList();
    for (Object element : elementList) {
      Element item = (Element) element;
      //Object itemObj = Class.forName(getClassName(item.getName())).newInstance();
      Object itemObj = null;
      Type type = rootField.getGenericType();
      if (type instanceof ParameterizedType) {
        ParameterizedType paramType = (ParameterizedType) type;
        Type[] actualTypes = paramType.getActualTypeArguments();
        itemObj = ((Class) actualTypes[0]).newInstance();
      }
      list.add(itemObj);
      setDefaultField(itemObj, item);
    }
    rootField.set(fieldObject,list);
  }

  private static void setField(Object obj, Field rootField, Attribute attribute) throws Exception {
    rootField.set(obj, attribute.getValue());
  }
}
