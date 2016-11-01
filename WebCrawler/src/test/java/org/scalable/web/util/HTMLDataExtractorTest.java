package org.scalable.web.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.scalable.web.htmlreader.HTMLReaderImpl;

/**
 * Test cases for {@link HTMLDataExtractor}.
 *
 * @author Jino George
 */
public class HTMLDataExtractorTest
{
    private HTMLDataExtractor htmlDataExtractor;

    private static String htmlContent;

    @BeforeClass
    public static void init() throws Exception
    {
        htmlContent = new HTMLReaderImpl().read(new URL("https://www.facebook.com/"));
    }

    @Before
    public void setUp()
    {
        htmlDataExtractor = new HTMLDataExtractor();
    }

    /**
     * Test method for
     * {@link org.scalable.web.util.HTMLDataExtractor#getJSLibraries(java.lang.String)}
     * Tests if the results are JavaScript files.
     */
    @Test
    public final void testGetJSLibraries()
    {
        assertTrue(htmlDataExtractor.getJSLibraries(htmlContent).get(0).endsWith(".js"));
    }

    /**
     * Test method for
     * {@link org.scalable.web.util.HTMLDataExtractor#getJSLibraries(java.lang.String)}
     * Tests if the results are not null.
     */
    @Test
    public final void testGetJSLibrariesNotNull()
    {
        assertNotNull(htmlDataExtractor.getJSLibraries(htmlContent));
    }
}
