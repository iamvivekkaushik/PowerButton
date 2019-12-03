package com.vivekkaushik.powerbutton;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.N)
public class LockService extends TileService {
    @Override
    public void onClick() {
        lockDevice();
    }

    void lockDevice() {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        boolean active = Objects.requireNonNull(devicePolicyManager)
                .isAdminActive(new ComponentName(this, MyAdmin.class));

        if (active) {
            devicePolicyManager.lockNow();
        } else {
            Toast.makeText(this, "You need to enable the Admin Device Features",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
