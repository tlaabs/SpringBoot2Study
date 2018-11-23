package com.example.demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AutoconfigurationApplicationTests {

    @Value("${property.test.name")
    private String propertyTestName;

    @Value("${propertyTestList}")
    private String[] propertyTestArray;

    @Value("#{'${propertyTestList}'.split(',')}")
    private List<String> propertyTestList;

    @Test
    public void testValue(){
        assertThat(propertyTestArray[0], is("a"));
    }
}
