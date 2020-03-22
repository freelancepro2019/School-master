package com.softray_solutions.newschoolproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.databinding.ChatFileLeftRowBinding;
import com.softray_solutions.newschoolproject.databinding.ChatFileRightRowBinding;
import com.softray_solutions.newschoolproject.databinding.ChatImageLeftRowBinding;
import com.softray_solutions.newschoolproject.databinding.ChatImageRightRowBinding;
import com.softray_solutions.newschoolproject.databinding.ChatMessageLeftRowBinding;
import com.softray_solutions.newschoolproject.databinding.ChatMessageRightRowBinding;
import com.softray_solutions.newschoolproject.model.MessageModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int msg_left = 1;
    private final int msg_right = 2;
    private final int img_left = 3;
    private final int img_right = 4;
    private final int file_left = 5;
    private final int file_right = 6;

    private List<MessageModel> list;
    private Context context;
    private String current_user_id;
    private String chat_user_image;
    private LayoutInflater inflater;

    public ChatAdapter(List<MessageModel> list, Context context, String current_user_id, String chat_user_image) {
        this.list = list;
        this.context = context;
        this.current_user_id = current_user_id;
        this.chat_user_image = chat_user_image;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == msg_left) {
            ChatMessageLeftRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.chat_message_left_row, parent, false);
            return new HolderMsgLeft(binding);
        } else if (viewType == msg_right) {
            ChatMessageRightRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.chat_message_right_row, parent, false);
            return new HolderMsgRight(binding);
        } else if (viewType == img_left) {
            ChatImageLeftRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.chat_image_left_row, parent, false);
            return new HolderImageLeft(binding);
        } else if (viewType == img_right) {
            ChatImageRightRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.chat_image_right_row, parent, false);
            return new HolderImageRight(binding);
        } else if (viewType == file_left) {
            ChatFileLeftRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.chat_file_left_row, parent, false);
            return new HolderFileLeft(binding);
        } else {
            ChatFileRightRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.chat_file_right_row, parent, false);
            return new HolderFileRight(binding);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel model = list.get(position);

        if (holder instanceof HolderMsgLeft) {
            HolderMsgLeft holderMsgLeft = (HolderMsgLeft) holder;
            holderMsgLeft.binding.setModel(model);
            holderMsgLeft.binding.tvTime.setText(getTime(Long.parseLong(model.getCreated_at())));
        } else if (holder instanceof HolderMsgRight) {
            HolderMsgRight holderMsgRight = (HolderMsgRight) holder;
            holderMsgRight.binding.setModel(model);
            holderMsgRight.binding.tvTime.setText(getTime(Long.parseLong(model.getCreated_at())));

        } else if (holder instanceof HolderImageLeft) {
            HolderImageLeft holderImageLeft = (HolderImageLeft) holder;
            holderImageLeft.binding.setModel(model);
            holderImageLeft.binding.tvTime.setText(getTime(Long.parseLong(model.getCreated_at())));

        } else if (holder instanceof HolderImageRight) {
            HolderImageRight holderImageRight = (HolderImageRight) holder;
            holderImageRight.binding.setModel(model);
            holderImageRight.binding.tvTime.setText(getTime(Long.parseLong(model.getCreated_at())));

        } else if (holder instanceof HolderFileLeft) {
            HolderFileLeft holderFileLeft = (HolderFileLeft) holder;
            holderFileLeft.binding.setModel(model);
            holderFileLeft.binding.tvTime.setText(getTime(Long.parseLong(model.getCreated_at())));

        } else if (holder instanceof HolderFileRight) {
            HolderFileRight holderFileRight = (HolderFileRight) holder;
            holderFileRight.binding.setModel(model);
            holderFileRight.binding.tvTime.setText(getTime(Long.parseLong(model.getCreated_at())));

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private String getTime(long time) {
        return new SimpleDateFormat("dd/MMM/yyyy hh:mm", Locale.ENGLISH).format(new Date(time * 1000));
    }


    public class HolderMsgLeft extends RecyclerView.ViewHolder {
        private ChatMessageLeftRowBinding binding;

        public HolderMsgLeft(@NonNull ChatMessageLeftRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class HolderMsgRight extends RecyclerView.ViewHolder {
        private ChatMessageRightRowBinding binding;

        public HolderMsgRight(@NonNull ChatMessageRightRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class HolderImageLeft extends RecyclerView.ViewHolder {
        private ChatImageLeftRowBinding binding;

        public HolderImageLeft(@NonNull ChatImageLeftRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class HolderImageRight extends RecyclerView.ViewHolder {
        private ChatImageRightRowBinding binding;

        public HolderImageRight(@NonNull ChatImageRightRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class HolderFileLeft extends RecyclerView.ViewHolder {
        private ChatFileLeftRowBinding binding;

        public HolderFileLeft(@NonNull ChatFileLeftRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public class HolderFileRight extends RecyclerView.ViewHolder {
        private ChatFileRightRowBinding binding;

        public HolderFileRight(@NonNull ChatFileRightRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public int getItemViewType(int position) {

        MessageModel messageModel = list.get(position);

        if (messageModel.getAttachment() != null && !messageModel.getAttachment().isEmpty()) {
            if (messageModel.getAttachment().toLowerCase().endsWith(".png") || messageModel.getAttachment().toLowerCase().endsWith(".jpeg") || messageModel.getAttachment().toLowerCase().endsWith(".jpg")) {
                if (messageModel.getFrom_user().equals(current_user_id)) {
                    return img_right;
                } else {
                    return img_left;
                }
            } else {
                if (messageModel.getFrom_user().equals(current_user_id)) {
                    return file_right;
                } else {
                    return file_left;
                }
            }
        } else if (messageModel.getMsg_text() != null && !messageModel.getMsg_text().isEmpty()) {
            if (messageModel.getFrom_user().equals(current_user_id)) {
                return msg_right;
            } else {
                return msg_left;
            }
        } else {
            if (messageModel.getFrom_user().equals(current_user_id)) {
                return msg_right;
            } else {
                return msg_left;
            }
        }

    }

}
