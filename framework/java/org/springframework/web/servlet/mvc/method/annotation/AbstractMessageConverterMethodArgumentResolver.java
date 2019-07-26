package org.springframework.web.servlet.mvc.method.annotation;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gwhere.springconfig.SpringMVCConfig;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyAdviceChain;

public abstract class AbstractMessageConverterMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Set<HttpMethod> SUPPORTED_METHODS;
    private static final Object NO_VALUE;
    protected final Log logger;
    protected final List<HttpMessageConverter<?>> messageConverters;
    protected final List<MediaType> allSupportedMediaTypes;
    private final RequestResponseBodyAdviceChain advice;

    public AbstractMessageConverterMethodArgumentResolver(List<HttpMessageConverter<?>> converters) {
        this(converters, (List)null);
    }

    public AbstractMessageConverterMethodArgumentResolver(List<HttpMessageConverter<?>> converters, List<Object> requestResponseBodyAdvice) {
        this.logger = LogFactory.getLog(this.getClass());
        Assert.notEmpty(converters, "\'messageConverters\' must not be empty");
        this.messageConverters = converters;
        this.allSupportedMediaTypes = getAllSupportedMediaTypes(converters);
        this.advice = new RequestResponseBodyAdviceChain(requestResponseBodyAdvice);
    }

    private static List<MediaType> getAllSupportedMediaTypes(List<HttpMessageConverter<?>> messageConverters) {
        LinkedHashSet allSupportedMediaTypes = new LinkedHashSet();
        Iterator result = messageConverters.iterator();

        while(result.hasNext()) {
            HttpMessageConverter messageConverter = (HttpMessageConverter)result.next();
            allSupportedMediaTypes.addAll(messageConverter.getSupportedMediaTypes());
        }

        ArrayList result1 = new ArrayList(allSupportedMediaTypes);
        MediaType.sortBySpecificity(result1);
        return Collections.unmodifiableList(result1);
    }

    protected RequestResponseBodyAdviceChain getAdvice() {
        return this.advice;
    }

    protected <T> Object readWithMessageConverters(NativeWebRequest webRequest, MethodParameter methodParam, Type paramType) throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {
        ServletServerHttpRequest inputMessage = this.createInputMessage(webRequest);
        return this.readWithMessageConverters((HttpInputMessage)inputMessage, methodParam, paramType);
    }

    protected <T> Object readWithMessageConverters(HttpInputMessage inputMessage, MethodParameter param, Type targetType) throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {
        boolean noContentType = false;

        MediaType contentType;
        try {
            contentType = inputMessage.getHeaders().getContentType();
        } catch (InvalidMediaTypeException var14) {
            throw new HttpMediaTypeNotSupportedException(var14.getMessage());
        }

        if(contentType == null) {
            noContentType = true;
            contentType = MediaType.APPLICATION_OCTET_STREAM;
        }

        Class contextClass = param != null?param.getContainingClass():null;
        Class targetClass = targetType instanceof Class?(Class)targetType:null;
        if(targetClass == null) {
            ResolvableType httpMethod = param != null?ResolvableType.forMethodParameter(param):ResolvableType.forType(targetType);
            targetClass = httpMethod.resolve();
        }

        HttpMethod httpMethod1 = ((HttpRequest)inputMessage).getMethod();
        Object body = NO_VALUE;

        Object inputMessage1;
        try {
            inputMessage1 = new AbstractMessageConverterMethodArgumentResolver.EmptyBodyCheckingHttpInputMessage(inputMessage);
            Iterator ex = this.messageConverters.iterator();

            while(ex.hasNext()) {
                HttpMessageConverter converter = (HttpMessageConverter)ex.next();
                Class converterType = converter.getClass();
                if(converter instanceof GenericHttpMessageConverter) {
                    GenericHttpMessageConverter genericConverter = (GenericHttpMessageConverter)converter;
                    if(genericConverter.canRead(targetType, contextClass, contentType)) {
                        if(this.logger.isDebugEnabled()) {
                            this.logger.debug("Read [" + targetType + "] as \"" + contentType + "\" with [" + converter + "]");
                        }

                        if(((HttpInputMessage)inputMessage1).getBody() != null) {
                            inputMessage1 = this.getAdvice().beforeBodyRead((HttpInputMessage)inputMessage1, param, targetType, converterType);
                            body = genericConverter.read(targetType, contextClass, (HttpInputMessage)inputMessage1);
                            body = this.getAdvice().afterBodyRead(body, (HttpInputMessage)inputMessage1, param, targetType, converterType);
                        } else {
                            body = null;
                            body = this.getAdvice().handleEmptyBody(body, (HttpInputMessage)inputMessage1, param, targetType, converterType);
                        }
                        break;
                    }
                }
                // 修改源码 yexin 为了解决Controller中json到List参数的自动转换你问题 begin
                // 说明:若发现是使用PagedFastJsonHttpMessageConverter,则在调用read时将targetType传入供类型判断
                else if(converter instanceof SpringMVCConfig.PagedFastJsonHttpMessageConverter && targetType instanceof ParameterizedType) {
                    SpringMVCConfig.PagedFastJsonHttpMessageConverter pagedFastJsonHttpMessageConverter = (SpringMVCConfig.PagedFastJsonHttpMessageConverter)converter;
                    if(pagedFastJsonHttpMessageConverter.canRead(targetClass, contentType)) {
                        if(this.logger.isDebugEnabled()) {
                            this.logger.debug("Read [" + targetType + "] as \"" + contentType + "\" with [" + converter + "]");
                        }

                        if(((HttpInputMessage)inputMessage1).getBody() != null) {
                            inputMessage1 = this.getAdvice().beforeBodyRead((HttpInputMessage)inputMessage1, param, targetType, converterType);
                            body = pagedFastJsonHttpMessageConverter.read((ParameterizedType) targetType, (HttpInputMessage)inputMessage1);
                            body = this.getAdvice().afterBodyRead(body, (HttpInputMessage)inputMessage1, param, targetType, converterType);
                        } else {
                            body = null;
                            body = this.getAdvice().handleEmptyBody(body, (HttpInputMessage)inputMessage1, param, targetType, converterType);
                        }
                        break;
                    }
                }
                // 修改源码 end
                else if(targetClass != null && converter.canRead(targetClass, contentType)) {
                    if(this.logger.isDebugEnabled()) {
                        this.logger.debug("Read [" + targetType + "] as \"" + contentType + "\" with [" + converter + "]");
                    }

                    if(((HttpInputMessage)inputMessage1).getBody() != null) {
                        inputMessage1 = this.getAdvice().beforeBodyRead((HttpInputMessage)inputMessage1, param, targetType, converterType);
                        body = converter.read(targetClass, (HttpInputMessage)inputMessage1);
                        body = this.getAdvice().afterBodyRead(body, (HttpInputMessage)inputMessage1, param, targetType, converterType);
                    } else {
                        body = null;
                        body = this.getAdvice().handleEmptyBody(body, (HttpInputMessage)inputMessage1, param, targetType, converterType);
                    }
                    break;
                }
            }
        } catch (IOException var15) {
            throw new HttpMessageNotReadableException("Could not read document: " + var15.getMessage(), var15);
        }

        if(body != NO_VALUE) {
            return body;
        } else if(httpMethod1 != null && SUPPORTED_METHODS.contains(httpMethod1) && (!noContentType || ((HttpInputMessage)inputMessage1).getBody() != null)) {
            throw new HttpMediaTypeNotSupportedException(contentType, this.allSupportedMediaTypes);
        } else {
            return null;
        }
    }

    protected ServletServerHttpRequest createInputMessage(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = (HttpServletRequest)webRequest.getNativeRequest(HttpServletRequest.class);
        return new ServletServerHttpRequest(servletRequest);
    }

    protected void validateIfApplicable(WebDataBinder binder, MethodParameter methodParam) {
        Annotation[] annotations = methodParam.getParameterAnnotations();
        Annotation[] var4 = annotations;
        int var5 = annotations.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Annotation ann = var4[var6];
            Validated validatedAnn = (Validated)AnnotationUtils.getAnnotation(ann, Validated.class);
            if(validatedAnn != null || ann.annotationType().getSimpleName().startsWith("Valid")) {
                Object hints = validatedAnn != null?validatedAnn.value():AnnotationUtils.getValue(ann);
                Object[] validationHints = hints instanceof Object[]?(Object[])((Object[])hints):new Object[]{hints};
                binder.validate(validationHints);
                break;
            }
        }

    }

    protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter methodParam) {
        int i = methodParam.getParameterIndex();
        Class[] paramTypes = methodParam.getMethod().getParameterTypes();
        boolean hasBindingResult = paramTypes.length > i + 1 && Errors.class.isAssignableFrom(paramTypes[i + 1]);
        return !hasBindingResult;
    }

    static {
        SUPPORTED_METHODS = EnumSet.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH);
        NO_VALUE = new Object();
    }

    private static class EmptyBodyCheckingHttpInputMessage implements HttpInputMessage {
        private final HttpHeaders headers;
        private final InputStream body;
        private final HttpMethod method;

        public EmptyBodyCheckingHttpInputMessage(HttpInputMessage inputMessage) throws IOException {
            this.headers = inputMessage.getHeaders();
            InputStream inputStream = inputMessage.getBody();
            if(inputStream == null) {
                this.body = null;
            } else if(inputStream.markSupported()) {
                inputStream.mark(1);
                this.body = inputStream.read() != -1?inputStream:null;
                inputStream.reset();
            } else {
                PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream);
                int b = pushbackInputStream.read();
                if(b == -1) {
                    this.body = null;
                } else {
                    this.body = pushbackInputStream;
                    pushbackInputStream.unread(b);
                }
            }

            this.method = ((HttpRequest)inputMessage).getMethod();
        }

        public HttpHeaders getHeaders() {
            return this.headers;
        }

        public InputStream getBody() throws IOException {
            return this.body;
        }

        public HttpMethod getMethod() {
            return this.method;
        }
    }
}
