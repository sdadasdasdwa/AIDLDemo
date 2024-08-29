package com.java.aidl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {

    /**
     * 根据类名，参数实例化对象
     *
     * @param className 类的路径全名
     * @param params    构造函数需要的参数
     * @return 返回 T 类型的一个对象
     */
    public static Object getInstance(String className, Object... params) {
        if (className == null || className.isEmpty()) {
            throw new IllegalArgumentException("className 不能为空");
        }
        try {
            Class<?> clazz = Class.forName(className);
            if (params != null && params.length > 0) {
                Class<?>[] paramTypes = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    paramTypes[i] = params[i].getClass();
                }
                Constructor<?> constructor = clazz.getDeclaredConstructor(paramTypes);
                constructor.setAccessible(true);
                return constructor.newInstance(params);
            } else {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行 instance 的方法
     *
     * @param className  类的全名
     * @param instance   对应的对象，为 null 时执行类的静态方法
     * @param methodName 方法名称
     * @param params     参数
     * @return 方法执行结果
     */
    public static Object invoke(String className, Object instance, String methodName, Object... params) {
        if (className == null || className.isEmpty()) {
            throw new IllegalArgumentException("className 不能为空");
        }
        if (methodName == null || methodName.isEmpty()) {
            throw new IllegalArgumentException("methodName 不能为空");
        }
        try {
            Class<?> clazz = Class.forName(className);
            if (params != null && params.length > 0) {
                Class<?>[] paramTypes = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    paramTypes[i] = params[i].getClass();
                }
                Method method = clazz.getDeclaredMethod(methodName, paramTypes);
                method.setAccessible(true);
                return method.invoke(instance, params);
            } else {
                Method method = clazz.getDeclaredMethod(methodName);
                method.setAccessible(true);
                return method.invoke(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行指定的方法
     *
     * @param instance 需要执行该方法的对象，为空时，执行静态方法
     * @param method   需要执行的方法对象
     * @param params   方法对应的参数
     * @return 方法执行结果
     */
    public static Object invokeMethod(Object instance, Method method, Object... params) {
        if (method == null) {
            throw new IllegalArgumentException("method 不能为空");
        }
        method.setAccessible(true);
        try {
            return method.invoke(instance, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得属性值
     *
     * @param className 类的全名
     * @param fieldName 属性名
     * @param instance  对应的对象，为 null 时取静态变量
     * @return 属性对应的值
     */
    public static Object getField(String className, Object instance, String fieldName) {
        if (className == null || className.isEmpty()) {
            throw new IllegalArgumentException("className 不能为空");
        }
        if (fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("fieldName 不能为空");
        }
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置属性
     *
     * @param className 类的全名
     * @param fieldName 属性名
     * @param instance  对应的对象，为 null 时改变的是静态变量
     * @param value     值
     */
    public static void setField(String className, Object instance, String fieldName, Object value) {
        if (className == null || className.isEmpty()) {
            throw new IllegalArgumentException("className 不能为空");
        }
        if (fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("fieldName 不能为空");
        }
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据方法名，类名，参数获取方法
     *
     * @param className  类名，全名称
     * @param methodName 方法名
     * @param paramTypes 参数类型列表
     * @return 方法对象
     */
    public static Method getMethod(String className, String methodName, Class<?>... paramTypes) {
        if (className == null || className.isEmpty()) {
            throw new IllegalArgumentException("className 不能为空");
        }
        if (methodName == null || methodName.isEmpty()) {
            throw new IllegalArgumentException("methodName 不能为空");
        }
        try {
            Class<?> clazz = Class.forName(className);
            return clazz.getDeclaredMethod(methodName, paramTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
