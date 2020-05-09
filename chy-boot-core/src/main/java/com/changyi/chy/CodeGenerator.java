package com.changyi.chy;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/代码生成/src/main/java");
        gc.setAuthor("wuwh");
        gc.setXmlName("%sMapper");
        gc.setOpen(false);
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);

        //开启 baseColumnList
        gc.setBaseColumnList(true);

        //开启 BaseResultMap
        gc.setBaseResultMap(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://sh-cdb-5wr4bn64.sql.tencentcdb.com:63285/dev-chy?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("ZeueGhtMder83tBO");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.changyi.chy");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        //String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
         String templatePath = "templates/mapperTemplate.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/代码生成/src/main/resources/mapper/"
                         + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        // 自定义请求添加实体类
        String reqAddtemplatePath = "templates/reqAddEntityTemplate.java.vm";
        focList.add(new FileOutConfig(reqAddtemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/代码生成/src/main/java/"+ pc.getParent().replace(".","/") +"/"+ "request/"
                        + "Req" + tableInfo.getEntityName()+"Add" + StringPool.DOT_JAVA;
            }
        });

        // 自定义请求更新实体类
        String reqUpdatetemplatePath = "templates/reqUpdateEntityTemplate.java.vm";
        focList.add(new FileOutConfig(reqUpdatetemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/代码生成/src/main/java/"+ pc.getParent().replace(".","/") +"/"+ "request/"
                        + "Req" + tableInfo.getEntityName()+"Update" + StringPool.DOT_JAVA;
            }
        });

        // 自定义请求返回实体类
        String ResponseEntitytemplatePath = "templates/responseEntityTemplate.java.vm";
        focList.add(new FileOutConfig(ResponseEntitytemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/代码生成/src/main/java/"+pc.getParent().replace(".","/") +"/"+"/"+"response/"
                        + "Response" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });




        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setController("templates/controllerTemplate.java");
        templateConfig.setEntity("templates/entityTemplate.java");
        templateConfig.setEntityKt("templates/entityTemplate.kt");
        templateConfig.setMapper("templates/mapperTemplate.java");
        templateConfig.setService("templates/serviceTemplate.java");
        templateConfig.setServiceImpl("templates/serviceImplTemplate.java");
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        //mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }
}
