package com.platform.aix.common.handler.base;

import com.platform.aix.common.annotation.*;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.common.util.CopyUtils;
import com.platform.aix.common.util.RSACoder;
import com.platform.aix.common.validator.AccountValidator;
import com.platform.aix.common.validator.EmailValidator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-24 10:56
 **/

public class IUtil {
    private static final Log log = LogFactory.getLog(IUtil.class);
    /**
     * 对特定的请求参数字段进行转换
     *
     * @param sourceObj
     */
    public static void translateParams(Map<Class<?>, List<Field>> CLZ_FIELDS, Object sourceObj) {
        if (null == sourceObj) {
            return;
        }
        List<Field> fields = getValidClassFields(CLZ_FIELDS,sourceObj.getClass());
        boolean accessible = false;
        for (Field field : fields) {
            accessible = field.isAccessible();
            field.setAccessible(true);
            Object obj = null;
            try {
                obj = field.get(sourceObj);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            }
            try {
                if (field.getType().equals(String.class)) {
                    String objStr = null;
                    if (obj != null) {
                        objStr = obj.toString();
                    }

                    String fieldName = field.getName();
                    String newVal = null;
                    if (fieldName.equals("page")) {
                        if ((null == obj) || !StringUtils.hasText(objStr)) {
                            newVal = "0";
                        }

                    } else if (fieldName.equals("rows")) {
                        if ((null == obj) || !StringUtils.hasText(objStr)) {
                            newVal = String.valueOf(1000);
                        }

                    }

                    if (StringUtils.hasText(newVal)) {
                        try {
                            field.set(sourceObj, newVal);
                        } catch (Exception ex) {
                            log.error(ex.getMessage(), ex);
                        }
                    }
                }

            } finally {
                field.setAccessible(accessible);
            }
        }
    }

    /**
     * 利用注解方式进行校验，包括组合在Request中的对象
     */
    @SuppressWarnings("rawtypes")
    public static void validateParams(Map<Class<?>, List<Field>> CLZ_FIELDS,Object sourceObj) {
        //若当前校验的参数为空，则不进行校验
        if (StringUtils.isEmpty(sourceObj)) {
            return;
        }
        String msg = null;
        List<Field> fields = getValidClassFields(CLZ_FIELDS,sourceObj.getClass());
        // 通过反射进行注解校验
        boolean accessible = false;
        for (Field field : fields) {
            field.setAccessible(true);
            Object obj = null;
            try {
                obj = field.get(sourceObj);
            } catch (Exception e) {
//				log.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            }
            try {
                if (!CopyUtils.types.contains(field.getType().getName())) {
                    //当需要校验的参数下还存在对象时，进行递归校验
                    //validateParams(obj);
                    if (field.isAnnotationPresent(Valid.class)) {//复杂对象需要校验，加上Valid注解
                        //List
                        if (obj instanceof List) {
                            //判断泛型是否是基本类型
                            Type type = field.getGenericType();
                            Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
                            if (typeArguments.length == 1) { //List 只允许一个泛型
                                Type argType = typeArguments[0];
                                if (!CopyUtils.types.contains(argType.getTypeName())) {
                                    List list = (List) obj;
                                    for (int i = 0; i < list.size(); i++) {
                                        Object listObj = list.get(i);
                                        validateParams(CLZ_FIELDS,listObj);
                                    }
                                } else {
                                    validateParams(CLZ_FIELDS,obj);
                                }
                            }
                        } else {
                            //其他
                            validateParams(CLZ_FIELDS,obj);
                        }
                    }
                }
                accessible = field.isAccessible();
                if (field.isAnnotationPresent(NotNull.class)) {
                    if (null == obj || !StringUtils.hasText(obj.toString())) {
                        NotNull anno = field.getAnnotation(NotNull.class);
                        msg = anno.message();
                        break;
                    }
                }
                //除NotNull注解外，字段为空时校验通过
                if (null == obj) {
                    continue;
                }
                String objStr = obj.toString();
                //解密需要解密的参数
                if (field.isAnnotationPresent(Decryption.class)) {
                    try {
                        objStr = RSACoder.decryptByPrivateKey(objStr);
                        field.set(sourceObj, objStr);
                    } catch (Exception e) {
                        throw new RuntimeException(ResponseResult.BIZ_ERROR_RSA_DECODE.getResultmsg());
                    }
                }
                if (field.isAnnotationPresent(Max.class)) {
                    Max anno = field.getAnnotation(Max.class);
                    long maxLength = anno.value();
                    if (objStr.length() > maxLength) {
                        msg = anno.message();
                        break;
                    }
                }
                if (field.isAnnotationPresent(Min.class)) {
                    Min anno = field.getAnnotation(Min.class);
                    long minLength = anno.value();
                    if (objStr.length() < minLength) {
                        msg = anno.message();
                        break;
                    }
                }

                // Email
                if (field.isAnnotationPresent(Email.class)) {
                    Email anno = field.getAnnotation(Email.class);
                    boolean isValid = EmailValidator.isValid(objStr);
                    if (!isValid) {
                        msg = anno.msg();
                        break;
                    }
                }
                // accout
                if (field.isAnnotationPresent(Account.class)) {
                    Account anno = field.getAnnotation(Account.class);
                    boolean isValid = AccountValidator.isValid(objStr);
                    if (!isValid) {
                        msg = anno.msg();
                        break;
                    }
                }

                if (field.isAnnotationPresent(Pattern.class)) {
                    Pattern anno = field.getAnnotation(Pattern.class);
                    String regexp = anno.regexp();
                    Matcher matcher = java.util.regex.Pattern.compile(regexp).matcher(objStr);
                    boolean isMatch = matcher.find();
                    if (!isMatch) {
                        msg = anno.message();
                        break;
                    }
                }

                if (field.isAnnotationPresent(ValidType.class)) {
                    ValidType anno = field.getAnnotation(ValidType.class);
                    Class clz = anno.value();
                    if (clz != null) {
                        try {
                            Method method = getSupportTypeMethod(clz, field);
                            if (method != null) {
                                method.setAccessible(true);
                                boolean ret = (boolean) method.invoke(null, obj);
                                if (!ret) {
                                    msg = anno.msg();
                                    break;
                                }
                            } else {
                                throw new RuntimeException("没找到对应的isSupportType方法");
                            }
                        } catch (Exception ex) {
                            throw new RuntimeException(ex.getMessage());
                        }

                    } else {
                        throw new RuntimeException("没找到注解对应的类");
                    }

                }

            } finally {
                field.setAccessible(accessible);
            }
        }
        //当msg有值时表示校验未通过，抛RuntimeException结束当前进程，在调用该方法的地方进行捕获
        if (!StringUtils.isEmpty(msg)) {
            log.info(msg);
            throw new RuntimeException(msg);
        }
    }

    /**
     * 取得类的所有属性，包括父类中的属性
     *
     * @param clz
     * @param lst
     */
    @SuppressWarnings({"rawtypes", "unused"})
    public static void getClassFields(Class clz, List<Field> lst) {
        Field[] fields = clz.getDeclaredFields();
        for (Field f : fields) {
            lst.add(f);
        }
        clz = clz.getSuperclass();
        if (clz != null) {
            getClassFields(clz, lst);
        }
    }

    /**
     * 获取需要校验的字段集合
     *
     * @param
     * @return List<Field>
     */
    @SuppressWarnings("rawtypes")
    public static List<Field> getValidClassFields(Map<Class<?>, List<Field>> CLZ_FIELDS,Class clz) {
        List<Field> fields = CLZ_FIELDS.get(clz);
        if (CollectionUtils.isEmpty(fields)) {
            fields = new ArrayList<Field>();
            getValidClassFields(clz, fields);
            // CLZ_FIELDS.put(clz, fields);
        }
        return fields;
    }

    //
    @SuppressWarnings("rawtypes")
    public static void getValidClassFields(Class clz, List<Field> lst) {
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            if (!CopyUtils.types.contains(field.getType().getTypeName())) {
                //不是基本类型，需要对象声明Valid注解才校验
                //但是 NotNull 除外
                if (field.isAnnotationPresent(Valid.class)
                        || field.isAnnotationPresent(NotNull.class)) {
                    lst.add(field);
                }
            } else {
                lst.add(field);
            }
        }
        clz = clz.getSuperclass();
        if (clz != null) {
            getValidClassFields(clz, lst);
        }
    }

    /**
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Method getSupportTypeMethod(Class clz, Field field) {
        final String IsSupportTypeMethodName = "isSupportType";
        final String IsSupportTypeListMethodName = "isSupportTypeList";
        Method method = null;
        Class<?> fieldTypeClz = field.getType();
        if (clz != null) {
            String methodName = IsSupportTypeMethodName;

            // TODO : 是否需要支持List的派生类？
            if (fieldTypeClz.equals(List.class)) {
                methodName = IsSupportTypeListMethodName;
            }

            try {
                method = clz.getMethod(methodName, fieldTypeClz);
            } catch (NoSuchMethodException ex) {

            }

            if (method == null) {
                Method[] methods = clz.getDeclaredMethods();
                for (Method m : methods) {
                    if (m.isAnnotationPresent(IsSupportType.class)) {
                        Class<?> clzs[] = m.getParameterTypes();
                        if (clzs.length == 1) {
                            if (clzs[0].equals(fieldTypeClz)) {
                                method = m;
                                break;
                            }
                        } else {
                            throw new RuntimeException("isSupportType参数大于一个");
                        }
                    }
                }
            }
        }
        return method;
    }
}
