package android.example.com.creditcoupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TrackActivity_bank_mount_category extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_bank_mount_category);



    }

    public void click_housing(View view) {
        Intent intent = new Intent(this, TrackActivity_bank_mount.class);
        intent.putExtra("intent_url", "http://140.116.96.199:8000/getCategory/housing/");
        startActivity(intent);
    }

    public void click_onlineshopping(View view) {
        Intent intent = new Intent(this, TrackActivity_bank_mount.class);
        intent.putExtra("intent_url", "http://140.116.96.199:8000/getCategory/onlineshop/");
        startActivity(intent);
    }

    public void click_shopping(View view) {
        Intent intent = new Intent(this, TrackActivity_bank_mount.class);
        intent.putExtra("intent_url", "http://140.116.96.199:8000/getCategory/shopping/");
        startActivity(intent);
    }

    public void click_travel(View view) {
        Intent intent = new Intent(this, TrackActivity_bank_mount.class);
        intent.putExtra("intent_url", "http://140.116.96.199:8000/getCategory/travel/");
        startActivity(intent);
    }

    public void click_communication(View view) {
        Intent intent = new Intent(this, TrackActivity_bank_mount.class);
        intent.putExtra("intent_url", "http://140.116.96.199:8000/getCategory/communication/");
        startActivity(intent);
    }

    public void click_medicine(View view) {
        Intent intent = new Intent(this, TrackActivity_bank_mount.class);
        intent.putExtra("intent_url", "http://140.116.96.199:8000/getCategory/medicine/");
        startActivity(intent);
    }

    public void click_food(View view) {
        Intent intent = new Intent(this, TrackActivity_bank_mount.class);
        intent.putExtra("intent_url", "http://140.116.96.199:8000/getCategory/food/");
        startActivity(intent);
    }

    public void click_transportation(View view) {
        Intent intent = new Intent(this, TrackActivity_bank_mount.class);
        intent.putExtra("intent_url", "http://140.116.96.199:8000/getCategory/transportation/");
        startActivity(intent);
    }

    public void click_movie(View view) {
        Intent intent = new Intent(this, TrackActivity_bank_mount.class);
        intent.putExtra("intent_url", "http://140.116.96.199:8000/getCategory/movie/");
        startActivity(intent);
    }

    public void click_others(View view) {
        Intent intent = new Intent(this, TrackActivity_bank_mount.class);
        intent.putExtra("intent_url", "http://140.116.96.199:8000/getCategory/others/");
        startActivity(intent);
    }
}
