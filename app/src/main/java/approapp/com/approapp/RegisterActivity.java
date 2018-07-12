package approapp.com.approapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText name;
    private EditText Email;
    private EditText Password;
    private EditText confirmPass;
    private Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.nameText);
        Email = findViewById(R.id.emailText);
        Password = findViewById(R.id.passwordText);
        Register = findViewById(R.id.registerButton);
        confirmPass = findViewById(R.id.confirmPasswordText);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Email.getText().toString().trim();
                final String password = Password.getText().toString().trim();
                final String Name = name.getText().toString().trim();
                if (email.equals("") ||  Name.equals("")) {
                    if (email.equals(" "))
                        Toast.makeText(RegisterActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    if (password.equals(confirmPass.getText().toString().trim()))
                        Toast.makeText(RegisterActivity.this, "Enter Same Pin", Toast.LENGTH_SHORT).show();
                    if (Name.equals(" "))
                        Toast.makeText(RegisterActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();

                } else {
                    if ((password.equals(confirmPass.getText().toString().trim()))) {
                        ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
                        pd.setMessage("loading");
                        pd.show();
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            final FirebaseUser user = mAuth.getCurrentUser();
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(Name)
                                                    .build();

                                            user.updateProfile(profileUpdates)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        public static final String TAG = "message";

                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d(TAG, "User profile updated.");
                                                            }
                                                        }
                                                    });

                                            user.sendEmailVerification()
                                                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener() {
                                                        @Override
                                                        public void onComplete(@NonNull Task task) {
                                                            // Re-enable button

                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(RegisterActivity.this,
                                                                        "Verification email sent to " + user.getEmail(),
                                                                        Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(RegisterActivity.this,
                                                                        "Failed to send verification email.",
                                                                        Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                            updateUI(user);

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                            final FirebaseUser user = mAuth.getCurrentUser();

                                            updateUI(user);
                                        }

                                        // [START_EXCLUDE]
                                        // [END_EXCLUDE]
                                    }
                                });

                    }
                    else
                        Toast.makeText(RegisterActivity.this , "Password Does not match" , Toast.LENGTH_SHORT).show();
                }
                }


            private void updateUI(FirebaseUser user) {
                if (user != null) {
                    Toast.makeText(RegisterActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, "Verify your email ID using email sent", Toast.LENGTH_SHORT)
                            .show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(RegisterActivity.this, "Error! try after some time", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

