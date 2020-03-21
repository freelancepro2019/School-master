package com.softray_solutions.newschoolproject.ui.activities.activity_file;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.adapters.FileAdapter;
import com.softray_solutions.newschoolproject.databinding.ActivityFileBinding;
import com.softray_solutions.newschoolproject.model.FileModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileActivity extends AppCompatActivity {
    private ActivityFileBinding binding;
    private FileAdapter fileAdapter;
    private List<FileModel> files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file);
        initView();
    }

    private void initView() {

        files = new ArrayList<>();
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        fileAdapter = new FileAdapter(files, this);
        binding.recView.setAdapter(fileAdapter);

        new Task().execute();

        binding.toolBar.setNavigationOnClickListener(view -> {
            finish();

        });
    }


    private void getFiles(File dir) {

        File[] files = dir.listFiles();

        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {

                    getFiles(files[i]);
                } else {
                    if (files[i].getName().endsWith(".pdf") || files[i].getName().endsWith(".doc") || files[i].getName().endsWith(".docx")) {
                        String size = getFileSize(files[i].length());
                        FileModel fileModel = new FileModel(files[i].getName(), size, files[i].getAbsolutePath());
                        FileActivity.this.files.add(fileModel);
                    }
                }


            }


        }

    }

    private String getFileSize(long usedSpace) {
        long Kb = 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (usedSpace < Kb) return usedSpace + " byte";
        if (usedSpace >= Kb && usedSpace < Mb)
            return (String.format(Locale.ENGLISH, "%.2f", (double) usedSpace / Kb)) + " Kb";
        if (usedSpace >= Mb && usedSpace < Gb)
            return (String.format(Locale.ENGLISH, "%.2f", (double) usedSpace / Mb)) + " Mb";
        if (usedSpace >= Gb && usedSpace < Tb)
            return (String.format(Locale.ENGLISH, "%.2f", (double) usedSpace / Gb)) + " Gb";
        if (usedSpace >= Tb && usedSpace < Pb)
            return (String.format(Locale.ENGLISH, "%.2f", (double) usedSpace / Tb)) + " Tb";
        if (usedSpace >= Pb && usedSpace < Eb)
            return (String.format(Locale.ENGLISH, "%.2f", (double) usedSpace / Pb)) + " Pb";
        if (usedSpace >= Eb)
            return (String.format(Locale.ENGLISH, "%.2f", (double) usedSpace / Eb)) + " Eb";

        return "";
    }


    public void setItemData(FileModel model) {
        Intent intent = getIntent();
        intent.putExtra("data", model);
        setResult(RESULT_OK, intent);
        finish();
    }

    private class Task extends AsyncTask<Void, Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            getFiles(Environment.getExternalStorageDirectory());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (files.size()>0)
            {
                binding.tvNoFile.setVisibility(View.GONE);
                fileAdapter.notifyDataSetChanged();
            }else
                {
                    binding.tvNoFile.setVisibility(View.VISIBLE);

                }

        }
    }
}
