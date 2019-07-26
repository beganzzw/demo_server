package org.gwhere.springconfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.gwhere.exception.ApplicationExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = "org.gwhere.**.controller")
public class SpringMVCConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ApplicationExceptionResolver appExceptionResolver() {
        return new ApplicationExceptionResolver();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("redirect:/back/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/back/login.html").addResourceLocations("classpath:/public/");
//        registry.addResourceHandler("/back/index.html").addResourceLocations("classpath:/public/");
//        registry.addResourceHandler("/back/lib/**").addResourceLocations("classpath:/public/bower_components/");
//        registry.addResourceHandler("/back/view/**").addResourceLocations("classpath:/public/back/view/");
//        registry.addResourceHandler("/back/script/**").addResourceLocations("classpath:/public/back/script/");
//        registry.addResourceHandler("/back/css/**").addResourceLocations("classpath:/public/back/styles/css/");
//        registry.addResourceHandler("/back/img/**").addResourceLocations("classpath:/public/back/styles/img/");
//        registry.addResourceHandler("/back/**").addResourceLocations("classpath:/public/back/templates/");
//        registry.addResourceHandler("/wx/**").addResourceLocations("classpath:/public/wx/");
//        registry.addResourceHandler("/customer/**").addResourceLocations("classpath:/public/customer/");
    }

    @Bean
    public ViewResolver getViewResolver() {
        return new InternalResourceViewResolver();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter smc = new StringHttpMessageConverter();
        List<MediaType> mts = new ArrayList<MediaType>();
        mts.add(new MediaType("text","html",Charset.forName("utf-8")));
        smc.setSupportedMediaTypes(mts);
        converters.add(smc);
        PagedFastJsonHttpMessageConverter fastJsonHttpMessageConverter = new PagedFastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setCharset(Charset.forName("UTF-8"));
        fastJsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        fastJsonHttpMessageConverter.setFeatures(SerializerFeature.WriteMapNullValue, SerializerFeature.QuoteFieldNames, SerializerFeature.DisableCircularReferenceDetect);
        converters.add(fastJsonHttpMessageConverter);
    }

    public class PagedFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {
        @Override
        protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
            super.writeInternal(obj, outputMessage);
        }

        public Object read(ParameterizedType targetType, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
            Class actualClass = (Class) targetType.getActualTypeArguments()[0];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream in = inputMessage.getBody();
            byte[] buf = new byte[1024];

            while(true) {
                int bytes = in.read(buf);
                if(bytes == -1) {
                    byte[] bytes1 = baos.toByteArray();
                    if(targetType.getRawType().equals(List.class)) {
                        return JSON.parseArray(new String(bytes1, this.getCharset().name()), actualClass);
                    }
                    return JSON.parseObject(bytes1, 0, bytes1.length, this.getCharset().newDecoder(), actualClass);
                }

                if(bytes > 0) {
                    baos.write(buf, 0, bytes);
                }
            }
        }
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1024 * 1024 * 10);
        return multipartResolver;
    }
}
