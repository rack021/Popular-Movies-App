package movie.popular.rac.popularmovie.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

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
    ArrayList<PopularMovieModel> popularMovieList;
    String defaultPage = "1";
    String fliter = ApiConstants.SORT_POPULARITY;
    ImageView emptyListMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null || !savedInstanceState.containsKey(getString(R.string.MOVIE_KEY))){
            popularMovieList = new ArrayList<PopularMovieModel>();
        }
        else{
            popularMovieList = savedInstanceState.getParcelableArrayList(getString(R.string.MOVIE_KEY));
            fliter = savedInstanceState.getString(getString(R.string.FLITER), ApiConstants.SORT_POPULARITY);
        }
        setHasOptionsMenu(true);
        gson = new Gson();
        listOfPopularMovieModel = new TypeToken<List<PopularMovieModel>>() {
        }.getType();
        popularMoviesAdapter = new PopularMoviesAdapter(getActivity().getApplicationContext(), popularMovieList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getString(R.string.MOVIE_KEY), popularMovieList);
        outState.putString(getString(R.string.FLITER), fliter);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View popularMovieLayout = inflater.inflate(R.layout.fragment_popularmovies, container, false);
        emptyListMessage = (ImageView) popularMovieLayout.findViewById(R.id.no_internet);
        popularMoviesGridView = (GridView) popularMovieLayout.findViewById(R.id.popularmovies_gridview);
        popularMoviesGridView.setAdapter(popularMoviesAdapter);
        popularMoviesGridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if(isNetworkAvailable()){
                    new FetchPopularMovieTask().execute(fliter, String.valueOf(page));
                }
            }
        });
        popularMoviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setExitTransition(new Fade());
                MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                Bundle bundles = new Bundle();
                bundles.putParcelable(ApiConstants.MOVIE_DELTAIL_BUNDLE, popularMovieList.get(position));
                movieDetailFragment.setArguments(bundles);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, movieDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return popularMovieLayout;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (popularMovieList.isEmpty()) {
            if(isNetworkAvailable()){
                new FetchPopularMovieTask().execute(fliter, defaultPage);
            }
            else{
                popularMoviesGridView.setEmptyView(emptyListMessage);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_popularty:
                fliter = ApiConstants.SORT_POPULARITY;
                break;
            case R.id.action_rating:
                fliter = ApiConstants.SORT_RATING;
                break;
        }
        popularMovieList.clear();
        if(isNetworkAvailable()){
            new FetchPopularMovieTask().execute(fliter, defaultPage);
        }
        else{
            popularMoviesGridView.setEmptyView(emptyListMessage);
        }
        popularMoviesAdapter.notifyDataSetChanged();

        return super.onOptionsItemSelected(item);
    }

    private String getData(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
                Log.e(ApiConstants.TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                JsonObject o = new JsonParser().parse(s).getAsJsonObject();
                List<PopularMovieModel> popularMovieList_temp = gson.fromJson(o.get("results"), listOfPopularMovieModel);
                popularMovieList.addAll(popularMovieList_temp);
                popularMoviesAdapter.notifyDataSetChanged();
            }else{
                popularMoviesGridView.setEmptyView(emptyListMessage);
            }
        }
    }
}
