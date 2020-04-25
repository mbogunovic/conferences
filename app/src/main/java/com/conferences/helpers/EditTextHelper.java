package com.conferences.helpers;

import android.view.View;
import android.widget.EditText;

import com.conferences.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    public static boolean CheckDateText(EditText et, View view){
        boolean isTextInvalid = false;

        try {
            SimpleDateFormat.getDateTimeInstance().parse(et.getText().toString());
        } catch (ParseException e) {
            isTextInvalid = true;
        }

        if(isTextInvalid){
            et.setError(view.getResources().getString(R.string.validation_date));
        }
        else{
            et.setError(null);
        }

        return !isTextInvalid;
    }
}
