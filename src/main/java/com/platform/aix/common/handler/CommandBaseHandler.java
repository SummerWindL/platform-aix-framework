package com.platform.aix.common.handler;



import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.annotation.*;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.common.util.CopyUtils;
import com.platform.aix.common.validator.AccountValidator;
import com.platform.aix.common.validator.EmailValidator;
import com.platform.aix.config.ApisPorperties;
import com.platform.common.util.security.RSACoder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public abstract class CommandBaseHandler implements CommandHandler{

    private static Map<Class<?>, List<Field>> CLZ_FIELDS = new HashMap<Class<?>, List<Field>>();

//	private static Map<Class<? extends BaseRequest>, List<Field>> CLZ_FIELDS = new HashMap<Class<? extends BaseRequest>, List<Field>>();
    private final Log log = LogFactory.getLog(CommandBaseHandler.class);

    //@Autowired
    //private InterceptorConfig interceptorConfig;
    @Autowired
    private ApisPorperties apisPorperties;

    // 验证请求参数字段合法性，比如期望数字，输入的是汉字等
    public APIResponse checkParamsValid(BaseRequest request) {
        return new APIResponse(ResponseResult.COMMON_SUCCESS);
    }

    @Override
//	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public APIResponse handle(BaseRequest request) {

        // HTTP请求响应
        APIResponse response = null;

        // 1.对request对象的注解进行校验 TODO
        try {
            validateParams(request);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            // 2.若校验接口抛出异常，则表示注解验证未通过
            response = new APIResponse(ResponseResult.HTTP_ERROR_INVALID_PARAM);
            response.setResultmsg(e.getMessage());
            return response;
        }

        // 3.验证请求参数字段合法性
        response = checkParamsValid(request);
        if (!response.equals(ResponseResult.COMMON_SUCCESS)) {
            return response;
        }


        // 4.执行业务逻辑，业务处理流程错误通过抛异常的方式进行处理
        try {
            // 请求参数字段转换
            translateParams(request);
            response = execute(request);
        } catch (BIZException e) {
            log.info(e.getMessage(), e);
            response = new APIResponse(e.getResponseResult());
            response.setData(e.getData());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new APIResponse(ResponseResult.COMMON_ERROR_EXCEPTION);
        }

        return response;
    }

    /*
     * JSON转换成对应实体字段注解
     */
	/*private String validateParams(BaseRequest cmd) {
		String msg = null;
		Class<? extends BaseRequest> clz = cmd.getClass();

		// 先使用类属性注解进行校验，再通过接口进行校验
		List<Field> fields = CLZ_FIELDS.get(clz);
		if (fields == null) {
			fields = new ArrayList<Field>();
			getClassFields(clz, fields);
			CLZ_FIELDS.put(clz, fields);
		}
		// 通过反射进行注解校验
		for (Field field : fields) {
			boolean accessible = field.isAccessible();
			try {
				field.setAccessible(true);
				Object obj = field.get(cmd);
				if (field.isAnnotationPresent(NotNull.class)) {
					if (StringUtils.isEmpty(obj)) {
						NotNull anno = field.getAnnotation(NotNull.class);
						msg = anno.message();
						break;
					}
				}
				if (field.isAnnotationPresent(Pattern.class)) {
					Pattern anno = field.getAnnotation(Pattern.class);
					String regexp = anno.regexp();
					Matcher matcher = java.util.regex.Pattern.compile(regexp).matcher(obj.toString());
					boolean isMatch = matcher.find();
					if (!isMatch) {
						msg = anno.message();
						break;
					}
				}
				if (field.isAnnotationPresent(Max.class)) {
					Max anno = field.getAnnotation(Max.class);
					long maxLength = anno.value();
					if (obj.toString().length() > maxLength) {
						msg = anno.message();
						break;
					}
				}
				if (field.isAnnotationPresent(ValidGenderRange.class)) {
					// 值为空不进行校验
					if (null == obj) {
						continue;
					}
					boolean supportGenderType = ValidGenderRange.EnumGenderType.isSupportGenderType(obj.toString());
					if (!supportGenderType) {
						msg = field.getAnnotation(ValidGenderRange.class).msg();
						break;
					}
				}
				if (field.isAnnotationPresent(ValidBooleanRange.class)) {
					// 值为空不进行校验
					if (null == obj) {
						continue;
					}
					boolean supportBooleanType = ValidBooleanRange.EnumBooleanType.isSupportBooleanType(obj.toString());
					if (!supportBooleanType) {
						msg = field.getAnnotation(ValidBooleanRange.class).msg();
						break;
					}
				}
				if (field.isAnnotationPresent(ValidDisesRange.class)) {
					// 值为空不进行校验
					if (null == obj) {
						continue;
					}
					if (obj instanceof List) {
						@SuppressWarnings("unchecked")
						List<String> lists = (List<String>) obj;
						boolean supportDisesType = ValidDisesRange.EnumDisesType.validDisesTypeLists(lists);
						if (!supportDisesType) {
							msg = field.getAnnotation(ValidDisesRange.class).msg();
							break;
						}
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				msg = e.getMessage();
			} finally {
				field.setAccessible(accessible);
			}
		}

		return msg;
	}
	*/

    /**
     * 对特定的请求参数字段进行转换
     *
     * @param sourceObj
     */
    private void translateParams(Object sourceObj) {
        if (null == sourceObj)
            return;

        List<Field> fields = getValidClassFields(sourceObj.getClass());
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
    private void validateParams(Object sourceObj) {
        //若当前校验的参数为空，则不进行校验
        if (StringUtils.isEmpty(sourceObj)) {
            return;
        }
        String msg = null;
        List<Field> fields = getValidClassFields(sourceObj.getClass());
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
                                        validateParams(listObj);
                                    }
                                } else {
                                    validateParams(obj);
                                }
                            }
                        } else {
                            //其他
                            validateParams(obj);
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
    private void getClassFields(Class clz, List<Field> lst) {
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
    private List<Field> getValidClassFields(Class clz) {
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
    private void getValidClassFields(Class clz, List<Field> lst) {
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
    private Method getSupportTypeMethod(Class clz, Field field) {
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
