package movie.popular.rac.popularmovie.network;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by User on 6/26/2015.
 */
public class FetchPopularMovieTask extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... params) {
        String result;
        try {
            String api = String.format(Locale.ENGLISH, ApiConstants.MOVIE_API, params[0], ApiConstants.API_KEY);
            String url = ApiConstants.BASE_URL + api;
            return HttpClient.get(url);
        }catch (IOException e){

        }
        return null;
    }
}
