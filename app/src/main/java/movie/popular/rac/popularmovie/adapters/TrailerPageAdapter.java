package movie.popular.rac.popularmovie.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import movie.popular.rac.popularmovie.R;
import movie.popular.rac.popularmovie.models.TrailerModel;

/**
 * Created by rac on 10/4/15.
 */
public class TrailerPageAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<TrailerModel> mResources;
    public TrailerPageAdapter(Activity activity, ArrayList<TrailerModel> resources) {
        mContext = activity;
        mResources = resources;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ViewPager pager = (ViewPager) container;
        String youtubethumbUrl = String.format(Locale.ENGLISH, mContext.getString(R.string.youtube_thumb_url), mResources.get(position).key.toString());
        View itemView = mLayoutInflater.inflate(R.layout.trailer_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.bigposter);
        Picasso.with(mContext).load(youtubethumbUrl)
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder)
                .into(imageView);

        pager.addView(itemView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + mResources.get(position).key)));
            }
        });
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
