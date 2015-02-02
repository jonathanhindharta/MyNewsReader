package com.test.mynewsreader;

import com.test.mynewsreader.adapter.NavDrawerListAdapter;
import com.test.mynewsreader.model.NavDrawerItem;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;



import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.io.InputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ListView;




@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	String userFb;
	private static final String TAG = null;
	private ProfilePictureView profilePictureView;
	Session session;
	private GoogleApiClient mGoogleApiClient;
	private ImageView imgProfilePic;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private LinearLayout drawer1;
	private ActionBarDrawerToggle mDrawerToggle;
	TextView txtuser;
	Button btnlg;
	
	
	// nav drawer title
	private CharSequence mDrawerTitle;
	
	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle sesi = getIntent().getExtras();
		
		
		
		setContentView(R.layout.activity_main);
		
		drawer1 = (LinearLayout) findViewById(R.id.drawer1);

		profilePictureView = (ProfilePictureView) findViewById(R.id.selection_profile_pic);
		txtuser = (TextView) findViewById(R.id.txtuser);
		btnlg = (Button) findViewById(R.id.btnlg);
		imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
		int lfg = getIntent().getIntExtra("lo", 0);
		
		if (sesi != null) {           
			//txtsesi.setText("Ada Sesi fb");
			
				if (lfg==2) {
					//mGoogleApiClient.connect();
					final String users = getIntent().getStringExtra("userg");
					final String fotos = getIntent().getStringExtra("foto");
					txtuser.setText(users);
					imgProfilePic.setVisibility(View.VISIBLE);
					new LoadProfileImage(imgProfilePic).execute(fotos);
					
				}
				
				else {
					Session.setActiveSession((Session) sesi.getSerializable("fb_session"));
					session = Session.getActiveSession();
					final String users = getIntent().getStringExtra("userfb");
					final String ids = getIntent().getStringExtra("idfb");
					txtuser.setText(users);
					profilePictureView.setVisibility(View.VISIBLE);
					profilePictureView.setProfileId(ids);
				}
				
				
			}
	
		
		
		//TextView txtsesi = (TextView) findViewById(R.id.txtsesi);
		
		
		
		
		//Untuk LogOut
		btnlg.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// Execute Description AsyncTask
				if (mGoogleApiClient.isConnected()) {
					Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
					mGoogleApiClient.disconnect();
					mGoogleApiClient.connect();
					finish();
					Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
					startActivity(intent);
					
				}
				else {
						if (session != null  && session.isOpened()) {
	
					        if (!session.getState().isClosed()) {
					            session.getActiveSession().closeAndClearTokenInformation();
					            //session=null;
					            //clear your preferences if saved
					            finish();
					            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
					            Bundle lt = new Bundle();
					            lt.putInt("nc", 1);
			    	    		
			    	            intent.putExtras(lt);
					            startActivity(intent);
					        }
					    } else {
	
					        session = new Session(getBaseContext());
					        Session.setActiveSession(session);
					        session.getActiveSession().closeAndClearTokenInformation();
					        //session=null;
					        //clear your preferences if saved
					        finish();
					        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				            Bundle lt = new Bundle();
				            lt.putInt("nc", 1);
		    	            intent.putExtras(lt);
				            startActivity(intent);
					           
	
					    }
				}
			    
			}
		}); 
	
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		
		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// RSS
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Tambah RSS Reader
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Twitter
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		
		

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}

		
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(drawer1);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new TambahRSSFeedFragment();
			break;
		case 2:
			fragment = new TwitterFragment();
			break;
		

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(drawer1);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	
	/**
	 * Background Async task to load user profile picture from url
	 * */
	private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public LoadProfileImage(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}


}
