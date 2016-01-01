package gameWindow;

import java.net.URL;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import levelData.SoundEffects;

public class SoundPlayer {
	
	public SoundPlayer() {
		totalVolume = .5;
		backgroundMusicVolume = 1;
		soundEffectVolume = 1;
	}
	
	public void setTotalVolume(double totalVolume) {
		this.totalVolume = totalVolume;
		refreshVolumes();
	}
	
	public void setBackgroundMusicVolume(double backgroundMusicVolume) {
		this.backgroundMusicVolume = backgroundMusicVolume;
		refreshVolumes();
	}
	
	public void setSoundEffectVolume(double soundEffectVolume) {
		this.soundEffectVolume = soundEffectVolume;
	}
	
	public void setBackgroundMusicFromURL(String backgroundMusicURL) {
		try {
			URL resource = getClass().getResource(backgroundMusicURL);
			Media media = new Media(resource.toString());
			backgroundMusicPlayer = new MediaPlayer(media);
			backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			refreshVolumes();
		} catch (Exception e) {
			e.printStackTrace();
			backgroundMusicPlayer = null;
		}
	}
	
	/**
	 * Sets current volume and plays the background music clip if not null.
	 */
	public void playBackgroundMusic() {
		refreshVolumes();			// maybe not necessary
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.play();
		}
	}
	
	/**
	 * Pauses the background music MediaPlayer if not null.
	 */
	public void pauseBackgroundMusic() {
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.pause();
		}
	}
	
	/**
	 * Stops the background music clip if not null.
	 */
	public void stopBackgroundMusic() {
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.stop();
		}
	}
	
	/**
	 * Plays the AudioClip of a particular SoundEffects.
	 * @param effect
	 */
	public void playSoundEffect(SoundEffects effect) {
		AudioClip clip = effect.getAudioClip();
		clip.setVolume(soundEffectVolume * totalVolume);
		clip.play();
	}
	
	
	private void refreshVolumes() {
		if (backgroundMusicPlayer != null) {
			backgroundMusicPlayer.setVolume(backgroundMusicVolume * totalVolume);
		}
	}
	
	private MediaPlayer backgroundMusicPlayer;
	
	private double totalVolume;				// range [0, 1]
	private double backgroundMusicVolume;	// range [0, 1]
	private double soundEffectVolume;		// range [0, 1]
	
}
