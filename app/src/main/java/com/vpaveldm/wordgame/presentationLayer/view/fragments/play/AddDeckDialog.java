package com.vpaveldm.wordgame.presentationLayer.view.fragments.play;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.vpaveldm.wordgame.R;
import com.vpaveldm.wordgame.databinding.FragmentAddDeckBinding;
import com.vpaveldm.wordgame.presentationLayer.viewModel.PlayViewModel;

public class AddDeckDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        FragmentAddDeckBinding binding = FragmentAddDeckBinding.inflate(inflater, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(binding.getRoot())
                .setPositiveButton(getString(R.string.label_ok), (dialog, which) -> {
                    AppCompatActivity activity = (AppCompatActivity) getActivity();
                    assert activity != null;
                    PlayViewModel viewModel = ViewModelProviders.of(activity).get(PlayViewModel.class);
                    viewModel.addDeck(binding.deckNameET.getText().toString());
                });
        return builder.create();
    }
}
