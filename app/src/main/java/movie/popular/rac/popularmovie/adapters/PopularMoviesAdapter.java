package movie.popular.rac.popularmovie.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by User on 6/25/2015.
 */
public class PopularMoviesAdapter extends BaseAdapter {
    private Context mContext;
    public PopularMoviesAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
