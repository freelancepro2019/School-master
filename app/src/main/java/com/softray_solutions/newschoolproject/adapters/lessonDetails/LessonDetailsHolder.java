package com.softray_solutions.newschoolproject.adapters.lessonDetails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LessonDetailsHolder extends RecyclerView.ViewHolder implements LessonDetailsItemView {
    @BindView(R.id.lesson_title)
    TextView titleText;
    @BindView(R.id.lesson_content)
    TextView contentText;
    Context context;

    LessonDetailsHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setTitle(int title) {

        titleText.setText(context.getResources().getString(title));
    }

    @Override
    public void setContent(String content) {
        contentText.setText(content);
    }

    public TextView getTitleText() {
        return titleText;
    }

    public void setContentAsAutolink() {
        contentText.setAutoLinkMask(Linkify.WEB_URLS);
    }

    public void startIntent(Uri uri)
    {
        try {

            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            context.startActivity(intent);
        }catch (Exception e){
            Toast.makeText(context, R.string.text, Toast.LENGTH_SHORT).show();
        }

    }
}