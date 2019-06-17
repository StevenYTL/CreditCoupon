package android.example.com.creditcoupon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavorAdapter extends RecyclerView.Adapter<FavorAdapter.ViewHolder> {
    private ArrayList<Image> mImageData;
    private Context mContext;


    FavorAdapter(Context context, ArrayList<Image> imageData){
        this.mContext = context;
        this.mImageData = imageData;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.favor_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get current sport.
        Image currentImage = mImageData.get(position);

        // Populate the textviews with data.
        viewHolder.bindTo(currentImage);
    }
    @Override
    public int getItemCount() {
        return mImageData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle;
        private ImageView mImage;
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            //mTitle = itemView.findViewById(R.id.Image_title);
            mImage = itemView.findViewById(R.id._Image);
            mTitle = itemView.findViewById(R.id._TextView);
            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(Image currentImage) {
            // Populate the textviews with data.
            mTitle.setText(currentImage.getTitle());
//            mTitle.setText("test");
            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(
                    currentImage.getImageResource()).into(mImage);
        }

        @Override
        public void onClick(View view) {
            Image currentImage = mImageData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, MainActivity_detail.class);
            detailIntent.putExtra("title", currentImage.getTitle());
            detailIntent.putExtra("content", currentImage.getContent());
            detailIntent.putExtra("uri", currentImage.getUri());
            detailIntent.putExtra("image_resource",
                    currentImage.getImageResource());
            mContext.startActivity(detailIntent);
        }

    }
}
