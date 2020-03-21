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
import com.softray_solutions.newschoolproject.model.ParentNotificationDataModel;
import com.softray_solutions.newschoolproject.ui.fragments.notifications.NotificationPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class ParentNotificationAdapter extends RecyclerView.Adapter<ParentNotificationAdapter.MainViewHolder> {
    private List<ParentNotificationDataModel> parentDataList;
    private NotificationPresenter notificationPresenter;
    private Context context;
    private String language;

    public ParentNotificationAdapter(List<ParentNotificationDataModel> parentDataList, NotificationPresenter notificationPresenter, Context context, String language) {
        this.parentDataList = parentDataList;
        this.notificationPresenter = notificationPresenter;
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
        notificationPresenter.bindParentNotificationsToView(holder, position, parentDataList);

    }

    @Override
    public int getItemCount() {
        return parentDataList.size();
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
