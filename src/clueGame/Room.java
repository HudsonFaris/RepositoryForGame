package clueGame;

/**
 * Room Class - This class holds the constructor for a room and any getters/setters that are relevant for a room.
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/3/2024
 * 
 */

public class Room {

	//Class... uhh variables. -huddy
	private String name;
	BoardCell centerCell;
	BoardCell labelCell;
	
	public Room() {}
	
	public Room(String name) {
		this.name = name;
	}
	
	// returns the labelcell
	public BoardCell getLabelCell() {
		return labelCell;
	}
	
	//getter
	public String getName() {
		return name;
	}
	
	//getter for the center cell
	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	//sets label cell
	public void setLabel(BoardCell cell) {
		labelCell = cell;
	}
	
	// sets center cell
	public void setCenter(BoardCell cell) {
		centerCell = cell;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}