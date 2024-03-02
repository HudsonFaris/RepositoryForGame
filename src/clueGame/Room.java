package clueGame;
import java.util.List;

/**
 * Room Class - This class holds the constructor for a room and any getters/setters that are relevant for a room.
 * 
 * @author Hudson Faris
 * @author Sam Bangapadang
 * 
 * Sources: JavaDocs
 * Date: 3/1/2024
 * 
 */

public class Room {
    private String name;
    private BoardCell centerCell;
    private BoardCell labelCell;

    
    
    
    /**
     * Constructor
     * @param name
     */
    public Room(String name) {
        this.name = name;
        
    }
    
    
    



 // Getters and Setters for centerCell and labelCell
    public BoardCell getCenterCell() {
        return centerCell;
    }

    public void setCenterCell(BoardCell centerCell) {
        this.centerCell = centerCell;
    }

    public BoardCell getLabelCell() {
        return labelCell;
    }

    public void setLabelCell(BoardCell labelCell) {
        this.labelCell = labelCell;
    }
    
    public String getName() {
    	return name;
    }
    
    

}