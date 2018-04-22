package com.soul.app.utils;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.ImageView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;


public class FasterAnimationsContainer {
    // single instance procedures
    private static FasterAnimationsContainer sInstance;
    private ArrayList<AnimationFrame> mAnimationFrames; // list for all frames of animation
    private int mIndex; // index of current frame

    private boolean mShouldRun; // true if the animation should continue running. Used to stop the animation
    private boolean mIsRunning; // true if the animation prevents starting the animation twice
    private SoftReference<ImageView> mSoftReferenceImageView; // Used to prevent holding ImageView when it should be dead.
    private Handler mHandler; // Handler to communication with UIThread

    private Bitmap mRecycleBitmap;  //Bitmap can recycle by inBitmap is SDK Version >=11

    // Listeners
    private OnAnimationStoppedListener mOnAnimationStoppedListener;
    private OnAnimationFrameChangedListener mOnAnimationFrameChangedListener;

    private FasterAnimationsContainer(ImageView imageView) {
        init(imageView);
    }


    public static FasterAnimationsContainer getInstance(ImageView imageView) {
        if (sInstance == null)
            sInstance = new FasterAnimationsContainer(imageView);
        // sInstance.mRecycleBitmap = null;
        return sInstance;
    }

    /**
     * initialize imageview and frames
     *
     * @param imageView
     */
    public void init(ImageView imageView) {
        mAnimationFrames = new ArrayList<AnimationFrame>();
        mSoftReferenceImageView = new SoftReference<ImageView>(imageView);

        mHandler = new Handler();
        if (mIsRunning) {
            stop();
        }

        mShouldRun = false;
        mIsRunning = false;

        mIndex = -1;
    }

    /**
     * add a frame of animation
     *
     * @param index    index of animation
     * @param resId    resource id of drawable
     * @param interval milliseconds
     */
    public void addFrame(int index, int resId, int interval) {
        mAnimationFrames.add(index, new AnimationFrame(resId, interval));
    }

    /**
     * add a frame of animation
     *
     * @param resId    resource id of drawable
     * @param interval milliseconds
     */
    public void addFrame(int resId, int interval) {
        mAnimationFrames.add(new AnimationFrame(resId, interval));
    }

    /**
     * add all frames of animation
     *
     * @param interval milliseconds
     */
    public void addAllFrames(int[] resIds, int interval) {
        for (int resId : resIds) {
            mAnimationFrames.add(new AnimationFrame(resId, interval));
        }
    }

    /**
     * remove a frame with index
     *
     * @param index index of animation
     */
    public void removeFrame(int index) {
        mAnimationFrames.remove(index);
    }

    /**
     * clear all frames
     */
    public void removeAllFrames() {
        mAnimationFrames.clear();
    }

    /**
     * change a frame of animation
     *
     * @param index    index of animation
     * @param resId    resource id of drawable
     * @param interval milliseconds
     */
    public void replaceFrame(int index, int resId, int interval) {
        mAnimationFrames.set(index, new AnimationFrame(resId, interval));
    }

    private AnimationFrame getNext() {
        mIndex++;
        if (mIndex >= mAnimationFrames.size())
            mIndex = 0;
        return mAnimationFrames.get(mIndex);
    }

    /**
     * set a listener for OnAnimationStoppedListener
     *
     * @param listener OnAnimationStoppedListener
     */
    public void setOnAnimationStoppedListener(OnAnimationStoppedListener listener) {
        mOnAnimationStoppedListener = listener;
    }

    /**
     * set a listener for OnAnimationFrameChangedListener
     *
     * @param listener OnAnimationFrameChangedListener
     */
    public void setOnAnimationFrameChangedListener(OnAnimationFrameChangedListener listener) {
        mOnAnimationFrameChangedListener = listener;
    }

    /**
     * Starts the animation
     */
    public synchronized void start() {
        mShouldRun = true;
        if (mIsRunning)
            return;
        mHandler.post(new FramesSequenceAnimation());
    }

    /**
     * Stops the animation
     */
    public synchronized void stop() {
        //  mHandler.removeCallbacks();
        mShouldRun = false;
        sInstance = null;
    }

    /**
     * Listener of animation to detect stopped
     */
    public interface OnAnimationStoppedListener {
        public void onAnimationStopped();
    }

    /**
     * Listener of animation to get index
     */
    public interface OnAnimationFrameChangedListener {
        public void onAnimationFrameChanged(int index);
    }

    private class AnimationFrame {
        private int mResourceId;
        private int mDuration;

        AnimationFrame(int resourceId, int duration) {
            mResourceId = resourceId;
            mDuration = duration;
        }

        public int getResourceId() {
            return mResourceId;
        }

        public int getDuration() {
            return mDuration;
        }
    }

    private class FramesSequenceAnimation implements Runnable {

        @Override
        public void run() {
            ImageView imageView = mSoftReferenceImageView.get();
            if (!mShouldRun || imageView == null) {
                mIsRunning = false;
                if (mOnAnimationStoppedListener != null) {
                    mOnAnimationStoppedListener.onAnimationStopped();
                }
                return;
            }
            mIsRunning = true;

            if (imageView.isShown()) {
                AnimationFrame frame = getNext();
                GetImageDrawableTask task = new GetImageDrawableTask(imageView);
                task.execute(frame.getResourceId());
                // TODO postDelayed after onPostExecute
                mHandler.postDelayed(this, frame.getDuration());
            }
        }
    }

    private class GetImageDrawableTask extends AsyncTask<Integer, Integer, Drawable> {

        private ImageView mImageView;

        public GetImageDrawableTask(ImageView imageView) {
            mImageView = imageView;
        }

        @SuppressLint("NewApi")
        @Override
        protected Drawable doInBackground(Integer... params) {
            publishProgress(params[0]);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inMutable = true;
//            if (mRecycleBitmap != null)
//                options.inBitmap = mRecycleBitmap;
//            mRecycleBitmap = BitmapFactory.decodeResource(mImageView
//                    .getContext().getResources(), params[0], options);
//            BitmapDrawable drawable = new BitmapDrawable(mImageView.getContext().getResources(), mRecycleBitmap);
            return /*drawable*/null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Integer redId = values[0];
            mImageView.setImageResource(redId);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            super.onPostExecute(result);
//            if (result != null) mImageView.setImageDrawable(result);
            if (mOnAnimationFrameChangedListener != null)
                mOnAnimationFrameChangedListener.onAnimationFrameChanged(mIndex);
        }


    }


}
