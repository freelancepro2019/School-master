package com.softray_solutions.newschoolproject.adapters;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.databinding.ChatFileLeftRowBinding;
import com.softray_solutions.newschoolproject.databinding.ChatFileRightRowBinding;
import com.softray_solutions.newschoolproject.databinding.ChatImageLeftRowBinding;
import com.softray_solutions.newschoolproject.databinding.ChatImageRightRowBinding;
import com.softray_solutions.newschoolproject.databinding.ChatMessageAudioLeftRowBinding;
import com.softray_solutions.newschoolproject.databinding.ChatMessageAudioRightRowBinding;
import com.softray_solutions.newschoolproject.databinding.ChatMessageLeftRowBinding;
import com.softray_solutions.newschoolproject.databinding.ChatMessageRightRowBinding;
import com.softray_solutions.newschoolproject.model.MessageModel;
import com.softray_solutions.newschoolproject.ui.activities.activity_chat.ChatActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int msg_left = 1;
    private final int msg_right = 2;
    private final int img_left = 3;
    private final int img_right = 4;
    private final int file_left = 5;
    private final int file_right = 6;
    private final int sound_left = 7;
    private final int sound_right = 8;
    private List<MessageModel> list;
    private Context context;
    private String current_user_id;
    private String chat_user_image;
    private String current_user_image;
    private LayoutInflater inflater;
    private String base_url_image = "";
    private String base_url_audio = "";
    private ChatActivity activity;
    private MediaPlayer mediaPlayer;
    private int pos = -1;
    private SparseBooleanArray array;



    public ChatAdapter(List<MessageModel> list, Context context, String current_user_id, String chat_user_image,String current_user_image) {
        this.list = list;
        this.context = context;
        this.current_user_id = current_user_id;
        this.chat_user_image = chat_user_image;
        this.current_user_image = current_user_image;
        inflater = LayoutInflater.from(context);
        activity = (ChatActivity) context;
        array = new SparseBooleanArray();


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
        } else if (viewType == file_right) {
            ChatFileRightRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.chat_file_right_row, parent, false);
            return new HolderFileRight(binding);
        } else if (viewType == sound_left) {
            ChatMessageAudioLeftRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.chat_message_audio_left_row, parent, false);
            return new SoundLeftHolder(binding);

        } else {
            ChatMessageAudioRightRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.chat_message_audio_right_row, parent, false);
            return new SoundRightHolder(binding);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel model = list.get(position);

        if (pos == position) {

            if (holder instanceof SoundRightHolder) {


                SoundRightHolder soundRightHolder = (SoundRightHolder) holder;

                if (mediaPlayer!=null){
                    if (mediaPlayer.isPlaying())
                    {
                        soundRightHolder.binding.imagePlay.setImageResource(R.drawable.ic_pause);
                        soundRightHolder.binding.imagePlay.setVisibility(View.VISIBLE);
                    }else {
                        soundRightHolder.binding.imagePlay.setVisibility(View.INVISIBLE);
                        soundRightHolder.binding.progBar.setVisibility(View.VISIBLE);
                    }

                }else {
                    soundRightHolder.binding.imagePlay.setVisibility(View.INVISIBLE);

                }




            } else if (holder instanceof SoundLeftHolder) {

                SoundLeftHolder soundLeftHolder = (SoundLeftHolder) holder;



                if (mediaPlayer!=null){
                    if (mediaPlayer.isPlaying())
                    {
                        soundLeftHolder.binding.imagePlay.setImageResource(R.drawable.ic_pause);
                        soundLeftHolder.binding.imagePlay.setVisibility(View.VISIBLE);
                    }else {
                        soundLeftHolder.binding.imagePlay.setVisibility(View.INVISIBLE);
                        soundLeftHolder.binding.progBar.setVisibility(View.VISIBLE);
                    }

                }else {
                    soundLeftHolder.binding.imagePlay.setVisibility(View.INVISIBLE);

                }


            }


        } else {

            if (holder instanceof SoundRightHolder) {

                SoundRightHolder soundRightHolder = (SoundRightHolder) holder;
                soundRightHolder.binding.imagePlay.setImageResource(R.drawable.ic_play);
                soundRightHolder.binding.imagePlay.setVisibility(View.VISIBLE);
                soundRightHolder.binding.progBar.setVisibility(View.GONE);

            } else if (holder instanceof SoundLeftHolder) {
                SoundLeftHolder soundLeftHolder = (SoundLeftHolder) holder;
                soundLeftHolder.binding.imagePlay.setImageResource(R.drawable.ic_play);
                soundLeftHolder.binding.imagePlay.setVisibility(View.VISIBLE);
                soundLeftHolder.binding.progBar.setVisibility(View.GONE);



            }

        }


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
            Picasso.get().load(Uri.parse(base_url_image + model.getAttachment())).fit().into(holderImageLeft.binding.imageChat);


            holderImageLeft.itemView.setOnClickListener(view -> {
                MessageModel model1 = list.get(holderImageLeft.getAdapterPosition());
                activity.setItemFile(model1);
            });
        } else if (holder instanceof HolderImageRight) {
            HolderImageRight holderImageRight = (HolderImageRight) holder;
            holderImageRight.binding.setModel(model);
            holderImageRight.binding.tvTime.setText(getTime(Long.parseLong(model.getCreated_at())));
            Picasso.get().load(Uri.parse(base_url_image + model.getAttachment())).fit().into(holderImageRight.binding.image);

            holderImageRight.itemView.setOnClickListener(view -> {
                MessageModel model1 = list.get(holderImageRight.getAdapterPosition());
                activity.setItemFile(model1);
            });

        } else if (holder instanceof HolderFileLeft) {
            HolderFileLeft holderFileLeft = (HolderFileLeft) holder;
            holderFileLeft.binding.setModel(model);
            holderFileLeft.binding.setUrl(base_url_image);
            holderFileLeft.binding.tvTime.setText(getTime(Long.parseLong(model.getCreated_at())));

            holderFileLeft.itemView.setOnClickListener(view -> {
                MessageModel model1 = list.get(holderFileLeft.getAdapterPosition());
                activity.setItemFile(model1);

            });
        } else if (holder instanceof HolderFileRight) {
            HolderFileRight holderFileRight = (HolderFileRight) holder;
            holderFileRight.binding.setModel(model);
            holderFileRight.binding.setUrl(base_url_image);
            holderFileRight.binding.tvTime.setText(getTime(Long.parseLong(model.getCreated_at())));

            holderFileRight.itemView.setOnClickListener(view -> {
                MessageModel model1 = list.get(holderFileRight.getAdapterPosition());
                activity.setItemFile(model1);

            });
        } else if (holder instanceof SoundRightHolder) {

            SoundRightHolder soundRightHolder = (SoundRightHolder) holder;
            MessageModel messageModel = list.get(holder.getAdapterPosition());
            soundRightHolder.BindData(messageModel);

            soundRightHolder.itemView.setOnClickListener(view -> {

                if (holder.getAdapterPosition()!=pos)
                {
                    pos = holder.getAdapterPosition();
                    notifyDataSetChanged();
                    soundRightHolder.initAudio(list.get(pos));

                }else {

                    if (mediaPlayer!=null){
                        if (mediaPlayer.isPlaying())
                        {
                            soundRightHolder.binding.imagePlay.setImageResource(R.drawable.ic_play);
                            soundRightHolder.binding.imagePlay.setVisibility(View.VISIBLE);
                            soundRightHolder.binding.progBar.setVisibility(View.GONE);
                            stopPlay();
                            pos = -1;
                            notifyDataSetChanged();
                        }else {
                            soundRightHolder.initAudio(list.get(pos));

                        }
                    }else {
                        soundRightHolder.initAudio(list.get(pos));
                    }

                }

            });

        } else if (holder instanceof SoundLeftHolder) {
            SoundLeftHolder soundLeftHolder = (SoundLeftHolder) holder;
            MessageModel messageModel = list.get(holder.getAdapterPosition());
            soundLeftHolder.BindData(messageModel);

            soundLeftHolder.itemView.setOnClickListener(view -> {
                if (holder.getAdapterPosition()!=pos)
                {
                    pos = holder.getAdapterPosition();
                    soundLeftHolder.initAudio(list.get(pos));
                    notifyDataSetChanged();


                }else {

                    if (mediaPlayer!=null){
                        if (mediaPlayer.isPlaying())
                        {
                            soundLeftHolder.binding.imagePlay.setImageResource(R.drawable.ic_play);
                            soundLeftHolder.binding.imagePlay.setVisibility(View.VISIBLE);
                            soundLeftHolder.binding.progBar.setVisibility(View.GONE);
                            stopPlay();
                            pos = -1;
                            notifyDataSetChanged();
                        }else {
                            soundLeftHolder.initAudio(list.get(pos));

                        }
                    }else {
                        soundLeftHolder.initAudio(list.get(pos));
                    }

                }

            });


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private String getTime(long time) {
        return new SimpleDateFormat("dd/MMM/yyyy hh:mm aa", Locale.ENGLISH).format(new Date(time * 1000));
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


    public class SoundRightHolder extends RecyclerView.ViewHolder {

        private ChatMessageAudioRightRowBinding binding;


        public SoundRightHolder(ChatMessageAudioRightRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }


        public void BindData(final MessageModel messageModel) {
            binding.tvTime.setText(getTime(Long.parseLong(messageModel.getCreated_at())));
            Picasso.get().load(Uri.parse(current_user_image)).fit().into(binding.image);

        }


        private void initAudio(MessageModel messageModel) {



            try {


                if (mediaPlayer!=null)
                {
                    stopPlay();
                }


                binding.progBar.setVisibility(View.VISIBLE);
                binding.imagePlay.setVisibility(View.INVISIBLE);

                Log.e("path", base_url_audio + messageModel.getAudio());
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(base_url_audio + messageModel.getAudio());
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setVolume(100.0f, 100.0f);
                mediaPlayer.setLooping(false);
                mediaPlayer.prepareAsync();

                mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                    binding.imagePlay.setVisibility(View.VISIBLE);
                    binding.imagePlay.setImageResource(R.drawable.ic_play);
                    binding.progBar.setVisibility(View.GONE);
                    stopPlay();
                    return false;
                });
                mediaPlayer.setOnPreparedListener(mediaPlayer -> {

                    binding.progBar.setVisibility(View.GONE);
                    binding.imagePlay.setVisibility(View.VISIBLE);
                    binding.imagePlay.setImageResource(R.drawable.ic_pause);
                    mediaPlayer.start();

                });

                mediaPlayer.setOnCompletionListener(md -> {
                    if (mediaPlayer!=null)
                    {
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    pos = -1;

                    notifyDataSetChanged();
                });


            } catch (IOException e) {
                Log.e("eeeex", e.getMessage());
                mediaPlayer.release();
                mediaPlayer = null;

            }






        }




    }

    public class SoundLeftHolder extends RecyclerView.ViewHolder {
        private ChatMessageAudioLeftRowBinding binding;


        public SoundLeftHolder(ChatMessageAudioLeftRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }


        public void BindData(final MessageModel messageModel) {
            binding.tvTime.setText(getTime(Long.parseLong(messageModel.getCreated_at())));
        }

        private void initAudio(MessageModel messageModel) {



            try {


                if (mediaPlayer!=null)
                {
                    stopPlay();
                }


                binding.progBar.setVisibility(View.VISIBLE);
                binding.imagePlay.setVisibility(View.INVISIBLE);

                Log.e("path", base_url_audio + messageModel.getAudio());
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(base_url_audio + messageModel.getAudio());
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setVolume(100.0f, 100.0f);
                mediaPlayer.setLooping(false);
                mediaPlayer.prepareAsync();

                mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                    binding.imagePlay.setVisibility(View.VISIBLE);
                    binding.imagePlay.setImageResource(R.drawable.ic_play);
                    binding.progBar.setVisibility(View.GONE);
                    stopPlay();
                    return false;
                });
                mediaPlayer.setOnPreparedListener(mediaPlayer -> {

                    binding.progBar.setVisibility(View.GONE);
                    binding.imagePlay.setVisibility(View.VISIBLE);
                    binding.imagePlay.setImageResource(R.drawable.ic_pause);
                    mediaPlayer.start();

                });

                mediaPlayer.setOnCompletionListener(md -> {
                    if (mediaPlayer!=null)
                    {
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    pos = -1;

                    notifyDataSetChanged();
                });


            } catch (IOException e) {
                Log.e("eeeex", e.getMessage());
                mediaPlayer.release();
                mediaPlayer = null;

            }






        }





    }


    public void stopPlay() {

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;

        }
    }

    @Override
    public int getItemViewType(int position) {

        MessageModel messageModel = list.get(position);

        if (messageModel.getAudio() != null && !messageModel.getAudio().isEmpty()) {
            if (messageModel.getFrom_user().equals(current_user_id)) {
                return sound_right;
            } else {
                return sound_left;
            }
        } else if (messageModel.getAttachment() != null && !messageModel.getAttachment().isEmpty()) {
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

    public void setBase_url_image(String url) {
        this.base_url_image = url;
        this.base_url_audio = this.base_url_image + "audio/";
    }




}
