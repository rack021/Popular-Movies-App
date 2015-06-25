package movie.popular.rac.popularmovie.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import movie.popular.rac.popularmovie.R;

/**
 * Created by User on 6/25/2015.
 */
public class PopularMoviesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_popularmovies, container, false);
    }
}
