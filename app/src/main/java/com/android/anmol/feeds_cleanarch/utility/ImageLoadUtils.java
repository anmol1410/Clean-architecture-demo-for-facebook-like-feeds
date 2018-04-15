package com.android.anmol.feeds_cleanarch.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.anmol.feeds_cleanarch.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;

/**
 * Created by anmolsehgal on 28-03-2018.
 * <p>
 * Utility method to Load the images.
 */
public class ImageLoadUtils {

    /**
     * Load the Image from the ImageUrl and set in onto the supplied ImageView.
     * Incase the ProgressBar is provided, show the progressbar until the Image is fetched.
     *
     * @param imageUrl          URL to fetch the image from.
     * @param imageView         ImageView to set te image on.
     * @param progressBar       Progress Bar to show until the image is successfully downloaded.
     * @param tvImageLoadError  TextView to sow error if image failed to load.
     * @param isFeedTextVisible In case Title is present, then don't show Image load error,else show.
     */
    public static void loadRoundedImage(String imageUrl,
                                        @Nullable final ImageView imageView,
                                        @Nullable final ProgressBar progressBar,
                                        @Nullable final TextView tvImageLoadError,
                                        final boolean isFeedTextVisible) {
        if (imageView == null) {
            return;
        }

        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        if (tvImageLoadError != null) {
            tvImageLoadError.setVisibility(View.GONE);
        }

        final Context context = imageView.getContext();
        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .centerCrop()
                .dontAnimate()

                // Listener to listen to the image download status.
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        // Unable to get image.
                        imageView.setVisibility(View.GONE);

                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }

                        // Show error incase tvImageLoadError is provided and title is not present.
                        // If title is present we show title in complete Width, so no need for error.
                        if (tvImageLoadError != null && !isFeedTextVisible) {
                            tvImageLoadError.setVisibility(View.VISIBLE);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        return false;
                    }
                })
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        // Make the corners round.
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(ResUtils.getDimen(context, R.dimen.spacing));
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }
}