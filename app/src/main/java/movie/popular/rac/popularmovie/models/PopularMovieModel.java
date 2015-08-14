package movie.popular.rac.popularmovie.models;

import java.io.Serializable;

/**
 * Created by User on 6/26/2015.
 */

public class PopularMovieModel implements Serializable {
    public String title;
    public String poster_path;
    public String backdrop_path;
    public String original_title;
    public String release_date;
    public String overview;
    public String vote_average;

    public String getYear(){
        return release_date.split("-")[0];
    }
}
