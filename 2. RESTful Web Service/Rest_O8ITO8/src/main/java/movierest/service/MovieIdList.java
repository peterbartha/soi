package movierest.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "movies")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class MovieIdList {
	
	@XmlElement(name = "id")
	private List<Integer> ids;
	
	public MovieIdList() {
		ids = new ArrayList<Integer>();
	}
	
	public void add(int id) {
		ids.add(new Integer(id));
	}
	
}
