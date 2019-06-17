package android.example.com.creditcoupon;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>{

    private ArrayList<Notice> mNoticeData;
    private Context mContext;

    NoticeAdapter(Context context, ArrayList<Notice> noticeData){
        this.mContext = context;
        this.mNoticeData = noticeData;
    }

    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup  parent , int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.notice_list, parent, false));
    }

    @Override
    public void onBindViewHolder(NoticeAdapter.ViewHolder viewHolder, int position) {
        // Get current .
        Notice currentNotice = mNoticeData.get(position);

        // Populate the textviews with data.
        viewHolder.bindTo(currentNotice);
    }

    @Override
    public int getItemCount() {
        return mNoticeData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mNotice;
        private TextView mTitle;

        ViewHolder(View inflate) {
            super(inflate);
            // Initialize the views.
            mTitle = itemView.findViewById(R.id.Notice_title);
            mNotice = itemView.findViewById(R.id._Notice);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }
        void bindTo(Notice currentNotice) {
            // Populate the textviews with data.
            mTitle.setText(currentNotice.getTitle());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(
                    currentNotice.getNoticeResource()).into(mNotice);
        }
        @Override
        public void onClick(View view) {
            Notice currentNotice = mNoticeData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, MainActivity_detail.class);
            detailIntent.putExtra("title", currentNotice.getTitle());
            detailIntent.putExtra("content", currentNotice.getContent());
            detailIntent.putExtra("uri", currentNotice.getUri());
            detailIntent.putExtra("image_resource",
                    currentNotice.getNoticeResource());
            mContext.startActivity(detailIntent);
        }


    }
}
