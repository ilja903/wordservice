package com.wordservice.mvc.controller;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordRepository;
import com.wordservice.mvc.service.wordfinder.WordTupleFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wordservice.mvc.util.CleanUtil.*;

@Controller
@RequestMapping("/")
public class WordController {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordTupleFinderService wordTupleFinderService;


    @RequestMapping(value = "getTopFor/{word}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> get10TopWordsAfter(@PathVariable String word) {
        return wordRepository.getTop10WordsAfter(clean(word));
    }

    @RequestMapping(value = "getTopFor/{previous}/{last}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> get10TopWordsAfter(@PathVariable String previous, @PathVariable String last) {
        return wordRepository.getTop10WordsAfter(clean(previous), clean(last));
    }


    @RequestMapping(value = "context/{f}/{s}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getByFirstTwo(@PathVariable String f, @PathVariable String s) {
        return wordTupleFinderService.getNextWordsViaTuple(clean(f), clean(s));
    }

    @RequestMapping(value = "context/{f}/{s}/{t}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getByFirstTwo(@PathVariable String f, @PathVariable String s, @PathVariable String t) {
        return wordTupleFinderService.getNextWordsViaTuple(clean(f), clean(s), clean(t));
    }

}
