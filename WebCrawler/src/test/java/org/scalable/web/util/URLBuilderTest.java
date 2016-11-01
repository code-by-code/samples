package org.scalable.web.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link URLBuilder}.
 *
 * @author Jino George
 */
public class URLBuilderTest
{
    private URLBuilder urlBuilder;

    @Before
    public void setUp()
    {
        urlBuilder = new URLBuilder("http://www.google.com");
    }

    /**
     * Test method for
     * {@link org.scalable.web.util.URLBuilder#addParameter(java.lang.String, java.lang.String)}
     * Tests if the query string is constructed correctly.
     */
    @Test
    public final void testAddParameter()
    {
        urlBuilder.addParameter("q", "query");
        urlBuilder.addParameter("cr", "countryDE");
        assertEquals("http://www.google.com?q=query&cr=countryDE", urlBuilder.build().toString());
    }

    /**
     * Test method for {@link org.scalable.web.util.URLBuilder#build()}. Tests
     * if the URL is correctly generated.
     */
    @Test
    public final void testBuild()
    {
        assertEquals("http://www.google.com", urlBuilder.build().toString());
    }

    /**
     * Test method for {@link org.scalable.web.util.URLBuilder#build()}. Tests
     * if the URL is not null.
     */
    @Test
    public final void testBuildNotNull()
    {
        assertNotNull(urlBuilder.build());
    }
}
