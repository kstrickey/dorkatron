package gameEngines.realmEngine;

public enum SquareSide {
	RIGHT			("right"),
	BOTTOM			("bottom"),
	LEFT			("left"),
	TOP				("top");
	
	private SquareSide(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public SquareSide getOppositeSide() {
		switch (this) {
			case RIGHT:
				return LEFT;
			case BOTTOM:
				return TOP;
			case LEFT:
				return RIGHT;
			case TOP:
				return BOTTOM;
		}
		
		return null;
	}
	
	/**
	 * Returns the SquareSide value based on a passed String name.
	 * @param name
	 * @return
	 */
	public static SquareSide getFromName(String name) {
		for (SquareSide side : SquareSide.values()) {
			if (side.name.equals(name)) {
				return side;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns true if side1 and side2 form a right angle, and false otherwise.
	 * @param side1
	 * @param side2
	 * @return
	 */
	public static boolean isBent(SquareSide side1, SquareSide side2) {
		if (side1 == RIGHT && side2 != LEFT) {
			return true;
		}
		if (side1 == BOTTOM && side2 != TOP) {
			return true;
		}
		if (side1 == LEFT && side2 != RIGHT) {
			return true;
		}
		if (side1 == TOP && side2 != BOTTOM) {
			return true;
		}
		return false;
	}
	
	private String name;
	
}
