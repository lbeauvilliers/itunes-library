package parser;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import dao.SongDAOInterface;
import entities.Playlist;
import entities.Song;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

@Local
@Stateless
public class Parser implements ParserInterface {
	String library_persistent_id;
	Set<Song> songs = new HashSet<Song>();
	
	@EJB
	private SongDAOInterface dao;

	public void parser(){

		try {

			//file to be parsed
			File inputFile = new File("iTunes Music Library1.xml");
			
			//document builder factory
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			
			//get the root element
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();
			
			//FIRST LAYER
			//get the outer dictionary
			NodeList outerLayer = root.getElementsByTagName("dict");
			Node outerDict = outerLayer.item(0);
			
			//SECOND LAYER
			NodeList secondLayer = outerDict.getChildNodes();
			//print the Library Persistent ID
			for(int h = 0; h < secondLayer.getLength(); ++h){
				if(secondLayer.item(h).getTextContent().equals("Library Persistent ID")){
					library_persistent_id = secondLayer.item(h).getNextSibling().getFirstChild().getTextContent();
					break;
				}
			}
			
			//get the outer song list dictionary and playlist array
			Element songlistDict = null;
			Element playlistArray = null;
			
			for(int i = 0; i < secondLayer.getLength(); ++i){
				if(secondLayer.item(i).getNodeName().equals("dict")){
					songlistDict = (Element)secondLayer.item(i);
				}else if(secondLayer.item(i).getNodeName().equals("array")){
					playlistArray = (Element)secondLayer.item(i);
				}
			}
			
			//THIRD LAYER
			//get a list of song dicts and playlist nodes
			NodeList songDicts = songlistDict.getElementsByTagName("dict");
			NodeList playlistNodes = playlistArray.getChildNodes();
			
			//PARSER
			
			//---------------------//
			//------- SONGS -------//
			//---------------------//
			
			//cycle through each node (song) in the songDicts NodeList
			for (int i = 0; i < songDicts.getLength(); ++i) {
				Element song = (Element)songDicts.item(i);
				NodeList songDetails = song.getChildNodes();
				
				//set the variables to be persisted
				String song_id = null;
				String song_name = null;
				String artist = null;
				String album = null;
				
				//cycle through each child node to create the song object
				for(int j = 0; j < songDetails.getLength(); ++j) {
					if(songDetails.item(j).getTextContent().equals("Track ID")){
						song_id = library_persistent_id + songDetails.item(j).getNextSibling().getFirstChild().getTextContent();
					}else if(songDetails.item(j).getTextContent().equals("Name")){
						song_name = songDetails.item(j).getNextSibling().getFirstChild().getTextContent();
					}else if(songDetails.item(j).getTextContent().equals("Artist")){
						artist = songDetails.item(j).getNextSibling().getFirstChild().getTextContent();
					}else if(songDetails.item(j).getTextContent().equals("Album")){
						album = songDetails.item(j).getNextSibling().getFirstChild().getTextContent();
					}	
				}
				
				//add the song object to the songs list
				if(song_id != null){
					Song newSong = new Song(song_id,song_name,artist,album,library_persistent_id);
					songs.add(newSong);
				}
				
			}
			
			//-------------------------//
			//------- PLAYLISTS -------//
			//-------------------------//
			
			//cycle through every playlist node
			for(int k = 0; k < playlistNodes.getLength(); ++k) {
				
				//set the variables to be persisted
				String playlist_id = null;
				String playlist_name = null;
				Set<Song> playlistsongs = new HashSet<Song>();
				
				//get the dicts (playlists)
				if(playlistNodes.item(k).getNodeName().equals("dict")){
					
					//save the playlist dict as an element
					Element currentPlaylistDict = (Element) playlistNodes.item(k);
					
					//FOR PLAYLIST NAME AND ID
					//get all of the playlist's children
					NodeList playlistData = currentPlaylistDict.getChildNodes();
					
					//cycle through the children
					for(int l = 0; l < playlistData.getLength(); ++l){
						
						//find and print the name and playlist id
						if(playlistData.item(l).getTextContent().equals("Name")){
							playlist_name = playlistData.item(l).getNextSibling().getFirstChild().getTextContent();
						}else if(playlistData.item(l).getTextContent().equals("Playlist ID")){
							playlist_id = library_persistent_id + playlistData.item(l).getNextSibling().getFirstChild().getTextContent();
						}
					}
					
					//FOR PLAYLIST SONGS
					//get the playlist array if it exists (to get the songs on the playlist)
					NodeList currentPlaylistArrayNode = currentPlaylistDict.getElementsByTagName("array");
					Element currentPlaylistArray = (Element) currentPlaylistArrayNode.item(0);
					
					try {	
						NodeList songIDs = currentPlaylistArray.getElementsByTagName("integer");
						
						//print the songs
						for(int m = 0; m < songIDs.getLength();++m){
							String song_id = library_persistent_id + songIDs.item(m).getTextContent();
							
							for(Song song : songs){
								if(song.getSong_id().equals(song_id)){
									playlistsongs.add(song);
								}
							}

						}
					} catch (Exception e) {
						System.out.println("Empty playlist.");
					}
				
				
				if(playlist_id != null){
					Playlist newPlaylist = new Playlist(playlist_id, playlist_name, library_persistent_id, playlistsongs);
					dao.uploadPlaylist(newPlaylist);
				}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//persist the songs
		dao.uploadSongs(songs);
	}

}
