/** Aplikasi New Reader
 * Di Buat oleh Jonathan Hindharta**/

//Activity untuk melihat detail berita

package com.test.mynewsreader;


import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.PendingCall;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
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


import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RssDetailFragment extends Fragment implements ConnectionCallbacks, OnConnectionFailedListener  {
	
	public RssDetailFragment(){}
	private TextView txtjudul;
	private WebView webrss;
	private Button btnsg,btnsf;
	private GoogleApiClient mGoogleApiClient;	
	String wurl, wjudul;
	private UiLifecycleHelper uiHelper;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		 
		View rootView = inflater.inflate(R.layout.fragment_rss_detail, container, false);
        webrss = (WebView) rootView.findViewById(R.id.webrss); 
		txtjudul = (TextView) rootView.findViewById(R.id.txtjudul);
		btnsg = (Button) rootView.findViewById(R.id.btnsg);
		btnsf = (Button) rootView.findViewById(R.id.btnsf);
		uiHelper = new UiLifecycleHelper(getActivity(), null);
	    uiHelper.onCreate(savedInstanceState);
		
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
				btnsg.setVisibility(View.VISIBLE);
				
			}
			//Cek Sesi Login Facebook
			else if (MainActivity.lfgs==1) {
				Session.setActiveSession((Session) MainActivity.sesi.getSerializable("fb_session"));
				MainActivity.fsession = Session.getActiveSession();
				btnsf.setVisibility(View.VISIBLE);
			}
			
			
		}
		//Ambil Data bundle dari list judul yang dipilih
		wurl = this.getArguments().getString("urls");
		wjudul =this.getArguments().getString("juduls");
		String wdesc = this.getArguments().getString("descs");
		
		txtjudul.setText(wjudul);
		webrss.getSettings().setJavaScriptEnabled(true);

		webrss.loadDataWithBaseURL(null, wdesc, "text/html", "UTF-8", null);

		//Button Share google+
		btnsg.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
			    // Memunculkan Google+ SHare Dialog
			    Intent shareIntent = new PlusShare.Builder(getActivity())
			      .setType("text/plain")
			      .setText("")
			      .setContentUrl(Uri.parse(wurl))
			      .getIntent();

			    startActivityForResult(shareIntent, 0);
			  }
			});
		
		//Button Share Facebook
		btnsf.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
			    // Memunculkan Facebook Share Dialog
				publishFeedDialog();
			  }
			});
		


        return rootView;
    }
	
//=================Method Implementasi Login Google+==========================
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
//=================Akhir Method Implementasi Login Google+==========================	
	
//=================Method untuk Implementasi UI Share Facebook======================	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {

			@Override
			public void onComplete(PendingCall pendingCall, Bundle data) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(PendingCall pendingCall, Exception error,
					Bundle data) {
				// TODO Auto-generated method stub
				
			}
	        
	    });
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
	
	//Method untuk Share Dialog Facebook
	private void publishFeedDialog() {
	    Bundle params = new Bundle();
	    params.putString("name", wjudul);
	    
	    params.putString("link", wurl);
	    

	    WebDialog feedDialog = (
	        new WebDialog.FeedDialogBuilder(getActivity(),
	            Session.getActiveSession(),
	            params))
	        .setOnCompleteListener(new OnCompleteListener() {

	            @Override
	            public void onComplete(Bundle values,
	                FacebookException error) {
	                if (error == null) {
	                    // When the story is posted, echo the success
	                    // and the post Id.
	                    final String postId = values.getString("post_id");
	                    if (postId != null) {
	                        Toast.makeText(getActivity(),
	                            "Feed Shared",
	                            Toast.LENGTH_SHORT).show();
	                    } else {
	                        // User clicked the Cancel button
	                        Toast.makeText(getActivity().getApplicationContext(), 
	                            "Share cancelled", 
	                            Toast.LENGTH_SHORT).show();
	                    }
	                } else if (error instanceof FacebookOperationCanceledException) {
	                    // User clicked the "x" button
	                    Toast.makeText(getActivity().getApplicationContext(), 
	                        "Share cancelled", 
	                        Toast.LENGTH_SHORT).show();
	                } else {
	                    // Generic, ex: network error
	                    Toast.makeText(getActivity().getApplicationContext(), 
	                        "Error posting feed", 
	                        Toast.LENGTH_SHORT).show();
	                }
	            }

				

	        })
	        .build();
	    feedDialog.show();
	}

//================= AKhir Method untuk Implementasi UI Share Facebook======================	
	
	
}
