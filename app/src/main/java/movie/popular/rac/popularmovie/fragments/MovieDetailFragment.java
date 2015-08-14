package movie.popular.rac.popularmovie.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import movie.popular.rac.popularmovie.R;
import movie.popular.rac.popularmovie.models.PopularMovieModel;
import movie.popular.rac.popularmovie.network.ApiConstants;

public class MovieDetailFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View movieDetailLayout = inflater.inflate(R.layout.fragment_moviedetailview, container, false);
        Bundle bundle = getArguments();
        PopularMovieModel popularMovieModel = (PopularMovieModel) bundle.getSerializable(ApiConstants.MOVIE_DELTAIL_BUNDLE);
        ImageView banner = (ImageView) movieDetailLayout.findViewById(R.id.bigposter);
        ImageView poster = (ImageView) movieDetailLayout.findViewById(R.id.poster);
        TextView movie_name = (TextView) movieDetailLayout.findViewById(R.id.moviename);
        TextView year = (TextView) movieDetailLayout.findViewById(R.id.year);
        TextView description = (TextView) movieDetailLayout.findViewById(R.id.description);
        TextView rating = (TextView) movieDetailLayout.findViewById(R.id.rating);
        Picasso.with(getActivity().getApplicationContext()).load(getActivity().getString(R.string.banner_base_url) + popularMovieModel.backdrop_path)
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder)
                .into(banner);
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
}
