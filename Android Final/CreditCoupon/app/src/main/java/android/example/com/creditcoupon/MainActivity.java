package android.example.com.creditcoupon;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ArrayList<Image> mImageData;
    private ImageAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private OkHttpClient client;

    private String[] imagesTitle_f;
    private String[] imagesContent_f;
    private String[] imagesUri_f;
    private String[] imagesImguri_f;

    final private ArrayList<String> imagesTitle = new ArrayList<String>();
    final private ArrayList<String> imagesContent = new ArrayList<String>();
    final private ArrayList<String> imagesUri = new ArrayList<String>();
    final private ArrayList<String> images_img = new ArrayList<String>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_main:
                    mTextMessage.setText(R.string.title_main);
                    return true;
                case R.id.navigation_track:
                    mTextMessage.setText(R.string.title_my);
                    return true;
                case R.id.navigation_notice:
                    mTextMessage.setText(R.string.title_notice);
                    return true;
                case R.id.navigation_my:
                    mTextMessage.setText(R.string.title_my);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //設定幾個圖一行
        int gridColumnCount =
                getResources().getInteger(R.integer.grid_column_count);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new
                GridLayoutManager(this, gridColumnCount));
        mImageData = new ArrayList<>();
        mAdapter = new ImageAdapter(this, mImageData);
        mRecyclerView.setAdapter(mAdapter);

        client = new OkHttpClient();

//        initializeData();


        mTextMessage = (TextView) findViewById(R.id.message);
        //final是為了scroll hide加
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        //scroll消去底下那層
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && navigation.isShown()) {
                    navigation.setVisibility(View.GONE);
                } else if (dy < 0 ) {
                    navigation.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_main:
                        break;
                    case R.id.navigation_track:
                        Intent intent_track = new Intent(MainActivity.this,TrackActivity.class);
                        startActivity(intent_track);
                        break;
                    case R.id.navigation_notice:
                        Intent intent_notice = new Intent(MainActivity.this,NoticeActivity.class);
                        startActivity(intent_notice);
                        break;
                    case R.id.navigation_my:
                        Intent intent_my = new Intent(MainActivity.this,MyActivity.class);
                        startActivity(intent_my);
                        break;
                }
                return false;

            }
        });
        new JsonAsyncTask().execute();
    }

    /**
     * Initialize the image data from resources.
     */
    private void initializeData() {

        // Clear the existing data (to avoid duplication).
        mImageData.clear();

        // Create the ArrayList of Sports objects with the titles and
        // information about each sport
        for (int i = 0; i < imagesTitle_f.length; i++) {
            mImageData.add(new Image(imagesTitle_f[i], imagesContent_f[i],imagesUri_f[i],imagesImguri_f[i]));
           } //imagesContent[i], imagesUri[i],

        // Recycle the typed array.
//        ImageResources_m.recycle();
//        ImageResources_t.recycle();
//        ImageResources_c.recycle();

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }


    class JsonAsyncTask extends AsyncTask<Void,Void,String> {
        String resStr;
        @Override
        protected String doInBackground(Void... voids) {
            Request request = new Request.Builder()
                    .url("http://140.116.96.199:8000/getLatest/")
                    .build();

            try{
                Response response = client.newCall(request).execute();
                resStr = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return resStr;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if(s!=null){
                    // Do you work here on success
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                        imagesTitle.add(jsonObject2.getString("title"));
                        imagesContent.add(jsonObject2.getString("discount"));
                        imagesUri.add(jsonObject2.getString("url"));
                        images_img.add(jsonObject2.getString("img_url"));
                    }
                    imagesTitle_f = imagesTitle.toArray(new String[imagesTitle.size()]);
                    imagesContent_f = imagesContent.toArray(new String[imagesContent.size()]);
                    imagesUri_f = imagesUri.toArray(new String[imagesUri.size()]);
                    imagesImguri_f = images_img.toArray(new String[images_img.size()]);

                    initializeData();

                }else{
                    // null response or Exception occur
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
