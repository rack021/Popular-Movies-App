package movie.popular.rac.popularmovie.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by User on 9/5/2015.
 */
public class FavoriteMoviesContract {
    public static final String CONTENT_AUTHORITY = "movie.popular.rac.popularmovie.data";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class FlavoriteMoviesEntry implements BaseColumns {
        public static final String TABLE_FAVORITE_MOVIE = "flavorite";
        // columns
        public static final String _ID = "_id";
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_TITLE= "original_title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POSTER = "poster_path";
        public static final String COLUMN_BACKDROP_POSTER = "backdrop_path";
        public static final String COLUMN_VOTE = "vote_average";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_FAVORITE_MOVIE).build();
        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_FAVORITE_MOVIE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_FAVORITE_MOVIE;

        public static Uri buildFavoriteUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
