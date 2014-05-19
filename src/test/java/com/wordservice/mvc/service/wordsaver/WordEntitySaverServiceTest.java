package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.repository.SentenceRepository;
import com.wordservice.mvc.repository.WordRelationshipRepository;
import com.wordservice.mvc.repository.WordRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


public class WordEntitySaverServiceTest {

    @Mock
    WordRelationshipRepository wordRelationshipRepository;

    @Mock
    WordRepository wordRepository;

    @Mock
    SentenceRepository sentenceRepository;

    @InjectMocks
    WordEntitySaverService wordEntitySaverService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveToRepo() throws Exception {
        when(wordEntitySaverService.createOrIncrementPopularityOfRelationship(any(WordEntity.class),any(WordEntity.class)))
                .thenReturn(new WordRelationship());
        wordEntitySaverService.saveToRepo("Hello World!");

        verify(wordRepository, times(2)).save(any(WordEntity.class));
        verify(wordRelationshipRepository).save(any(WordRelationship.class));
        verify(wordRelationshipRepository, atLeastOnce()).getRelationshipBetween(any(WordEntity.class), any(WordEntity.class));
    }

    @Test
    public void testSaveToRepo2() throws Exception {
        when(wordEntitySaverService.createOrIncrementPopularityOfRelationship(any(WordEntity.class),any(WordEntity.class)))
                .thenReturn(new WordRelationship());
        wordEntitySaverService.saveToRepo("Hello World! Hello Sun and Earth.");
        int numberOfWords = 6;
        int numberOfRelationsBetween2Words = 4;

        verify(wordRepository, times(numberOfWords)).save(any(WordEntity.class));
        verify(wordRelationshipRepository, times(numberOfRelationsBetween2Words)).save(any(WordRelationship.class));
        verify(wordRelationshipRepository, atLeastOnce()).getRelationshipBetween(any(WordEntity.class), any(WordEntity.class));
    }
}