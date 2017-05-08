package entities;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlaylistList implements Serializable {
	private Collection<Playlist> playlistList;

	public Collection<Playlist> getPlaylistList() {
		return playlistList;
	}

	public void setPlaylistList(Collection<Playlist> playlistList) {
		this.playlistList = playlistList;
	}

}