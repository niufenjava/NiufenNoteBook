package com.linkdoc.gen.utils;

import com.linkdoc.gen.entity.ColumnEntity;
import com.linkdoc.gen.entity.TableEntity;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 */
public class GenUtils {

    public static List<String> getTemplatesLocal() {
        //配置信息
        List<String> templates = new ArrayList<String>();
        Configuration config = getConfig();
        String templatePath = config.getString("templatePath");
        List<File> fileList = FileUtil.listFile("/Users/niufen/code/Git-Niufen/NiufenNoteBook/gen/target/classes/" + templatePath);
        for (File file : fileList) {
            templates.add(templatePath + "/" + file.getName());
        }

        return templates;
    }

    public static List<String> getTemplates() {
        //配置信息
        List<String> templates = new ArrayList<String>();
        Configuration config = getConfig();
        String templatePath = config.getString("templatePath");
        List<File> fileList = FileUtil.listFile(ClassUtils.getDefaultClassLoader().getResource("").getPath() + templatePath);
        for (File file : fileList) {
            templates.add(templatePath + "/" + file.getName());
        }

        return templates;
    }


    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip) {
        //配置信息
        Configuration config = getConfig();
        boolean hasBigDecimal = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getStringArray("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && attrType.equals("BigDecimal")) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        String mainPath = config.getString("mainPath");
        mainPath = StringUtils.isBlank(mainPath) ? "com.linkdoc.gen" : mainPath;
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("uri", camelToUri(tableEntity.getClassName()));
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("mainPath", mainPath);
        map.put("package", config.getString("package"));
        map.put("moduleName", config.getString("moduleName"));
        map.put("modulePackage", config.getString("package") + "." + config.getString("moduleName"));
        map.put("basePackage", config.getString("package") + ".common.base");
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                String fileName = getFileName(tableEntity, template, tableEntity.getClassName(), config.getString("package"), config.getString("moduleName"));
                String proName  = "onedata-server-platform/src/";
                zip.putNextEntry(new ZipEntry(proName+fileName));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }


    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String[] tablePrefixArray) {
        if (null != tablePrefixArray && tablePrefixArray.length > 0) {
            for (String tablePrefix : tablePrefixArray) {
                tableName = tableName.replace(tablePrefix, "");
            }
        }
        return columnToJava(tableName);
    }


    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
            throw new RRException("获取配置文件失败，", e);
        }
    }


    /**
     * 获取文件名
     */
    public static String getFileName(TableEntity tableEntity, String template, String className, String packageName, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }
        String testPackagePath = "test" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            testPackagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains("Bo.java.vm")) {
            return packagePath + "bo" + File.separator + className + "Bo.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller"+File.separator + className + "Controller.java";
        }

        if (template.contains("CreateAo.java.vm")) {
            return packagePath + "ao" + File.separator + className + "CreateAo.java";
        }

        if (template.contains("Entity.java.vm")) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains("ListAo.java.vm")) {
            return packagePath + "ao" + File.separator + className + "ListAo.java";
        }

        if (template.contains("Mapper.java.vm")) {
            return packagePath + "dao" + File.separator + className + "Mapper.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Mapper.xml";
        }

        if (template.contains("PageAo.java.vm")) {
            return packagePath + "ao" + File.separator + className + "PageAo.java";
        }


        if (template.contains("Qo.java.vm")) {
            return packagePath + "qo" + File.separator + className + "Qo.java";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("ServiceImplTest.java.vm")) {
            return testPackagePath + "service" + File.separator + "impl" + File.separator +className + "ServiceImplTest.java";
        }

        if (template.contains("Tools.java.vm")) {
            return packagePath + "tools" + File.separator + className + "Tools.java";
        }

        if (template.contains("UpdateAo.java.vm")) {
            return packagePath + "ao" + File.separator + className + "UpdateAo.java";
        }

        return null;
    }


    /**
     * 生成代码
     */
    public static void generatorCodeFile(Map<String, String> table,
                                     List<Map<String, String>> columns) {
        //配置信息
        Configuration config = getConfig();
        boolean hasBigDecimal = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getStringArray("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && attrType.equals("BigDecimal")) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        String mainPath = config.getString("mainPath");
        String packagePath = mainPath + ".module";
        mainPath = StringUtils.isBlank(mainPath) ? "com.linkdoc.gen" : mainPath;
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("ClassName", tableEntity.getClassName());
        map.put("uri", camelToUri(tableEntity.getClassName()));
        map.put("className", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("mainPath", mainPath);
        map.put("moduleName", config.getString("moduleName"));
        map.put("package", packagePath);
        map.put("modulePackage", packagePath + "." + config.getString("moduleName"));
        map.put("utilsPackage", mainPath + ".utils");
        map.put("basePackage", mainPath + ".base");
        map.put("commonPackage", mainPath + ".common");
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplatesLocal();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
//            if (!template.contains("Mapper.xml.vm")) {
//                continue;
//            }
            try {
                //添加到zip
                String fileName = getFileName(tableEntity, template, tableEntity.getClassName(), packagePath, config.getString("moduleName"));
                String proName = "fw-ecrf-service/src";
                String filePath = "/Users/niufen/code/Git-Linkdoc-BaseService/fw-ecrf/" + proName +"/" +fileName;
                File newFile = new File(filePath);
                File fileParent = newFile.getParentFile();
                if(!fileParent.exists()){
                    fileParent.mkdirs();
                }
                if(newFile.exists()){
                    if(!newFile.delete()){
                        System.out.println("文件删除失败");
                    }
                }
                newFile.createNewFile();

                FileOutputStream file = new FileOutputStream(filePath);

                IOUtils.write(sw.toString(), file, "UTF-8");
                IOUtils.closeQuietly(sw);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }
    public static String camelToUri(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("/");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
