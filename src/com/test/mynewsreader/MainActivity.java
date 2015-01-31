package com.test.mynewsreader;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;





import com.facebook.model.GraphUser;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	
	private static final String TAG = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final TextView txtuser = (TextView) findViewById(R.id.txtuser);
		TextView txtsesi = (TextView) findViewById(R.id.txtsesi);
		Button btnlg = (Button) findViewById(R.id.btnlg);
		
		Bundle sesi = getIntent().getExtras();
		final String users = getIntent().getStringExtra("userfb");
		if (sesi != null) {           
			txtsesi.setText("Ada Sesi fb");
			Session.setActiveSession((Session) sesi.getSerializable("fb_session"));
			
		}
		txtuser.setText(users);
		
		//Untuk LogOut
		btnlg.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// Execute Description AsyncTask
				Session session = Session.getActiveSession();
			    if (session != null) {

			        if (!session.isClosed()) {
			            session.closeAndClearTokenInformation();
			            //clear your preferences if saved
			            finish();
			            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			            startActivity(intent);
			        }
			    } else {

			        session = new Session(getBaseContext());
			        Session.setActiveSession(session);
			        session.closeAndClearTokenInformation();
			        finish();
			        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		            startActivity(intent);
			            //clear your preferences if saved

			    }
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
