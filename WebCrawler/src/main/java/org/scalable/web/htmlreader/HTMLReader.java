package org.scalable.web.htmlreader;

import java.net.URL;

/**
 * Interface to define the basic operations to be implemented for reading HTML
 * content from a web page located with URL.
 *
 * @author Jino George
 */
public interface HTMLReader
{
    /**
     * Reads from the resource located with the {@link URL} and extract the
     * contents as a {@link String}.
     *
     * @param url
     * @return HTML content.
     */
    public String read(URL url);
}
