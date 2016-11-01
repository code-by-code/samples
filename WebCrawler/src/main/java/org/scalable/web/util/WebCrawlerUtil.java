package org.scalable.web.util;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.scalable.web.search.GoogleSearchClientImpl;
import org.scalable.web.search.SearchClient;

/**
 * This class is used to run this utility.
 *
 * @author Jino George
 */
public class WebCrawlerUtil
{
    /**
     * Search client to be used to perform search operation.
     */
    private SearchClient searchClient = new GoogleSearchClientImpl(50);

    /**
     * Constructor.
     */
    public WebCrawlerUtil()
    {
        super();
        // sets the logging level. Logging level can be changed to get more
        // details of the processing.
        LoggerFactory.setConsoleLoggingLevel(Level.INFO);
    }

    /**
     * @purpose To accept a search keyword from the standard input and list out
     *          the JS libraries used in the resulting web pages from google
     *          search with the entered keyword.
     * @precondition Internet should be accessible.
     * @postcondition Prints the top most used JavaScript library file names and
     *                their frequencies from the search results in the standard
     *                output.
     * @param args
     */
    public static void main(String[] args)
    {
        Logger logger = LoggerFactory.getLogger(WebCrawlerUtil.class);
        // Reading search keyword from console.
        System.out.println("Enter the search keyword: ");
        Scanner scanIn = new Scanner(System.in);
        String keyword = scanIn.nextLine();
        scanIn.close();
        // Initiating search for JS libraries.
        System.out
                .println("Finding top 5 most used JavaScript libraries and their frequncies (based on the search results)...");
        Instant start = Instant.now();
        new WebCrawlerUtil().crawlForJSLibraries(keyword).entrySet().stream()
                .sorted(Map.Entry.<String, Long> comparingByValue().reversed()).limit(5)
                .forEach(entry -> System.out.println(entry.getKey() + ", " + entry.getValue()));
        Instant end = Instant.now();
        logger.log(Level.INFO, "Completed in " + Duration.between(start, end).getSeconds()
                + " seconds.");
    }

    /**
     * This method crawls the google search resulting pages for JavaScript
     * library file names and their frequencies.
     *
     * @param keyword
     */
    public Map<String, Long> crawlForJSLibraries(String keyword)
    {
        return searchClient.search(keyword).parallelStream()
                .map(result -> new URLBuilder(result).build())
                .flatMap(html -> new HTMLDataExtractor().getJSLibraries(html).parallelStream())
                .collect(Collectors.groupingBy(lib -> lib, Collectors.counting()));
    }
}
