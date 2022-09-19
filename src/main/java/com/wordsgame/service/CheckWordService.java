package com.wordsgame.service;

import com.wordsgame.entity.Words;

import java.util.List;

public interface CheckWordService {
    Words checkWords(Words words);
    List<Words> getAllWords();
}
