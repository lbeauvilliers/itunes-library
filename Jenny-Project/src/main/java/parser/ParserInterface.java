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

import dao.SongDAO;
import dao.SongDAOInterface;
import entities.Song;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

@Local
 
public interface ParserInterface {
	
	public void parser();

}
