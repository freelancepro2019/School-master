package com.softray_solutions.newschoolproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.ui.fragments.news.ItemNewsView;
import com.softray_solutions.newschoolproject.ui.fragments.news.NewsPresenter;
import com.squareup.picasso.Picasso;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private NewsPresenter newsPresenter;

    public NewsAdapter(NewsPresenter newsPresenter) {
        this.newsPresenter = newsPresenter;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_activty, parent, false);
        return new NewsHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        newsPresenter.onBindItemView(holder, position);
    }

    @Override
    public int getItemCount() {
        return newsPresenter.getNewsCount();
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements ItemNewsView {

        TextView titleTextView, contentTextView, timeTextView, dateTextView;
        RoundedImageView newsImageView;

        NewsHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.news_title_text);
            contentTextView = itemView.findViewById(R.id.news_content_text);
            timeTextView = itemView.findViewById(R.id.news_time_text);
            dateTextView = itemView.findViewById(R.id.news_date_text);
            newsImageView = itemView.findViewById(R.id.news_image);
        }

        @Override
        public void setTitleText(String title) {
            titleTextView.setText(title);
        }

        @Override
        public void setContentText(String content) {
            contentTextView.setText(content);
        }

        @Override
        public void setTimeText(String time) {
            timeTextView.setText(time);
        }

        @Override
        public void setDateText(String date) {
            dateTextView.setText(date);
        }

        @Override
        public void setNewsImage(String image) {
            Picasso.get().load(image).placeholder(R.drawable.placeholder_image)
                    .resize(500, 500).centerInside().into(newsImageView);
        }
    }
}