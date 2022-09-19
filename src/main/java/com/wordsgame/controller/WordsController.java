package com.wordsgame.controller;

import com.wordsgame.entity.Words;
import com.wordsgame.service.CheckWordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class WordsController {

    @Resource
    CheckWordService checkWordService;

    @PostMapping(path = "/")
    public ResponseEntity<Words> checkWords(@RequestBody Words words){
        Words response = checkWordService.checkWords(words);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<Words>> getOrderedWords(){
        return new ResponseEntity<>(checkWordService.getAllWords(), HttpStatus.OK);
    }

}
