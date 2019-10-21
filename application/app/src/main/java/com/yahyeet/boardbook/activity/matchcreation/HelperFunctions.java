package com.yahyeet.boardbook.activity.matchcreation;

import android.content.Context;

public class HelperFunctions {

	// TODO: Move somewhere more fitting, need utils package somewhere
	static public int dpFromPx(int px, Context c) {
		return (int) (px * c.getResources().getDisplayMetrics().density);
	}
}
