package com.yahyeet.boardbook.activity.CreateingMatches;

import android.content.Context;

public class HelperFunctions {

	static public int dpFromPx(int px, Context c) {
		return (int) (px * c.getResources().getDisplayMetrics().density);
	}
}
