package lr.pro.vitorath.webview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String URL = "https://www.google.com.br";
    private WebView webView;

    private Dialog dialog;
    private Button auth;
    private SharedPreferences sharedPreferences;
    private TextView access;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("AppPref", MODE_PRIVATE);
        access = findViewById(R.id.Acesss);
        auth = findViewById(R.id.auth);
        auth.setOnClickListener(new AuthClick());
        dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.activity_authentication);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Token", "onResume");
        String code = getIntent().getStringExtra("code");
        if (code != null)
            Log.i("Token", code);
    }

    protected class AuthClick implements View.OnClickListener {

        Dialog authDialog;

        @Override
        public void onClick(View view) {
            webView = dialog.findViewById(R.id.webview);
            webView.getSettings().setSupportZoom(false);
            webView.requestFocus(View.FOCUS_DOWN);
            webView.loadUrl(URL);
            webView.setWebViewClient(new CustomWebViewClient());
            dialog.show();
//            Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
//            startActivity(intent);
        }
    }

    protected class CustomWebViewClient extends WebViewClient {

        private Context context;
        private boolean authenticationComplete = false;
        private String code;
        private Intent resultIntent = new Intent();



        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.i("Token", "onPageFinished " + url);
            super.onPageFinished(view, url);
            if (url.contains("search") && !authenticationComplete) {
                Uri uri = Uri.parse(url);
                code = uri.getQueryParameter("q");
                authenticationComplete = true;
                resultIntent.putExtra("code", code);
                Log.i("Token", code);
                dialog.dismiss();
            }
        }
    }
}

