package org.scalable.web.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.scalable.web.htmlreader.HTMLReader;
import org.scalable.web.htmlreader.HTMLReaderImpl;

/**
 * A utility class to attribute values in elements from the html content using
 * regular expressions.
 *
 * @author Jino George
 */
public class HTMLDataExtractor
{
    private Logger logger = LoggerFactory.getLogger(HTMLDataExtractor.class);

    /**
     * Holds the {@link HTMLReader} for crawler.
     */
    private HTMLReader htmlReader = new HTMLReaderImpl();

    /**
     * Regex to find the &lt;a&gt; tag.
     */
    private static final Pattern PATTERN_A_TAG = Pattern
            .compile("(?i)<li class=\"g\"><h3 class=\"r\"><a([^>]+)>(.+?)</a>");

    /**
     * Regex to find the 'href' attribute.
     */
    private static final Pattern PATTERN_A_LINK = Pattern
            .compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");

    /**
     * Regex to find the &lt;script&gt; tag.
     */
    private static final Pattern PATTERN_JAVASCRIPT_TAG = Pattern
            .compile("(?i)<script([^>]+)>(.+?)</script>");

    /**
     * Regex to find the 'src' attribute.
     */
    private static final Pattern PATTERN_JAVASCRIPT_SRC = Pattern
            .compile("\\s*(?i)src\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");

    /**
     * Extracts the result urls from the google search results page.
     *
     * @param html
     * @return list of result urls.
     */
    public List<String> getSearchResults(final String html)
    {
        logger.log(Level.INFO, "Extracting search result URLs.");
        List<String> resultUrls = extract(html, PATTERN_A_TAG, PATTERN_A_LINK).parallelStream()
                .filter(url -> url.contains("/url?q="))
                .map(url -> url.substring(url.indexOf("/url?q=") + 7, url.indexOf("&amp;sa=")))
                .collect(Collectors.toList());
        logger.log(Level.INFO, "Extracted " + resultUrls.size() + " URLs from search result.");
        return resultUrls;
    }

    /**
     * Extracts the JavaScript library names from the &lt;script&gt; tags.
     *
     * @param url
     * @return list of JS library names.
     */
    public List<String> getJSLibraries(URL url)
    {
        return getJSLibraries(htmlReader.read(url));
    }

    /**
     * Extracts the JavaScript library names from the &lt;script&gt; tags.
     *
     * @param html
     * @return list of JS library names.
     */
    public List<String> getJSLibraries(final String html)
    {
        logger.log(Level.FINEST, "Extracting JavaScript library names from : " + html);
        List<String> jsLibs = extract(html, PATTERN_JAVASCRIPT_TAG, PATTERN_JAVASCRIPT_SRC)
                .parallelStream()
                .filter(lib -> lib.contains(".js"))
                .map(lib -> lib.replaceAll("'", "").replaceAll("\"", "")
                        .replaceFirst(".*/([^/?]+).*", "$1")).collect(Collectors.toList());
        return jsLibs;
    }

    /**
     * Extracts the list of values from the specified attribute in the specified
     * tag.
     *
     * @param html
     * @param tagPattern
     * @param attributePattern
     * @return list of attribute values.
     */
    private List<String> extract(final String html, Pattern tagPattern, Pattern attributePattern)
    {
        List<String> results = new ArrayList<String>();
        Matcher tagMatcher, attrMatcher;
        tagMatcher = tagPattern.matcher(html);
        // Finding matching tags.
        while(tagMatcher.find())
        {
            attrMatcher = attributePattern.matcher(tagMatcher.group(1));
            // Finding matching attributes.
            while(attrMatcher.find())
            {
                results.add((attrMatcher.group(1)));
            }
        }
        return results;
    }
}
