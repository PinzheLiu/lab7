package com.cs407.lab7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.RemoteInput;

public class ReplyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        int id = intent.getIntExtra("id", -1);

        if (remoteInput != null) {
            CharSequence charSequence = remoteInput.getCharSequence(NotificationHelper.TEXT_REPLY);
            if (charSequence == null) return;

            Toast.makeText(context, context.getString(R.string.replied, charSequence, id), Toast.LENGTH_LONG).show();
            NotificationHelper.getInstance().showNotification(context, id);
        }
    }
}
