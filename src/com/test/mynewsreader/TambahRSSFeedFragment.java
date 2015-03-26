/** Aplikasi New Reader
 * Di Buat oleh Jonathan Hindharta**/

//Activity untuk membuka link Rss Inputan User
package com.test.mynewsreader;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TambahRssFeedFragment extends Fragment {
	
	Spinner  mSpr_pr_name;
	String[] mPr_link=null;
	String[] mPr_name=null;
	private EditText txtlxu;
	private Button btnbuka,btnbuka2;
	RssService rsvc = new RssService();
	public TambahRssFeedFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_tambah_rss, container, false);
        
        // Array of Portal Link
        mPr_link = getResources().getStringArray(R.array.pr_link);
 
        // Array of Portal name
        mPr_name = getResources().getStringArray(R.array.pr_name);
 
        // Creating an array adapter with an array of Portal name
        // to populate the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,  mPr_name);
 
        // Getting reference to the Spinner
        mSpr_pr_name = (Spinner) rootView.findViewById(R.id.spr_pr_name);
 
        // Setting adapter on Spinner to set Portal name
        mSpr_pr_name.setAdapter(adapter);

        txtlxu = (EditText) rootView.findViewById(R.id.txtlxu);
        btnbuka = (Button) rootView.findViewById(R.id.btnbuka);
        btnbuka2 = (Button) rootView.findViewById(R.id.btnbuka2);
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
        btnbuka2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				//Merubah link rss pada RssService
				int selectedPosition = mSpr_pr_name.getSelectedItemPosition();
				RssService.RSS_LINK =  mPr_link[selectedPosition];
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
