package approapp.com.approapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static long back;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address etc
            Toast.makeText(MainActivity.this , "Welcome "+user.getDisplayName() , Toast.LENGTH_SHORT)
                    .show();
        }
        else{
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Login Again", Toast.LENGTH_SHORT)
                    .show();
            finish();
        }

        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.approapp.com/");
        setContentView(webView);

    }

    @Override
    public void onBackPressed() {
        if((back + 2000) > System.currentTimeMillis())
        {
            Toast.makeText(getApplicationContext() , "Hope you like our product." , Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
        else Toast.makeText(getApplicationContext() , "Press Again to exit" , Toast.LENGTH_SHORT).show();
        back = System.currentTimeMillis();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Logout Successful", Toast.LENGTH_SHORT)
                        .show();
                finish();
                return true;
                default:
                    return true;

        }
    }
}

