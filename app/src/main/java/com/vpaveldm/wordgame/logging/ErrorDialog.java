package com.vpaveldm.wordgame.logging;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.vpaveldm.wordgame.R;

public class ErrorDialog extends DialogFragment {

    private static final String KEY_MESSAGE = "KEY_MESSAGE";
    private String message;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args == null)
            throw new NullPointerException(getString(R.string.illegal_access));
        message = args.getString(KEY_MESSAGE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.dialog_error_view, null);
        TextView messageTV = view.findViewById(R.id.errorDialogTextView);
        messageTV.setText(message);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view)
                .setPositiveButton(R.string.label_ok, null);
        return builder.create();
    }

    public void prepareBundle(String message) {
        Bundle args = new Bundle();
        args.putString(KEY_MESSAGE, message);
        setArguments(args);
    }
}
