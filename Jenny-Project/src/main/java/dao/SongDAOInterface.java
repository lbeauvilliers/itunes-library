package dao;

import javax.ejb.Local;

import entities.Playlist;
import entities.Song;
import entities.User;

import java.util.Collection;
import java.util.Set;

@Local
public interface SongDAOInterface {
	
	//--- SELECT ALL ---//
	public Collection<Song> getAllSongs();
	public Collection<Playlist> getAllPlaylists();
	
	//--- SELECT BY LPID ---//
	public Collection<Song> getSongsByLPID(String lpid);
	public Collection<Playlist> getPlaylistsByLPID(String lpid);
	
	//--- DELETE ---//
	public void deleteSong(String song_id);
	public void deletePlaylist(String playlist_id);
	
	//--- UPDATE ---//
	public void updateSong(Song song);
	public void updatePlaylist(Playlist playlist);
	
	//--- USERS ---//
	public void registerUser(User user);
	
	public User loginUser(String user_name, String user_password);
	
	//--- PERSIST ---//
	public void uploadSongs(Set<Song> songs);
	
	public void uploadPlaylist(Playlist playlist);

}
