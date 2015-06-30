package movie.popular.rac.popularmovie.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.AutoTransition;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

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
        popularMoviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setExitTransition(new Fade());
                TextView image = (TextView) view.findViewById(R.id.movie_name);
                Log.d("movie name", image.getText().toString());
                View view1 = view.findViewById(R.id.poster);
                view1.setTransitionName("view.findViewById(R.id.poster)");
                MovieDetailFragment movieDetailFragment =  new MovieDetailFragment();

                Bundle bundles = new Bundle();
                bundles.putSerializable("me", popularMovieList.get(position));
                movieDetailFragment.setArguments(bundles);
                movieDetailFragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.move));
                movieDetailFragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.move));
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, movieDetailFragment)
                        .addToBackStack("transaction")
                        .addSharedElement(view1, "picture")
                        .commit();
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
