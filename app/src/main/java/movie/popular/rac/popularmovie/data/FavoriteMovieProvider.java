package movie.popular.rac.popularmovie.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by rac on 11/6/15.
 */
public class FavoriteMovieProvider extends ContentProvider {

    private static final String LOG_TAG = FavoriteMovieProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private PopularMovieDBHelper mOpenHelper;
    private static final int FLAVOR = 100;
    private static final int FLAVOR_WITH_ID = 200;


    private static UriMatcher buildUriMatcher(){
        // Build a UriMatcher by adding a specific code to return based on a match
        // It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavoriteMoviesContract.CONTENT_AUTHORITY;

        // add a code for each type of URI you want
        matcher.addURI(authority, FavoriteMoviesContract.FlavoriteMoviesEntry.TABLE_FAVORITE_MOVIE, FLAVOR);
        matcher.addURI(authority, FavoriteMoviesContract.FlavoriteMoviesEntry.TABLE_FAVORITE_MOVIE + "/#", FLAVOR_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new PopularMovieDBHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch(sUriMatcher.match(uri)){
            // All Flavors selected
            case FLAVOR:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        FavoriteMoviesContract.FlavoriteMoviesEntry.TABLE_FAVORITE_MOVIE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            // Individual flavor based on Id selected
            case FLAVOR_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        FavoriteMoviesContract.FlavoriteMoviesEntry.TABLE_FAVORITE_MOVIE,
                        projection,
                        FavoriteMoviesContract.FlavoriteMoviesEntry._ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            default:{
                // By default, we assume a bad URI
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match){
            case FLAVOR:{
                return FavoriteMoviesContract.FlavoriteMoviesEntry.CONTENT_DIR_TYPE;
            }
            case FLAVOR_WITH_ID:{
                return FavoriteMoviesContract.FlavoriteMoviesEntry.CONTENT_ITEM_TYPE;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case FLAVOR: {
                long _id = db.insert(FavoriteMoviesContract.FlavoriteMoviesEntry.TABLE_FAVORITE_MOVIE, null, contentValues);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = FavoriteMoviesContract.FlavoriteMoviesEntry.buildFavoriteUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch(match){
            case FLAVOR:
                numDeleted = db.delete(
                        FavoriteMoviesContract.FlavoriteMoviesEntry.TABLE_FAVORITE_MOVIE, selection, selectionArgs);
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        FavoriteMoviesContract.FlavoriteMoviesEntry.TABLE_FAVORITE_MOVIE + "'");
                break;
            case FLAVOR_WITH_ID:
                numDeleted = db.delete(FavoriteMoviesContract.FlavoriteMoviesEntry.TABLE_FAVORITE_MOVIE,
                        FavoriteMoviesContract.FlavoriteMoviesEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        FavoriteMoviesContract.FlavoriteMoviesEntry.TABLE_FAVORITE_MOVIE+ "'");

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
