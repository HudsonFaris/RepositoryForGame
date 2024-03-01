package clueGame;

public class Room {
    private String name;
    private BoardCell centerCell;
    private BoardCell labelCell;
    
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

}