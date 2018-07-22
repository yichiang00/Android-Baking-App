/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.android_me.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.android_me.R;
import com.example.android.android_me.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

import java.util.ArrayList;


// This fragment displays all of the AndroidMe images in one large list
// The list appears as a grid of images
public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener {
    private static final String TAG = "StepDetailFragment";

    public static final String STEP_ID_LIST = "steps_ids";
    public static final String LIST_INDEX = "list_index";
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;
    View rootView;
//    private PlaybackStateCompat.Builder mStateBuilder;

//    private static MediaSessionCompat mMediaSession;

    ArrayList<Step> mSteps;
    Integer mListIndex = 0;

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    public void setData(ArrayList<Step> sSteps)
    {
        mSteps = sSteps;
    }
    public void setListIndex(Integer index)
    {
        mListIndex = index;
    }


    // Mandatory empty constructor
    public StepDetailFragment() {
    }


    // Inflates the GridView of all AndroidMe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_detail_step, container, false);


        if(savedInstanceState != null) {
            mSteps = (ArrayList<Step>) savedInstanceState.getSerializable(STEP_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);
        }
        TextView descTextView = rootView.findViewById(R.id.receipt_step_description);
        descTextView.setText(mSteps.get(mListIndex).getDescription());
        // Initialize the player view.
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);

        // Initialize the Media Session.
        initializeMediaSession();
        // Initialize the player.
        initializePlayer(Uri.parse(mSteps.get(mListIndex).getVideoURL()));

        return rootView;
    }
    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    // ExoPlayer Event Listeners

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    /**
     Source:  https://raw.githubusercontent.com/yusufcakmak/ExoPlayerSample/master/app/src/main/java/com/yusufcakmak/exoplayersample/VideoPlayerActivity.java
     https://github.com/ayalus/ExoPlayer-2-Example/blob/master/ExoPlayer2Example/app/src/main/java/com/ayalus/exoplayer2example/MainActivity.java
     */
//    private void initializePlayer(Uri uri) {
//
//
//
//        // 1. Create a default TrackSelector
//        bandwidthMeter = new DefaultBandwidthMeter();
//
//        TrackSelection.Factory videoTrackSelectionFactory =
//                new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
//
//        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//        // 2. Create a default LoadControl
//        LoadControl loadControl = new DefaultLoadControl();
//        // 3. Create the player
//        mPlayerView = new SimpleExoPlayerView(getContext());
//        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
//        mExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector, loadControl);
//        //Set media controller
//        mPlayerView.setUseController(true);
//        mPlayerView.requestFocus();
//        // Bind the player to the view.
//        mPlayerView.setPlayer(mExoPlayer);
//
//        mExoPlayer.setPlayWhenReady(shouldAutoPlay);
//
//
////        MediaSource mediaSource = new ExtractorMediaSource(uri,
////                mediaDataSourceFactory, extractorsFactory, null, null);
////
////        mExoPlayer.prepare(mediaSource);
//
//        //Measures bandwidth during playback. Can be null if not required.
//        DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();
//        //Produces DataSource instances through which media data is loaded.
//        String userAgent = Util.getUserAgent(getContext(), "bakingAPP");
//
//        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(),userAgent , bandwidthMeterA);
//        //Produces Extractor instances for parsing the media data.
//        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//
//
////FOR LIVESTREAM LINK:
//        MediaSource videoSource = new HlsMediaSource(uri, dataSourceFactory, 1, null, null);
//        final LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
//
//// Prepare the player with the source.
//        mExoPlayer.prepare(loopingSource);
//
//        mExoPlayer.addListener(new ExoPlayer.EventListener() {
//            @Override
//            public void onTimelineChanged(Timeline timeline, Object manifest) {
//                Log.v(TAG, "Listener-onTimelineChanged...");
//            }
//
//            @Override
//            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//                Log.v(TAG, "Listener-onTracksChanged...");
//            }
//
//            @Override
//            public void onLoadingChanged(boolean isLoading) {
//                Log.v(TAG, "Listener-onLoadingChanged...isLoading:"+isLoading);
//            }
//
//            @Override
//            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                Log.v(TAG, "Listener-onPlayerStateChanged..." + playbackState);
//            }
//
////            @Override
////            public void onRepeatModeChanged(int repeatMode) {
////                Log.v(TAG, "Listener-onRepeatModeChanged...");
////            }
//
//            @Override
//            public void onPlayerError(ExoPlaybackException error) {
//                Log.v(TAG, "Listener-onPlayerError...");
//                mExoPlayer.stop();
//                mExoPlayer.prepare(loopingSource);
//                mExoPlayer.setPlayWhenReady(true);
//            }
//
//            @Override
//            public void onPositionDiscontinuity() {
//                Log.v(TAG, "Listener-onPositionDiscontinuity...");
//            }
//
////            @Override
////            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
////                Log.v(TAG, "Listener-onPlaybackParametersChanged...");
////            }
//        });
//
//        mExoPlayer.setPlayWhenReady(true); //run file/link when ready to play.
//        mExoPlayer.setVideoDebugListener(this); //for listening to resolution change and  outputing the resolution
//    }
//    @Override
//    public void onVideoEnabled(DecoderCounters counters) {
//
//    }
//
//    @Override
//    public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
//
//    }
//
//    @Override
//    public void onVideoInputFormatChanged(Format format) {
//
//    }
//
//    @Override
//    public void onDroppedFrames(int count, long elapsedMs) {
//
//    }
//
//    @Override
//    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
//        Log.v(TAG, "onVideoSizeChanged ["  + " width: " + width + " height: " + height + "]");
//    }
//
//    @Override
//    public void onRenderedFirstFrame(Surface surface) {
//
//    }
//
//    @Override
//    public void onVideoDisabled(DecoderCounters counters) {
//
//    }
    /**
            * Method that is called when the ExoPlayer state changes. Used to update the MediaSession
     * PlayBackState to keep in sync, and post the media notification.
     * @param playWhenReady true if ExoPlayer is playing, false if it's paused.
            * @param playbackState int describing the state of ExoPlayer. Can be STATE_READY, STATE_IDLE,
            *                      STATE_BUFFERING, or STATE_ENDED.
            */
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
        //showNotification(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }

    @Override
    public void onPositionDiscontinuity() {
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            shouldAutoPlay = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
            trackSelector = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(Uri.parse(mSteps.get(mListIndex).getVideoURL()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer(Uri.parse(mSteps.get(mListIndex).getVideoURL()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putSerializable(STEP_ID_LIST, mSteps);
        currentState.putInt(LIST_INDEX, mListIndex);
    }


}
