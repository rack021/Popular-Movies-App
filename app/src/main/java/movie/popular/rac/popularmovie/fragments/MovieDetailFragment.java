package movie.popular.rac.popularmovie.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import movie.popular.rac.popularmovie.MainActivity;
import movie.popular.rac.popularmovie.R;
import movie.popular.rac.popularmovie.adapters.MovieReviewAdapter;
import movie.popular.rac.popularmovie.adapters.TrailerPageAdapter;
import movie.popular.rac.popularmovie.data.FlavoriteMoviesContract;
import movie.popular.rac.popularmovie.models.PopularMovieModel;
import movie.popular.rac.popularmovie.models.ReviewModel;
import movie.popular.rac.popularmovie.models.TrailerModel;
import movie.popular.rac.popularmovie.network.ApiConstants;
import movie.popular.rac.popularmovie.network.HttpClient;
import movie.popular.rac.popularmovie.utilities.AppUtility;

public class MovieDetailFragment extends Fragment {
    Gson gson;
    PopularMovieModel popularMovieModel;
    ImageView trailer_btn;
    TrailerPageAdapter mTrailerPageAdapter;
    ViewPager mViewPager;
    int dotsCount;
    LinearLayout dotsLayout;
    TextView[] dots;
    ListView reviewListView;
    ArrayList<ReviewModel> reviews;
    MovieReviewAdapter movieReviewAdapter;
    MenuItem flavorite;
    MenuItem unflavorite;
    TextView noReview;
    FetchMovieTrialers movieTrialersAsync;
    FetchMovieReviewTask movieReviewTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            Bundle bundle = getArguments();
            if (bundle != null) {
                popularMovieModel = (PopularMovieModel) bundle.getParcelable(ApiConstants.MOVIE_DELTAIL_BUNDLE);
            }else {
                popularMovieModel = new PopularMovieModel();
            }


        setHasOptionsMenu(true);
        gson = new Gson();
        reviews = new ArrayList<ReviewModel>();
        movieReviewAdapter = new MovieReviewAdapter(getActivity(), reviews);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(((MainActivity)getActivity()).isTablet()){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }else {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (reviews.isEmpty()) {
            if(AppUtility.isNetworkAvailable(getActivity())){
                movieReviewTask= new FetchMovieReviewTask();
                movieReviewTask.execute(String.valueOf(popularMovieModel.id), "1");
            }
            else{
                //popularMoviesGridView.setEmptyView(emptyListMessage);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUiPageViewController();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ApiConstants.MOVIE_DELTAIL_BUNDLE, popularMovieModel);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here

        inflater.inflate(R.menu.menu_movie, menu);
        super.onCreateOptionsMenu(menu, inflater);
        flavorite = menu.findItem(R.id.flavorite);
        unflavorite = menu.findItem(R.id.unflavorite);
        Cursor cursor = getActivity().getContentResolver().query(FlavoriteMoviesContract.FlavoriteMoviesEntry.CONTENT_URI, null, "id=?", new String[]{String.valueOf(popularMovieModel.id)}, null);
        if(cursor.getCount() > 0){
            unflavorite.setVisible(false);
        }else{
            flavorite.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.unflavorite:
                markFlavorite();
                flavorite.setVisible(true);
                unflavorite.setVisible(false);
                return true;
            case R.id.flavorite:
                unMarkFlavorite();
                unflavorite.setVisible(true);flavorite.setVisible(false);
                return true;
            case R.id.share:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, popularMovieModel.title);
                i.putExtra(Intent.EXTRA_TEXT, Uri.parse("http://www.youtube.com/watch?v=" + popularMovieModel.trailers.get(0).key).toString());
                startActivity(Intent.createChooser(i, "Share URL"));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(popularMovieModel.trailers == null) {
            popularMovieModel.trailers = new ArrayList<TrailerModel>();
            movieTrialersAsync = new FetchMovieTrialers();
            movieTrialersAsync.execute(String.valueOf(popularMovieModel.id));
        }
        View movieDetailLayout = inflater.inflate(R.layout.fragment_moviedetailview, container, false);
        dotsLayout = (LinearLayout)movieDetailLayout.findViewById(R.id.viewPagerCountDots);
        mViewPager = (ViewPager)movieDetailLayout.findViewById(R.id.pager);
        mTrailerPageAdapter = new TrailerPageAdapter(getActivity(), popularMovieModel.trailers);
        mViewPager.setAdapter(mTrailerPageAdapter);
        mViewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        noReview = (TextView)movieDetailLayout.findViewById(R.id.no_review);

        reviewListView = (ListView)movieDetailLayout.findViewById(R.id.reviews_list);
        reviewListView.setAdapter(movieReviewAdapter);

        ImageView poster = (ImageView) movieDetailLayout.findViewById(R.id.poster);
        TextView movie_name = (TextView) movieDetailLayout.findViewById(R.id.moviename);
        TextView year = (TextView) movieDetailLayout.findViewById(R.id.year);
        TextView description = (TextView) movieDetailLayout.findViewById(R.id.description);
        TextView rating = (TextView) movieDetailLayout.findViewById(R.id.rating);
        Picasso.with(getActivity().getApplicationContext()).load(getActivity().getString(R.string.porter_base_url) + popularMovieModel.poster_path)
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder)
                .into(poster);
        movie_name.setText(popularMovieModel.original_title);
        year.setText(popularMovieModel.getYear());
        description.setText(popularMovieModel.overview);
        rating.setText(String.format(Locale.ENGLISH, getActivity().getString(R.string.rating), popularMovieModel.vote_average));
        return movieDetailLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(movieTrialersAsync != null)movieTrialersAsync.cancel(true);
        if(movieReviewTask != null)movieReviewTask.cancel(true);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    private void setUiPageViewController() {
        if(dotsCount == 0) {
            dotsCount = mTrailerPageAdapter.getCount();
            dots = new TextView[dotsCount];
            if (dotsCount > 0) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i] = new TextView(getActivity());
                    dots[i].setText(Html.fromHtml("&#8226;"));
                    dots[i].setTextSize(30);
                    dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
                    dotsLayout.addView(dots[i]);
                }

                dots[0].setTextColor(getResources().getColor(R.color.primarydark));
            }
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if(dots != null) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                dots[position].setTextColor(getResources().getColor(R.color.primarydark));
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    private class FetchMovieTrialers extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String api;
            try {
                api = String.format(Locale.ENGLISH, ApiConstants.TRAILER_API, strings[0], ApiConstants.API_KEY);
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
                List<TrailerModel> popularMovieList_temp = gson.fromJson(o.get("results"), new TypeToken<List<TrailerModel>>() {
                }.getType());
                if(popularMovieList_temp != null){
                    popularMovieModel.trailers.addAll(popularMovieList_temp);
                    mTrailerPageAdapter.notifyDataSetChanged();
                }

                setUiPageViewController();
                Log.d("","");
            }else{
                //popularMoviesGridView.setEmptyView(emptyListMessage);
            }
        }
    }

    public void markFlavorite(){
        ContentValues flavoriteValue = new ContentValues();
        flavoriteValue.put(FlavoriteMoviesContract.FlavoriteMoviesEntry.COLUMN_MOVIE_ID, popularMovieModel.id);
        flavoriteValue.put(FlavoriteMoviesContract.FlavoriteMoviesEntry.COLUMN_TITLE, popularMovieModel.original_title);
        flavoriteValue.put(FlavoriteMoviesContract.FlavoriteMoviesEntry.COLUMN_OVERVIEW, popularMovieModel.overview);
        flavoriteValue.put(FlavoriteMoviesContract.FlavoriteMoviesEntry.COLUMN_RELEASE_DATE, popularMovieModel.release_date);
        flavoriteValue.put(FlavoriteMoviesContract.FlavoriteMoviesEntry.COLUMN_POSTER, popularMovieModel.poster_path);
        flavoriteValue.put(FlavoriteMoviesContract.FlavoriteMoviesEntry.COLUMN_BACKDROP_POSTER, popularMovieModel.backdrop_path);
        flavoriteValue.put(FlavoriteMoviesContract.FlavoriteMoviesEntry.COLUMN_VOTE, popularMovieModel.vote_average);
        // bulkInsert our ContentValues array
        getActivity().getContentResolver().insert(FlavoriteMoviesContract.FlavoriteMoviesEntry.CONTENT_URI, flavoriteValue);
    }
    public void unMarkFlavorite(){
        getActivity().getContentResolver().delete(FlavoriteMoviesContract.FlavoriteMoviesEntry.CONTENT_URI, "id=?", new String[]{String.valueOf(popularMovieModel.id)});
    }

    private class FetchMovieReviewTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String api;
            try {
                api = String.format(Locale.ENGLISH, ApiConstants.REVIEW_API, params[0], params[1], ApiConstants.API_KEY);
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
                ArrayList<ReviewModel> popularMovieList_temp = gson.fromJson(o.get("results"), new TypeToken<List<ReviewModel>>() {
                }.getType());
                if(popularMovieList_temp != null && popularMovieList_temp.size() > 0){
                    reviews.addAll(popularMovieList_temp);
                    movieReviewAdapter.notifyDataSetChanged();
                }else {
                    noReview.setVisibility(View.VISIBLE);
                    reviewListView.setEmptyView(noReview);
                }

            }else{
                noReview.setVisibility(View.VISIBLE);
                reviewListView.setEmptyView(noReview);
            }
        }
    }
}
