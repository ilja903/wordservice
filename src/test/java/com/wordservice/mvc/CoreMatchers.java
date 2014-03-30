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

import com.wordservice.mvc.model.Product;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

public class CoreMatchers {

	public static <T> Matcher<T> with(Matcher<T> matcher) {
		return matcher;
	}

	public static Matcher<Product> named(String name) {
		return hasProperty("name", is(name));
	}
}
