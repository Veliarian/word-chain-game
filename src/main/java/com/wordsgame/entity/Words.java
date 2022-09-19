package com.wordsgame.entity;

import lombok.Data;

import java.util.List;

@Data
public class Words {
    private List<String> words;

    public Words() {
    }
    public Words(List<String> words) {
        this.words = words;
    }
}
