package com.conferences.helpers;

import android.os.Build;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.conferences.R;

public class EditTextHelper {
    public static boolean CheckMandatoryText(EditText et, View view){
        boolean isTextInvalid = StringHelper.isNullOrWhitespace(et.getText().toString());

        if (isTextInvalid) {
            et.setError(view.getResources().getString(R.string.validation_mandatory));
        }
        else{
            et.setError(null);
        }

        return !isTextInvalid;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean CheckDateText(EditText et, View view){
        boolean isTextInvalid = false;

        isTextInvalid = CalendarHelper.parse(et.getText().toString()) == null;

        if(isTextInvalid){
            et.setError(view.getResources().getString(R.string.validation_date));
        }
        else{
            et.setError(null);
        }

        return !isTextInvalid;
    }
}
