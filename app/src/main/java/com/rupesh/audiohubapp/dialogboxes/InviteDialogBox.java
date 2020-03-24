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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rupesh.audiohubapp.R;
import com.rupesh.audiohubapp.helper.NetworkHelper;
import com.rupesh.audiohubapp.helper.RequestState;
import com.rupesh.audiohubapp.interfaces.InterfaceInvite;
import com.rupesh.audiohubapp.model.Project;
import com.rupesh.audiohubapp.model.User;

public class InviteDialogBox extends DialogFragment implements InterfaceInvite {
    private ImageView mProfileImgView;
    private TextView mDisplayUserName;
    private TextView mDisplayUserStatus;
    private Button mInviteButton;
    private Button mDeclineButton;

    private User user;

    //public InterfaceInvite inviteInterface;
    //public InterfaceDecline declineInterface;

    private DatabaseReference inviteDatabaseRef;
    private DatabaseReference projectDatabaseRef;
    private FirebaseUser mCurrentUser;

    private Project project;
    private String projectId;
    private NetworkHelper networkHelper;
    int currentState;
    int state;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_invite_member, null);

        builder.setView(view).setTitle("Invitation");

        mProfileImgView = view.findViewById(R.id.invite_dialog_user_image);
        mDisplayUserName = view.findViewById(R.id.invite_dialog_display_name);
        mDisplayUserStatus = view. findViewById(R.id.invite_dialog_display_status);
        mInviteButton = view.findViewById(R.id.invite_dialog_user_btn);
        mDeclineButton= view.findViewById(R.id.decline_dialog_user_btn);

        inviteDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Invite_Requests");
        projectDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Projects");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        // default value
       // currentState = RequestState.NOT_IN_PROJECT;
       // System.out.println(currentState + "-------");


       // currentState = state;



        // get the data sent by AllUserActivity as Bundle since InviteDialogBox is a fragment on top of
        // AllUserActivity
        Bundle bundle = getArguments();
        user = (User) bundle.getSerializable("user");
        System.out.println(user +"-------");

        project = (Project) bundle.getSerializable("project");
        System.out.println(project+"----------");


        networkHelper = new NetworkHelper(project,user);
        networkHelper.interfaceInvite = this;

        mDisplayUserName.setText(user.getName());
        mDisplayUserStatus.setText(user.getStatus());

        Glide.with(requireActivity()).load(user.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.default_avatar))
                .into(mProfileImgView);


        networkHelper.searchUser();


       /* mDeclineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //declineInterface.declineMember(user, currentState);

                dismiss();
            }
        });*/


       mInviteButton.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               mInviteButton.setEnabled(false);
               if (currentState == RequestState.NOT_IN_PROJECT){
                   networkHelper.sendRequest();
               }

               if (currentState == RequestState.REQUEST_SENT) {
                   networkHelper.cancelRequest();
               }

               if (currentState == RequestState.REQUEST_RECEIVED) {
                   networkHelper.acceptRequest();
               }

               if (currentState == RequestState.IN_PROJECT) {

               }

               dismiss();
           }

       });


        return builder.create();
    }

    @Override
    public void inviteNetworkCallback(String text, int state, int visiblity) {
        // setting mInviteButton
        mInviteButton.setEnabled(true);
        mInviteButton.setVisibility(visiblity);
        mInviteButton.setText(text);
        currentState = state;

    }



    // not used
    public void searchUser() {
        /*FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(mUserDataRef, User.class)
                        .build();*/

        // GIVEN:
        // (current user)
        // (clicked user)
        // project id

        /*

            Example
            (Current User is A)
            (Clicked User us B)
            User A, User B, User C, User D, User E

            User A -> Searches in INVITE_REQUEST for projectId in -> User B
            Received (Y/N)

            if search result in projectId found and received found then show Cancel button.
            else show Invite button

         */


        // Point the invite database to the current user for dataSnapshot
        inviteDatabaseRef.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // If the current user has a child with other userId, check the for the REQUEST TYPE (sent/received)
                if(dataSnapshot.hasChild(user.getUid())) {
                    String request_type = dataSnapshot.child(user.getUid()).child("request_type").getValue().toString();
                    projectId = dataSnapshot.child(user.getUid()).child("project_type").getValue().toString();
                    //System.out.println(projectId+"-----------------------");

                    // If the REQUEST TYPE is "received"
                    // It means the request is received from other user
                    // Change the invite button text to ACCEPT INVITATION
                    if(request_type.equals("received")){
                        mInviteButton.setEnabled(true);
                        currentState = RequestState.NOT_IN_PROJECT;
                        mInviteButton.setText("ACCEPT INVITATION");
                        mInviteButton.setVisibility(View.VISIBLE);

                    }else {

                        // If the REQUEST TYPE is "sent"
                        // It means current user sent the request
                        // Change the invite button to CANCEL REQUEST
                        if (request_type.equals("sent")) {
                            mInviteButton.setEnabled(true);
                            currentState = RequestState.REQUEST_SENT;
                            mInviteButton.setText("CANCEL REQUEST");
                            mInviteButton.setVisibility(View.VISIBLE);
                        }
                    }

                }else {
                    /*// If the project already has other member id,
                    projectDatabaseRef.child("member").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(user.getUid())){
                                currentState = IN_PROJECT;
                                mInviteButton.setText("LEAVE PROJECT");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }





    /*public void trackState(int state) {

        switch (state) {
            case 0:
                mInviteButton.setEnabled(true);
                mInviteButton.setText("SEND REQUEST");
                break;

            case 1:
                mInviteButton.setEnabled(true);
                mInviteButton.setText("CANCEL REQUEST");
                break;

            case 2:
                break;

            case 3:
                mInviteButton.setEnabled(true);
                mInviteButton.setText("LEAVE PROJECT");
                break;

            default:
                break;

        }
    }*/

}


/*
    // Invite
                       (Handle)    <- (User, State) <- Invite
    Login -> Main ->  Member Project -> AllUser -> DialogBox

    // Accept
           (Handle) <- (User, State) <- Accept
    Login -> Main -> Top All User -> DialogBox


 */

/*
    Invite button (show) pressed -> sendRequest

 */