package com.softray_solutions.newschoolproject.ui.activities.code;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.jaydenxiao.guider.HighLightGuideView;
import com.softray_solutions.newschoolproject.Customization;
import com.softray_solutions.newschoolproject.R;
import com.softray_solutions.newschoolproject.data.network.service.RetrofitClient;
import com.softray_solutions.newschoolproject.ui.activities.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.softray_solutions.newschoolproject.BaseApp.schoolNameVariable;

public class CodeActivity extends AppCompatActivity {

    @BindView(R.id.et_user_code)
    EditText codeEditText;
    Unbinder unbinder;
    @BindView(R.id.button_code)
    Button buttonCode;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        bindView();
        HighLightGuideView.builder(this).addHighLightGuidView(codeEditText, R.drawable.guide)
                .setHighLightStyle(HighLightGuideView.VIEWSTYLE_RECT).show();

        sharedPreferences = getSharedPreferences("code", MODE_PRIVATE);
        buttonCode.setOnClickListener(v -> {
            String code = codeEditText.getText().toString();
            switch (code) {
                case "3512":
                    schoolNameVariable = Customization.demo;
                    submitCode(Customization.demo);
                    break;
                case "5708":
                    schoolNameVariable = Customization.ibnRushdSchools;
                    submitCode(Customization.ibnRushdSchools);
                    break;
                case "5347":
                    schoolNameVariable = Customization.beetElQeemSchools;
                    submitCode(Customization.beetElQeemSchools);
                    break;
                case "5828":
                    schoolNameVariable = Customization.zaidSchools;
                    submitCode(Customization.zaidSchools);
                    break;
                case "6928":
                    schoolNameVariable = Customization.andaulsSchools;
                    submitCode(Customization.andaulsSchools);
                    break;
                case "8429":
                    schoolNameVariable = Customization.abhaSchools;
                    submitCode(Customization.abhaSchools);
                    break;
                case "1070":
                    schoolNameVariable = Customization.ahdSchools;
                    submitCode(Customization.ahdSchools);
                    break;
                default:
                    codeEditText.setError("الكود غير صحيح");
                    break;
            }
        });
    }

    private void submitCode(String code) {
        sharedPreferences.edit().remove("selectedCode").apply();
        sharedPreferences.edit().putString("selectedCode", code).apply();
        RetrofitClient.invalidateClient();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void bindView() {
        unbinder = ButterKnife.bind(this);
    }
}