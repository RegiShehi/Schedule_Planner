package com.INFSeniorProject.eventtracker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.widget.ImageView;

public class PictureUtils {

	@SuppressWarnings("deprecation")
	public static BitmapDrawable getScaledDrawable(Activity a, String path) {
		Display display = a.getWindowManager().getDefaultDisplay();
		float destWidth = display.getWidth();
		float destHeight = display.getHeight();

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		float srcWidth = options.outWidth;
		float srcHeight = options.outHeight;

		int inSampleSize = 1;
		if (srcHeight > destHeight || srcWidth > destWidth) {
			if (srcWidth > srcHeight) {
				inSampleSize = Math.round(srcHeight / destHeight);
			} else {
				inSampleSize = Math.round(srcWidth / destWidth);
			}
		}

		options = new BitmapFactory.Options();
		options.inSampleSize = inSampleSize;

		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		return new BitmapDrawable(a.getResources(), bitmap);
	}

	public static void cleanImageView(ImageView imageView) {
		if (!(imageView.getDrawable() instanceof BitmapDrawable))
			return;

		BitmapDrawable b = (BitmapDrawable) imageView.getDrawable();
		b.getBitmap().recycle();
		imageView.setImageDrawable(null);
	}

	public static BitmapDrawable getPortraitDrawable(ImageView iView,
			BitmapDrawable origImage) {
		Matrix m = new Matrix();

		m.postRotate(90);
		Bitmap br = Bitmap.createBitmap(origImage.getBitmap(), 0, 0,
				origImage.getIntrinsicWidth(), origImage.getIntrinsicHeight(),
				m, true);
		origImage = new BitmapDrawable(iView.getResources(), br);

		return origImage;
	}
}
