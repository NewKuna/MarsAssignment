package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ExampleApplicationTest {

    @Test
    void isPhoneNumber() {
        String s = "1234567890";
        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(s);
        Assert.isTrue(m.matches(), "This is not a valid phone number");
    }

    @Test
    void test() {
        String filePath = "src/main/resources/sample-image.jpg";
        Path path = Paths.get(filePath);
        try {
            Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
