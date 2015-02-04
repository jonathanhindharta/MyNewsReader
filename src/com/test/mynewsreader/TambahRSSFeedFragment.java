// Class Untuk Membuka Link Rss XML baru
package com.test.mynewsreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class TambahRssFeedFragment extends Fragment {
	
	private EditText txtlxu;
	private Button btnbuka;
	RssService rsvc = new RssService();
	public TambahRssFeedFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_tambah_rss, container, false);
        txtlxu = (EditText) rootView.findViewById(R.id.txtlxu);
        btnbuka = (Button) rootView.findViewById(R.id.btnbuka);
        btnbuka.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				//Merubah link rss pada RssService
				RssService.RSS_LINK = txtlxu.getText().toString();
			  	FragmentManager fragmentManager = ((FragmentActivity) getActivity()).getSupportFragmentManager();
			    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			    RssUtamaFragment rssuf = new RssUtamaFragment();
			    fragmentTransaction.replace(R.id.fragment_tambah, rssuf);
			    fragmentTransaction.commit();
				
			}
		});
        
        return rootView;
    }
}
