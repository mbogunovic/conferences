package com.conferences.helpers;

import android.view.View;
import android.widget.EditText;

import com.conferences.R;

public class EditTextHelper {
    public static boolean CheckMandatoryText(EditText et, View view){
        boolean isTextInvalid = StringHelper.isNullOrWhitespace(et.getText().toString());

        if (isTextInvalid) {
            et.setError(view.getResources().getString(R.string.validation_mandatory));
        }

        return !isTextInvalid;
    }
}
