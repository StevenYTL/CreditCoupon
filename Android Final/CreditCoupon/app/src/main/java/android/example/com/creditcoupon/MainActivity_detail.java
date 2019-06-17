package android.example.com.creditcoupon;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity_detail extends AppCompatActivity {

    private ShareActionProvider mShareActionProvider;
    private String uri;
    private String title_my;
    private String img_my;
    private String content_my;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        client = new OkHttpClient();

        // Initialize the views.
        TextView detail_Title = findViewById(R.id.detail_title);
        TextView detail_Content = findViewById(R.id.detail_content);
        ImageView detail_Image = findViewById(R.id._ImageDetail);

        title_my = getIntent().getStringExtra("title");
        img_my = getIntent().getStringExtra("image_resource");
        content_my = getIntent().getStringExtra("content");

        // Set the text from the Intent extra.
        detail_Title.setText(getIntent().getStringExtra("title"));
        detail_Content.setText(getIntent().getStringExtra("content"));
        uri = getIntent().getStringExtra("uri");

        // Load the image using the Glide library and the Intent extra.
        Glide.with(this).load(getIntent().getStringExtra("image_resource"))
                .into(detail_Image);

    }

    public void share_to(View view) {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "要分享的標題");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "要分享的內文");
        startActivity(Intent.createChooser(shareIntent,"Share to your friends!!"));
    }

    public void open_web(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(uri));
        startActivity(i);
    }

    public void add_collection(View view) {
        Toast.makeText(this,"Save!!!!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MyActivity.class);
        intent.putExtra("title",title_my );
        intent.putExtra("img",img_my );
        intent.putExtra("content",content_my );
        intent.putExtra("uri",uri );
//        startActivity(intent);
        new JsonAsyncTask().execute();

    }

    class JsonAsyncTask extends AsyncTask<Void,Void,String> {
        String resStr;
        @Override
        protected String doInBackground(Void... voids) {
            Request request = new Request.Builder()
                    .url("http://140.116.96.199:8000/postSaving/"+ title_my)
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

        }
    }
}
