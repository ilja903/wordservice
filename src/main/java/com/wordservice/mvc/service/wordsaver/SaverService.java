package com.wordservice.mvc.service.wordsaver;


import com.wordservice.mvc.model.*;
import com.wordservice.mvc.repository.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SaverService {
    private static final Logger logger = LogManager
            .getLogger(TextSaverService.class.getName());

    @Autowired
    private WordRelationshipRepository wordRelationshipRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordRepositoryFixedIndexesSearch wordRepositoryFixedIndexesSearch;

    @Autowired
    private WordTupleRepository wordTupleRepository;

    @Autowired
    private WordTriTupleRepository wordTriTupleRepository;

    @Autowired
    private WordRelationshipTupleRepository wordRelationshipTupleRepository ;

    WordTuple createOrIncrementPopularityOfWordTuple(WordRelationship wordRelationship1, WordRelationship wordRelationship2) {
        long startTime = System.currentTimeMillis();

        WordTuple wordTuple = wordTupleRepository.getTupleWithRelationShipIds(wordRelationship1.getId(), wordRelationship2.getId());
        if(wordTuple == null) {
            wordTuple = wordTupleRepository.save(new WordTuple(wordRelationship1.getId(), wordRelationship2.getId()));
        }else {
            wordTuple.incrementPopularity();
            wordTupleRepository.save(wordTuple);
        }

        logger.info("Elapsed time for tuple " + wordTuple + " operations is " + (System.currentTimeMillis() - startTime));

        return wordTuple;
    }

    WordTriTuple createOrIncrementPopularityOfWordTriTuple(WordRelationship wordRelationship1, WordRelationship wordRelationship2, WordRelationship wordRelationship3) {
        long startTime = System.currentTimeMillis();

        WordTriTuple wordTriTuple = wordTriTupleRepository.getWithRelationShipIds(wordRelationship1.getId(), wordRelationship2.getId(), wordRelationship3.getId());
        if(wordTriTuple == null) {
            wordTriTuple = wordTriTupleRepository.save(new WordTriTuple(wordRelationship1.getId(), wordRelationship2.getId(), wordRelationship3.getId()));
        }else {
            wordTriTuple.incrementPopularity();
            wordTriTupleRepository.save(wordTriTuple);
        }

        logger.info("Elapsed time for trituple " + wordTriTuple + " operations is " + (System.currentTimeMillis() - startTime));

        return wordTriTuple;
    }

    WordEntity getOrCreateWordEntity(String word) {
        long startTime = System.currentTimeMillis();

        WordEntity wordEntity = wordRepositoryFixedIndexesSearch.findByWord(word);
        if (wordEntity == null) {
            wordEntity = new WordEntity(word);
        } else {
            wordEntity.incrementPopularity();
        }
        wordEntity = wordRepository.save(wordEntity);

        logger.info("Elapsed time for word " + word + " operations is " + (System.currentTimeMillis() - startTime));

        return wordEntity;
    }

    WordRelationshipTuple createOrIncrementPopularityOfWordRelationshipTuple(WordEntity first, WordEntity second, WordEntity third, WordEntity fourth) {
        long startTime = System.currentTimeMillis();

        WordRelationshipTuple wordRelationshipTuple = wordRelationshipTupleRepository.getRelationshipBetween(third, fourth);
        if (wordRelationshipTuple == null) {
            wordRelationshipTuple = new WordRelationshipTuple(first,second,third,fourth);
        } else {
            wordRelationshipTuple.incrementPopularity();
        }
        wordRelationshipTuple = wordRelationshipTupleRepository.save(wordRelationshipTuple);

        logger.info("Elapsed time for wordRelationshipTuple " + wordRelationshipTuple + " operations is " + (System.currentTimeMillis() - startTime));

        return wordRelationshipTuple;
    }

    WordRelationship createOrIncrementPopularityOfRelationship(WordEntity wordEntity1, WordEntity wordEntity2) {
        long startTime = System.currentTimeMillis();

        WordRelationship relationshipBetween = wordRelationshipRepository.getRelationshipBetween(wordEntity1, wordEntity2);
        if (relationshipBetween == null) {
            WordRelationship wordRelationship = new WordRelationship(wordEntity1, wordEntity2);
            relationshipBetween = wordRelationshipRepository.save(wordRelationship);
        } else {
            relationshipBetween.incrementPopularity();
            relationshipBetween = wordRelationshipRepository.save(relationshipBetween);
        }

        logger.info("Elapsed time for relationship  operations is " + (System.currentTimeMillis() - startTime));

        return relationshipBetween;
    }
}
