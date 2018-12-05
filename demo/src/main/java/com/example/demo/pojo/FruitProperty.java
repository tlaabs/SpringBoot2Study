package com.example.demo.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/*
intellij lombok cannot find symbol 에러 해결
https://stackoverflow.com/questions/9424364/cant-compile-project-when-im-using-lombok-under-intellij-idea
 */
@Data
@Component
@ConfigurationProperties("fruit")
public class FruitProperty {
    private List<Map> list;
}
