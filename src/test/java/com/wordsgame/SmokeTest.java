package com.wordsgame;

import static org.assertj.core.api.Assertions.assertThat;

import com.wordsgame.controller.WordsController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {
    @Autowired
    WordsController wordsController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(wordsController).isNotNull();
    }
}
