package com.test.mynewsreader;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TwitterFragment extends Fragment {
	
	public TwitterFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_twitter, container, false);
         
        return rootView;
    }
	
	//Aksi Saat Tombol Backpressed Ditekan
	  public void onBackPressed()
	  {
		  AlertDialog.Builder mauKeluar = new AlertDialog.Builder(getActivity());
		mauKeluar.setMessage("Anda Yakin Ingin Keluar dari Aplikasi Ini ?").setCancelable(false)
		.setPositiveButton("Ya", new AlertDialog.OnClickListener(){
			public void onClick(DialogInterface arg0, int arg1){
				getActivity().finish();;
			}
		})
		.setNegativeButton("Tidak", new AlertDialog.OnClickListener(){
			public void onClick(DialogInterface dialog, int arg1){
				dialog.cancel();
			}
		});
		AlertDialog dialog1 = mauKeluar.create();
		dialog1.setTitle("My News Reader");
		dialog1.show();
	  }
}
