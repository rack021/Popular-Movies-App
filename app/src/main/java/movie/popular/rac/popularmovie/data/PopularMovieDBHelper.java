package movie.popular.rac.popularmovie.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
