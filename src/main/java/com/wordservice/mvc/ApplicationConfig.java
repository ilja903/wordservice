/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wordservice.mvc;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;
import java.io.IOException;

@Configuration
@ComponentScan
@ImportResource("classpath:META-INF/spring/spring-data-context.xml")
@EnableTransactionManagement
class ApplicationConfig {

    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
//        return new SpringRestGraphDatabase("http://localhost:7474/db/data");
//        try {
//            FileUtils.deleteRecursively(new File("real-db"));
            return new GraphDatabaseFactory().newEmbeddedDatabase("real-db");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
