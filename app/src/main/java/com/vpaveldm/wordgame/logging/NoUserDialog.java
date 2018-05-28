package com.vpaveldm.wordgame.logging;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.vpaveldm.wordgame.R;

public class NoUserDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.dialog_no_user_view, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view)
                .setPositiveButton(R.string.label_ok, null);
        return builder.create();
    }
}
