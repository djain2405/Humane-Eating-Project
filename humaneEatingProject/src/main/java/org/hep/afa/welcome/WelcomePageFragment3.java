package org.hep.afa.welcome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.hep.afa.R;

public class WelcomePageFragment3 extends Fragment {
	ImageButton imageButtonGetStarted;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// View view = inflater.inflate(R.layout.welcome_page_fragment3_layout,
		// container, false);
		// imageButtonGetStarted = (ImageButton)
		// view.findViewById(R.id.imageButton1);
		// imageButtonGetStarted.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// // startActivity (new
		// Intent(getApplicationContext(),HumanEatingMainActivity.class));
		// startActivity(new Intent(getActivity(),
		// HumanEatingMainActivity.class));
		// }
		// });
		if (container == null) {
			return null;
		}
		return inflater.inflate(R.layout.welcome_page_fragment3_layout,
				container, false);

	}

	private void initializeViews() {
	}

}
