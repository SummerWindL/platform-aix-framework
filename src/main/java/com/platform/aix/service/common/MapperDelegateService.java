///*
// * 文件名：BussCommonMapperDelegateService.java
// * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
// * 描述：理财资管管理系统V2.0
// * 修改人：杨智
// * 修改时间：2018年10月16日
// * 修改内容：新建
// * 系统名称：理财资管管理系统V2.0
// */
//package com.platform.aix.service.common;
//
//import com.platform.aix.service.common.dao.MapperDelegateMapper;
//import com.platform.common.util.BeanUtil;
//import com.platform.common.util.ReflectUtil;
//import com.platform.common.util.StringUtil;
//import com.platform.core.base.BaseEntity;
//import com.platform.core.util.SpringContextUtil;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.reflect.MethodUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.Map.Entry;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.stream.Collectors;
//
///**
// * 共通Mapper的通用代理方案
// */
//@Service
//public class MapperDelegateService {
//
//	private static final Logger LOG = LoggerFactory.getLogger(MapperDelegateService.class);
//
//	private static final String METHOD_INSERT = "insert";
//
//	private static final String METHOD_INSERT_SELECTIVE = "insertSelective";
//
//	private static final String METHOD_DELETE_BY_PK = "deleteByPrimaryKey";
//
//	private ConcurrentHashMap<Class<?>, Class<?>> entity2MapDict = new ConcurrentHashMap<>();
//
//    @Autowired
//    private MapperDelegateMapper mapperDelegateMapper;
//
//	public Class<?> getMapperClass(Class<?> entityClass) {
//		if (!entity2MapDict.containsKey(entityClass)) {
//			String mapperName = StringUtil.replaceOnce(entityClass.getName(), "com.joyintech.tams.common.base.entity.",
//					"com.joyintech.tams.common.base.dao.") + "Mapper";
//			try {
//				Class<?> mapperClass = Class.forName(mapperName);
//				entity2MapDict.put(entityClass, mapperClass);
//			} catch (ClassNotFoundException e) {
//				entity2MapDict.put(entityClass, null);
//			}
//		}
//		return entity2MapDict.get(entityClass);
//	}
//
//	public int insert(Class<?> mapper, BaseEntity entity) {
//		return modify(mapper, METHOD_INSERT, entity);
//	}
//
//	public int insertSelective(Class<?> mapper, BaseEntity entity) {
//		return modify(mapper, METHOD_INSERT_SELECTIVE, entity);
//	}
//
//	public int batchInsertSelective(Class<?> mapper, List<? extends BaseEntity> listEntity) {
//		int result = 0;
//    	for(BaseEntity param : listEntity) {
//			result += modify(mapper, METHOD_INSERT_SELECTIVE, param);
//    	}
//    	return result;
//	}
//
//	/**
//	 * 批量插入
//	 *
//	 * @param vargs
//	 *            [entity1, entity2, [entity3, entity4], entity5...]
//	 * @return [ret1,ret2,ret3,ret4,ret5...]
//	 * @throws ReflectiveOperationException
//	 */
//	public List<Integer> insertSelectiveAutoClassifyBatch(Object... vargs) {
//		Map<String, Integer> map = batchUpdate(METHOD_INSERT_SELECTIVE, wrapAutoClassifyArgs(vargs));
//		return wrapReturn(map);
//	}
//
//	public int insertBasicByBasicInfo(Class<?> mapper, BaseEntity entity) {
//		return modify(mapper, "insertBasicByBasicInfo", entity);
//	}
//
//	public int updateByPrimaryKeySelective(Class<?> mapper, BaseEntity entity) {
//		return modify(mapper, "updateByPrimaryKeySelective", entity);
//	}
//
//	public int updateByPrimaryKey(Class<?> mapper, BaseEntity entity) {
//		return modify(mapper, "updateByPrimaryKey", entity);
//	}
//
//	public int deleteByPrimaryKey(Class<?> mapper, BaseEntity entity) {
//		return modify(mapper, METHOD_DELETE_BY_PK, entity);
//	}
//
//	public int deleteByPrimaryKey(Class<?> mapper, String key) {
//		return modify(mapper, METHOD_DELETE_BY_PK, key);
//	}
//
//	/**
//	 * 批量插入
//	 *
//	 * @param vargs
//	 *            [entity1, entity2, [entity3, entity4], entity5...]
//	 * @return [ret1,ret2,ret3,ret4,ret5...]
//	 * @throws ReflectiveOperationException
//	 */
//	public List<Integer> insertAutoClassifyBatch(Object... vargs) {
//		Map<String, Integer> map = batchUpdate(METHOD_INSERT, wrapAutoClassifyArgs(vargs));
//		return wrapReturn(map);
//	}
//
//	/**
//	 * 批量插入
//	 *
//	 * @param vargs
//	 *            [mapper1, entity1, mapper2, entity2...]
//	 * @return [ret1,ret2...]
//	 * @throws ReflectiveOperationException
//	 */
//	public List<Integer> insertBatch(Object... vargs) {
//		return wrapReturn(batchUpdate(METHOD_INSERT, wrapArgs(vargs)));
//	}
//
//	/**
//	 * 批量插入
//	 *
//	 * @param vargs
//	 *            [mapper1, entity1, mapper2, entity2...]
//	 * @return [ret1,ret2...]
//	 * @throws ReflectiveOperationException
//	 */
//	public List<Integer> insertSelectiveBatch(Object... vargs) {
//		return wrapReturn(batchUpdate(METHOD_INSERT_SELECTIVE, wrapArgs(vargs)));
//	}
//
//	/**
//	 * 批量插入
//	 *
//	 * @param vargs
//	 *            [mapper1, entity1, mapper2, entity2...]
//	 * @return [ret1,ret2...]
//	 * @throws ReflectiveOperationException
//	 */
//	public List<Integer> insertBasicByBasicInfoBatch(Object... vargs) {
//		return wrapReturn(batchUpdate("insertBasicByBasicInfo", wrapArgs(vargs)));
//	}
//
//	/**
//	 * 批量更新
//	 *
//	 * @param vargs
//	 *            [mapper1, entity1, mapper2, entity2...]
//	 * @return [ret1,ret2...]
//	 * @throws ReflectiveOperationException
//	 */
//	public List<Integer> updateByPrimaryKeySelectiveBatch(Object... vargs) {
//		return wrapReturn(batchUpdate("updateByPrimaryKeySelective", wrapArgs(vargs)));
//	}
//
//	/**
//	 * 批量更新
//	 *
//	 * @param vargs
//	 *            [mapper1, entity1, mapper2, entity2...]
//	 * @return [ret1,ret2...]
//	 * @throws ReflectiveOperationException
//	 */
//	public List<Integer> updateByPrimaryKeyBatch(Object... vargs) {
//		return wrapReturn(batchUpdate("updateByPrimaryKey", wrapArgs(vargs)));
//	}
//
//	/**
//	 * 批量删除
//	 *
//	 * @param vargs
//	 *            [mapper1, entity1, mapper2, entity2...]
//	 * @return [ret1,ret2...]
//	 * @throws ReflectiveOperationException
//	 */
//	public List<Integer> deleteByPrimaryKeyBatch(Object... vargs) {
//		return wrapReturn(batchUpdate(METHOD_DELETE_BY_PK, wrapArgs(vargs)));
//	}
//
//	private List<Integer> wrapReturn(Map<String, Integer> ret) {
//		return ret.entrySet().stream()
//				.sorted((Comparator<Entry<String, Integer>>) (o1, o2) -> new BigDecimal(o1.getKey())
//						.compareTo(new BigDecimal(o1.getKey())))
//				.map(Entry::getValue).collect(Collectors.toList());
//	}
//
//	private Class<?> getBaseEntityClass(Object obj) {
//		for (Class<?> clz = obj.getClass(); clz != Object.class; clz = clz.getSuperclass()) {
//			if (clz.getName().startsWith("com.joyintech.tams.common.base.entity.")) {
//				return clz;
//			}
//		}
//		return Object.class;
//	}
//
//	private Map<String, Object> wrapArgs(Class<?> mapperClass, Object entity) {
//		Map<String, Object> target = new HashMap<>();
//		target.put("mapperClassName", mapperClass.getName());
//		target.put("entityClassName", getBaseEntityClass(entity));
//		target.put("entity", entity);
//		return target;
//	}
//
//	@SuppressWarnings("rawtypes")
//	private Map<String, Map<String, Object>> wrapAutoClassifyArgs(Object... vargs) {
//		Map<String, Map<String, Object>> param = new HashMap<>();
//		for (int i = 0; i < vargs.length; i++) {
//			if (vargs[i] instanceof List) {
//				for (int j = 0; j < ((List) vargs[i]).size(); j++) {
//					Object entity = ((List) vargs[i]).get(j);
//					Class<?> entityClass = getBaseEntityClass(entity);
//					Class<?> mapperClass = getMapperClass(entityClass);
//					if (mapperClass != null) {
//						Map<String, Object> target = wrapArgs(mapperClass, entity);
//						param.put(String.format("%d.%08d", i, j), target);
//					}
//				}
//			} else {
//				Class<?> entityClass = getBaseEntityClass(vargs[i]);
//				Class<?> mapperClass = getMapperClass(entityClass);
//				if (mapperClass != null) {
//					Map<String, Object> target = wrapArgs(mapperClass, vargs[i]);
//					param.put(String.valueOf(i), target);
//				}
//			}
//		}
//		return param;
//	}
//
//	private Map<String, Map<String, Object>> wrapArgs(Object... vargs) {
//		Map<String, Map<String, Object>> param = new HashMap<>();
//		for (int i = 0; i < vargs.length / 2; i++) {
//			Map<String, Object> target = wrapArgs(((Class<?>) vargs[i * 2]), vargs[i * 2 + 1]);
//			param.put(String.valueOf(i), target);
//		}
//		return param;
//	}
//
//	/**
//	 * 按照指定键删除数据
//	 *
//	 * @param tableName
//	 *            表名
//	 * @param conditions
//	 *            [column1, value1, column2, value2...]
//	 * @return
//	 */
//	public int deleteBySpecificKey(String tableName, String... conditions) {
//		Map<String, Object> conditionMap = new HashMap<>();
//		for (int i = 0; i < conditions.length / 2; i++) {
//			conditionMap.put(conditions[i * 2], conditions[i * 2 + 1]);
//		}
//        if (conditionMap.isEmpty()) {
//            return -1;
//        }
//        Map<String, Object> param = new HashMap<>();
//        param.put("tableName", tableName);
//        param.put("conditions", conditionMap);
//        return mapperDelegateMapper.deleteBySpecificKey(param);
//	}
//
//	/**
//	 * 按照指定键删除数据
//	 *
//	 * @param tableNames 表名
//	 *            表名
//	 * @param conditions
//	 *            [column1, value1, column2, value2...]
//	 * @return
//	 */
//	public int batchDeleteBySpecificKey(List<String> tableNames, List<Map<String, Object>> conditions) {
//        if (CollectionUtils.isEmpty(conditions)) {
//            return -1;
//        }
//        int result = 0;
//        if(null != tableNames) {
//        	if(tableNames.size() != conditions.size()) {
//        		LOG.error("参数不匹配");
//        	}
//        	else {
//        		for(int i=0;i<tableNames.size();i++) {
//        			HashMap<String, Object> param = new HashMap<>();
//        			param.put("tableName", tableNames.get(i));
//        			param.put("conditions", conditions.get(i));
//        			result += mapperDelegateMapper.deleteBySpecificKey(param);
//        		}
//        	}
//        }
//        return result;
//	}
//
//	public int modify(Class<?> mapper, String methodName, Object param) {
//		try {
//			Object result = MethodUtils.invokeMethod(SpringContextUtil.getBean(mapper), methodName, param);
//			if(result instanceof Integer) {
//				return (int)result;
//			}
//		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//			LOG.error(mapper.getName() + "." + methodName + "不存在或参数不匹配", e);
//			throw new RuntimeException(e);
//		}
//		return 0;
//	}
//
//	@SuppressWarnings("unchecked")
//	private Map<String, Integer> batchUpdate(String methodName, Map<String, Map<String, Object>> param) {
//        Map<String, Integer> retMap = new HashMap<>();
//        for (Entry<String, Map<String, Object>> entry : param.entrySet()) {
//        	try {
//	            Map<String, Object> methodParam = entry.getValue();
//	            Class<?> mapper = null;
//	            Class<?> type = null;
//	            Object mapperClassName = methodParam.get("mapperClassName");
//	            Object entityClassName = methodParam.get("entityClassName");
//	            Object object = methodParam.get("entity");
//	            if(mapperClassName instanceof String) {
//	            	mapper = Class.forName((String)mapperClassName);
//	            }
//	            else if(mapperClassName instanceof Class) {
//	            	mapper = (Class<?>)mapperClassName;
//	            }
//	            if(entityClassName instanceof String) {
//	            	type = Class.forName((String)entityClassName);
//	            }
//	            else if(entityClassName instanceof Class) {
//	            	type = (Class<?>)entityClassName;
//	            }
//	            if(object instanceof Map) {
//	            	object = BeanUtil.mapToObject((Map<String, Object>)object, type);
//	            }
//                Object bean = SpringContextUtil.getBean(mapper);
//    			retMap.put(entry.getKey(), (Integer) MethodUtils.invokeMethod(bean, methodName, object));
//	        }
//	        catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
//	        	retMap.put(entry.getKey(), -1);
//	        	LOG.error("操作数据库失败", e);
//	        	throw new RuntimeException(e);
//	        }
//        }
//        return retMap;
//	}
//
//	/**
//	 * 仅解析dto第一层所有的属性
//	 * @param obj 带解析数据
//	 * @return 解析结果
//	 */
//	public Object[] analysisEntityForObjectArr(Object obj){
//		 List<Object> objList = new ArrayList();
//		try {
//			Field[] fields = ReflectUtil.getDeclaredFields(obj);
//			if (fields.length > 0) {
//				for (Field field : fields) {
//					field.setAccessible(true);
//					Object value = field.get(obj);
//					if (value != null) {
//						objList.add(value);
//					}
//				}
//			}
//		}
//		catch (IllegalAccessException e){
//			LOG.error("数据实例解析失败", e);
//			throw new RuntimeException(e);
//		}
//		if (objList.size() > 0) {
//			return objList.toArray(new Object[objList.size()]);
//		} else {
//			return null;
//		}
//
//	}
//}
