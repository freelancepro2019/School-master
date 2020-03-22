package com.softray_solutions.newschoolproject.ui.activities.activity_chat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.ChatAdapter;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.databinding.ActivityChatBinding;
import com.softray_solutions.newschoolproject.model.ChatUserModel;
import com.softray_solutions.newschoolproject.model.FileModel;
import com.softray_solutions.newschoolproject.model.MessageDataModel;
import com.softray_solutions.newschoolproject.model.MessageModel;
import com.softray_solutions.newschoolproject.model.User;
import com.softray_solutions.newschoolproject.ui.activities.activity_file.FileActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements ChatView {
    private ActivityChatBinding binding;
    private ChatUserModel chatUserModel;
    private SharedPreferences preferences;
    private int read_req =100 ,read_req2= 300;
    private int camera_req = 400;
    private String attachment ="";
    private User user;
    private String read_perm = Manifest.permission.READ_EXTERNAL_STORAGE;
    private String write_perm = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private String camera_perm = Manifest.permission.CAMERA;
    private ChatAdapter adapter;
    private List<MessageModel> messageModelList;
    private ChatPresenter presenter;
    private LinearLayoutManager manager;
    private boolean isNewMessage = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("data")) {
            chatUserModel = (ChatUserModel) intent.getSerializableExtra("data");
        } else {
            finish();
        }
    }

    private void initView() {
        presenter = new ChatPresenter(this, this);
        messageModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences("userData", MODE_PRIVATE);
        Gson gson = new Gson();
        String userString = preferences.getString("user", "");
        user = gson.fromJson(userString, User.class);

        binding.tvName.setText(chatUserModel.getFrom_name());
        Picasso.get().load(Uri.parse(chatUserModel.getFrom_image())).placeholder(R.drawable.default_avatar).fit().into(binding.imageAvatar);

        adapter = new ChatAdapter(messageModelList, this, user.getId(), chatUserModel.getFrom_image());

        manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        binding.recView.setLayoutManager(manager);
        binding.recView.setAdapter(adapter);

        binding.imageFile.setOnClickListener(view -> {
            checkPermission();
        });

        binding.imageDeleteAttachment.setOnClickListener(view -> {
            attachment = "";
            binding.cardView.setVisibility(View.GONE);
        });

        binding.toolBar.setNavigationOnClickListener(view -> {
            finish();

        });

        binding.imageSelectImage.setOnClickListener(view ->{
            if (binding.exandLayout.isExpanded())
            {
                binding.exandLayout.collapse(true);
            }else
            {
                binding.exandLayout.expand(true);
            }
        });
        binding.btnHide.setOnClickListener(view ->{
            binding.exandLayout.collapse(true);
        });

        binding.imgGallery.setOnClickListener(view -> {
            binding.exandLayout.collapse(true);
            checkReadPermission();

        });
        binding.imgCamera.setOnClickListener(view ->{
            binding.exandLayout.collapse(true);
            checkCameraPermission();

        } );

        binding.btnSend.setOnClickListener(view -> {
            String message = binding.edtMessage.getText().toString().trim();
            if (!message.isEmpty() || !attachment.isEmpty()) {

                binding.cardView.setVisibility(View.GONE);
                presenter.sendMessage(chatUserModel.getChat_id(), user.getId(), chatUserModel.getFrom_id(), message, attachment);
                binding.edtMessage.setText("");
                attachment = "";
            }

        });

        binding.recView.addOnLayoutChangeListener((view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom < oldBottom) {
                binding.recView.postDelayed(() -> binding.recView.smoothScrollToPosition(messageModelList.size() - 1), 10);
            }

        });
        Log.e("chat_id", chatUserModel.getChat_id() + "__conv" + chatUserModel.getConversation_id() + "_from" + chatUserModel.getFrom_id() + "__userid" + user.getId());

        presenter.getAllChatMessages(chatUserModel.getChat_id(), chatUserModel.getConversation_id(), chatUserModel.getFrom_id(), user.getId());


    }




    private void navigateToFileActivity() {

        Intent intent = new Intent(this, FileActivity.class);
        startActivityForResult(intent,200);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, read_perm) == PackageManager.PERMISSION_GRANTED) {
            navigateToFileActivity();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{read_perm}, read_req);
        }
    }

    private void checkReadPermission()
    {
        if (ContextCompat.checkSelfPermission(this, read_perm) == PackageManager.PERMISSION_GRANTED) {

            selectImage(read_req2);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{read_perm}, read_req2);
        }
    }
    private void checkCameraPermission()
    {
        if (ContextCompat.checkSelfPermission(this, write_perm) == PackageManager.PERMISSION_GRANTED
                &&ContextCompat.checkSelfPermission(this, camera_perm) == PackageManager.PERMISSION_GRANTED
        ) {
            selectImage(camera_req);

        } else {
            ActivityCompat.requestPermissions(this, new String[]{write_perm,camera_perm}, camera_req);
        }
    }

    private void selectImage(int req)
    {
        Intent intent = new Intent();
        if (req==read_req2)
        {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
            {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.setType("image/*");

            }else
            {
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("image/*");

            }

        }else
            {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            }

        startActivityForResult(intent,req);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == read_req) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               navigateToFileActivity();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode==camera_req)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {


            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode==read_req2)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==200&&resultCode==RESULT_OK&&data!=null)
        {
            FileModel fileModel = (FileModel) data.getSerializableExtra("data");

            if (fileModel!=null)
            {
                attachment = fileModel.getPath();
                binding.tvAttachmentName.setText(fileModel.getName());
                if (fileModel.getName().endsWith(".pdf"))
                {
                    binding.imageAttachment.setImageResource(R.drawable.pdf);
                }else if (fileModel.getName().endsWith(".doc")||fileModel.getName().endsWith(".docx"))
                {
                    binding.imageAttachment.setImageResource(R.drawable.word);

                }
                binding.cardView.setVisibility(View.VISIBLE);

            }

        } else if (requestCode == read_req2 && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            Picasso.get().load(uri).fit().into(binding.imageAttachment);
            attachment = Common.getImagePath(this,uri);
            File file = new File(attachment);
            binding.tvAttachmentName.setText(file.getName());
            binding.cardView.setVisibility(View.VISIBLE);


        } else if (requestCode == camera_req && resultCode == RESULT_OK && data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            binding.imageAttachment.setImageBitmap(bitmap);
            Uri uri = getBitmapFromUri(bitmap);
            attachment = Common.getImagePath(this,uri);
            File file = new File(attachment);
            binding.tvAttachmentName.setText(file.getName());
            binding.cardView.setVisibility(View.VISIBLE);

        }
    }

    private Uri getBitmapFromUri(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        return Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"",""));
    }


    @Override
    public void hideProgress() {
        binding.progBar.setVisibility(View.GONE);
    }

    @Override
    public void onMessageSendSuccess(MessageModel messageModel) {
        isNewMessage = true;
        messageModelList.add(messageModel);
        adapter.notifyItemInserted(messageModelList.size() - 1);
        binding.recView.postDelayed(() -> binding.recView.smoothScrollToPosition(messageModelList.size() - 1), 100);

    }

    @Override
    public void onMessagesSuccess(MessageDataModel messageDataModel) {
        if (adapter != null) {
            adapter.setBase_url_image(messageDataModel.getBase());
        }
        messageModelList.clear();
        messageModelList.addAll(messageDataModel.getRecords());
        adapter.notifyDataSetChanged();
        binding.recView.postDelayed(() -> binding.recView.smoothScrollToPosition(messageModelList.size() - 1), 1500);

    }

    @Override
    public void onError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        intent.putExtra("refresh", isNewMessage);
        setResult(RESULT_OK, intent);
        finish();
    }
}
