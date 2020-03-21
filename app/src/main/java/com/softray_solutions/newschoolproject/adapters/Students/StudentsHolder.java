package com.softray_solutions.newschoolproject.adapters.Students;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class StudentsHolder extends RecyclerView.ViewHolder implements StudentsItemView {
    @BindView(R.id.profile_image)
    CircleImageView profileImageView;
    @BindView(R.id.profile_name)
    TextView nameTextView;

    StudentsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setStudentName(String name) {
        nameTextView.setText(name);
    }

    @Override
    public void setStudentImage(String image) {
        Picasso.get().load(image).placeholder(R.drawable.default_avatar).into(profileImageView);
    }
}