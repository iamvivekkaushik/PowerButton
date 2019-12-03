package com.vivekkaushik.powerbutton;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button disable;
    private Button enable;
    public static final int RESULT_ENABLE = 11;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName compName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        compName = new ComponentName(this, MyAdmin.class);

        Button lock = findViewById(R.id.lock);
        enable = findViewById(R.id.enableBtn);
        disable = findViewById(R.id.disableBtn);

        lock.setOnClickListener(view -> {
            boolean active = devicePolicyManager.isAdminActive(compName);

            if (active) {
                devicePolicyManager.lockNow();
            } else {
                Toast.makeText(this, "You need to enable the Admin Device Features",
                        Toast.LENGTH_SHORT).show();
            }
        });
        enable.setOnClickListener(view -> {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "To be able to lock or unlock the device this app requires " +
                            "additional permission.");
            startActivityForResult(intent, RESULT_ENABLE);
        });
        disable.setOnClickListener(view -> {
            devicePolicyManager.removeActiveAdmin(compName);
            disable.setVisibility(View.GONE);
            enable.setVisibility(View.VISIBLE);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isActive = devicePolicyManager.isAdminActive(compName);
        disable.setVisibility(isActive ? View.VISIBLE : View.GONE);
        enable.setVisibility(isActive ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_ENABLE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(MainActivity.this, "You have enabled the Admin Device features", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Problem to enable the Admin Device features", Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
