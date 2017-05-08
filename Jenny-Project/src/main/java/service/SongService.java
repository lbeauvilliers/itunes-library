package service;

import java.util.Collection;
import java.util.Set;

import javax.ejb.*;

import dao.SongDAOInterface;
import entities.Playlist;
import entities.Song;
import entities.User;

@Stateless
@Local
@TransactionAttribute (TransactionAttributeType.REQUIRED)
public class SongService implements SongServiceInterface {
	
	@EJB
	private SongDAOInterface dao;
	
	//--- SELECT ALL ---//
	public Collection<Song> getAllSongs() {
		return dao.getAllSongs();
	}
	
	public Collection<Playlist> getAllPlaylists() {
		return dao.getAllPlaylists();
	}
	
	//--- SELECT BY LPID ---//
	public Collection<Song> getSongsByLPID(String lpid){
		return dao.getSongsByLPID(lpid);
	}
		
	public Collection<Playlist> getPlaylistsByLPID(String lpid){
		return dao.getPlaylistsByLPID(lpid);
	}
	
	//--- DELETE ---//
	public void deleteSong(String song_id){
		dao.deleteSong(song_id);
	}
	
	public void deletePlaylist(String playlist_id){
		dao.deletePlaylist(playlist_id);
	}
	
	//--- UPDATE ---//
	public void updateSong(Song song){
		dao.updateSong(song);
	}
	
	public void updatePlaylist(Playlist playlist){
		dao.updatePlaylist(playlist);
	}
	
	//--- USERS ---//
	public void registerUser(User user){
		dao.registerUser(user);
	}
	
	public User loginUser(String user_name, String user_password){
		return dao.loginUser(user_name, user_password);
	}
	
	//--- PERSIST ---//
	public void uploadSongs(Set<Song> songs){
		dao.uploadSongs(songs);
	}
	
	public void uploadPlaylist(Playlist playlist){
		dao.uploadPlaylist(playlist);
	}

}
