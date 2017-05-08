package dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Playlist;
import entities.Song;
import entities.User;

@Local
@Stateless
public class SongDAO implements SongDAOInterface {

	@PersistenceContext
	private EntityManager em;
	
	//--- SELECT ALL ---//
	public Collection<Song> getAllSongs() {
		Query query = em.createQuery("from Song");
		return (List<Song>)query.getResultList();
	}
	
	public Collection<Playlist> getAllPlaylists() {
		Query query = em.createQuery("from Playlist");
		return (List<Playlist>)query.getResultList();
	}
	
	//--- SELECT BY LPID ---//
	public Collection<Song> getSongsByLPID(String lpid){
		Query query = em.createQuery("from Song s where s.library_persistent_id = :lpid");
		query.setParameter("lpid",lpid);
		return (List<Song>)query.getResultList();
	}
		
	public Collection<Playlist> getPlaylistsByLPID(String lpid){
		Query query = em.createQuery("from Playlist p where p.library_persistent_id = :lpid");
		query.setParameter("lpid",lpid);
		return (List<Playlist>)query.getResultList();
	}
	
	//--- DELETE ---//
	public void deleteSong(String song_id){
		Query query = em.createQuery("delete from Song s where s.song_id = :song_id");
		query.setParameter("song_id",song_id);
	}
	
	public void deletePlaylist(String playlist_id){
		Query query = em.createQuery("delete from Playlist p where p.playlist_id = :playlist_id");
		query.setParameter("playlist_id",playlist_id);
	}
	
	//--- UPDATE ---//
	public void updateSong(Song song){
		em.merge(song);
	}
	
	public void updatePlaylist(Playlist playlist){
		em.merge(playlist);
	}
	
	//--- USERS ---//
	public void registerUser(User user){
		em.persist(user);
	}
	
	public User loginUser(String user_name, String user_password){
		Query query = em.createQuery("from User c where c.user_name = :user_name AND c.user_password = :user_password");
		query.setParameter("user_name", user_name);
		query.setParameter("user_password", user_password);
		return (User) query.getSingleResult();
	}
	
	//--- PERSIST ---//
	public void uploadSongs(Set<Song> songs) {
		for(Song song : songs){
			em.persist(song);
		}
	}
	
	public void uploadPlaylist(Playlist playlist){
		em.persist(playlist);
	}

}
