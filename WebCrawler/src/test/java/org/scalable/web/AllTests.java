package org.scalable.web;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.scalable.web.htmlreader.HTMLReaderImplTest;
import org.scalable.web.search.GoogleSearchClientImplTest;
import org.scalable.web.util.HTMLDataExtractorTest;
import org.scalable.web.util.URLBuilderTest;

/**
 * Test suit to invoke all test cases.
 *
 * @author Jino George
 */
@RunWith(Suite.class)
@SuiteClasses({HTMLReaderImplTest.class, GoogleSearchClientImplTest.class,
        HTMLDataExtractorTest.class, URLBuilderTest.class})
public class AllTests
{
}
