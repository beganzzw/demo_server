//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.web.method.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.alibaba.fastjson.JSON;
import org.gwhere.annotation.JSONArray;
import org.gwhere.annotation.JSONObject;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

public class InvocableHandlerMethod extends HandlerMethod {
    private WebDataBinderFactory dataBinderFactory;
    private HandlerMethodArgumentResolverComposite argumentResolvers = new HandlerMethodArgumentResolverComposite();
    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    public InvocableHandlerMethod(HandlerMethod handlerMethod) {
        super(handlerMethod);
    }

    public InvocableHandlerMethod(Object bean, Method method) {
        super(bean, method);
    }

    public InvocableHandlerMethod(Object bean, String methodName, Class... parameterTypes) throws NoSuchMethodException {
        super(bean, methodName, parameterTypes);
    }

    public void setDataBinderFactory(WebDataBinderFactory dataBinderFactory) {
        this.dataBinderFactory = dataBinderFactory;
    }

    public void setHandlerMethodArgumentResolvers(HandlerMethodArgumentResolverComposite argumentResolvers) {
        this.argumentResolvers = argumentResolvers;
    }

    public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }

    public Object invokeForRequest(NativeWebRequest request, ModelAndViewContainer mavContainer, Object... providedArgs) throws Exception {
        Object[] args = this.getMethodArgumentValues(request, mavContainer, providedArgs);
        if(this.logger.isTraceEnabled()) {
            StringBuilder returnValue = new StringBuilder("Invoking [");
            returnValue.append(this.getBeanType().getSimpleName()).append(".");
            returnValue.append(this.getMethod().getName()).append("] method with arguments ");
            returnValue.append(Arrays.asList(args));
            this.logger.trace(returnValue.toString());
        }

        Object returnValue1 = this.doInvoke(args);
        if(this.logger.isTraceEnabled()) {
            this.logger.trace("Method [" + this.getMethod().getName() + "] returned [" + returnValue1 + "]");
        }

        return returnValue1;
    }

    private Object[] getMethodArgumentValues(NativeWebRequest request, ModelAndViewContainer mavContainer, Object... providedArgs) throws Exception {
        MethodParameter[] parameters = this.getMethodParameters();
        Object[] args = new Object[parameters.length];

        for(int i = 0; i < parameters.length; ++i) {
            MethodParameter parameter = parameters[i];
            parameter.initParameterNameDiscovery(this.parameterNameDiscoverer);
            GenericTypeResolver.resolveParameterType(parameter, this.getBean().getClass());
            Class paramType = parameter.getParameterType();
            if (parameter.getParameterAnnotation(JSONObject.class) != null) {
                args[i] = resolveJSONObjectArgument(parameter.getParameterName(), paramType, request);
            } else if (parameter.getParameterAnnotation(JSONArray.class) != null) {
                args[i] = resolveJSONOArrayArgument(parameter.getParameterName(),
                        parameter.getParameterAnnotation(JSONArray.class).type(), request);
            } else {
                args[i] = this.resolveProvidedArgument(parameter, providedArgs);
            }
            if(args[i] == null) {
                if(this.argumentResolvers.supportsParameter(parameter)) {
                    try {
                        args[i] = this.argumentResolvers.resolveArgument(parameter, mavContainer, request, this.dataBinderFactory);
                    } catch (Exception var9) {
                        if(this.logger.isDebugEnabled()) {
                            this.logger.debug(this.getArgumentResolutionErrorMessage("Error resolving argument", i), var9);
                        }

                        throw var9;
                    }
                } else if(args[i] == null) {
                    String msg = this.getArgumentResolutionErrorMessage("No suitable resolver for argument", i);
                    throw new IllegalStateException(msg);
                }
            }
        }

        return args;
    }

    private Object resolveJSONObjectArgument(String parameterName, Class<?> parameterType, NativeWebRequest webRequest) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String parameter = request.getParameter(parameterName);
        if (!StringUtils.hasText(parameter)) {
            return null;
        }
        return JSON.parseObject(parameter, parameterType);
    }

    private Object resolveJSONOArrayArgument(String parameterName, Class<?> parameterType, NativeWebRequest webRequest) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String parameter = request.getParameter(parameterName);
        if (!StringUtils.hasText(parameter)) {
            return null;
        }
        return JSON.parseArray(parameter, parameterType);
    }

    private String getArgumentResolutionErrorMessage(String message, int index) {
        MethodParameter param = this.getMethodParameters()[index];
        message = message + " [" + index + "] [type=" + param.getParameterType().getName() + "]";
        return this.getDetailedErrorMessage(message);
    }

    protected String getDetailedErrorMessage(String message) {
        StringBuilder sb = (new StringBuilder(message)).append("\n");
        sb.append("HandlerMethod details: \n");
        sb.append("Controller [").append(this.getBeanType().getName()).append("]\n");
        sb.append("Method [").append(this.getBridgedMethod().toGenericString()).append("]\n");
        return sb.toString();
    }

    private Object resolveProvidedArgument(MethodParameter parameter, Object... providedArgs) {
        if(providedArgs == null) {
            return null;
        } else {
            Object[] var3 = providedArgs;
            int var4 = providedArgs.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Object providedArg = var3[var5];
                if(parameter.getParameterType().isInstance(providedArg)) {
                    return providedArg;
                }
            }

            return null;
        }
    }

    protected Object doInvoke(Object... args) throws Exception {
        ReflectionUtils.makeAccessible(this.getBridgedMethod());

        try {
            return this.getBridgedMethod().invoke(this.getBean(), args);
        } catch (IllegalArgumentException var5) {
            this.assertTargetBean(this.getBridgedMethod(), this.getBean(), args);
            String targetException1 = var5.getMessage() != null?var5.getMessage():"Illegal argument";
            throw new IllegalStateException(this.getInvocationErrorMessage(targetException1, args), var5);
        } catch (InvocationTargetException var6) {
            Throwable targetException = var6.getTargetException();
            if(targetException instanceof RuntimeException) {
                throw (RuntimeException)targetException;
            } else if(targetException instanceof Error) {
                throw (Error)targetException;
            } else if(targetException instanceof Exception) {
                throw (Exception)targetException;
            } else {
                String msg = this.getInvocationErrorMessage("Failed to invoke controller method", args);
                throw new IllegalStateException(msg, targetException);
            }
        }
    }

    private void assertTargetBean(Method method, Object targetBean, Object[] args) {
        Class methodDeclaringClass = method.getDeclaringClass();
        Class targetBeanClass = targetBean.getClass();
        if(!methodDeclaringClass.isAssignableFrom(targetBeanClass)) {
            String msg = "The mapped controller method class \'" + methodDeclaringClass.getName() + "\' is not an instance of the actual controller bean class \'" + targetBeanClass.getName() + "\'. If the controller requires proxying " + "(e.g. due to @Transactional), please use class-based proxying.";
            throw new IllegalStateException(this.getInvocationErrorMessage(msg, args));
        }
    }

    private String getInvocationErrorMessage(String message, Object[] resolvedArgs) {
        StringBuilder sb = new StringBuilder(this.getDetailedErrorMessage(message));
        sb.append("Resolved arguments: \n");

        for(int i = 0; i < resolvedArgs.length; ++i) {
            sb.append("[").append(i).append("] ");
            if(resolvedArgs[i] == null) {
                sb.append("[null] \n");
            } else {
                sb.append("[type=").append(resolvedArgs[i].getClass().getName()).append("] ");
                sb.append("[value=").append(resolvedArgs[i]).append("]\n");
            }
        }

        return sb.toString();
    }
}
