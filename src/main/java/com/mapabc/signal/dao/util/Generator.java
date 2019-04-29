package com.mapabc.signal.dao.util;

import com.camelot.core.generator.CamelotAutoGenerator;
import com.camelot.core.generator.InjectionConfig;
import com.camelot.core.generator.config.*;
import com.camelot.core.generator.config.rules.DbType;
import com.camelot.core.generator.config.rules.NamingStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 代码生成器演示
 * </p>
**/
public class Generator {

    /**
     * <p>Description:[要生成的表]</p>
     **/
    private static String[] tables = new String[]{"t_base_vendor_method"};

    /**
     * <p>Description:[在判断的时候不添加!=''的字段]</p>
     **/
    private static String[] notAppendApostropheTypes = new String[]{"Boolean", "Date"};

    /**
     * <p>Description:[需要范围查询的时间字段]</p>
     **/
    private static String[] rangeDateFileds = new String[]{""};

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        generateCode();
    }

    /**
     * <p>Description: [代码生成] </p>
     * Created on: 2018年07月17日15:29:19
     *
     * @author yinguijin
     */
    private static void generateCode() {
        CamelotAutoGenerator mpg = new CamelotAutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("D:/template");
        gc.setFileOverride(true);
        gc.setActiveRecord(false);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setTableAndFieldAnnotations(true);
        gc.setCustomAnnotations(true);
        gc.setAuthor("yinguijin");


        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setControllerName("%sController");
        //gc.setEntityName("%sBean");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("mapabc");
        dsc.setUrl("jdbc:mysql://44.104.93.92:3306/its_db_190422");
        mpg.setDataSource(dsc);

        // 策略配置
        // 字段名生成策略
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(tables);
        strategy.setFieldNaming(NamingStrategy.underline_to_camel);
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        //strategy.setDbColumnUnderline(true);
        mpg.setStrategy(strategy);
        strategy.setSuperControllerClass("com.mapabc.signal.controller.BaseController");
        strategy.setSuperMapperClass("com.mapabc.signal.dao.MyBaseMapper");
        strategy.setSuperServiceImplClass("com.mapabc.signal.service.impl.BaseServiceImpl");
        strategy.setSuperServiceClass("com.mapabc.signal.service.BaseService");

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.mapabc.signal");
        pc.setMapper("dao.mapper");
        pc.setController("controller");
        pc.setEntity("dao.vo");
        //pc.setSubModuleName("product");
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                //配置生成范围查询
                map.put("rangeDateFileds", rangeDateFileds);
                map.put("notAppendApostropheTypes", notAppendApostropheTypes);
                this.setMap(map);
            }
        };
        mpg.setCfg(cfg);

        //自定义模板
        TemplateConfig tc = new TemplateConfig();
        tc.setTemplatePath("/generate_code_vm");
        tc.setEntity("entity.java.ftl");
        tc.setMapper("mapper.java.ftl");
        tc.setXml("mapper.xml.ftl");
        tc.setService("service.java.ftl");
        tc.setServiceImpl("serviceImpl.java.ftl");
        tc.setController("controller.java.ftl");
        mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();
        // 打印注入设置【可无】
        //System.err.println(mpg.getCfg().getMap().get("time"));
    }

}

