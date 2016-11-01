package org.scalable.web.search;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.scalable.web.htmlreader.HTMLReader;
import org.scalable.web.htmlreader.HTMLReaderImpl;
import org.scalable.web.util.HTMLDataExtractor;
import org.scalable.web.util.LoggerFactory;
import org.scalable.web.util.URLBuilder;

/**
 * Implements {@link SearchClient} to perform search with google search.
 *
 * @author Jino George
 */
public class GoogleSearchClientImpl implements SearchClient
{
    private Logger logger = LoggerFactory.getLogger(GoogleSearchClientImpl.class);

    private static final String GOOGLE = "http://www.google.com/search";

    /**
     * {@link URLBuilder} to build the {@link URL} for search.
     */
    private URLBuilder urlBuilder;

    /**
     * {@link HTMLReader} to read the search results google page.
     */
    private HTMLReader htmlReader;

    /**
     * {@link HTMLDataExtractor} to extract the search results.
     */
    private HTMLDataExtractor dataExtractor;

    /**
     * Indicates the max number of search results.
     */
    private int numberOfResults = 50;

    /**
     * Constructor.
     */
    public GoogleSearchClientImpl()
    {
        super();
        this.urlBuilder = new URLBuilder(GOOGLE);
        this.htmlReader = new HTMLReaderImpl();
        this.dataExtractor = new HTMLDataExtractor();
    }

    /**
     * Parameterized constructor.
     *
     * @param maxNumberOfResults
     */
    public GoogleSearchClientImpl(int maxNumberOfResults)
    {
        this();
        this.numberOfResults = maxNumberOfResults;
        this.urlBuilder = new URLBuilder(GOOGLE);
        this.htmlReader = new HTMLReaderImpl();
        this.dataExtractor = new HTMLDataExtractor();
    }

    /*
     * (non-Javadoc)
     * @see org.scalable.web.search.SearchClient#search(java.lang.String)
     */
    @Override
    public List<String> search(String keyword)
    {
        logger.log(Level.INFO, "Searching in " + GOOGLE + " with keyword : " + keyword);
        // Building URL.
        urlBuilder.addParameter("num", String.valueOf(numberOfResults)).addParameter("start", "0")
                .addParameter("q", keyword).addParameter("cr", "countryDE");
        String htmlContent = null;
        // Reading HTML content.
        htmlContent = htmlReader.read(urlBuilder.build());
        if(htmlContent != null && !htmlContent.isEmpty())
        {
            // Extracting the search results.
            logger.log(Level.FINER, "HTML content of the google results page : " + htmlContent);
            return dataExtractor.getSearchResults(htmlContent);
        }
        return Collections.emptyList();
    }
}
