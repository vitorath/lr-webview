package lr.pro.vitorath.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class AuthenticationActivity extends AppCompatActivity {

    private String URL = "https://www.google.com.br";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        webView = findViewById(R.id.webview);
        webView.getSettings().setSupportZoom(false);
        webView.requestFocus(View.FOCUS_DOWN);
        webView.loadUrl(URL);
        webView.setWebViewClient(new CustomWebViewClient());

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
            super.onPageFinished(view, url);
            if (url.contains("ei") && !authenticationComplete) {
                Uri uri = Uri.parse(url);
                code = uri.getQueryParameter("ei");
                authenticationComplete = true;
                resultIntent.putExtra("code", code);
                Log.i("Token", code);
                webView.destroy();
                finish();
            }
        }
    }
}
