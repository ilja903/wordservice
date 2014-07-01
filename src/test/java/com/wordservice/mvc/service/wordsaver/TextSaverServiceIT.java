package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.model.WordTriTuple;
import com.wordservice.mvc.model.WordTuple;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.Iterator;

import static junit.framework.Assert.*;

public class TextSaverServiceIT extends IntegrationTestsBase {


    @Test
    @Ignore
    public void shouldSaveBigAmountOfWordsWithoutFailing() {
        long startTime = System.currentTimeMillis();

        textSaverService.saveToRepo(dickensText);
        assertTrue(wordRepository.count() > 300);
        assertNull(template.getRelationshipBetween(new WordEntity("zxc"), new WordEntity("ds"),
                WordRelationship.class, WordRelationship.relationshipType));

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.err.println(estimatedTime);
    }

    @Test
    @Rollback
    public void shouldReturnCorrectAmountOfWords() {
        textSaverService.saveToRepo("Hello Ilja!");
        assertEquals(2, wordRepository.count());
    }

    @Test
    @Rollback
    public void shouldSaveOneWord() {
        textSaverService.saveToRepo("Hello");
        assertEquals(1, wordRepository.count());
    }

    @Test
    @Rollback
    public void shouldReturnCorrectPopularityOfSavedWord() {
        textSaverService.saveToRepo("Hello Hello Hello");
        assertEquals(2, wordRepositoryFixedIndexesSearch.findByWord("Hello").getPopularity());
        assertEquals(1, wordTupleRepository.count());
    }

    @Test
    @Rollback
    public void shouldReturnCorrectTupleAmountForOneWord() {
        textSaverService.saveToRepo("Hello");
        assertEquals(0, wordTupleRepository.count());
        assertEquals(0, sentenceRepository.count());
    }

    @Test
    @Rollback
    public void shouldReturnCorrectTupleAmountForTwoWords() {
        textSaverService.saveToRepo("Hello Great World");
        assertEquals(1, wordTupleRepository.count());
    }


    @Test
    @Rollback
    public void tupleShouldNotContainDuplicates() {
        textSaverService.saveToRepo("Lenin the great. Lenin the great?");
        assertEquals(1, wordTupleRepository.count());
        assertEquals(new Long(1), wordTupleRepository.findAll().single().getPopularity());
    }

    @Test
    @Rollback
    public void savedTriTupleShouldBeOk() {
        textSaverService.saveToRepo("Heil to the great.");
        assertEquals(1, wordTriTupleRepository.count());
        WordTriTuple single = wordTriTupleRepository.findAll().single();
        assertTrue(single.getFirstWordRelationshipId()<single.getSecondWordRelationshipId());
        assertTrue(single.getSecondWordRelationshipId()<single.getThirdWordRelationshipId());
    }

    @Test
    @Rollback
    public void savedTriTupleShouldHaveCorrectCount() {
        textSaverService.saveToRepo("Heil to the great leader.");
        assertEquals(2, wordTriTupleRepository.count());
    }

    @Test
    @Rollback
    public void savedTriTupleAmountShouldBeNull() {
        textSaverService.saveToRepo("Heil to the.");
        assertEquals(0, wordTriTupleRepository.count());
    }

    @Test
    @Rollback
    public void savedTupleShouldBeOk() {
        textSaverService.saveToRepo("Lenin the great.");
        WordTuple single = wordTupleRepository.findAll().single();
        assertTrue(single.getfWordRelationshipId() < single.getSecondWordRelationshipId());
    }

    @Test
    @Rollback
    public void shouldSaveWordsFromDifferentSenteces() {
        textSaverService.saveToRepo("Hello Ilja! My name is neo4j, and I am confused.");
        assertTrue(wordRepository.count() == 10);
    }

    @Test
    @Rollback
    public void shouldCheckThatSavedWordsShouldBeRetrivable() {
        textSaverService.saveToRepo("tralala hahaha.");
        WordEntity hahaha = wordRepositoryFixedIndexesSearch.findByWord("hahaha");
        assertNotNull(hahaha);
        WordEntity a = wordRepositoryFixedIndexesSearch.findByWord("a");
        assertNull(a);
    }

    @Test
    @Rollback
    public void shouldCreateCorrectAmountOfRelationshipsAndCorrectlyIncrementPopularityOfThem() {
        textSaverService.saveToRepo("Hello Ilja, I am neo4j, I am slow and ugly! I am happy, I am happy.");
        WordRelationship relationshipBetweenAmAndHappy = template.getRelationshipBetween(
                wordRepositoryFixedIndexesSearch.findByWord("am"), wordRepositoryFixedIndexesSearch.findByWord("happy"),
                WordRelationship.class, WordRelationship.relationshipType);
        assertEquals(1, relationshipBetweenAmAndHappy.getPopularity());
    }

    @Test
    @Rollback
    public void checkInjection() {
        assertNotNull(textSaverService);
        assertNotNull(template);
        assertNotNull(wordRepository);
    }


}