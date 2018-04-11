package com.xulc.algorithmstudy.ui;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xulc.algorithmstudy.R;
import com.xulc.algorithmstudy.widget.AndroidToJs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Date：2018/3/28
 * Desc：探索webview
 * Created by xuliangchun.
 */

public class StudyWebViewActivity extends BaseActivity {
    private WebView webView;
    private ImageView ivLogo;
    private TextView tvTitle;
    private ProgressBar progressBar;
    //    private final String url = "http://api.ytx5.com/ylf/trade/order";
    private final String url = "https://app.ytx.com/download";
    private Button btnEvaluteJs;

    private boolean isNeedClearHistory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_webview);
        webView = (WebView) findViewById(R.id.webView);
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnEvaluteJs = (Button) findViewById(R.id.btnEvaluteJs);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //setWebViewClient主要处理解析，渲染网页等浏览器做的事
        //setWebChromeClient辅助WebView处理对话框、网站图标、网站title、加载进度等
        webView.setWebViewClient(new WebViewClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.i("xlc", "url=" + request.getUrl());
//                if (!Pattern.matches("^https?://\\S*",request.getUrl().toString())){
//                    return true;
//                }
                Uri uri = request.getUrl();
                // 步骤2：根据协议的参数，判断是否是所需要的url
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?name=xulc&age=27"（同时也是约定好的需要拦截的）
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if (uri.getScheme().equals("js")){
                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")){
                        Log.i("xlc","js调用了android的方法");
                        HashMap<String,String> params = new HashMap<String, String>();
                        Set<String> collection = uri.getQueryParameterNames();
                        Iterator iterator = collection.iterator();
                        StringBuffer stringBuffer = new StringBuffer();
                        while (iterator.hasNext()){
                            String key = (String) iterator.next();
                            params.put(key,uri.getQueryParameter(key));
                            stringBuffer.append(uri.getQueryParameter(key)+";");
                        }
                        Log.i("xlc",params.toString());
                        Toast.makeText(StudyWebViewActivity.this,"我来自："+stringBuffer,Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("xlc", "onPageStarted：");
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("xlc", "onPageFinished：");


            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
//                Log.i("xlc","onLoadResource：");

            }

            //onReceivedError方法捕获的是 文件找不到，网络连不上，服务器找不到等问题，并不能捕获 http errors。
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.i("xlc", "onReceivedError：");
                isNeedClearHistory = true;
                webView.loadUrl("file:///android_asset/http_error.html");

            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                Log.i("xlc", "onReceivedHttpError：");
                isNeedClearHistory = true;
                webView.loadUrl("file:///android_asset/http_error.html");


            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
                Log.i("xlc", "doUpdateVisitedHistory：" + isNeedClearHistory);
                //在这个时机清除历史才生效
                if (isNeedClearHistory) {
                    webView.clearHistory();
                }

            }

            //关于自签名证书，安全做法应该是校验证书，校验不通过则cancel
            //https://www.cnblogs.com/liyiran/p/7011317.html
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
                Log.i("xlc", "onReceivedSslError：");

            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                Log.i("xlc","onProgressChanged："+newProgress);
                progressBar.setProgress(newProgress);
                ivLogo.getDrawable().setLevel(100 * newProgress);
                if (newProgress == 100) {
                    progressBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                        }
                    }, 150);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.i("xlc", "onReceivedTitle：" + title);
                tvTitle.setText(title);

            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
//                ivLogo.setImageBitmap(icon);
            }

            @Override
            public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                super.onReceivedTouchIconUrl(view, url, precomposed);
                Log.i("xlc", "onReceivedTouchIconUrl：" + url);

            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });

        //对于JS调用Android代码的方法有3种：
        //1. 通过WebView的addJavascriptInterface进行对象映射
        //2. 通过 WebViewClient 的shouldOverrideUrlLoading方法回调拦截 url
        //3. 通过 WebChromeClient 的onJsAlert、onJsConfirm、onJsPrompt方法回调拦截JS对话框alert()、confirm()、prompt() 消息
        webView.addJavascriptInterface(new AndroidToJs(new AndroidToJs.WebClickListener() {
            @Override
            public void loadAgain() {

                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl(url);
                    }
                });

            }
        }), "webclick");

        webView.loadUrl(url);

        //对于Android调用JS代码的方法有2种：
        //1. 通过WebView的loadUrl
        //2. 通过WebView的evaluateJavascript  效率更高 因为该方法的执行不会使页面刷新，而第一种方法（loadUrl ）的执行则会；4.4以上才可使用

        btnEvaluteJs.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                //webView.loadUrl("javascript:setTip()");
//                mWebView.loadUrl("javascript:returnResult(" + result + ")");

                webView.evaluateJavascript("javascript:setTip()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.i("xlc", "js返回结果：" + value);
                    }
                });
            }
        });

    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            Log.i("xlc", webView.canGoForward() + "");
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
