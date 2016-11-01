package org.scalable.web.search;

import java.net.URL;
import java.util.List;

/**
 * Interface to define the basic operations in a {@link SearchClient} which can
 * be used to perform web serach with a keyword.
 *
 * @author Jino George
 */
public interface SearchClient
{
    /**
     * Performs the search operations with the given 'keyword'.
     *
     * @param keyword
     * @return List of search result {@link URL}
     */
    public List<String> search(String keyword);
}
