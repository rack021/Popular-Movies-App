package movie.popular.rac.popularmovie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.securepreferences.SecurePreferences;
import com.squareup.okhttp.OkHttpClient;

import movie.popular.rac.popularmovie.fragments.MovieDetailFragment;
import movie.popular.rac.popularmovie.fragments.PopularMoviesFragment;
import movie.popular.rac.popularmovie.network.ApiConstants;


public class MainActivity extends AppCompatActivity {
    private static final String MOVIEDETAILFRAGMENT_TAG = "MDFT";
    private static final String POPULARMOVIEFRAGMENT_TAG = "PMFT";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this).enableDumpapp(Stetho.defaultDumperPluginsProvider(this)).enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)).build());

        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.moviedetail) != null) {
            mTwoPane = true;

        }else{
            mTwoPane = false;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new PopularMoviesFragment(), POPULARMOVIEFRAGMENT_TAG)
                        .commit();
            }
        }
    }

    public void UpdateUI(MovieDetailFragment movieDetailFragment){
        if(movieDetailFragment == null){
            if (getSupportFragmentManager().findFragmentById(R.id.moviedetail) != null) {
                getSupportFragmentManager().beginTransaction().
                        remove(getSupportFragmentManager().findFragmentById(R.id.moviedetail)).commit();
            }
        }else{
            if(mTwoPane == false){
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, movieDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }else {
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.moviedetail, movieDetailFragment)
                        .commit();
            }
        }

    }

    public boolean isTablet(){
        return  mTwoPane;
    }
}
