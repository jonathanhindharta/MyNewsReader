package com.test.mynewsreader;


import com.facebook.Session;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.test.mynewsreader.MainActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class RssDetailFragment extends Fragment implements ConnectionCallbacks, OnConnectionFailedListener  {
	
	public RssDetailFragment(){}
	private TextView txtjudul;
	private WebView webrss;
	private Button btnsg;
	private GoogleApiClient mGoogleApiClient;	
	//private MainActivity ma = new MainActivity();
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		//Connect ke Api Google (Agar bisa memanggil sesi login dari MainActivity)
		mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this).addApi(Plus.API, null)
		.addScope(Plus.SCOPE_PLUS_LOGIN).build();
		
		//Cek Sesi Login yang aktif
		if (MainActivity.sesi != null) {           
			
			//Cek Sesi Login Google+
			if (MainActivity.lfgs==2) {
				mGoogleApiClient.connect();
				Session.setActiveSession((Session) MainActivity.sesi.getSerializable("gsession"));
				MainActivity.gsession = Session.getActiveSession();
				
				
			}
			//Cek Sesi Login Facebook
			else if (MainActivity.lfgs==1) {
				Session.setActiveSession((Session) MainActivity.sesi.getSerializable("fb_session"));
				MainActivity.fsession = Session.getActiveSession();
				
			}
			
			
		}

		
        View rootView = inflater.inflate(R.layout.fragment_rss_detail, container, false);
        webrss = (WebView) rootView.findViewById(R.id.webrss); 
		txtjudul = (TextView) rootView.findViewById(R.id.txtjudul);
		btnsg = (Button) rootView.findViewById(R.id.btnsg);
		
		final String wurl = this.getArguments().getString("urls");
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
		
		btnsg.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
			    // Launch the Google+ share dialog with attribution to your app.
			    Intent shareIntent = new PlusShare.Builder(getActivity())
			      .setType("text/plain")
			      .setText("Test Share ke Google+")
			      .setContentUrl(Uri.parse(wurl))
			      .getIntent();

			    startActivityForResult(shareIntent, 0);
			  }
			});

        return rootView;
    }
	
	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		mGoogleApiClient.connect();
		
	}

	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub
		mGoogleApiClient.connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		mGoogleApiClient.connect();
	}



}
