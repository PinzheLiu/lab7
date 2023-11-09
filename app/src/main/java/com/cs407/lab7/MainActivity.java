package com.cs407.lab7;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    Toast.makeText(this, R.string.please_allow_notification, Toast.LENGTH_LONG).show();
                }
            });

    private void requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            //Notification Permission not required till Android 13 (Tiramisu)
            return;
        }
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED) {
            //Permission not granted
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonSend = findViewById(R.id.buttonSend);
        EditText editTextSender = findViewById(R.id.editTextSender);
        EditText editTextMessage = findViewById(R.id.editTextMessage);

        requestPermission();
        NotificationHelper.getInstance().createNotificationChannel(getApplicationContext());

        buttonSend.setOnClickListener(view -> {
            NotificationHelper.getInstance().setNotificationContent(
                    editTextSender.getText().toString(), editTextMessage.getText().toString()
            );
            NotificationHelper.getInstance().showNotification(getApplicationContext());
        });
    }
}