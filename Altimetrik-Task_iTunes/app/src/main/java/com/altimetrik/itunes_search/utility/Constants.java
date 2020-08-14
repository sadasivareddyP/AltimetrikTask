package com.altimetrik.itunes_search.utility;

public class Constants {

    /**
     * Base URL of the iTunes Search API
     */
    public static String BASE_URL = "https://itunes.apple.com";

    /**
     * Default value of country code. This value is based on this code challenge instruction to load data from this URL: https://itunes.apple.com/search?term=star&amp;country=au&amp;media=movie&amp;all
     */
    public static String DEFAULT_COUNTRY_CODE = "au";

    /**
     * Default value of term to search. This value is based on this code challenge instruction to load data from this URL: https://itunes.apple.com/search?term=star&amp;country=au&amp;media=movie&amp;all
     */
    public static String DEFAULT_TERM = "search";

    /**
     * Default value of media position. This value is based on this code challenge instruction to load data from this URL: https://itunes.apple.com/search?term=star&amp;country=au&amp;media=movie&amp;all
     */
    public static int DEFAULT_MEDIA_POSITION = 1;

}
