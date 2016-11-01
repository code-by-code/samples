package org.scalable.web.htmlreader;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.scalable.web.util.LoggerFactory;

/**
 * {@link HTMLReader} implementation with core java for reading html contents
 * from a web page.
 *
 * @author Jino George
 */
public class HTMLReaderImpl implements HTMLReader
{
    private Logger logger = LoggerFactory.getLogger(HTMLReaderImpl.class);

    /**
     * Constructor.
     */
    public HTMLReaderImpl()
    {
        super();
    }

    /*
     * (non-Javadoc)
     * @see org.scalable.web.htmlreader.HTMLReader#read(java.net.URL)
     */
    @Override
    public String read(URL url)
    {
        StringBuilder builder = new StringBuilder();
        if(url != null)
        {
            try
            {
                // Opening connection.
                URLConnection connection = url.openConnection();
                connection.setRequestProperty("User-Agent", "CrawlerBot 1.0");
                // Reading the HTML content.
                logger.log(Level.INFO, "Reading HTML from " + url.toString());
                Scanner scanIn = new Scanner(connection.getInputStream());
                while(scanIn.hasNextLine())
                {
                    builder.append(scanIn.nextLine());
                }
                scanIn.close();
            }
            catch(IOException e)
            {
                logger.log(Level.WARNING, "Could not read from " + url.toString() + " due to : "
                        + e.getLocalizedMessage());
            }
            logger.log(Level.FINEST, "HTML content of " + url.toString() + " : " + builder);
        }
        return builder.toString();
    }
}
