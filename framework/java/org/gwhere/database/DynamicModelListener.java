package org.gwhere.database;

import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.AnnotationsAttribute;
import org.apache.ibatis.javassist.bytecode.ClassFile;
import org.apache.ibatis.javassist.bytecode.ConstPool;
import org.apache.ibatis.javassist.bytecode.annotation.Annotation;
import org.apache.ibatis.javassist.bytecode.annotation.EnumMemberValue;
import org.apache.ibatis.type.JdbcType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Table;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.*;

import static org.apache.ibatis.javassist.bytecode.AnnotationsAttribute.*;

/**
 * Created by jiangtao on 16/5/17.
 */
public class DynamicModelListener implements ServletContextListener {

    private final static Logger logger = LogManager.getLogger(DynamicModelListener.class);

    private final static String MODEL_PATH = "org/gwhere";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ClassPool pool = ClassPool.getDefault();
        String path = getClass().getClassLoader().getResource("/").getPath();
        File modelDir = new File(path + MODEL_PATH);
        if (!modelDir.isDirectory()) {
            return;
        }
        wrapFile(modelDir, pool, false);
    }

    private void wrapFile(File file, ClassPool pool, boolean needWrap) {
        try {
            if (file.isDirectory()) {
                needWrap = needWrap ? needWrap : "model".equals(file.getName());
                for (File childFile : file.listFiles()) {
                    wrapFile(childFile, pool, needWrap);
                }
            } else if (needWrap) {
                ClassFile classFile = new ClassFile(new DataInputStream(new FileInputStream(file)));
                CtClass ctClass = pool.makeClass(classFile);
                if (ctClass.getAnnotation(Table.class) == null) {
                    return;
                }
                ConstPool constpool = classFile.getConstPool();
                for (CtField ctField : ctClass.getDeclaredFields()) {
                    try {
                        AnnotationsAttribute attr = (AnnotationsAttribute) ctField.getFieldInfo().getAttribute(visibleTag);
                        if (attr == null) {
                            attr = new AnnotationsAttribute(constpool, visibleTag);
                            ctField.getFieldInfo().addAttribute(attr);
                        } else if (attr.getAnnotation(ColumnType.class.getName()) != null) {
                            continue;
                        }
                        Annotation annotation = new Annotation(ColumnType.class.getName(), constpool);
                        EnumMemberValue enumValue = new EnumMemberValue(constpool);
                        enumValue.setType(JdbcType.class.getName());
                        enumValue.setValue(getJdbcType(Class.forName(ctField.getType().getName())).name());
                        annotation.addMemberValue("jdbcType", enumValue);
                        attr.addAnnotation(annotation);
                    } catch (Exception e) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("处理异常:file=" + file.getPath() + ",field=" + ctField.getName(), e);
                        }
                    }
                }
                ctClass.toClass();
            }
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("处理异常:file=" + file.getPath(), e);
            }
        }
    }

    private JdbcType getJdbcType(Class javaType) {
        if (javaType == String.class) {
            return JdbcType.VARCHAR;
        } else if (javaType == BigDecimal.class) {
            return JdbcType.NUMERIC;
        } else if (javaType == Boolean.class || javaType == boolean.class) {
            return JdbcType.BOOLEAN;
        } else if (javaType == Byte.class || javaType == byte.class) {
            return JdbcType.TINYINT;
        } else if (javaType == Short.class || javaType == short.class) {
            return JdbcType.SMALLINT;
        } else if (javaType == Integer.class || javaType == int.class) {
            return JdbcType.INTEGER;
        } else if (javaType == Long.class || javaType == long.class) {
            return JdbcType.BIGINT;
        } else if (javaType == Float.class || javaType == float.class) {
            return JdbcType.REAL;
        } else if (javaType == Double.class || javaType == double.class) {
            return JdbcType.DOUBLE;
        } else if (javaType == Byte[].class || javaType == byte[].class) {
            return JdbcType.VARBINARY;
        } else if (javaType == Date.class) {
            return JdbcType.DATE;
        } else if (javaType == Time.class) {
            return JdbcType.TIME;
        } else if (javaType == Timestamp.class) {
            return JdbcType.TIMESTAMP;
        } else if (javaType == Clob.class) {
            return JdbcType.CLOB;
        } else if (javaType == Blob.class) {
            return JdbcType.BLOB;
        } else {
            return JdbcType.UNDEFINED;
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
