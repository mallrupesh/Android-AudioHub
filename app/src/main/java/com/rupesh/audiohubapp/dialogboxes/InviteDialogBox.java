package com.rupesh.audiohubapp.dialogboxes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.helper.NetworkHelper;
import com.rupesh.audiohubapp.helper.RequestState;
import com.rupesh.audiohubapp.interfaces.InterfaceDecline;
import com.rupesh.audiohubapp.interfaces.InterfaceInvite;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;

public class InviteDialogBox extends DialogFragment implements InterfaceInvite, InterfaceDecline {
    private ImageView mProfileImgView;
    private TextView mDisplayUserName;
    private TextView mDisplayUserStatus;
    private Button mInviteButton;
    private Button mDeclineButton;

    private User user;


    private FirebaseUser mCurrentUser;

    private Project project;
    private String projectId;
    private NetworkHelper networkHelper;
    private int currentState;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_invite_member, null);

        builder.setView(view).setTitle("Invitation");

        mProfileImgView = view.findViewById(R.id.invite_dialog_user_image);
        mDisplayUserName = view.findViewById(R.id.invite_dialog_display_name);
        mDisplayUserStatus = view.findViewById(R.id.invite_dialog_display_status);
        mInviteButton = view.findViewById(R.id.invite_dialog_user_btn);
        mDeclineButton = view.findViewById(R.id.decline_dialog_user_btn);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();


        // Get the data sent by AllUserActivity as Bundle since InviteDialogBox is a fragment on top of
        // AllUserActivity
        Bundle bundle = getArguments();
        user = (User) bundle.getSerializable("user");

        project = (Project) bundle.getSerializable("project");

        networkHelper = new NetworkHelper(project, user);
        networkHelper.interfaceInvite = this;
        networkHelper.interfaceDecline = this;

        mDisplayUserName.setText(user.getName());
        mDisplayUserStatus.setText(user.getStatus());

        Glide.with(requireActivity()).load(user.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.default_avatar))
                .into(mProfileImgView);


        //Default state
        currentState = RequestState.NOT_IN_PROJECT;

        // Check Decline Button Visibility
        checkDeclineButton();

        networkHelper.searchUser();

        mInviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInviteButton.setEnabled(false);
                if (currentState == RequestState.NOT_IN_PROJECT) {
                    networkHelper.sendRequest();
                }

                if (currentState == RequestState.REQUEST_SENT) {
                    networkHelper.cancelRequest();
                }

                if (currentState == RequestState.REQUEST_RECEIVED) {
                    networkHelper.acceptRequest();
                }

                dismiss();
            }
        });

        mDeclineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeclineButton.setEnabled(false);
                if (currentState == RequestState.REQUEST_RECEIVED) {
                    networkHelper.declineRequest();
                }
                dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void inviteNetworkCallback(String text, int state, int visibility) {
        // Set Invite button settings
        mInviteButton.setEnabled(true);
        mInviteButton.setVisibility(visibility);
        mInviteButton.setText(text);
        currentState = state;
    }

    @Override
    public void declineNetworkCallback(String text, int state, int visibility) {
        // Set Decline button settings
        mDeclineButton.setEnabled(true);
        mDeclineButton.setVisibility(visibility);
        mDeclineButton.setText(text);
        currentState = state;
    }

    // Check Decline button visibility
    private void checkDeclineButton() {
        if (project != null) {
            mDeclineButton.setVisibility(View.INVISIBLE);
        } else {
            mDeclineButton.setVisibility(View.VISIBLE);
        }
    }
}
