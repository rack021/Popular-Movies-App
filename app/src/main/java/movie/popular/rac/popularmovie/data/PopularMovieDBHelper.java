package movie.popular.rac.popularmovie.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 9/5/2015.
 */
public class PopularMovieDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = PopularMovieDBHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "popularmovie.db";
    private static final int DATABASE_VERSION = 1;

    public PopularMovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                FlavoriteMoviesContract.FlavoriteMoviesEntry.TABLE_FAVORITE_MOVIE + "(" + FlavoriteMoviesContract.FlavoriteMoviesEntry._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FlavoriteMoviesContract.FlavoriteMoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                FlavoriteMoviesContract.FlavoriteMoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FlavoriteMoviesContract.FlavoriteMoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                FlavoriteMoviesContract.FlavoriteMoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                FlavoriteMoviesContract.FlavoriteMoviesEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
                FlavoriteMoviesContract.FlavoriteMoviesEntry.COLUMN_BACKDROP_POSTER + " TEXT NOT NULL, " +
                FlavoriteMoviesContract.FlavoriteMoviesEntry.COLUMN_VOTE +
                " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
                newVersion + ". OLD DATA WILL BE DESTROYED");
        // Drop the table
        db.execSQL("DROP TABLE IF EXISTS " + FlavoriteMoviesContract.FlavoriteMoviesEntry.TABLE_FAVORITE_MOVIE);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                FlavoriteMoviesContract.FlavoriteMoviesEntry.TABLE_FAVORITE_MOVIE + "'");

        // re-create database
        onCreate(db);
    }
}
