package org.gwhere.utils;

import org.apache.commons.beanutils.ConvertUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public final class Converter<T> {

    private final Class<T> targetType;

    private Converter(Class<T> targetType) {
        this.targetType = targetType;
    }

    public static <T> Converter<T> to(Class<T> targetType) {
        return new Converter<T>(targetType);
    }

    public T convert(Object value) {
        if(isControled(value)) {
            String parameter = value == null ? null : value.toString();
            if (parameter == null || "".equals(parameter.trim())) {
                return null;
            }
            value = (T) ConvertUtils.convert(parameter, targetType);
        }
        return (T) value;
    }

    private boolean isControled(Object value) {
        if (value instanceof Date) {
            return false;
        }
        return true;
    }

    public T convert(Map<String, Object> parameters, String propertyName) {
        Object value = parameters.get(propertyName);
        return convert(value);
    }

    public T convert(HttpServletRequest request, String propertyName) {
        String parameter = request.getParameter(propertyName);
        if (parameter == null || "".equals(parameter.trim()) || "null".equals(parameter.trim())) {
            return null;
        }
        return convert(parameter);
    }

}
