package org.gwhere.utils;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.gwhere.common.messagehandler.net.FileData;
import org.gwhere.common.messagehandler.net.FormData;
import org.gwhere.message.converter.MessageConverter;
import org.gwhere.message.converter.json.FastJsonMessageConverter;
import org.gwhere.message.handler.net.HttpConnectException;

import java.io.*;

public class HttpUtil {

    public static HttpEntity doFilePostOutput(FileData uploadFileRequestMessage) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        File file = uploadFileRequestMessage.getFile();
        builder.addBinaryBody("media", file, ContentType.DEFAULT_BINARY, file.getName());
        builder.addTextBody("type", uploadFileRequestMessage.getFileProp("type"), ContentType.DEFAULT_BINARY);
        return builder.build();
    }

    public static HttpEntity doFormPostOutput(FormData formDataMessage) {
        StringEntity reqEntity = new StringEntity(formDataMessage.toString(), "UTF-8");
        reqEntity.setContentType("application/x-www-form-urlencoded");
        return reqEntity;
    }

    public static HttpEntity doTextPostOutput(Object httpRequestMessage) {
        MessageConverter messageConverter = new FastJsonMessageConverter();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (messageConverter.canWrite(httpRequestMessage.getClass())) {
            messageConverter.write(httpRequestMessage, outputStream);
        } else {
            throw new HttpConnectException("Unable write request data with class " + httpRequestMessage.getClass());
        }
        try {
            return new StringEntity(outputStream.toString("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new HttpConnectException("Unsupported Encoding UTF-8", e);
        }
    }

    public static String getInputStreamString(InputStream inputStream) {
        String jsonStr = "";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }
            jsonStr = new String(out.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
