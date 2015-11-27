package movie.popular.rac.popularmovie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import movie.popular.rac.popularmovie.R;
import movie.popular.rac.popularmovie.models.ReviewModel;

/**
 * Created by rac on 10/5/15.
 */
public class MovieReviewAdapter extends BaseAdapter {
    private Context mContext;
    private List<ReviewModel> mMovieReviewList;

    public MovieReviewAdapter(Context c, ArrayList<ReviewModel> movieReviewList){
        mContext = c;
        mMovieReviewList = movieReviewList;
    }


    @Override
    public int getCount() {
        return mMovieReviewList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewHolder {
        TextView author;
        TextView review;
        public ViewHolder(View v){
            author = (TextView)v.findViewById(R.id.author);
            review = (TextView) v.findViewById(R.id.review);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if(row == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.movie_review_item,parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        }else{
            holder = (ViewHolder)row.getTag();
        }
        holder.author.setText(mMovieReviewList.get(position).author);
        holder.review.setText(mMovieReviewList.get(position).content);
        return row;
    }
}
