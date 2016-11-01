package org.scalable.web.search;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link GoogleSearchClientImpl}.
 *
 * @author Jino George
 */
public class GoogleSearchClientImplTest
{
    private SearchClient googleSearchClient;

    @Before
    public void setUp() throws Exception
    {
        googleSearchClient = new GoogleSearchClientImpl(1);
    }

    /**
     * Test method for {@link GoogleSearchClientImpl#search(String)}. Tests if
     * the result URLs are valid.
     */
    @Test
    public final void testSearch()
    {
        googleSearchClient.search("google").forEach(url -> {
            try
            {
                new URL(url);
            }
            catch(Exception e)
            {
                fail();
            }
        });
    }

    /**
     * Test method for {@link GoogleSearchClientImpl#search(String)}. Tests if
     * the result is not null.
     */
    @Test
    public final void testSearchNotNull()
    {
        assertNotNull(googleSearchClient.search("test"));
    }
}
