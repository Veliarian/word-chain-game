package com.wordsgame.service.impl;

import com.wordsgame.entity.Word;
import com.wordsgame.entity.Words;
import com.wordsgame.repository.WordRepository;
import com.wordsgame.service.CheckWordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CheckWordServiceImpl implements CheckWordService {

    private final WordRepository wordRepository;

    public CheckWordServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public Words checkWords(Words words) {
        List<String> requestWords = words.getWords();
        List<String> responseWords = new ArrayList<>();
        List<String> wordsForSave = new ArrayList<>();

        if (!requestWords.isEmpty()) {

            int emptyWordIndex = hasEmptyWord(requestWords);
            if (emptyWordIndex == -1) {
                if (requestWords.size() > 1) {
                    for (int i = 0; i < requestWords.size(); i++) {
                        String firstWord = requestWords.get(i);
                        String secondWord;
                        int j = i + 1;
                        if (j < requestWords.size()) {
                            secondWord = requestWords.get(j);

                            if (isWalidWords(firstWord, secondWord)) {
                                responseWords.add(firstWord);
                                wordsForSave.add(firstWord);
                            } else {
                                responseWords.add(firstWord);
                                wordsForSave.add(firstWord);
                                break;
                            }
                        } else {
                            responseWords.add(firstWord);
                            wordsForSave.add(firstWord);
                        }
                    }
                } else {
                    responseWords = requestWords;
                    wordsForSave = requestWords;
                }

            } else {
                responseWords = requestWords.subList(0, emptyWordIndex);
                wordsForSave = requestWords.subList(0, emptyWordIndex);
            }
        }

        words.setWords(responseWords);
        saveWords(wordsForSave);
        return words;
    }

    @Override
    public List<Words> getAllWords() {
        List<Word> wordList = wordRepository.findAll();

        List<Words> orderedWords = null;
        if (!wordList.isEmpty()) {
            List<String> unorderedWords = wordList.stream().map(Word::getWord).collect(Collectors.toList());
            orderedWords = orderWords(unorderedWords);
        }

        return orderedWords;
    }

    private List<Words> orderWords(List<String> unorderedWords) {
        List<Words> words = new ArrayList<>();

        List<String> wordsChain;

        for (String word : unorderedWords) {
            wordsChain = new ArrayList<>();
            wordsChain.add(word);
            setWordChain(unorderedWords, wordsChain);
            words.add(new Words(wordsChain));
        }

        return words.stream().filter(distinctByKey(Words::getWords)).collect(Collectors.toList());

    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private void setWordChain(List<String> unorderedWords, List<String> wordsChain) {
        for (String unorderedWord : unorderedWords) {
            if (!wordsChain.contains(unorderedWord)) {
                if (isWalidWords(wordsChain.get(wordsChain.size() - 1), unorderedWord)) {
                    wordsChain.add(unorderedWord);
                } else if (isWalidWords(unorderedWord, wordsChain.get(0))) {
                    wordsChain.add(0, unorderedWord);
                }
            }
        }
    }

    private int hasEmptyWord(List<String> words) {
        int indexOfEmptyWord = -1;
        for (String word : words) {
            if (word.isEmpty()) {
                indexOfEmptyWord = words.indexOf(word);
                break;
            }
        }
        return indexOfEmptyWord;
    }

    private boolean isWalidWords(String firstWord, String secondWord) {
        Character lastChar = firstWord.toLowerCase().charAt(firstWord.length() - 1);
        Character firstChar = secondWord.toLowerCase().charAt(0);

        return lastChar.equals(firstChar);
    }

    private void saveWords(List<String> words) {
        List<Word> wordsFromDB = wordRepository.findAll();
        List<Word> wordsForSave = new ArrayList<>();

        if (!wordsFromDB.isEmpty()) {
            List<String> wordsValueFromDB = new ArrayList<>();
            wordsFromDB.forEach(word -> {
                wordsValueFromDB.add(word.getWord());
            });

            List<String> wordsForRm = new ArrayList<>();

            for (String w : words) {
                for (String dbW : wordsValueFromDB) {
                    if (w.equals(dbW)) {
                        wordsForRm.add(w);
                    }
                }
            }

            if (!wordsForRm.isEmpty()) {
                words.removeAll(wordsForRm);
            }
        }

        if (!words.isEmpty()) {
            for (String w : words) {
                Word word = new Word();
                word.setWord(w);
                wordsForSave.add(word);
            }
        }

        if (!wordsForSave.isEmpty()) {
            wordRepository.saveAll(wordsForSave);
        }
    }
}
