package br.org.mantra.anjoy.ui.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import br.org.mantra.anjoy.R;

public class ProgressDialogFragment extends DialogFragment {


	private String title;

	private ImageView imageView;

	private Animation animation;

	private View view;


	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, Bundle instanceState) {			

		view = null;
		view = inflater.inflate(R.layout.progress_dialog_fragment, container);

		setCancelable(false);
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);   
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
		animation = AnimationUtils.loadAnimation(inflater.getContext(),
				R.drawable.rotate_loading);

		imageView = (ImageView) view.findViewById(R.id.img_dialog);
		imageView.setImageResource(R.drawable.progress_img);
		view.setBackgroundColor(Color.TRANSPARENT);
		imageView.setVisibility(View.VISIBLE);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle arg0) {    
		super.onActivityCreated(arg0);

	}

	@Override
	public void onSaveInstanceState(final Bundle arg0) {
		imageView.clearAnimation();
		//animation.stop();
		super.onSaveInstanceState(arg0);
	}

	@Override
	public void onResume() {
		if (imageView == null) {
			imageView = (ImageView) view.findViewById(R.id.img_dialog);
		}

		imageView.startAnimation(animation);
		//animation = (AnimationDrawable)imageView.getBackground();
		//animation.start();
		super.onResume();
	}

	public String getMessage() {
		return title;
	}

	public void setMessage(final String message) {
		this.title = message;
	}


}