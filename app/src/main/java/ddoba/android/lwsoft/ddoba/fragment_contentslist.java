package ddoba.android.lwsoft.ddoba;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ironJack on 2015. 4. 11..
 */

public class fragment_contentslist extends Fragment {
    Context mContext;
    public static String ARG_TITLE="title";
    private WebView webView;
    public String mTitle;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;
    private myWebChromeClient mWebChromeClient;
    private myWebViewClient mWebViewClient;

    void test_jsoup2(final Context context,  final String url){
        //Toast.makeText( context, url, Toast.LENGTH_LONG).show();//this will just show the result in Toast message
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Log.i("tag_", url);

                    String result = "";
                    Document doc = Jsoup.connect(url).get();
                    Elements nodeBlogStats = doc.select("a[href]");
                    for ( Element column :  nodeBlogStats ) {
                        //Log.i("tag_", "link:" + column.text() );
                        result += column.text();
                        result +="\n";
                    }
                    Toast.makeText( context, result, Toast.LENGTH_LONG).show();//this will just show the result in Toast message
                }catch(Exception e){

                }
            }
        });
        thread.start();
    }
    void test_jsoup(final String url){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    Document doc = Jsoup.connect(url).get();
                    Log.i("tag_", url );

                    //Elements divs = doc.select("div#test");
                    Log.i("tag_", doc.html());

                    Elements elems =  doc. select("html.ui-mobile"); //CSS 셀렉터

                    for(Element elem : elems)
                    {
                        String txt = elem.text();
                        String html = elem.html();

                        Log.i("tag_", txt + "::" + html);
                        //String src = elem.attr("abs:src"); //절대경로로 변경하여 리턴
                        //doc = Jsoup.connect(src).get();
                        //Elements elems2 = doc.select("a[href]");
                        //for(Element e : elems2)
                        //{
                        //Log.i("tag_", e.attr("abs:href"));
                        //}
                        //System.out.printf("%s\n", doc.toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }


    public static void setwebsetting(WebView webView){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);

        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
    }
    /*
    public static void setWebViewSettings(WebView webview) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);

        webSettings.setSaveFormData(true);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        //webSettings.setMediaPlaybackRequiresUserGesture(true);

        webSettings.setSupportMultipleWindows(true);
        if (Build.VERSION.SDK_INT < 19) {
            if (Build.VERSION.SDK_INT < 8) {
                //webSettings.setPluginsEnabled(true);
            } else {
                //webSettings.setPluginState(WebSettings.PluginState.ON);
            }
        }

        if( Build.VERSION.SDK_INT >= 16 )
        {
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }


        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationEnabled(true);

        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);

        webSettings.setSaveFormData(true);
    }*/

    public static fragment_contentslist newInstance(int page) {

        Bundle args = new Bundle();
        args.putInt(ARG_TITLE, page);
        fragment_contentslist fragment = new fragment_contentslist();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Bundle args = getArguments();
            if (args != null) {
                mTitle = args.getCharSequence( ARG_TITLE, "noneTitle").toString();
            }
        }


        /**
         * Create the view for this fragment, using the arguments given to it.
         */
        @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contentslist, container, false);
            //View tv = v.findViewById(R.id.text);
            //((TextView)tv).setText(mLabel != null ? mLabel : "(no label)");
            //tv.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.gallery_thumb));
            webView = (WebView)v.findViewById(ddoba.android.lwsoft.ddoba.R.id.webView);

            customViewContainer = (FrameLayout) v.findViewById(ddoba.android.lwsoft.ddoba.R.id.customViewContainer);
            mWebViewClient = new myWebViewClient();
            webView.setWebViewClient(mWebViewClient);

            mWebChromeClient = new myWebChromeClient();
            webView.setWebChromeClient(mWebChromeClient);

            //MainActivity.setWebViewSettings(webView);
            setwebsetting(webView);


            webView.loadUrl("http://www.dasibogi.com/");
            return v;
        }

    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
    }

    @Override
    public void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
        webView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        webView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        if (inCustomView()) {
            hideCustomView();
        }
    }
/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (inCustomView()) {
                hideCustomView();
                return true;
            }

            if ((mCustomView == null) && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onkey onKeyDown(keyCode, event);
    }*/


    class myWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            onShowCustomView(view, callback);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onShowCustomView(View view,CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            webView.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);
            customViewCallback = callback;
        }

        @Override
        public View getVideoLoadingProgressView() {

            if (mVideoProgressView == null) {
                //LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                //mVideoProgressView = inflater.inflate(R.layout.video_progress, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates.
            if (mCustomView == null)
                return;

            webView.setVisibility(View.VISIBLE);
            customViewContainer.setVisibility(View.GONE);

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            customViewContainer.removeView(mCustomView);
            customViewCallback.onCustomViewHidden();

            mCustomView = null;
        }
    }

    class myWebViewClient extends WebViewClient {

        Context context;
        private ArrayList<HashMap<String, String>> data;
        //public Net_HTMLParse net_htmlParse;
        Handler handler_complete = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //Log.d("tag_ddoba","hohoho");
                //if( (int)msg.what == 0 )
                Toast.makeText(context, "complete!!!", Toast.LENGTH_LONG);
            }
        };


        @Override
        public void onPageStarted (WebView view, String url, Bitmap favicon)
        {
            // Log.i("tag_ddoba", "started: " + url );
        }


        @Override
        public void onPageFinished(WebView view, String url)
        {
            // Log.i("tag_ddoba", "finish: " + url );
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            context = view.getContext();
            test_jsoup2(context,url);
            //Log.i("tag_ddoba", getURLtoText(url) );
            //Log.i("tag_ddoba", "shouldOverrideUrlLoading: "+ url );
            //net_htmlParse = new Net_HTMLParse(view.getContext());
            //data = new ArrayList<HashMap<String, String>>();

            //net_htmlParse.open( url,handler_complete,data   );

            //String html="이것은 로컬 HTML입니다";
            //webView.loadData( html,"text/html", "EUC-KR" );
            //return true;
            return super.shouldOverrideUrlLoading(view, url);    //To change body of overridden methods use File | Settings | File Templates.
        }
        public void onUpdate(){

            /*
            for( int n=0; n< data.size();n++){
                HashMap<String,String> hs = data.get(n);

                Iterator<String> iter = hs.keySet().iterator();
                while(iter.hasNext()) {
                   // String key = iter.next();
                  //  String value = hs.get(key);
                  //  Log.i("tag_ddoba", "key : " + key + ", value : " + value);
                }

                //Log.i("tag_ddoba",  hs. );
            }*/
        }
    }

}
