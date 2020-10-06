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
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.helper.NetworkHelper;
import com.rupesh.audiohubapp.interfaces.InterfaceDecline;
import com.rupesh.audiohubapp.interfaces.InterfaceInvite;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.RequestState;
import com.rupesh.audiohubapp.model.User;
import com.rupesh.audiohubapp.presenter.DialogBoxPresenter;


/**
 * Handles the Invitation mechanism
 */
public class InviteDialogBox extends DialogFragment implements InterfaceInvite, InterfaceDecline {
    private ImageView mProfileImgView;
    private TextView mDisplayUserName;
    private TextView mDisplayUserStatus;
    private TextView mDisplayEmail;
    private Button mInviteButton;
    private Button mDeclineButton;

    private User user;

    private Project project;
    private NetworkHelper networkHelper;
    private int currentState;

    private DialogBoxPresenter dialogBoxPresenter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_invite_member, null);
        builder.setView(view).setTitle("Invitation");

        // Init UI components
        mProfileImgView = view.findViewById(R.id.invite_dialog_user_image);
        mDisplayUserName = view.findViewById(R.id.invite_dialog_display_name);
        mDisplayUserStatus = view.findViewById(R.id.invite_dialog_display_status);
        mDisplayEmail = view.findViewById(R.id.invite_dialog_displayEmail_txt);
        mInviteButton = view.findViewById(R.id.invite_dialog_user_btn);
        mDeclineButton = view.findViewById(R.id.decline_dialog_user_btn);

        // Get bundle of data to be tracked from SearchActivity
        Bundle bundle = getArguments();

        // Here user is the one who is being invited to the project
        user = (User) bundle.getSerializable("user");

        // Here project is the one which is being invited into
        project = (Project) bundle.getSerializable("project");

        dialogBoxPresenter = new DialogBoxPresenter();

        // Init NetworkHelper that triggers the database operations
        networkHelper = new NetworkHelper(project, user);
        networkHelper.interfaceInvite = this;
        networkHelper.interfaceDecline = this;

        setUserInfo();

        currentState = RequestState.NOT_IN_PROJECT;

        checkDeclineButton();

        networkHelper.handleInvitationRequest();

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

    /**
     * InterfaceInvite method called by NetworkHelper handleInvitationRequest method
     * @param text invitation text
     * @param state request state
     * @param visibility invitation button visibility
     */
    @Override
    public void inviteNetworkCallback(String text, int state, int visibility) {
        // Set Invite button settings
        mInviteButton.setEnabled(true);
        mInviteButton.setVisibility(visibility);
        mInviteButton.setText(text);
        currentState = state;
    }

    /**
     * InterfaceDecline method called by NetworkHelper handleInvitationRequest method
     * @param text invitation text
     * @param state request state
     * @param visibility invitation button visibility
     */
    @Override
    public void declineNetworkCallback(String text, int state, int visibility) {
        // Set Decline button settings
        mDeclineButton.setEnabled(true);
        mDeclineButton.setVisibility(visibility);
        mDeclineButton.setText(text);
        currentState = state;
    }

    /**
     * Init UI
     */
    public void setUserInfo() {
        mDisplayUserName.setText(user.getName());
        mDisplayUserStatus.setText(user.getStatus());
        mDisplayEmail.setText(user.getEmail());
        Glide.with(requireActivity()).load(user.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.default_avatar))
                .into(mProfileImgView);
    }

    /**
     *  Check Decline button visibility
     */
    private void checkDeclineButton() {
        if (project != null) {
            mDeclineButton.setVisibility(View.INVISIBLE);
        } else {
            mDeclineButton.setVisibility(View.VISIBLE);
        }

        // If the current user selects his own view in recycler view list
        if(user.getUid().equals(dialogBoxPresenter.getCurrentAppUser().getUid())) {
            mInviteButton.setEnabled(false);
            mInviteButton.setVisibility(View.INVISIBLE);
            mInviteButton.setEnabled(false);
            mInviteButton.setVisibility(View.INVISIBLE);
        }
    }
}
