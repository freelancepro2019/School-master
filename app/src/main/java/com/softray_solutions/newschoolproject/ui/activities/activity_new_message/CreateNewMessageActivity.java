package com.softray_solutions.newschoolproject.ui.activities.activity_new_message;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
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
import com.softray_solutions.newschoolproject.adapters.SelectedUserCategoryAdapter;
import com.softray_solutions.newschoolproject.adapters.SpinnerCategoryAdapter;
import com.softray_solutions.newschoolproject.adapters.UserCategoryAdapter;
import com.softray_solutions.newschoolproject.data.network.service.Common;
import com.softray_solutions.newschoolproject.data.sharedPref.AppPrefsHelper;
import com.softray_solutions.newschoolproject.databinding.ActivityCreateNewMessageBinding;
import com.softray_solutions.newschoolproject.model.CategoryModel;
import com.softray_solutions.newschoolproject.model.FileModel;
import com.softray_solutions.newschoolproject.model.User;
import com.softray_solutions.newschoolproject.model.UserCategoryModel;
import com.softray_solutions.newschoolproject.ui.activities.activity_file.FileActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreateNewMessageActivity extends AppCompatActivity implements NewMessageView{
    private ActivityCreateNewMessageBinding binding;
    private SharedPreferences preferences;
    private int read_req =100 ,read_req2= 300;
    private int camera_req = 400, write_req = 500;
    private String attachment ="";
    private User user;
    private String read_perm = Manifest.permission.READ_EXTERNAL_STORAGE;
    private String write_perm = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private String camera_perm = Manifest.permission.CAMERA;
    private ProgressDialog dialog;
    private List<CategoryModel> categoryModelList;
    private List<UserCategoryModel> userCategoryModelList,selectedUserCategoryModelList;
    private SpinnerCategoryAdapter spinnerCategoryAdapter;
    private UserCategoryAdapter userCategoryAdapter;
    private List<String> selectedIdsList;
    private SelectedUserCategoryAdapter adapter;
    private NewMessagePresenter presenter;
    private AppPrefsHelper appPrefsHelper;
    private String category ="";
    private String lang = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_new_message);
        initView();
    }

    private void initView() {
        selectedUserCategoryModelList = new ArrayList<>();
        selectedIdsList = new ArrayList<>();
        categoryModelList = new ArrayList<>();
        categoryModelList.add(new CategoryModel(getString(R.string.category),""));

        userCategoryModelList = new ArrayList<>();
        userCategoryModelList.add(new UserCategoryModel("0",getString(R.string.groups),""));

        presenter = new NewMessagePresenter(this,this);
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialog = Common.createProgressDialog(this,getString(R.string.please_wait));
        dialog.setCancelable(false);

        preferences = getSharedPreferences("userData", MODE_PRIVATE);
        appPrefsHelper = new AppPrefsHelper(preferences);

        if (appPrefsHelper.getSelectedLanguage().equals("not selected"))
        {
            if (Locale.getDefault().getLanguage().equals("ar"))
            {
                lang = "arabic";
            }else
                {
                    lang ="english";
                }
        }else
        {

            if (appPrefsHelper.getSelectedLanguage().equals("ar"))
            {
                lang = "arabic";
            }else
            {
                lang ="english";
            }

        }

        Gson gson = new Gson();
        String userString = preferences.getString("user", "");
        user = gson.fromJson(userString, User.class);

        binding.tvName.setText(user.getName());
        Picasso.get().load(Uri.parse(user.getImg())).placeholder(R.drawable.default_avatar).fit().into(binding.imageAvatar);

        ///////////////////////////////spinners////////////////////////////
        spinnerCategoryAdapter = new SpinnerCategoryAdapter(categoryModelList,this);
        binding.spinnerCategory.setAdapter(spinnerCategoryAdapter);

        userCategoryAdapter = new UserCategoryAdapter(userCategoryModelList,this);
        binding.spinnerTo.setAdapter(userCategoryAdapter);


        binding.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0)
                {
                    category = "";
                    userCategoryModelList.clear();
                    userCategoryModelList.add(new UserCategoryModel("0",getString(R.string.groups),""));
                    userCategoryAdapter.notifyDataSetChanged();
                }else
                    {
                        category = categoryModelList.get(i).getValue();
                        presenter.getUsersByCategory(user.getId(),user.getSchoolID(),lang,category);

                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0)
                {
                    UserCategoryModel model = userCategoryModelList.get(i);
                    if (!isUserAdded(model.getID()))
                    {
                        selectedUserCategoryModelList.add(model);
                        adapter.notifyItemInserted(selectedUserCategoryModelList.size()-1);
                        selectedIdsList.add(model.getID());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ///////////////////////////////////////////////////////////////////
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SelectedUserCategoryAdapter(selectedUserCategoryModelList,this);
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
            if ((!message.isEmpty() || !attachment.isEmpty())) {

                if (selectedIdsList.size()>0)
                {
                    presenter.sendFirstMessage(user.getId(),user.getSchoolID(),category,selectedIdsList,message,attachment);
                    binding.cardView.setVisibility(View.GONE);
                    binding.edtMessage.setText("");
                    attachment = "";
                }else
                    {
                        Toast.makeText(this, R.string.ch_group, Toast.LENGTH_SHORT).show();
                    }

            }

        });

        presenter.getCategoryByCurrentUserType(user.getType().toUpperCase());
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
                selectImage(camera_req);

            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode==read_req2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage(read_req2);
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


    private void navigateToFileActivity() {

        Intent intent = new Intent(this, FileActivity.class);
        startActivityForResult(intent,200);
    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    @Override
    public void onCategorySuccess(List<CategoryModel> categoryModelList) {
        if (categoryModelList.size()>0)
        {
            this.categoryModelList.clear();
            this.categoryModelList.add(new CategoryModel(getString(R.string.category),""));
            this.categoryModelList.addAll(categoryModelList);
            runOnUiThread(() -> {
                spinnerCategoryAdapter.notifyDataSetChanged();;
            });
        }

    }

    @Override
    public void onUserByCategorySuccess(List<UserCategoryModel> userCategoryModelList) {
        if (userCategoryModelList.size()>0)
        {
            this.userCategoryModelList.clear();
            this.userCategoryModelList.add(new UserCategoryModel("0",getString(R.string.groups),""));
            this.userCategoryModelList.addAll(userCategoryModelList);

            runOnUiThread(() -> {
                userCategoryAdapter.notifyDataSetChanged();
                binding.spinnerTo.setSelection(0);
            });

        }
    }

    @Override
    public void onMessageSentSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    public void setItemDataToDelete(UserCategoryModel model2, int adapterPosition) {
        selectedUserCategoryModelList.remove(adapterPosition);
        adapter.notifyItemRemoved(adapterPosition);
        selectedIdsList.remove(adapterPosition);
        if (selectedIdsList.size()==0)
        {
            binding.spinnerTo.setSelection(0);
        }
    }


    private boolean isUserAdded(String user_id)
    {
        for (String id : selectedIdsList)
        {
            if (id.equals(user_id))
            {
                return true;
            }
        }

        return false;
    }
}
