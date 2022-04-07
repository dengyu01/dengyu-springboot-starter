package com.hsccc.myspringbootstarter.util;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class MybatisPlusGeneratorTest {
    @Test
    @Disabled
    void generator() {
        String projectPath = System.getProperty("user.dir");
        String globalOutputPath = projectPath + "/src/main/java";
        String mapperXmlPath = projectPath + "src/main/resources";
        String url = "jdbc:mysql://82.157.49.190:3306/test_db?characterEncoding=utf-8&serverTimezone=UTC";
        String username = "root";
        String password = "123456798";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("hsccc") // 设置作者
                            // .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir()
                            .outputDir(globalOutputPath); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.hsccc.myspringbootstarter") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, mapperXmlPath)) // 设置mapperXml生成路径
                            .entity("model.entity");

                })
                // .templateConfig(builder -> builder.disable(TemplateType.SERVICE))
                .strategyConfig(builder -> {
                    builder.addInclude("menu", "user", "role"); // 设置需要生成的表名
                    builder.entityBuilder()  // 设置实体类配置
                            .enableLombok()
                            .logicDeleteColumnName("del_flag")
                            .logicDeletePropertyName("delFlag")
                            .addTableFills(new Column("create_time", FieldFill.INSERT))
                            .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                            .idType(IdType.AUTO);
                    builder.controllerBuilder().enableRestStyle(); // 设置为@RestController

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
