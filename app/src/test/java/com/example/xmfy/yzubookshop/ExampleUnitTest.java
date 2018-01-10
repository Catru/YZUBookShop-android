package com.example.xmfy.yzubookshop;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.getFormedData("http://172.17.7.13:8080/banner?size=5");
    }
}