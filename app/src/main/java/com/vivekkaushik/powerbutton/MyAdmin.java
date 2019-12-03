package com.vivekkaushik.powerbutton;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyAdmin extends DeviceAdminReceiver {
    @Override
    public void onEnabled(@NonNull Context context, @NonNull Intent intent) {
        Toast.makeText(context, "Device Admin : disabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(@NonNull Context context, @NonNull Intent intent) {
        Toast.makeText(context, "Device Admin : disabled", Toast.LENGTH_SHORT).show();
    }
}
