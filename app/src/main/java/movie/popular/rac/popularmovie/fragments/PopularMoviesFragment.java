package movie.popular.rac.popularmovie.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import movie.popular.rac.popularmovie.R;
import movie.popular.rac.popularmovie.adapters.PopularMoviesAdapter;
import movie.popular.rac.popularmovie.models.PopularMovieModel;
import movie.popular.rac.popularmovie.network.ApiConstants;
import movie.popular.rac.popularmovie.network.FetchPopularMovieTask;

/**
 * Created by User on 6/25/2015.
 */
public class PopularMoviesFragment extends Fragment {
    GridView popularMoviesGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View popularMovieLayout = inflater.inflate(R.layout.fragment_popularmovies, container, false);
        popularMoviesGridView = (GridView) popularMovieLayout.findViewById(R.id.popularmovies_gridview);

        List<PopularMovieModel> popularMovieList;
        try {
            Type listOfTestObject = new TypeToken<List<PopularMovieModel>>() {
            }.getType();
            String planets = "[{'adult':false,'backdrop_path':'/dkMD5qlogeRMiEixC4YNPUvax2T.jpg','genre_ids':[28,12,878,53],'id':135397,'original_language':'en','original_title':'Jurassic World','overview':'Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.','release_date':'2015-06-12','poster_path':'/uXZYawqUsChGSj54wcuBtEdUJbh.jpg','popularity':73.511837,'title':'Jurassic World','video':false,'vote_average':7.0,'vote_count':644},{'adult':false,'backdrop_path':'/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg','genre_ids':[28,12,53],'id':76341,'original_language':'en','original_title':'Mad Max: Fury Road','overview':'An apocalyptic story set in the furthest reaches of our planet, in a stark desert landscape where humanity is broken, and most everyone is crazed fighting for the necessities of life. Within this world exist two rebels on the run who just might be able to restore order. Theres Max, a man of action and a man of few words, who seeks peace of mind following the loss of his wife and child in the aftermath of the chaos. And Furiosa, a woman of action and a woman who believes her path to survival may be achieved if she can make it across the desert back to her childhood homeland.','release_date':'2015-05-15','poster_path':'/kqjL17yufvn9OVLyXYpvtyrFfak.jpg','popularity':50.324128,'title':'Mad Max: Fury Road','video':false,'vote_average':7.9,'vote_count':898},{'adult':false,'backdrop_path':'/cUfGqafAVQkatQ7N4y08RNV3bgu.jpg','genre_ids':[28,18,53],'id':254128,'original_language':'en','original_title':'San Andreas','overview':'In the aftermath of a massive earthquake in California, a rescue-chopper pilot makes a dangerous journey across the state in order to rescue his estranged daughter.','release_date':'2015-05-29','poster_path':'/6iQ4CMtYorKFfAmXEpAQZMnA0Qe.jpg','popularity':28.625939,'title':'San Andreas','video':false,'vote_average':6.2,'vote_count':278},{'adult':false,'backdrop_path':'/xjjO3JIdneMBTsS282JffiPqfHW.jpg','genre_ids':[10749,14,10751,18],'id':150689,'original_language':'en','original_title':'Cinderella','overview':'When her father unexpectedly passes away, young Ella finds herself at the mercy of her cruel stepmother and her daughters. Never one to give up hope, Ellas fortunes begin to change after meeting a dashing stranger in the woods.','release_date':'2015-03-13','poster_path':'/2i0JH5WqYFqki7WDhUW56Sg0obh.jpg','popularity':22.511043,'title':'Cinderella','video':false,'vote_average':7.1,'vote_count':306},{'adult':false,'backdrop_path':'/2BXd0t9JdVqCp9sKf6kzMkr7QjB.jpg','genre_ids':[12,10751,16,28,35],'id':177572,'original_language':'en','original_title':'Big Hero 6','overview':'The special bond that develops between plus-sized inflatable robot Baymax, and prodigy Hiro Hamada, who team up with a group of friends to form a band of high-tech heroes.','release_date':'2014-11-07','poster_path':'/3zQvuSAUdC3mrx9vnSEpkFX0968.jpg','popularity':22.307833,'title':'Big Hero 6','video':false,'vote_average':7.9,'vote_count':1512},{'adult':false,'backdrop_path':'/4liSXBZZdURI0c1Id1zLJo6Z3Gu.jpg','genre_ids':[878,14,28,12],'id':76757,'original_language':'en','original_title':'Jupiter Ascending','overview':'In a universe where human genetic material is the most precious commodity, an impoverished young Earth woman becomes the key to strategic maneuvers and internal strife within a powerful dynasty…','release_date':'2015-02-27','poster_path':'/aMEsvTUklw0uZ3gk3Q6lAj6302a.jpg','popularity':18.268814,'title':'Jupiter Ascending','video':false,'vote_average':5.4,'vote_count':716},{'adult':false,'backdrop_path':'/fii9tPZTpy75qOCJBulWOb0ifGp.jpg','genre_ids':[36,18,53,10752],'id':205596,'original_language':'en','original_title':'The Imitation Game','overview':'Based on the real life story of legendary cryptanalyst Alan Turing, the film portrays the nail-biting race against time by Turing and his brilliant team of code-breakers at Britains top-secret Government Code and Cypher School at Bletchley Park, during the darkest days of World War II.','release_date':'2014-11-14','poster_path':'/noUp0XOqIcmgefRnRZa1nhtRvWO.jpg','popularity':17.17016,'title':'The Imitation Game','video':false,'vote_average':8.1,'vote_count':1264}]";
            Gson gson = new Gson();
            new FetchPopularMovieTask().execute(ApiConstants.SORT_POPULARITY);
            popularMovieList = gson.fromJson(planets, listOfTestObject);
            popularMoviesGridView.setAdapter(new PopularMoviesAdapter(getActivity().getApplicationContext(), popularMovieList));
        } catch (Exception e) {
            Log.d("sd", "sds");
        }


        return popularMovieLayout;
    }

    private String getData(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
