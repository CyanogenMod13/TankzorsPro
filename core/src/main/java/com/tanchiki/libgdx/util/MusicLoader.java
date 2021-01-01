package com.tanchiki.libgdx.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicLoader {
	private static MusicLoader musicLoader = null;

	public static MusicLoader getInstance() {
		if (musicLoader == null) musicLoader = new MusicLoader();
		return musicLoader;
	}

	Music current = null;
	
	public MusicLoader() {}

	private Music load(String name) {
		if (current != null) {
			current.stop();
			current.dispose();
		}
		Music music = Gdx.audio.newMusic(Gdx.files.internal("music/" + name + ".mp3"));
		music.setLooping(true);
		music.setVolume(Settings.volumeMusic);
		current = music;
		return music;
	}
	
	public Music getIntro() {
		return load("intro");
	}
	
	public Music getTrack(int number) {
		return load("track" + number);
	}
	
	public void setVolume(float volume) {
		if (current != null) current.setVolume(volume);
	}
}
