package movie.popular.rac.popularmovie.network;

/**
 * Created by User on 6/26/2015.
 */
public class ApiConstants {
    public static final String TAG = "Api_call";
    public static final String MOVIE_DELTAIL_BUNDLE = "MOVIE_DELTAIL_BUNDLE";
    public static final String EMPTY_STRING = "";
    public static final String FLITER_KEY = "fliter_key";
    public static final String BASE_URL = "http://api.themoviedb.org";
    public static final String API_KEY = "86cc826b26ae45c20cc7067e602f0f0f";
    public static final String MOVIE_API = "/3/discover/movie?sort_by=%s&page=%s&api_key=%s";
    public static final String SORT_POPULARITY = "popularity.desc";
    public static final String SORT_RATING = "vote_average.desc";
    public static final int HTTP_CONNECT_TIMEOUT =  6000; // milliseconds
    public static final int HTTP_READ_TIMEOUT = 10000; // milliseconds
    public static String RESPONSE_VALUE_SUCCESS = "success";
    public static String PARAM_VALUE_OFF = "0";
    public static String PARAM_VALUE_ON = "1";
}
