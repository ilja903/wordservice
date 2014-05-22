package com.wordservice.mvc.controller;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordRepository;
import com.wordservice.mvc.service.wordfinder.SentenceContextWordFinderService;
import com.wordservice.mvc.service.wordsaver.WordEntitySaverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WordControllerImpl {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordEntitySaverService wordEntitySaverService;

    @Autowired
    private SentenceContextWordFinderService sentenceContextWordFinderService;

    @RequestMapping(value = "getTopFor/{word}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> get10TopWordsAfter(@PathVariable String word) {
        return wordRepository.getTop10WordsAfter(word);
    }

    @RequestMapping(value = "getTopFor/{word1}/{word2}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> get10TopWordsAfter(@PathVariable String word1, @PathVariable String word2) {
        return wordRepository.getTop10WordsAfter(word1, word2);
    }

    @RequestMapping(value = "getSentence/{word1}/{word2}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getSentence(@PathVariable String word1, @PathVariable String word2) {
        return sentenceContextWordFinderService.getNextWords(word1, word2);
    }

    @RequestMapping(value = "getWordStartingWith/{string}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getWordStartingWith(@PathVariable String string) {
        return wordRepository.findByWordStartingWith(string);
    }

    @RequestMapping(value = "getWordContaining/{string}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getWordContaining(@PathVariable String string) {
        return wordRepository.findByWordContaining(string);
    }

    @RequestMapping(value = "wordApi", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody String jsonString) {
        wordEntitySaverService.saveToRepo(jsonString);
    }
}
