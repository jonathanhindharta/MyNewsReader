//Activity untuk Login
package com.test.mynewsreader;

import java.util.Arrays;
import java.util.List;



import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


public class LoginActivity extends FragmentActivity {
	

	private LoginButton loginBtn;


	String userFb;

	private UiLifecycleHelper uiHelper;

	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);
		
	      Session session = Session.getActiveSession();

	        if ((session != null || session.isOpened())) {
	            // Kill login activity and go back to main
	            finish();
	            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				Bundle moves = new Bundle();
	    		moves.putString("userfbs", userFb);
	            intent.putExtra("fb_session", session);
	            startActivity(intent);
	        }
		
		setContentView(R.layout.activity_login);

		loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
		//Aksi Setelah button Login Facebook diklik
		loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(GraphUser user) {
				if (user != null) {
					userFb= user.getName();
					
				} else {
					
				}
			}
		});
		



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
        Session session = Session.getActiveSession();

        if ((session != null || session.isOpened())) {
            // Kill login activity and go back to main
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			Bundle moves = new Bundle();
    		moves.putString("userfbs", userFb);
            intent.putExtra("fb_session", session);
            startActivity(intent);
        }
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}
	

}
