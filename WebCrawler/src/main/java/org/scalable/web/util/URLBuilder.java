package org.scalable.web.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to build the resource URL with host address and attached
 * query-string.
 *
 * @author Jino George
 */
public class URLBuilder
{
    private Logger logger = LoggerFactory.getLogger(URLBuilder.class);

    /**
     * Holds the list of parameters as key-value pairs.
     */
    private Map<String, String> parameters = new HashMap<String, String>();

    /**
     * Holds the base URL (host address) for the resource.
     */
    private StringBuilder hostAddress;

    /**
     * Constructor.
     *
     * @param hostAddress
     */
    public URLBuilder(String hostAddress)
    {
        this.hostAddress = new StringBuilder(hostAddress);
    }

    /**
     * Adds a parameter to include in the URL.
     *
     * @param key
     * @param value
     * @return {@link URLBuilder}
     */
    public URLBuilder addParameter(String key, String value)
    {
        parameters.put(key, value);
        return this;
    }

    /**
     * Appends path to the base URL (host address).
     *
     * @param path
     * @return {@link URLBuilder}
     */
    public URLBuilder appendPath(String path)
    {
        hostAddress.append(path);
        return this;
    }

    /**
     * Builds the final {@link URL} with all the parameters and the host
     * address.
     *
     * @return {@link URL}.
     */
    public URL build()
    {
        logger.log(Level.FINER, "Building URL for " + hostAddress);
        StringBuilder queryString = new StringBuilder("?");
        for(Map.Entry<?, ?> entry : parameters.entrySet())
        {
            if(queryString.length() > 1)
            {
                // Appends query string delimiters.
                queryString.append("&");
            }
            // Appends query strings.
            queryString.append(String.format("%s=%s", encodeUTF8(entry.getKey().toString()),
                    encodeUTF8(entry.getValue().toString())));
        }
        if(queryString.length() > 1)
        {
            hostAddress.append(queryString);
        }
        try
        {
            return new URL(hostAddress.toString());
        }
        catch(MalformedURLException e)
        {
            logger.log(
                    Level.FINER,
                    "Could not build URL for " + hostAddress + "due to : "
                            + e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Performs UTF-8 encoding on the parameters.
     *
     * @param parameter
     * @return UTF-8 encoded parameter.
     */
    private String encodeUTF8(String parameter)
    {
        if(parameter != null && !parameter.isEmpty())
        {
            try
            {
                return URLEncoder.encode(parameter, "UTF-8");
            }
            catch(UnsupportedEncodingException e)
            {
                logger.log(Level.FINER,
                        "Could not encode parameter " + parameter + "due to : " + e.getMessage());
            }
        }
        return parameter;
    }
}