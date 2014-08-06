package com.wordservice.mvc.bdd;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.*;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertNotNull;

public class TextSaverServiceIT extends IntegrationTestsBase {


    @Test
    @Rollback
    @Ignore
    public void shouldSaveBigAmountOfWordsWithoutFailing() {
        long startTime = System.currentTimeMillis();

        textSaverService.saveToRepo(dickensText);
        assertTrue(wordEntityRepository.count() > 300);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.err.println(estimatedTime);
    }

    @Test
    @Rollback
    public void shouldReturnCorrectAmountOfWords() {
        textSaverService.saveToRepo("Hello Ilja!");
        assertEquals(2, wordEntityRepository.count());
    }

    @Test
    @Rollback
    public void shouldSaveOneWord() {
        textSaverService.saveToRepo("Hello");
        assertEquals(1, wordEntityRepository.count());
    }

    @Test
    @Rollback
    public void shouldReturnCorrectPopularityOfSavedWord() {
        textSaverService.saveToRepo("Hello Hello Hello");
        assertEquals(2, wordEntityDAO.findByWord("Hello").getPopularity());
    }

    @Test
    @Rollback
    public void shouldSaveWordsFromDifferentSenteces() {
        textSaverService.saveToRepo("Hello Ilja! My name is neo4j, and I am confused.");
        assertTrue(wordEntityRepository.count() == 10);
    }

    @Test
    @Rollback
    public void shouldCheckThatSavedWordsShouldBeRetrivable() {
        textSaverService.saveToRepo("tralala hahaha.");
        WordEntity hahaha = wordEntityDAO.findByWord("hahaha");
        assertNotNull(hahaha);
        WordEntity a = wordEntityDAO.findByWord("a");
        assertNull(a);
    }

    @Test
    @Rollback
    public void shouldCheckThatRelationshipTupleShouldBeRetrievable() {
        textSaverService.saveToRepo("Hello my sad world.");
        WordEntity hello = wordEntityDAO.findByWord("Hello");
        WordEntity my = wordEntityDAO.findByWord("my");
        WordEntity sad = wordEntityDAO.findByWord("sad");
        WordEntity world = wordEntityDAO.findByWord("world");
        assertNotNull(hello);
        assertNotNull(my);
        assertNotNull(sad);
        assertNotNull(world);

        List<WordRelationshipTuple> relationshipBetweenHelloAndMy = wordRelationshipTupleDAO.getRelationshipsBetweenAsList(hello, my);
        assertEquals(1, relationshipBetweenHelloAndMy.size());

        WordRelationshipTuple relationshipBetweenMyAndSad = wordRelationshipTupleDAO.getRelationshipBetween(my,sad);
        assertNotNull(relationshipBetweenMyAndSad);

        WordRelationshipTuple relationshipBetweenSadAndHello = wordRelationshipTupleDAO.getRelationshipBetween(sad,world);
        assertNotNull(relationshipBetweenSadAndHello);
    }



    @Test
    @Rollback
    public void checkInjection() {
        assertNotNull(textSaverService);
        assertNotNull(template);
        assertNotNull(wordEntityRepository);
    }


}