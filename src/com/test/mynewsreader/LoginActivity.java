//Activity untuk Login
package com.test.mynewsreader;

import java.util.Arrays;
import java.util.List;

import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import com.facebook.Request;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;


public class LoginActivity extends FragmentActivity {
	

	private LoginButton loginBtn;
	ProgressDialog mProgressDialog;

	String userFb;
	int konter=0;

	private UiLifecycleHelper uiHelper;

	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");


	protected static final String TAG = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);
		
		final Session session = Session.getActiveSession();
	      
		setContentView(R.layout.activity_login);
		//final TextView txtuser1 = (TextView) findViewById(R.id.txtuser1);
		
		loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
		 loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
				@Override
				public void onUserInfoFetched(GraphUser user) {
					
				}
			});
		

		

	    
	      if ((session != null  && session.isOpened())) {
	    	  /**  Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
				@Override
				public void onCompleted(GraphUser user, Response response) {
					// TODO Auto-generated method stub
					// If the response is successful
	                  if (session == Session.getActiveSession()) {
	                      if (user != null) { 
	                    	  String userFb = user.getName();//user's profile name
	                      }   
	                  }
				}   
	          }); 
	          Request.executeBatchAsync(request);
	    	 	finish();
	            // Kill login activity and go back to main
	           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
	            Bundle moves = new Bundle();
	    		//moves.putString("userfb", userFb);
	            intent.putExtra("fb_session", session);
	           
	            startActivity(intent); **/
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
	    	        	finish();
	    	            // Kill login activity and go back to main
	    	           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
	    	            Bundle moves = new Bundle();
	    	    		moves.putString("userfb", userFb);
	    	            intent.putExtra("fb_session", session);
	    	            intent.putExtras(moves);
	    	            startActivity(intent); 
	    	        	
	    	        }
	    	    }, 8000);

	 
	        }
	      else {
	    	  
	      }
	 



	}

	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (state.isOpened()) {
			
				Log.d("FacebookSampleActivity", "Facebook session opened");
			} else if (state.isClosed()) {
			
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
        final Session session = Session.getActiveSession();


	    	  loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
					@Override
					public void onUserInfoFetched(GraphUser user) {
						
						if (user != null && konter==0) {
							userFb = user.getName();
							//txtuser1.setText(userFb);
							konter++;

							         
						} else {
							
						}
					}
				});

	    	        	
	    	        	finish();
	    	            // Kill login activity and go back to main
	    	           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
	    	            Bundle moves = new Bundle();
	    	    		moves.putString("userfb", userFb);
	    	            intent.putExtra("fb_session", session);
	    	            intent.putExtras(moves);
	    	            startActivity(intent); 
	    	        	
	    	    
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}
	


	


}
