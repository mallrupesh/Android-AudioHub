package com.rupesh.audiohubapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RegistrationTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.rupesh.audiohubapp", appContext.getPackageName());
    }

    FirebaseAuth mockAuth;


    @Before
    public void setUp() throws Exception {
        mockAuth = FirebaseAuth.getInstance();
        // Declare Firebase database reference

    }

    @Test
    public void onRegister() {
        String email = "jam@in.com";
        String password = "Asdf123*";

        mockAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                assertTrue(task.isSuccessful());
            }
        });
    }

    @Test
    public void onRegisterAlreadyRegisteredEmail() {
        String email = "jam@in.com";
        String password = "Zxcv123*";

        mockAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                assertFalse(task.isSuccessful());
            }
        });
    }
}
