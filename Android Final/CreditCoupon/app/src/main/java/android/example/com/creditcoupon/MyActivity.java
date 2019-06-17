package android.example.com.creditcoupon;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyActivity extends AppCompatActivity {
    private ArrayList<Image> mImageData;
    private FavorAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Image> mImageData_bkfvr;
    private FavorAdapter mAdapter_bkfvr;
    private RecyclerView mRecyclerView_bkfvr;
    private TextView Favor_bank;
    private TextView Favor_catrgory;
    private TextView mname;
    public static final int EDIT_REQUEST = 1;

    private String[] imagesTitle_f;
    private String[] imagesContent_f;
    private String[] imagesUri_f;
    private String[] imagesImguri_f;

    final private ArrayList<String> imagesTitle = new ArrayList<String>();
    final private ArrayList<String> imagesContent = new ArrayList<String>();
    final private ArrayList<String> imagesUri = new ArrayList<String>();
    final private ArrayList<String> images_img = new ArrayList<String>();

    private OkHttpClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        client = new OkHttpClient();

        Favor_bank = findViewById(R.id.text_bank_editable);
        Favor_catrgory = findViewById(R.id.text_favor_editable);
        mname = findViewById(R.id.myname);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_main:
                        Intent intent_main = new Intent(MyActivity.this,MainActivity.class);
                        startActivity(intent_main);
                        break;
                    case R.id.navigation_track:
                        Intent intent_track = new Intent(MyActivity.this,TrackActivity.class);
                        startActivity(intent_track);
                        break;
                    case R.id.navigation_notice:
                        Intent intent_notice = new Intent(MyActivity.this,NoticeActivity.class);
                        startActivity(intent_notice);
                        break;
                    case R.id.navigation_my:
                        break;
                }
                return false;

            }
        });

//        recycler for favor bank
        mRecyclerView_bkfvr = findViewById(R.id.recyclerView_bkFvr);
        mRecyclerView_bkfvr.setLayoutManager(new
                GridLayoutManager(this, 1));
        mImageData_bkfvr = new ArrayList<>();
        mAdapter_bkfvr = new FavorAdapter(this, mImageData_bkfvr);
        mRecyclerView_bkfvr.setAdapter(mAdapter_bkfvr);

        //recycler for favor category
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new
                GridLayoutManager(this, 1));
        mImageData = new ArrayList<>();
        mAdapter = new FavorAdapter(this, mImageData);
        mRecyclerView.setAdapter(mAdapter);

        new JsonAsyncTask().execute();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == EDIT_REQUEST){
            if (resultCode == RESULT_OK){
                Favor_bank.setText(intent.getCharSequenceExtra("bank"));
                mname.setText(intent.getCharSequenceExtra("name"));
                Favor_catrgory.setText(intent.getCharSequenceExtra("favor"));
            }
        }
    }
    /**
     * Initialize the image data from resources.
     */
    private void initializeData() {


        // Clear the existing data (to avoid duplication).
//        mImageData.clear();
        mImageData_bkfvr.clear();
        // Create the ArrayList of Sports objects with the titles and
        // information about each sport
        for (int i = 0; i < imagesTitle_f.length; i++) {
//            mImageData.add(new Image(imagesTitle_m[i], imagesContent_m[i],imagesUri_m[i],imagesRecources_m[0]));
            mImageData_bkfvr.add(new Image(imagesTitle_f[i], imagesContent_f[i],imagesUri_f[i],imagesImguri_f[i]));
           } //imagesContent[i], imagesUri[i],

        // Notify the adapter of the change.
//        mAdapter.notifyDataSetChanged();
        mAdapter_bkfvr.notifyDataSetChanged();
    }

    class JsonAsyncTask extends AsyncTask<Void,Void,String> {
        String resStr;
        @Override
        protected String doInBackground(Void... voids) {
            Request request = new Request.Builder()
                    .url("http://140.116.96.199:8000/getSaving")
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

            } catch (JSONException e) {
                e.printStackTrace();
            }
            initializeData();

        }
    }
    public void editprofile(View view) {
        Intent intent = new Intent(this, EditProfile.class);
        intent.putExtra("bank", Favor_bank.getText());
        intent.putExtra("name", mname.getText());
        intent.putExtra("favor", Favor_catrgory.getText());
        startActivityForResult(intent, EDIT_REQUEST);
    }
}