package org.hep.afa.welcome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import org.hep.afa.main.HumaneEatingMainActivity;
import org.hep.afa.R;
import org.hep.afa.utils.GeneralUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomePageFragment4 extends Fragment {


    @BindView(R.id.disable_future_welcome_page_display_checkbox)
	CheckBox disableFutureWelcomePageDisplayCheckbox;

    @BindView(R.id.get_started_button)
    Button buttonWelcomePageFragment4GetStarted;

    @BindView(R.id.fb_button)
    ShareButton shareOnFacebook;

    @BindView(R.id.tweet_button)
    View tweetButton;

    @BindView(R.id.email_button)
    View emailButton;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.welcome_page_fragment4_layout,
				container, false);

		ButterKnife.bind(this, view);

		buttonWelcomePageFragment4GetStarted
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (disableFutureWelcomePageDisplayCheckbox.isChecked()) {
							WelcomePage.disableFutureDisplays(getContext());
						}

						startActivity(new Intent(getActivity(),
								HumaneEatingMainActivity.class));
					}
				});


				callbackManager = CallbackManager.Factory.create();
				shareDialog = new ShareDialog(this);
				if (ShareDialog.canShow(ShareLinkContent.class)) {
					ShareLinkContent linkContent = new ShareLinkContent.Builder()
							.setContentUrl(Uri.parse(getString(R.string.facebook_share_url)))
							.build();
					shareOnFacebook.setShareContent(linkContent);
				}

			    //tweet button
			    tweetButton
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								String tweetUrl = "https://twitter.com/intent/tweet?text=" + getString(R.string.tweet_body);
								Uri uri = Uri.parse(tweetUrl);
								startActivity(new Intent(Intent.ACTION_VIEW, uri));
							}
						});

		// email button
		emailButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                String emailBody = getString(R.string.email_invitation_body);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_invitation_subject));
                i.putExtra(Intent.EXTRA_TEXT, emailBody);
                if (GeneralUtils.isIntentSafe(i)) {
                    startActivity(Intent.createChooser(i, getString(R.string.email_invitation_share_title)));
                }
                else {
                    Toast.makeText(getActivity(), getString(R.string.email_no_client), Toast.LENGTH_SHORT).show();
                }
			}
		});

		if (container == null) {
			return null;
		}

		return view;

	}

	@Override
	public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}



}
