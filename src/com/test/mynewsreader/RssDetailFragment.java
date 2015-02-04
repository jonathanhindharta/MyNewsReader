package com.test.mynewsreader;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class RssDetailFragment extends Fragment  {
	
	public RssDetailFragment(){}
	private TextView txtjudul;
	private WebView webrss;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_rss_detail, container, false);
        webrss = (WebView) rootView.findViewById(R.id.webrss); 
		txtjudul = (TextView) rootView.findViewById(R.id.txtjudul);
		//Bundle dts = getActivity().getIntent().getExtras();
		String wurl = this.getArguments().getString("urls");
		String wjudul =this.getArguments().getString("juduls");
		String wdesc = this.getArguments().getString("descs");
		
		txtjudul.setText(wjudul);
		webrss.getSettings().setJavaScriptEnabled(true);
		//webrss.loadUrl(wurl);
		webrss.loadDataWithBaseURL(null, wdesc, "text/html", "UTF-8", null);
/*		webrss.setWebViewClient(new WebViewClient()
        {
            // Override URL (Menentukan Aksi ketika sebuah dipilih)
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
            	webrss.loadUrl(url);
				return true;
            }
        }); */


        return rootView;
    }
	




}
