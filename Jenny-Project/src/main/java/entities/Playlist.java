package entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="playlists")
public class Playlist implements Serializable {
	@Id
	@Column(name="playlist_id")private String playlist_id;
	
	@Column(name="playlist_name") private String playlist_name;

	@Column(name="library_persistent_id") String library_persistent_id;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		    name="playlist_songs",
		    joinColumns=
		        @JoinColumn(name="playlist_id", referencedColumnName="playlist_id"),
		    inverseJoinColumns=
		        @JoinColumn(name="song_id", referencedColumnName="song_id")
		    )
	private Set<Song> playlistsongs;
	
	public Playlist() {}
	
	public Playlist(String playlist_id, String playlist_name, String library_persistent_id, Set<Song> playlistsongs){
		this.playlist_id = playlist_id;
		this.playlist_name = playlist_name;
		this.library_persistent_id = library_persistent_id;
		this.playlistsongs = playlistsongs;
	}

	public String getPlaylist_id() {
		return playlist_id;
	}

	public void setPlaylist_id(String playlist_id) {
		this.playlist_id = playlist_id;
	}

	public String getPlaylist_name() {
		return playlist_name;
	}

	public void setPlaylist_name(String playlist_name) {
		this.playlist_name = playlist_name;
	}

	public String getLibrary_persistent_id() {
		return library_persistent_id;
	}

	public void setLibrary_persistent_id(String library_persistent_id) {
		this.library_persistent_id = library_persistent_id;
	}

	public Set<Song> getPlaylistsongs() {
		return playlistsongs;
	}

	public void setPlaylistsongs(Set<Song> playlistsongs) {
		this.playlistsongs = playlistsongs;
	}

}
