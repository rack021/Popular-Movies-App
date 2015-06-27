package movie.popular.rac.popularmovie.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import movie.popular.rac.popularmovie.R;
import movie.popular.rac.popularmovie.adapters.PopularMoviesAdapter;
import movie.popular.rac.popularmovie.listioners.EndlessScrollListener;
import movie.popular.rac.popularmovie.models.PopularMovieModel;
import movie.popular.rac.popularmovie.network.ApiConstants;
import movie.popular.rac.popularmovie.network.HttpClient;

/**
 * Created by User on 6/25/2015.
 */
public class PopularMoviesFragment extends Fragment {
    GridView popularMoviesGridView;
    Gson gson;
    PopularMoviesAdapter popularMoviesAdapter;
    Type listOfPopularMovieModel;
    List<PopularMovieModel> popularMovieList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        listOfPopularMovieModel = new TypeToken<List<PopularMovieModel>>() {
        }.getType();
        popularMovieList = new ArrayList<PopularMovieModel>();
        popularMoviesAdapter = new PopularMoviesAdapter(getActivity().getApplicationContext(), popularMovieList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View popularMovieLayout = inflater.inflate(R.layout.fragment_popularmovies, container, false);

        popularMoviesGridView = (GridView) popularMovieLayout.findViewById(R.id.popularmovies_gridview);
        popularMoviesGridView.setAdapter(popularMoviesAdapter);
        popularMoviesGridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                new FetchPopularMovieTask().execute(ApiConstants.SORT_POPULARITY, String.valueOf(page));
            }
        });
        return popularMovieLayout;
    }

    @Override
    public void onStart() {
        super.onStart();
        new FetchPopularMovieTask().execute(ApiConstants.SORT_POPULARITY, "1");
    }

    private String getData(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private class FetchPopularMovieTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String api;
            try {
                api = String.format(Locale.ENGLISH, ApiConstants.MOVIE_API, params[0], params[1],
                        ApiConstants.API_KEY);
                String url = ApiConstants.BASE_URL + api;
                return HttpClient.get(url);
            } catch (IOException e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JsonObject o = new JsonParser().parse(s).getAsJsonObject();
            List<PopularMovieModel> popularMovieList_temp = gson.fromJson(o.get("results"), listOfPopularMovieModel);
            popularMovieList.addAll(popularMovieList_temp);
            popularMoviesAdapter.notifyDataSetChanged();
        }
    }
}
