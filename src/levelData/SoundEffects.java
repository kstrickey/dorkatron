package levelData;

import javafx.scene.media.AudioClip;

public enum SoundEffects {
	BOUNCE								("/resources/sounds/bounce.wav"),
	FIRE_SLINGSHOT						("/resources/sounds/fire slingshot.wav"),
	HIT_ENEMY							("/resources/sounds/enemy hit.wav"),
	NO_AMMO_LEFT						("/resources/sounds/no ammo left.wav"),
	STOMP_HOVERING						("/resources/sounds/stomp hovering.wav"),
	STOMP_RECOVERY						("/resources/sounds/stomp recovery.wav"),
	STOMP_THROUGH_ENEMY					("/resources/sounds/stomp through enemy.wav");
	
	private SoundEffects(String file) {
		audioClip = new AudioClip(getClass().getResource(file).toString());
	}
	
	public AudioClip getAudioClip() {
		return audioClip;
	}
	
	private AudioClip audioClip;
	
}
