package com.sunland.hzhc.modules.sfz_module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NfcReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ((OnGetNfcDataListener) context).onReceiveDate(intent);
    }

    public interface OnGetNfcDataListener {
        public void onReceiveDate(Intent intent);
    }
}
