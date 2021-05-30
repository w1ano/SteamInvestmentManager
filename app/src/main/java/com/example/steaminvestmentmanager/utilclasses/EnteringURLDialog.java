package com.example.steaminvestmentmanager.utilclasses;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.steaminvestmentmanager.MainActivity;
import com.example.steaminvestmentmanager.R;

import org.w3c.dom.EntityReference;

public class EnteringURLDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.entering_url_dialog, null);
        v.findViewById(R.id.accept_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredURL = ((EditText) v.findViewById(R.id.entered_url)).getText().toString();
                MainActivity.sendEnteredURL(enteredURL);
            }
        });
        v.findViewById(R.id.cancel_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return v;
    }
}
