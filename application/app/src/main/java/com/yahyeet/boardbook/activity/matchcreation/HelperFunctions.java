package com.yahyeet.boardbook.activity.matchcreation;

import android.content.Context;

/**
 * @Autor Nox/Aaron Sandgren
 * Utils for the creating the UI.
 */
public class HelperFunctions {

	/**
	 * This converts between pixels and the androids unit DP.
	 * This is used so that the ratio stays the same no mather which phone is used since DP is phone(screen size) dependent
	 * @param px The amount of pixels
	 * @param c The view context so that the screen size can be found
	 * @return	The finished DP measurement
	 */
	static public int dpFromPx(int px, Context c) {
		return (int) (px * c.getResources().getDisplayMetrics().density);
	}
}
