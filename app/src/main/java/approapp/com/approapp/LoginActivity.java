package approapp.com.approapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.EnumMap;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button reg;
    private Button login;
    private EditText Email;
    private EditText Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        reg = findViewById(R.id.registerButton);
        login = findViewById(R.id.loginButton);
        Email = findViewById(R.id.emailText);
        Password = findViewById(R.id.passwordText);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(Email.getText().toString().equals(" ") || Password.toString().equals(" "))) {
                    String email = Email.getText().toString().trim();
                    String password = Password.getText().toString().trim();
                    if (email.equals("") || password.equals("")) {
                        Toast.makeText(LoginActivity.this, "Enter valid details.",
                                Toast.LENGTH_SHORT).show();

                    } else {
                        ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                        pd.setMessage("loading");
                        pd.show();
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            updateUI(user);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                            updateUI(null);
                                        }

                                        // ...
                                    }
                                });
                    }
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null){
            Intent intent = new Intent(LoginActivity.this , MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
