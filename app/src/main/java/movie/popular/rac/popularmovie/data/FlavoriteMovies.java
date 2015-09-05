package movie.popular.rac.popularmovie.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by User on 9/5/2015.
 */
public class FlavoriteMovies {
    public static final String CONTENT_AUTHORITY = "movie.popular.rac.popularmovie.data";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final class FlavoriteMoviesEntry implements BaseColumns {
        public static final String TABLE_FLAVORS = "flavorite";
        // columns
        public static final String _ID = "_id";
        public static final String COLUMN_NAME= "original_title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_VERSION_NAME = "version_name";

    }
}
