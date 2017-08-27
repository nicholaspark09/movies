package com.example.vn008xw.golf.util;

import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;

/**
 * Created by vn008xw on 8/26/17.
 */

public final class IntentUtil {

  private IntentUtil() {
    throw new AssertionError("No instances please");
  }

  public static Intent getInstantAppIntent(Context context, String url) {
      final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
      intent.setPackage(context.getPackageName());
      intent.addCategory(Intent.CATEGORY_BROWSABLE);
      return intent;
  }
}
