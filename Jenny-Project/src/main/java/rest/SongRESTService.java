package rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import entities.Playlist;
import entities.PlaylistList;
import entities.Song;
import entities.SongList;
import entities.User;
import parser.ParserInterface;
import service.SongServiceInterface;

@Path("/base")
public class SongRESTService {
	
	@Inject
	private SongServiceInterface service;
	
	@Inject
	private ParserInterface parser;
	
	//--- SELECT ALL ---//
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/getsongs")
	public SongList getAllSongs(){
		SongList list = new SongList();
		list.setSongList(service.getAllSongs());
		return list;
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/getplaylists")
	public PlaylistList getAllPlaylists(){
		PlaylistList list = new PlaylistList();
		list.setPlaylistList(service.getAllPlaylists());
		return list;
	}
	
	//--- SELECT BY LPID ---//
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/getsongsbylpid")
	public SongList getSongsByLPID(@QueryParam("lpid") String lpid){
		SongList list = new SongList();
		list.setSongList(service.getSongsByLPID(lpid));
		return list;
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/getplaylistsbylpid")
	public PlaylistList getPlaylistsByLPID(@QueryParam("lpid") String lpid){
		PlaylistList list = new PlaylistList();
		list.setPlaylistList(service.getPlaylistsByLPID(lpid));
		return list;
	}
	
	//--- DELETE ---//
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deletesong")
	public void deleteSong(@QueryParam("song_id") String song_id){
		service.deleteSong(song_id);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deleteplaylist")
	public void deletePlaylist(@QueryParam("playlist_id") String playlist_id){
		service.deletePlaylist(playlist_id);
	}
	
	//--- UPDATE ---//
/*	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updatesong")
	public void updateSong(@QueryParam("song") Song song){
		service.updateSong(song);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateplaylist")
	public void updatePlaylist(@QueryParam("playlist") Playlist playlist){
		service.updatePlaylist(playlist);
	}*/
	
	//--- USERS ---//
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/register")
	public void registerUser(User user){
		service.registerUser(user);
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public User loginUser(@QueryParam("user_name") String user_name, @QueryParam("user_password") String user_password){
		return service.loginUser(user_name, user_password);
	}
	
	//--- PARSE ---//
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/upload")
	public void parser(){
		parser.parser();
	}

}
