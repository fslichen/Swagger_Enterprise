package evolution;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Ref {
	public static Boolean isString(Class<?> clazz) {
		return clazz == String.class;
	}
	
	public static Boolean isString(Field field) {
		return isString(field.getType());
	}
	
	public static Boolean isString(Object object) {
		return isString(object.getClass());
	}
	
	public static String simpleClassName(Class<?> clazz) {
		return clazz.getSimpleName();
	}
	
	public static String simpleClassName(Object object) {
		return simpleClassName(object.getClass());
	}
	
	public static String simpleClassName(Field field) {
		return simpleClassName(field.getType());
	}
	
	public static Boolean isMap(Class<?> clazz) {
		return clazz == Map.class || clazz == LinkedHashMap.class || clazz == HashMap.class || clazz == TreeMap.class;
	}
	
	public static Boolean isMap(Object object) {
		return isMap(object.getClass());
	}
	
	public static Boolean isMap(Field field) {
		return isMap(field.getType());
	}
	
	// Get the annotation of a class, method or field object.
	@SuppressWarnings("unchecked")
	public static <T> T annotation(Object object, Class<T> annotationClass) {
		try {
			T annotation = (T) object.getClass().getDeclaredMethod("getAnnotation", Class.class).invoke(object, annotationClass);
			return annotation;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object defaultBasicObject(Class<?> clazz) {
		if (clazz == boolean.class || clazz == Boolean.class) {
			return false;
		} else if (clazz == byte.class || clazz == Byte.class) {
			return new Byte(0 + "");
		} else if (clazz == short.class || clazz == Short.class) {
			return new Short(0 + "");
		} else if (clazz == char.class) {
			return ' ';
		} else if (clazz == int.class || clazz == Integer.class) {
			return 0;
		} else if (clazz == long.class || clazz == Long.class) {
			return 0l;
		} else if (clazz == float.class || clazz == Float.class) {
			return 0f;
		} else if (clazz == double.class || clazz == Double.class) {
			return 0d;
		} else if (clazz == String.class) {
			return "anyString";
		} else if (clazz == Date.class) {
			return new Date();
		}
		return null;
	}

	public static Object defaultBasicObject(Field field) {
		return defaultBasicObject(field.getType());
	}

	public static Object defaultBasicObject(Object object) {
		return defaultBasicObject(object.getClass());
	}
	
	public static Object defaultObject(Field field) {
		return defaultObject(field.getType());
	}

	public static Object defaultObject(Object object) {
		return defaultObject(object.getClass());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object defaultObject(Class<?> clazz) {
		try {
			if (isBasic(clazz)) {
				return defaultBasicObject(clazz);
			} else if (clazz == List.class) {// Unable to get the generic type due to type erasure.
				return new LinkedList();
			} else if (clazz == Map.class) {
				return new LinkedHashMap<>();
			} else {// POJO
				Object object = clazz.newInstance();
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					if (isBasic(field)) {
						field.set(object, defaultBasicObject(field));
					} else if (isList(field)) {
						List list = new LinkedList();
						list.add(defaultObject(genericClass(field, 0)));
						field.set(object, list);
					} else if (isMap(field)) {
						Map map = new LinkedHashMap();
						List<Class<?>> genericClasses = genericClasses(field);
						map.put(defaultObject(genericClasses.get(0)), defaultObject(genericClasses.get(1)));
						field.set(object, map);
					} else {
						field.set(object, defaultObject(field));
					}
				}
				return object;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class<?> genericClass(Method method, Class<?> annotationClass, int k) {
		Annotation[][] as = method.getParameterAnnotations();
		int m = as.length;
		for (int i = 0; i < m; i++) {
			int n = as[i].length;
			for (int j = 0; j < n; j++) {
				if (as[i][0].annotationType() == annotationClass) {
					return genericClass(method, i, k);
				}
			}
		}
		return null;
	}
	
	// Get the generic class i of the return type.
	public static Class<?> genericClass(Method method, int i) {
		return (Class<?>) actualTypeArguments(method, -1, true)[i];
	}
	
	// Get the generic class j of the parameter type i.
	public static Class<?> genericClass(Method method, int i, int j) {
		return (Class<?>) actualTypeArguments(method, i, false)[j];
	}
	
	public static Type[] actualTypeArguments(Method method, int i, boolean isReturnType) {
		ParameterizedType parameterizedType;
		if (isReturnType) {
			parameterizedType = (ParameterizedType) method.getAnnotatedReturnType().getType();
		} else {
			parameterizedType = (ParameterizedType) method.getAnnotatedParameterTypes()[i].getType();
		}
		return parameterizedType.getActualTypeArguments();
	}
	
	public static Type[] actualTypeArguments(Field field) {
		ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
		return parameterizedType.getActualTypeArguments();
	}
	
	public static List<Class<?>> genericClasses(Type[] actualTypeArguments) {
		List<Class<?>> genericClasses = new LinkedList<>();
		for (Type type : actualTypeArguments) {
			genericClasses.add((Class<?>) type);
		}
		return genericClasses;
	}
	
	public static List<Class<?>> genericClasses(Field field) {
		return genericClasses(actualTypeArguments(field));
	}
	
	public static Class<?> genericClass(Field field, int i) {
		return (Class<?>) actualTypeArguments(field)[i];
	}

	public static Boolean isBasic(Class<?> clazz) {
		if (clazz == boolean.class || clazz == Boolean.class
				|| clazz == byte.class || clazz == Byte.class
				|| clazz == short.class || clazz == Short.class
				|| clazz == int.class || clazz == Integer.class
				|| clazz == long.class || clazz == Long.class
				|| clazz == float.class || clazz == Float.class
				|| clazz == double.class || clazz == Double.class
				|| clazz == String.class || clazz == Date.class) {
			return true;
		}
		return false;
	}

	public static Boolean isBasic(Field field) {
		return isBasic(field.getType());
	}

	public static Boolean isBasic(Object object) {
		return isBasic(object.getClass());
	}

	public static Boolean isList(Class<?> clazz) {
		return clazz == List.class || clazz == ArrayList.class || clazz == LinkedList.class;
	}

	public static Boolean isList(Field field) {
		return isList(field.getType());
	}
	
	public static Boolean isList(Object object) {
		return isList(object.getClass());
	}
}
