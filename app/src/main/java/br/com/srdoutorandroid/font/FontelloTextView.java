package br.com.srdoutorandroid.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class FontelloTextView extends android.support.v7.widget.AppCompatTextView {
	
	private static Typeface sFontello;

	public FontelloTextView(Context context) {
		super(context);
		if (isInEditMode()) return; //Won't work in Eclipse graphical layout
		setTypeface();
	}

	public FontelloTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode()) return;
		setTypeface();
	}

	public FontelloTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (isInEditMode()) return;
		setTypeface();
	}
	
	private void setTypeface() {
		if (sFontello == null) {
			sFontello = Typeface.createFromAsset(getContext().getAssets(), "fonts/Fontello.ttf");
		}
		setTypeface(sFontello);
	}
}