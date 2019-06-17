package android.example.com.creditcoupon;

import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TrackActivity_bank_mount extends AppCompatActivity {

    private ArrayList<Image> mImageData;
    private ImageAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private String[] imagesTitle_f;
    private String[] imagesContent_f;
    private String[] imagesUri_f;
    private String[] imagesImguri_f;

    final private ArrayList<String> imagesTitle = new ArrayList<String>();
    final private ArrayList<String> imagesContent = new ArrayList<String>();
    final private ArrayList<String> imagesUri = new ArrayList<String>();
    final private ArrayList<String> images_img = new ArrayList<String>();

    private TypedArray ImageResources;

    private ExecutorService service;
    private OkHttpClient client;

    private String intent_url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_bank_mount);

        client = new OkHttpClient();
        service = Executors.newSingleThreadExecutor();

        //設定傳遞的url種類
        intent_url = getIntent().getStringExtra("intent_url");

        //設定幾個圖一行
        int gridColumnCount =
                getResources().getInteger(R.integer.grid_column_count);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new
                GridLayoutManager(this, gridColumnCount));
        mImageData = new ArrayList<>();
        mAdapter = new ImageAdapter(this, mImageData);
        mRecyclerView.setAdapter(mAdapter);

        new JsonAsyncTask().execute();

    }
//
    private void initializeData() {
        // Get the resources from the XML file.
//        String[] imagesTitle = getResources()
//                .getStringArray(R.array.image_mount_titles);
//        String[] imagesContent = getResources()
//                .getStringArray(R.array.image_mount_content);
//        String[] imagesUri = getResources()
//                .getStringArray(R.array.image_mount_uri);
//        TypedArray ImageResources = getResources()
//                .obtainTypedArray(R.array._images_mount);

//        imagesTitle_f = imagesTitle.toArray(new String[imagesTitle.size()]);
//        imagesContent_f = imagesContent.toArray(new String[imagesContent.size()]);
//        imagesUri_f = imagesUri.toArray(new String[imagesUri.size()]);


        // Clear the existing data (to avoid duplication).
        mImageData.clear();

        // Create the ArrayList of Sports objects with the titles and
        // information about each sport
        for (int i = 1; i < imagesTitle_f.length; i++) {
            mImageData.add(new Image(imagesTitle_f[i],imagesContent_f[i],imagesUri_f[i], imagesImguri_f[i]));
        }

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }

    class JsonAsyncTask extends AsyncTask<Void,Void,String> {
        String resStr;
        @Override
        protected String doInBackground(Void... voids) {
            Request request = new Request.Builder()
                    .url(intent_url)
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
                if(s != null) {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    for (int i = 0; i < jsonArray.length(); i++) {
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

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
