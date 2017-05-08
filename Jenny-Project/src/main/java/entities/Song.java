package entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="songs")
public class Song implements Serializable {
	
	@Id
	@Column(name="song_id") private String song_id;
	
	@Column(name="song_name") private String song_name;
	@Column(name="artist") private String artist;
	@Column(name="album") private String album;
	@Column(name="library_persistent_id") private String library_persistent_id;

	public Song() {}
	
	public Song(String song_id, String song_name, String artist, String album, String library_persistent_id){
		this.song_id = song_id;
		this.song_name = song_name;
		this.artist = artist;
		this.album = album;
		this.library_persistent_id = library_persistent_id;
	}

	public String getSong_id() {
		return song_id;
	}

	public void setSong_id(String song_id) {
		this.song_id = song_id;
	}

	public String getSong_name() {
		return song_name;
	}

	public void setSong_name(String song_name) {
		this.song_name = song_name;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getLibrary_persistent_id() {
		return library_persistent_id;
	}

	public void setLibrary_persistent_id(String library_persistent_id) {
		this.library_persistent_id = library_persistent_id;
	}

}
