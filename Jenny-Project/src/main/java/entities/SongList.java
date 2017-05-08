package entities;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SongList implements Serializable {
	private Collection<Song> songList;

	public Collection<Song> getSongList() {
		return songList;
	}

	public void setSongList(Collection<Song> songList) {
		this.songList = songList;
	}

}
