package com.yahyeet.boardbook.activity.viewutils;

import android.content.Context;

public class ViewUtils {

	public static int dpFromPx(int px, Context c) {
		return (int) (px * c.getResources().getDisplayMetrics().density);
	}
}
