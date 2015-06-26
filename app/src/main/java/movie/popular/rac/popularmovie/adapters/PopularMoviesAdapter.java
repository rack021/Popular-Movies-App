package movie.popular.rac.popularmovie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import movie.popular.rac.popularmovie.R;
import movie.popular.rac.popularmovie.models.PopularMovieModel;

/**
 * Created by User on 6/25/2015.
 */
public class PopularMoviesAdapter extends BaseAdapter {
    private Context mContext;
    private List<PopularMovieModel> mpopularMovieList;
    public PopularMoviesAdapter(Context c, List<PopularMovieModel> popularMovieList){
        mContext = c;
        mpopularMovieList = popularMovieList;
    }

    @Override
    public int getCount() {
        return mpopularMovieList.size();
    }

    @Override
    public Object getItem(int position) {
        return mpopularMovieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView thumb;
        TextView movieName;
        public ViewHolder(View v){
            thumb = (ImageView)v.findViewById(R.id.poster);
            movieName = (TextView) v.findViewById(R.id.movie_name);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if(row == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.popularmovie_movie_grid_item,parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        }else{
            holder = (ViewHolder)row.getTag();
        }
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/"+ mpopularMovieList.get(position).poster_path).into(holder.thumb);
        holder.movieName.setText(mpopularMovieList.get(position).title);
        return row;
    }
}
