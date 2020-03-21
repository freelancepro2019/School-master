package com.softray_solutions.newschoolproject.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.model.StudentNotificaionDataModel;
import com.softray_solutions.newschoolproject.ui.fragments.notifications.NotificationPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class StudentNotificationAdapter extends RecyclerView.Adapter<StudentNotificationAdapter.MainViewHolder> {
    private List<StudentNotificaionDataModel> dataModelList = new ArrayList<>();
    private NotificationPresenter presenter;
    private Context context;
    private String language;

    public StudentNotificationAdapter(List<StudentNotificaionDataModel> dataModelList, NotificationPresenter presenter, Context context, String language) {
        this.dataModelList = dataModelList;
        this.presenter = presenter;
        this.context = context;
        this.language = language;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        presenter.bindStudentNotificationsToView(holder, position, dataModelList);
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_content)
        public
        TextView notificationContent;
        @BindView(R.id.notification_date)
        public
        TextView notificationDate;
        @BindView(R.id.notification_image)
        public
        CircleImageView imageView;
        @BindView(R.id.view)
        public
        View view;
        public Badge badge;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            badge = new QBadgeView(context).bindTarget(itemView.findViewById(R.id.root));
            badge.setBadgeBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
            if (language.equals("ar")) {
                badge.setBadgeGravity(Gravity.TOP | Gravity.START);
            } else {
                badge.setBadgeGravity(Gravity.TOP | Gravity.END);
            }
            badge.setBadgeTextSize(14, true);
            badge.setBadgeTextSize(14, false);
        }
    }
}
