package movie.popular.rac.popularmovie.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by User on 6/26/2015.
 */

public class PopularMovieModel implements Parcelable {
    public String title;
    public String poster_path;
    public String backdrop_path;
    public String original_title;
    public String release_date;
    public String overview;
    public String vote_average;

    protected PopularMovieModel(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        original_title = in.readString();
        release_date = in.readString();
        overview = in.readString();
        vote_average = in.readString();
    }

    public static final Creator<PopularMovieModel> CREATOR = new Creator<PopularMovieModel>() {
        @Override
        public PopularMovieModel createFromParcel(Parcel in) {
            return new PopularMovieModel(in);
        }

        @Override
        public PopularMovieModel[] newArray(int size) {
            return new PopularMovieModel[size];
        }
    };

    public String getYear(){
        return release_date.split("-")[0];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
        dest.writeString(original_title);
        dest.writeString(release_date);
        dest.writeString(overview);
        dest.writeString(vote_average);
    }
}
