//Activity untuk Login
package com.test.mynewsreader;

import java.util.Arrays;
import java.util.List;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import com.facebook.Request;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;


public class LoginActivity extends FragmentActivity implements OnClickListener,
ConnectionCallbacks, OnConnectionFailedListener {
	
	private ProgressDialog mProgressDialog;
	
	//===============Variabel Untuk Login Facebook========================
	private LoginButton loginBtn;
	String userFb, idFb;
	int lg =0;
	int konter=0;
	Session session;
	private UiLifecycleHelper uiHelper;
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	protected static final String TAG = null;
	//================Akhir Variabel Untuk Login Facebook==========================
	
	
	//==================Variabel untuk Login Google+==========================
	private static final int RC_SIGN_IN = 0;
	// Logcat tag
	//private static final String TAG = "MainActivity";

	// Profile pic image size in pixels
	private static final int PROFILE_PIC_SIZE = 100;

	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;
	String personName, personPhotoUrl; 
	/**
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	private boolean mIntentInProgress;
	private boolean mSignInClicked;
	private ConnectionResult mConnectionResult;
	private SignInButton btnSignIn;
	//==================Akhir Variabel untuk Login Google+==========================
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//====================Inisialisasi Var Login Facebook========================	
		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);		
		session = Session.getActiveSession();
		Bundle cek = getIntent().getExtras(); 
		setContentView(R.layout.activity_login);		
		loginBtn = (LoginButton) findViewById(R.id.fb_login_button);		
		//=================Akhir Inisialisasi Var Login Facebook========================	
		
		//====================Inisialisasi Var Login Google+========================	
		btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
		// Button click listeners
		btnSignIn.setOnClickListener(this);
		
		//Connect ke Api Google
		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this).addApi(Plus.API, null)
		.addScope(Plus.SCOPE_PLUS_LOGIN).build();
		//====================Akhir Inisialisasi Var Login Google+========================
		
		lg = getIntent().getIntExtra("nc", 0);
		//Jika lg bernilai lg>0 maka Halaman Login terbuka dari logout, jika tidak maka baru terbuka

		


	    //=====================Cek Sesi Facebook ada atau sudah ditutup==============================
		
		if ((session != null  && session.isOpened())) {
				
			
				if (lg==1) {
					
		            // Kill login activity and go back to main
					session.getActiveSession().closeAndClearTokenInformation();
					
		            
				}
				else {
					mProgressDialog = new ProgressDialog(LoginActivity.this);
					mProgressDialog.setTitle("Verifikasi Login Facebook");
					mProgressDialog.setMessage("Loading...");
					mProgressDialog.setIndeterminate(false);
					mProgressDialog.show();
					mProgressDialog.setCancelable(false);
		    	  loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
						@Override
						public void onUserInfoFetched(GraphUser user) {
							
							if (user != null && konter==0) {
								userFb = user.getName();
								 idFb = user.getId();
								//txtuser1.setText(userFb);
								konter++;

								         
							} else {
								
							}
						}
					});
		    	  Handler handler = new Handler();
		    	    handler.postDelayed(new Runnable()
		    	    {
		    	        public void run()
		    	        {
		    	        	mProgressDialog.dismiss();
		    	        	
		    	            // Kill login activity and go back to main
		    	           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		    	            Bundle moves = new Bundle();
		    	    		moves.putString("userfb", userFb);
		    	    		moves.putString("idfb", idFb);
		    	    		moves.putInt("lo",1);
		    	            intent.putExtra("fb_session", session);
		    	            intent.putExtras(moves);
		    	            
		    	            finish();
		    	            startActivity(intent); 
		    	        	
		    	        }
		    	    }, 8000);
					
				}
	    	  		 
	        } 

	    //===================== Akhir Cek Sesi Facebook ada atau sudah ditutup==============================

	}

	
	//=======================Method Implementasi Untuk Login Facebook===================================
	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, SessionState state,
				Exception exception) {
			if (lg==1){
				session.closeAndClearTokenInformation();
			}
			
			if (state.isOpened()) {
				mProgressDialog = new ProgressDialog(LoginActivity.this);
				mProgressDialog.setTitle("Verifikasi Login Facebook");
				mProgressDialog.setMessage("Loading...");
				mProgressDialog.setIndeterminate(false);
				mProgressDialog.show();
				mProgressDialog.setCancelable(false);
	    	  loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
					@Override
					public void onUserInfoFetched(GraphUser user) {
						
						if (user != null && konter==0) {
							userFb = user.getName();
							 idFb = user.getId();
							//txtuser1.setText(userFb);
							konter++;

							         
						} else {
							
						}
					}
				});
	    	  Handler handler = new Handler();
	    	    handler.postDelayed(new Runnable()
	    	    {
	    	        public void run()
	    	        {
	    	        	mProgressDialog.dismiss();
	    	        	
	    	            // Kill login activity and go back to main
	    	           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
	    	            Bundle moves = new Bundle();
	    	    		moves.putString("userfb", userFb);
	    	    		moves.putString("idfb", idFb);
	    	    		moves.putInt("lo",1);
	    	            intent.putExtra("fb_session", session);
	    	            intent.putExtras(moves);
	    	            
	    	            finish();
	    	            startActivity(intent); 
	    	        	
	    	        }
	    	    }, 8000);
				Log.d("FacebookSampleActivity", "Facebook session opened");
			} else if (state.isClosed()) {
				session.closeAndClearTokenInformation();
				Log.d("FacebookSampleActivity", "Facebook session closed");
			}
		}
	};


	public boolean checkPermissions() {
		Session s = Session.getActiveSession();
		if (s != null) {
			return s.getPermissions().contains("publish_actions");
		} else
			return false;
	}

	public void requestPermissions() {
		Session s = Session.getActiveSession();
		if (s != null)
			s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
					this, PERMISSIONS));
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		//buttonsEnabled(Session.getActiveSession().isOpened());
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
	//Untuk Aksi Setelah Login Facebook
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	      Session.getActiveSession()
	          .onActivityResult(this, requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
        final Session session = Session.getActiveSession();
        

			finish();
	        // Kill login activity and go back to main
	       Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
	        
	        startActivity(intent); 
		
     	
	    	    
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}
	
	//=======================Akhir Method Implementasi Untuk Login Facebook===================================
	
	
	//======================= Method Implementasi Untuk Login Google+===================================
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}
	
	/**
	 * Method to resolve any signin errors
	 * */
	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}

		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}

	}
	
	
	//Aksi Setelah login dengan google+
	@Override
	public void onConnected(Bundle arg0) {
		//Jika lg=2 maka berarti halaman login dipanggil setelah logout dari MainActivity
		if (lg==2) {
			if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
			}
		}
		//Proses Setelah login google+
		else {
			mSignInClicked = false;
			//Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

			// Get user's information
			getProfileInformation();

			// Update the UI after signin
			//updateUI(true);
			mProgressDialog = new ProgressDialog(LoginActivity.this);
			mProgressDialog.setTitle("Verifikasi Login Google+");
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.show();
			mProgressDialog.setCancelable(false);

			Handler handler = new Handler();
		    handler.postDelayed(new Runnable()
		    {
		        public void run()
		        {
		        	mProgressDialog.dismiss();
		        	
		            // Kill login activity and go back to main
		        	Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		            Bundle moves = new Bundle();
		    		moves.putString("userg", personName);
		    		moves.putString("foto", personPhotoUrl);
		    		moves.putInt("lo",2);
		    		intent.putExtra("gsession",session);
		            intent.putExtras(moves);
		            finish();
		            startActivity(intent);
		        	
		        }
		    }, 4000);	
			
		}
		
		

	}
	
	/**
	 * Fetching user's information name, email, profile pic
	 * */
	private void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				personName = currentPerson.getDisplayName();
				personPhotoUrl = currentPerson.getImage().getUrl();

				// by default the profile url gives 50x50 px image only
				// we can replace the value with whatever dimension we want by
				// replacing sz=X
				personPhotoUrl = personPhotoUrl.substring(0,
						personPhotoUrl.length() - 2)
						+ PROFILE_PIC_SIZE;

			} else {
				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
		//updateUI(false);
	}

	/**
	 * Button on click listener
	 * */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sign_in:
			// Signin button clicked
			signInWithGplus();
			break;
		
		}
	}

	/**
	 * Sign-in into google
	 * */
	private void signInWithGplus() {
		if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();
		}
	}

	
	
}
