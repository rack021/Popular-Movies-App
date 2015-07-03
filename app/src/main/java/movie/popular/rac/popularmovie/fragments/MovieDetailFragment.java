package movie.popular.rac.popularmovie.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import movie.popular.rac.popularmovie.R;
import movie.popular.rac.popularmovie.models.PopularMovieModel;

public class MovieDetailFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View movieDetailLayout = inflater.inflate(R.layout.fragment_moviedetailview, container, false);
        Bundle bundle = getArguments();
        PopularMovieModel division= (PopularMovieModel) bundle.getSerializable("me");
        ImageView image = (ImageView) movieDetailLayout.findViewById(R.id.bigposter);
        Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w185/"+ division.poster_path)
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder)
                .into(image);
        return movieDetailLayout;
    }
}
