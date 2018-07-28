package com.hk.dream.util;

/**
 * 坤
 * 2018/7/24 21:04
 */

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;

/**
 * 公用toString方法<br>
 * 将对象的以get为前缀的无参方法转化为字符串<br>
 * 主要用于输出日志信息<br>
 * 示例, 将User对象转换为字符串, 但地址不输出, 密码以*号输出<br>
 * new ToString(user).exclude("address").secrete("password").execute();
 * @author zhaohuihua
 * @version 2012-9-26
 * @since 1.0
 */
public class ToString {

    /**
     * 调试日志
     */
    private static Log log = new Log();

    /**
     * 默认最大缩进层次
     */
    private static final int MAX_INDENT = 3;
    /**
     * get方法正则表达式
     */
    private static final Pattern PTN_GETTER = Pattern.compile("^(?:get|is).+$");
    /**
     * 缩进符的正则表达式
     */
    private static final Pattern PTN_INDENT = Pattern
            .compile("([\\r\\n]+)(\\t*)");
    /**
     * 日期格式字符串
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 方法比较器, 用于方法按名称排序
     */
    private static final MethodComparator<Method> COMPARATOR = new MethodComparator<Method>();

    /**
     * 有一些特殊字符会影响日志的显示格式, 需要替换为文本形式, 在REPLACE中配置
     */
    private static final Map<Pattern, String> REPLACE = new HashMap<Pattern, String>();
    static {
        // 将回车符 换行符 制表符等替换为文本形式
        REPLACE.put(Pattern.compile("\\f"), "\\\\f");
        REPLACE.put(Pattern.compile("\\r"), "\\\\r");
        REPLACE.put(Pattern.compile("\\n"), "\\\\n");
        REPLACE.put(Pattern.compile("\\t"), "\\\\t");
    }
    /**
     * "getClass" 方法
     */
    private static Method getClass = null;
    static {
        try {
            getClass = Object.class.getMethod("getClass");
        } catch (Exception e) {
            log.warn("init Object.getClass() method html", e);
        }
    }

    /**
     * 待转化的对象
     */
    private Object target;
    /**
     * 缩进层次
     */
    private int indent;
    /**
     * 最大缩进层次
     */
    private int maxIndent;
    /**
     * 有一些字段不希望被输出, 在exclusion中配置
     */
    private List<String> exclusion;
    /**
     * 有一些内容需要保密, 在secrecy中配置
     */
    private List<String> secrecy;

    /**
     * 构造函数
     * @param  object 待转化的对象
     */
    public ToString(Object object) {
        this(object, MAX_INDENT);
    }

    /**
     * 构造函数
     * @param target 待转化的对象
     * @param indent 最大缩进层次
     */
    public ToString(Object target, int indent) {
        this.target = target;
        this.maxIndent = indent;
        this.exclusion = new ArrayList<String>();
        this.secrecy = new ArrayList<String>();
    }

    /**
     * 有一些字段不希望被输出, 通过这个方法排除
     * @param fields 字段名, 用字段名可匹配方法名和Map中的KEY<br>
     *        如address, 会排除getAddress()方法, 以及Map中KEY=address的值<br>
     *        注意: 这里并不会真正的去取字段名, 只是简单的将首字母大写再加上get|is
     * @return 返回当前对象, 用于连续调用
     */
    public ToString exclude(String... fields) {
        for (String getter : fields) {
            exclusion.add(getter);
        }
        return this;
    }

    /**
     * 有一些内容需要保密, 通过这个方法设置, 设置后将以*号代替内容
     * @param fields 字段名, 用字段名可匹配方法名和Map中的KEY<br>
     *        如password, 会保密getPassword()方法, 以及Map中KEY=password的值<br>
     *        注意: 这里并不会真正的去取字段名, 只是简单的将首字母大写再加上get|is
     * @return 返回当前对象, 用于连续调用
     */
    public ToString secrete(String... fields) {
        for (String getter : fields) {
            secrecy.add(getter);
        }
        return this;
    }

    /**
     * 将对象的以get为前缀的无参方法转化为字符串<br>
     * 主要用于输出日志信息<br>
     * @return String
     */
    public String execute() {
        if (target == null) {
            return null;
        }
        try {
            return toString(target);
        } catch (Exception e) {
            log.warn("To string html", e);
            return ((Object) target).toString();
        }
    }

    /**
     * 将对象的以get为前缀的无参方法转化为字符串
     * @param object 待转化的对象
     * @return String
     */
    @SuppressWarnings("unchecked")
    private String toString(Object object) {
        if (object == null) {
            return null;
        }

        Class<?> clazz = object.getClass();
        if (clazz.isArray()) { // 数组
            return arrayToString(object);
        } else if (CharSequence.class.isAssignableFrom(clazz)
                || clazz == Character.class) { // 字符类型或字符串类型
            return replace(object);
        } else if (clazz == Integer.class || clazz == Long.class
                || clazz == Short.class || clazz == Double.class
                || clazz == Float.class || clazz == Boolean.class
                || clazz == Byte.class) { // 基本类型及其包装类型
            return object.toString();
        } else if (clazz == Class.class) { // Class类型
            return getClassName(object);
        } else if (Date.class.isAssignableFrom(clazz)) { // 日期类型
            DateFormat formater = new SimpleDateFormat(DATE_FORMAT);
            return formater.format((Date) object);
        } else if (Collection.class.isAssignableFrom(clazz)) { // Collection类型
            return toString((Collection<?>) object);
        } else if (object != target && Enabled.class.isAssignableFrom(clazz)) {
            // 已经实现了ToString接口的类型
            String str = object.toString();
            // 增加缩进
            return PTN_INDENT.matcher(str).replaceAll("$1\t$2");
        }

        // 以下为复杂类型, 需要增加缩进了
        if (indent + 1 > maxIndent) { // 递归次数太多则不再转化
            return replace(object.toString());
        }
        indent++;
        String string;
        if (Map.class.isAssignableFrom(clazz)) { // Map类型
            string = toString((Map<Object, Object>) object);
        } else if (ServletRequest.class.isAssignableFrom(clazz)) {
            // ServletRequest类型
            Map map = ((ServletRequest) object).getParameterMap();
            string = toString(map);
        } else {
            // 其他类型
            // 将该对象的类及其父类的所有字段转换为字符串
            // 并将字段值按照对象的处理方式作递归转换
            List<Method> methods = getGetterList(object);
            string = toString(object, methods);
        }
        indent--;
        return string;
    }
    /**
     * 将对象的以get为前缀的无参方法转化为字符串<br>
     * @param object 待转化的对象
     * @param methods 指定的方法列表
     * @return String
     */
    private String toString(Object object, List<Method> methods) {
        if (object == null) {
            return null;
        }
        StringBuilder buffer = new StringBuilder();
        StringBuilder message = new StringBuilder();
        // 遍历方法列表, 依次调用对象的指定方法, 获取返回值
        for (Method method : methods) {
            if (method == null) {
                continue;
            }
            // 排除Static修饰符的方法和非公有的方法
            int modifier = method.getModifiers();
            if (Modifier.isStatic(modifier) || !Modifier.isPublic(modifier)) {
                continue;
            }
            String methodName = method.getName();
            if (method.getReturnType() == void.class // 没有返回结果
                    || method.getParameterTypes().length > 0 // 有参数
                    || !PTN_GETTER.matcher(methodName).matches()) { // 不是get方法
                continue;
            }
            try {
                if (buffer.length() > 0) {
                    buffer.append(", \r\n");
                }
                int index = methodName.startsWith("get") ? 3 : 2;
                // 打印缩进
                for (int i = 0; i < indent; i++) {
                    buffer.append("\t");
                }
                buffer.append(methodName.substring(index)).append(" = ");

                // 调用相应的方法获取返回结果
                if (isSecretMethod(methodName)) { // 保密的方法, 不再求值
                    buffer.append(toPassword(method.getReturnType()));
                    continue;
                }

                Object result = method.invoke(object, new Object[0]);
                Class<?> rclass = result == null ? null : result.getClass();
                // 获取被调用方法的返回值
                if (result == null) {
                    buffer.append("null");
                } else if (Class.class.isAssignableFrom(rclass)) { // Class类型
                    buffer.append(getClassName(object));
                } else if (CharSequence.class.isAssignableFrom(rclass)) {
                    buffer.append(replace(result));
                } else {
                    // 递归调用
                    buffer.append(toString(result));
                }
            } catch (Exception e) {
                if (message.length() > 0) {
                    message.append(", ");
                }
                message.append("Method:[").append(methodName).append("], ")
                        .append(getClassName(e))
                        .append(":[").append(e.getMessage()).append("]");
            }
        }
        if (message.length() > 0) { // 有失败的部分
            message.insert(0, "Get value is failed, ")
                    .append("Class:[").append(getClassName(object)).append("], ");
            log.warn(message.toString());
        }
        // 打印换行, 缩进
        buffer.append("\r\n");
        for (int i = 0; i < indent - 1; i++) {
            buffer.append("\t");
        }
        return buffer.insert(0, "{\r\n").append("}").toString();
    }

    private String toString(final Collection<?> list) {
        if (list == null) {
            return "null";
        }
        int length = list.size();
        if (length == 0) {
            return "[]";
        }
        int i = 0;
        StringBuilder buffer = new StringBuilder();
        for (Object item : list) {
            if (i++ >= 20) { // 太长则截断
                buffer.append(", ... (").append(length).append(") ");
                break;
            }
            if (buffer.length() > 0) {
                buffer.append(", ");
            }
            buffer.append(toString(item));
        }
        return buffer.insert(0, "[").append("]").toString();
    }

    private String toString(final Map<Object, Object> map) {
        if (map == null) {
            return "null";
        }
        if (map.size() == 0) {
            return "[]";
        }
        StringBuilder buffer = new StringBuilder();
        Set<Map.Entry<Object, Object>> set = map.entrySet();
        for (Map.Entry<Object, Object> entry : set) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (key == null) {
                continue;
            }
            if (isExcludeKey(key)) { // 被排除的KEY
                continue;
            }
            if (buffer.length() > 0) {
                buffer.append(", \r\n");
            }
            // 打印缩进
            for (int i = 0; i < indent; i++) {
                buffer.append("\t");
            }
            buffer.append(key).append(" = ");
            if (isSecretKey(key)) { // 保密的KEY
                buffer.append(toPassword(value)); // 输出*号
            } else {
                buffer.append(toString(value));
            }
        }
        // 打印换行, 缩进
        buffer.append("\r\n");
        for (int i = 0; i < indent - 1; i++) {
            buffer.append("\t");
        }
        return buffer.insert(0, "{\r\n").append("}").toString();
    }

    /**
     * 数组转换为字符串
     * @param array 数组
     * @return 字符串
     */
    private String arrayToString(final Object array) {
        if (array == null) {
            return "null";
        }
        if (!array.getClass().isArray()) {
            return toString(array);
        }
        int length = Array.getLength(array);
        if (length == 0) {
            return "[]";
        }
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i >= 20) { // 太长则截断
                buffer.append(", ... (").append(length).append(") ");
                break;
            }
            if (buffer.length() > 0) {
                buffer.append(", ");
            }
            buffer.append(toString(Array.get(array, i)));
        }
        return buffer.insert(0, "[").append("]").toString();
    }

    /**
     * 输出保密字符串
     * @param src 字符串
     * @return 保密字符串
     */
    private String toPassword(Object src) {
        if (src == null) return null;
        return toPassword(src.getClass());
    }

    /**
     * 输出保密字符串
     * @param clazz Class
     * @return 保密字符串
     */
    private String toPassword(Class<?> clazz) {
        if (clazz == null) return null;
        if (clazz.isArray()) { // 是数组
            return "[******]";
        } else {
            return "******";
        }
    }

    /**
     * 判断KEY是否为保密字段
     * @param key 字段
     * @return 是否为保密字段
     */
    private boolean isSecretKey(Object key) {
        return isSpecialKey(secrecy, key);
    }

    /**
     * 判断对象的方法是否为保密方法
     * @param method 方法名称
     * @return 是否为保密方法
     */
    private boolean isSecretMethod(Object method) {
        return isSpecialMethod(secrecy, method);
    }

    /**
     * 判断KEY是否为被排除字段
     * @param key 字段
     * @return 是否为被排除字段
     */
    private boolean isExcludeKey(Object key) {
        return isSpecialKey(exclusion, key);
    }

    /**
     * 判断对象的方法是否为被排除方法
     * @param method 方法名称
     * @return 是否为被排除方法
     */
    private boolean isExcludeMethod(Object method) {
        return isSpecialMethod(exclusion, method);
    }

    /**
     * 判断KEY是否为被排除或保密字段
     * @param list List
     * @param key 字段
     * @return 是否为被排除或保密字段
     */
    private boolean isSpecialKey(List<String> list, Object key) {
        if (key == null) return false;
        // 判断KEY是否为被排除或保密字段
        for (String name : list) {
            if (key.equals(name)) {
                return true; // 排除
            }
        }
        return false;
    }

    /**
     * 判断对象的方法是否为被排除或保密方法
     * @param list List
     * @param method 方法名称
     * @return 是否为被排除或保密方法
     */
    private boolean isSpecialMethod(List<String> list, Object method) {
        if (method == null)
            return false;
        // 判断方法名是否为被排除或保密字段
        for (String getter : list) {
            String name = getter;
            char initial = getter.charAt(0);
            if (initial >= 'a' && initial <= 'z') { // 首字母是小写字母转化为大写
                String lower = String.valueOf(initial);
                String upper = lower.toUpperCase();
                name = name.replaceFirst(lower, upper);
            }
            if (method.equals("get" + name)) {
                return true; // 排除或保密
            }
            if (method.equals("is" + name)) {
                return true; // 排除或保密
            }
        }
        return false;
    }

    /**
     * 将特殊字符替换为文本形式
     * @param obj 待转换的对象
     * @return 转换后的字符串
     */
    private String replace(Object obj) {
        if (obj == null) {
            return null;
        }
        String string = obj.toString();
        // 将特殊字符替换为文本形式
        Iterator<Map.Entry<Pattern, String>> iterator =
                REPLACE.entrySet().iterator();
        while (iterator.hasNext()) { // 遍历Map
            Map.Entry<Pattern, String> entry = iterator.next();
            Pattern pattern = entry.getKey();
            String replacement = entry.getValue();
            string = pattern.matcher(string).replaceAll(replacement);
        }
        if (string.length() > 60) { // 大于60则截取前50个字符
            string = new StringBuilder()
                    .append(string.substring(0, 50)).append("... ")
                    .append("(").append(string.length()).append(")")
                    .toString();
        }
        return string;
    }

    /**
     * 获取指定对象的get方法列表
     * @param object Object
     * @return List<Method>
     */
    private List<Method> getGetterList(Object object) {
        if (object == null) return null;

        // 获取实例的类
        Class<?> clazz = object.getClass();
        // 类的所有方法
        Method[] methodAll = clazz.getMethods();
        // 筛选后的方法
        List<Method> list = new ArrayList<Method>();
        // 遍历所有方法, 只取有返回结果,无参数的get方法
        // 并剔除在被排除方法之列的
        for (Method method : methodAll) {
            if (method == null) continue;
            String methodName = method.getName();
            if (getClass.getName().equals(methodName)) continue;
            if (method.getReturnType() == void.class // 没有返回结果
                    || method.getParameterTypes().length > 0 // 有参数
                    || !PTN_GETTER.matcher(methodName).matches()) { // 不是get方法
                continue;
            }
            if (isExcludeMethod(methodName)) continue;
            list.add(method);
        }
        // 以方法名排序
        Collections.sort(list, COMPARATOR);
        list.add(0, getClass);
        return list;
    }

    private String getClassName(Object obj) {
        if (obj == null) return "null";
        StringBuilder array = new StringBuilder();
        Class<?> clazz;
        if (obj.getClass() == Class.class) {
            clazz = (Class<?>) obj;
        } else {
            clazz = obj.getClass();
        }
        while (clazz.isArray()) {
            clazz = clazz.getComponentType();
            array.append("[]");
        }
        String className = clazz.getName();
        int index = className.lastIndexOf('.') + 1;
        return className.substring(index) + array.toString();
    }

    /**
     * 将对象的以get为前缀的无参方法转化为字符串<br>
     * 主要用于输出日志信息<br>
     * @return String
     */
    public String toString() {
        return this.execute();
    }

    /**
     * 方法比较器, 按照方法名称比较
     */
    private static class MethodComparator<T> implements Comparator<T> {

        public int compare(T o1, T o2) {
            if (o1 instanceof Method && o2 instanceof Method) {
                return ((Method)o1).getName().compareTo(((Method)o2).getName());
            }
            return 0;
        }
    }

    private static class Log {
        public void warn(String msg) {
            System.out.println("[WARN]" + msg);
        }
        public void warn(String msg, Throwable e) {
            System.out.println("[WARN]" + msg);
            e.printStackTrace();
        }
    }

    /**
     * ToString的标记接口<br>
     * 如果xxx已经实现该接口, 则new ToString(xxx)时直接调用xxx.toString()方法
     * @author  zhaohuihua
     * @version  2012-10-23
     */
    public interface Enabled { }


    /**
     * 对象toString 并过滤字段
     * @param exclude 不需要输出的字段名
     * @param secrete 加密成*号输出的字段名
     * @param t
     * @return
     */
    public String toStringFiltration(String exclude,String secrete,Object t){
        return new ToString(t, 4)
                .exclude(exclude).secrete(secrete).execute();
    }
}
