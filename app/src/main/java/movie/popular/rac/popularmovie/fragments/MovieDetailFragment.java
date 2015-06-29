package movie.popular.rac.popularmovie.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import movie.popular.rac.popularmovie.R;

/**
 * Created by User on 6/29/2015.
 */
public class MovieDetailFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View movieDetailLayout = inflater.inflate(R.layout.fragment_moviedetailview, container, false);
        return movieDetailLayout;
    }
}
