package com.android.test;

import com.android.test.ItemListFragment.ViewCreatedCallback;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class MasterDetailFlowFragment extends Fragment {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private ItemListFragment itemListFragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		return inflater.inflate(R.layout.activity_item_list, container, false);
		return inflater.inflate(R.layout.activity_item_twopane, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		if (getView().findViewById(R.id.item_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;
			
			final LinearLayout layout = (LinearLayout)getView().findViewById(R.id.item_list);
			final View detailContainer = getView().findViewById(R.id.item_detail_container);
			
			itemListFragment = new ItemListFragment();
			itemListFragment.setViewCreatedCallback(new ViewCreatedCallback() {
				
				@Override
				public void onViewCreated(View view, Bundle savedInstanceState) {
					itemListFragment.getView().setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f));
					itemListFragment.setActivateOnItemClick(true);
					// hack: show detailContainer at right side
					layout.removeView(detailContainer);
					layout.addView(detailContainer);
				}
			});
			
			FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
			transaction.add(R.id.item_list, itemListFragment).commit();
		}
		else {
			itemListFragment = new ItemListFragment();
			FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
			transaction.add(R.id.item_list, itemListFragment).commit();
		}
	}

	public boolean isTwoPane() {
		return mTwoPane;
	}
}
