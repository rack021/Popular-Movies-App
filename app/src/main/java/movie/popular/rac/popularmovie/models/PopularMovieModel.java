package movie.popular.rac.popularmovie.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by User on 6/26/2015.
 */

public class PopularMovieModel implements Parcelable {
    public long id;
    public String title;
    public String poster_path;
    public String backdrop_path;
    public String original_title;
    public String release_date;
    public String overview;
    public String vote_average;
    public ArrayList<TrailerModel> trailers;

    public PopularMovieModel(){

    }


    protected PopularMovieModel(Parcel in) {
        id = in.readLong();
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
        return (release_date == null)?"":release_date.split("-")[0];
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeString(original_title);
        parcel.writeString(release_date);
        parcel.writeString(overview);
        parcel.writeString(vote_average);
    }
}
