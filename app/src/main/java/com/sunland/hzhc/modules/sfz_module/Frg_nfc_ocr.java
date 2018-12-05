package com.sunland.hzhc.modules.sfz_module;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.sunland.hzhc.Ac_main;

public class Frg_nfc_ocr extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(new String[]{"NFC扫描", "身份证扫描"}, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ((Ac_main) getContext()).onChoose(which);
            }
        });
        return builder.create();

    }
}
