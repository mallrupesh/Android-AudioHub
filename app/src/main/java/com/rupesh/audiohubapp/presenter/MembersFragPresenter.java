package com.rupesh.audiohubapp.presenter;

import com.rupesh.audiohubapp.fragments.MembersFragment;
import com.rupesh.audiohubapp.helper.MemberNetworkHelper;
import com.rupesh.audiohubapp.interfaces.InterfaceMemberCallback;
import com.rupesh.audiohubapp.model.User;

import java.util.ArrayList;

public class MembersFragPresenter implements InterfaceMemberCallback {

    private MemberNetworkHelper memberNetworkHelper;
    private MembersFragment membersFragment;

    public MembersFragPresenter(MembersFragment membersFragment) {
        this.membersFragment = membersFragment;
        memberNetworkHelper = new MemberNetworkHelper(membersFragment.getProject());
        memberNetworkHelper.interfaceMemberCallback = this;
    }

    public void displayMembers() {
        memberNetworkHelper.getMember();
    }

    @Override
    public void mapUser(ArrayList<User> members) {
        membersFragment.initUI(members);
    }
}
