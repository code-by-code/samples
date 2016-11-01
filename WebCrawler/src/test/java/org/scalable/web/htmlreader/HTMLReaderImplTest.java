package org.scalable.web.htmlreader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link HTMLReaderImpl}.
 *
 * @author Jino George
 */
public class HTMLReaderImplTest
{
    private HTMLReaderImpl reader;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        reader = new HTMLReaderImpl();
    }

    /**
     * Test method for
     * {@link org.scalable.web.htmlreader.HTMLReaderImpl#read(java.net.URL)}.
     *
     * @throws MalformedURLException
     */
    @Test
    public final void testRead() throws MalformedURLException
    {
        URL url = new URL("http://www.google.com");
        assertNotNull(reader.read(url));
    }

    /**
     * Test method for
     * {@link org.scalable.web.htmlreader.HTMLReaderImpl#read(java.net.URL)}.
     */
    @Test
    public final void testReadEmpty() throws MalformedURLException
    {
        assertEquals("", reader.read(null));
    }
}
