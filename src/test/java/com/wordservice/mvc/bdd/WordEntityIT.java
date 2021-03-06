package com.wordservice.mvc.bdd;

import com.wordservice.mvc.TestApplicationConfig;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestApplicationConfig.class})
@Transactional
public class WordEntityIT {

    @Autowired
    Neo4jTemplate template;

    @Autowired
    WordEntityRepository wordEntityRepositoryImpl;

    @Test
    @Rollback
    public void persistedMovieShouldBeRetrievableFromGraphDb() {
        WordEntity gump = new WordEntity("Gump");
        WordEntity forrest = new WordEntity("Forrest");
        WordEntity lump = new WordEntity("Lump");

        wordEntityRepositoryImpl.save(gump);
        wordEntityRepositoryImpl.save(forrest);
        wordEntityRepositoryImpl.save(lump);

        assertEquals("retrieved id matches persisted one", gump, wordEntityRepositoryImpl.findOne(gump.getId()));
        assertEquals("retrieved word matches persisted one", gump.getWord(), wordEntityRepositoryImpl.findOne(gump.getId()).getWord());
        assertEquals("retrieved id matches persisted one", forrest, wordEntityRepositoryImpl.findOne(forrest.getId()));
        assertEquals("retrieved word matches persisted one", forrest.getWord(), wordEntityRepositoryImpl.findOne(forrest.getId()).getWord());

        assertEquals(gump, wordEntityRepositoryImpl.findAllByPropertyValue("word", "Gump").single());
    }

}
