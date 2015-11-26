package movie.popular.rac.popularmovie.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rac on 10/5/15.
 */
public class ReviewModel implements Parcelable{
    public String id;
    public String author;
    public String content;
    public String url;

    protected ReviewModel(Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
        url = in.readString();
    }

    public static final Creator<ReviewModel> CREATOR = new Creator<ReviewModel>() {
        @Override
        public ReviewModel createFromParcel(Parcel in) {
            return new ReviewModel(in);
        }

        @Override
        public ReviewModel[] newArray(int size) {
            return new ReviewModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeString(url);
    }
}
