package com.rupesh.audiohubapp.test.User;

import com.rupesh.audiohubapp.helper.UserHelperAbstract;

public class UserHelperMock extends UserHelperAbstract {

    Boolean isEmpty;
    Boolean patternMatched;

    @Override
    public boolean isEmpty(String userInput) {
        return isEmpty;
    }

    @Override
    public boolean patternEmailMatcher(String userEmail) {
        return patternMatched;
    }

    public void setEmpty(Boolean empty) {
        this.isEmpty = empty;
    }

    public void setPatternMatched(Boolean patternMatched) {
        this.patternMatched = patternMatched;
    }
}
