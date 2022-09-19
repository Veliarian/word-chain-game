package com.wordsgame.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "word", unique = true)
    private String word;
}
