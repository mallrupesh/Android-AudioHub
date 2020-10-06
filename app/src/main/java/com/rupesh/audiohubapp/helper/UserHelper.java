package com.rupesh.audiohubapp.helper;

import android.text.TextUtils;
import android.util.Patterns;


public class UserHelper extends UserHelperAbstract{

    public boolean isEmpty(String userInput) {
        return TextUtils.isEmpty(userInput);
    }

    public boolean patternEmailMatcher(String userEmail) {
        return Patterns.EMAIL_ADDRESS.matcher(userEmail).matches();
    }
}
