package com.callumdennien.jaseinvaders;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class AudioManager implements SoundPool.OnLoadCompleteListener {
    private final Map<Sound, Integer> soundIds;
    private final SoundPool pool;
    private int loadId;
    private boolean ready;
    private boolean online;

    AudioManager(Context context) {
        soundIds = new HashMap<>();
        pool = new SoundPool(3, android.media.AudioManager.STREAM_MUSIC, 0);
        pool.setOnLoadCompleteListener(this);
        pool.load(context, R.raw.incorrect, 0);
        pool.load(context, R.raw.laser, 0);
        pool.load(context, R.raw.bomb, 0);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        this.ready = status == 0;

        Sound sound = Sound.values()[loadId++];
        Log.i("AudioManager", "loaded sound: " + sound);
        soundIds.put(sound, sampleId);
    }

    boolean isReady() {
        return ready;
    }

    public void toggle(boolean toggle) {
        online = toggle;
    }

    void play(Sound sound) {
        if (online) {
            Integer id = soundIds.get(sound);
            assert id != null;
            pool.play(id, 1, 1, 1, 0, 1);
        }
    }
}
