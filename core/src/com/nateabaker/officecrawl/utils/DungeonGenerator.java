package com.nateabaker.officecrawl.utils;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class DungeonGenerator {
	
	private static TileSet t = new TileSet();

	private static long oldseed = 0;
	
	private static Dungeon dungeon;

	public static void createDungeon(Dungeon dungeon) {
		DungeonGenerator.dungeon = dungeon;

		for (int y = 0; y < dungeon.getySize(); y++) {
			for (int x = 0; x < dungeon.getxSize(); x++) {
				if (y == 0)
					dungeon.setTile(x, y, t.VOID_2);
				else if (y == dungeon.getySize() - 1)
					dungeon.setTile(x, y, t.VOID_2);
				else if (x == 0)
					dungeon.setTile(x, y, t.VOID_2);
				else if (x == dungeon.getxSize() - 1)
					dungeon.setTile(x, y, t.VOID_2);
				else
					dungeon.setTile(x, y, t.VOID_1);
			}
		}

		DungeonGenerator.makeRoom(dungeon.getxSize() / 2, dungeon.getySize() / 2, 8, 6, 0);

		int currentFeatures = 1;

		for (int countingTries = 0; countingTries < 1000; countingTries++) {
			if (currentFeatures == dungeon.getObjects()) {
				break;
			}

			int newx = 0;
			int xmod = 0;
			int newy = 0;
			int ymod = 0;
			int validTile = -1;

			for (int testing = 0; testing < 1000; testing++) {
				newx = getRand(1, dungeon.getxSize() - 1);
				newy = getRand(1, dungeon.getySize() - 1);
				validTile = -1;

				if (dungeon.isWall(newx, newy)) {
					if (dungeon.isFloor(newx, newy + 1)) {
						validTile = 0; //
						xmod = 0;
						ymod = -1;
					} else if (dungeon.isFloor(newx - 1, newy)) {
						validTile = 1; //
						xmod = +1;
						ymod = 0;
					} else if (dungeon.isFloor(newx, newy - 1)) {
						validTile = 2; //
						xmod = 0;
						ymod = +1;
					} else if (dungeon.isFloor(newx + 1, newy)) {
						validTile = 3; //
						xmod = -1;
						ymod = 0;
					}
					System.out.println("A: "+validTile);
					if (validTile > -1) {
						if (dungeon.getTile(newx, newy+1) == t.DOOR) //north
							validTile = -1;
						else if (dungeon.getTile(newx-1, newy) == t.DOOR)//east
							validTile = -1;
						else if (dungeon.getTile(newx, newy-1) == t.DOOR)//south
							validTile = -1;
						else if (dungeon.getTile(newx+1, newy) == t.DOOR)//west
							validTile = -1;
					}
					System.out.println("B: "+validTile);
					if (validTile > -1)
						break;
				}
			}

			if (validTile > -1) {
				if (makeRoom((newx + xmod), (newy + ymod), MathUtils.random(6, 15), MathUtils.random(6, 15),
						validTile)) {
					currentFeatures++; // add to our quota

					switch (validTile) {
					case 0://north
						dungeon.setTile(newx, newy, t.DOOR);
						dungeon.setTile(newx, newy - 1, t.DOOR);
						if (dungeon.getTile(newx - 1, newy) == t.TOP_LEFT_INSIDE)
							dungeon.setTile(newx - 1, newy, t.getLeftWall());
						else
							dungeon.setTile(newx - 1, newy, t.BOTTOM_RIGHT_OUTSIDE);

						if (dungeon.getTile(newx + 1, newy) == t.TOP_RIGHT_INSIDE)
							dungeon.setTile(newx + 1, newy, t.getRightWall());
						else
							dungeon.setTile(newx + 1, newy, t.BOTTOM_LEFT_OUTSIDE);
						//Other side  
						if (dungeon.getTile(newx - 1, newy-1) == t.BOTTOM_LEFT_INSIDE)
							dungeon.setTile(newx - 1, newy-1, t.getLeftWall());
						else
							dungeon.setTile(newx - 1, newy-1, t.TOP_LEFT_OUTSIDE);

						if (dungeon.getTile(newx + 1, newy-1) == t.BOTTOM_RIGHT_INSIDE)
							dungeon.setTile(newx + 1, newy-1, t.getRightWall());
						else
							dungeon.setTile(newx + 1, newy-1, t.TOP_RIGHT_OUTSIDE);
						break;
					case 1://east
						dungeon.setTile(newx, newy,t.DOOR);
						dungeon.setTile(newx+1, newy,t.DOOR);
						if (dungeon.getTile(newx, newy - 1) == t.TOP_RIGHT_INSIDE)
							dungeon.setTile(newx, newy - 1, t.getTopWall());
						else
							dungeon.setTile(newx, newy - 1, t.BOTTOM_LEFT_OUTSIDE);
						
						if (dungeon.getTile(newx, newy + 1) == t.BOTTOM_RIGHT_INSIDE)
							dungeon.setTile(newx, newy + 1, t.getBottomWall());
						else
							dungeon.setTile(newx, newy + 1, t.TOP_RIGHT_OUTSIDE);
						//Other side
						if (dungeon.getTile(newx + 1, newy - 1) == t.TOP_LEFT_INSIDE)
							dungeon.setTile(newx + 1, newy - 1, t.getTopWall());
						else
							dungeon.setTile(newx + 1, newy - 1, t.BOTTOM_RIGHT_OUTSIDE);
						
						if (dungeon.getTile(newx + 1, newy + 1) == t.BOTTOM_LEFT_INSIDE)
							dungeon.setTile(newx + 1, newy + 1, t.getBottomWall());
						else
							dungeon.setTile(newx + 1, newy + 1, t.TOP_LEFT_OUTSIDE);

						break;
					case 2:
						dungeon.setTile(newx, newy, t.DOOR);
						dungeon.setTile(newx, newy + 1, t.DOOR);
						if (dungeon.getTile(newx + 1, newy) == t.BOTTOM_RIGHT_INSIDE)//RIGHT
							dungeon.setTile(newx + 1, newy, t.getRightWall());
						else
							dungeon.setTile(newx + 1, newy, t.TOP_RIGHT_OUTSIDE);

						if (dungeon.getTile(newx - 1, newy) == t.BOTTOM_LEFT_INSIDE)//LEFT
							dungeon.setTile(newx - 1, newy,t.getLeftWall());
						else
							dungeon.setTile(newx - 1, newy, t.TOP_LEFT_OUTSIDE);
						//Other side  
						if (dungeon.getTile(newx - 1, newy+1) == t.TOP_RIGHT_INSIDE)//RIGHT
							dungeon.setTile(newx - 1, newy+1, t.getRightWall());
						else
							dungeon.setTile(newx - 1, newy+1,t.BOTTOM_RIGHT_OUTSIDE );
						
						if (dungeon.getTile(newx + 1, newy+1) == t.TOP_LEFT_INSIDE)//LEFT
							dungeon.setTile(newx + 1, newy+1, t.getLeftWall());
						else
							dungeon.setTile(newx + 1, newy+1, t.BOTTOM_LEFT_OUTSIDE);
						break;
					case 3://west
						dungeon.setTile(newx, newy,t.DOOR);
						dungeon.setTile(newx-1, newy,t.DOOR);
						if (dungeon.getTile(newx, newy + 1) == t.BOTTOM_LEFT_INSIDE)
							dungeon.setTile(newx, newy + 1, t.getBottomWall());
						else
							dungeon.setTile(newx, newy + 1, t.TOP_LEFT_OUTSIDE);
						
						if (dungeon.getTile(newx, newy - 1) == t.TOP_LEFT_INSIDE)
							dungeon.setTile(newx, newy - 1, t.getTopWall());
						else
							dungeon.setTile(newx, newy - 1, t.BOTTOM_RIGHT_OUTSIDE);
						//Other side
						if (dungeon.getTile(newx - 1, newy + 1) == t.TOP_RIGHT_INSIDE)
							dungeon.setTile(newx - 1, newy + 1, t.getTopWall());
						else
							dungeon.setTile(newx - 1, newy + 1, t.TOP_RIGHT_OUTSIDE);
						
						if (dungeon.getTile(newx - 1, newy - 1) == t.BOTTOM_RIGHT_INSIDE)
							dungeon.setTile(newx - 1, newy - 1, t.getBottomWall());
						else
							dungeon.setTile(newx - 1, newy - 1, t.BOTTOM_LEFT_OUTSIDE);

						break;
					}
				}
			}
		}
	}

	private static boolean makeRoom(int x, int y, int xlength, int ylength, int direction) {
		int xlen = getRand(6, xlength);
		int ylen = getRand(6, ylength);

		int dir = 0;
		if (direction > 0 && direction < 4)
			dir = direction;

		switch (dir) {
		case 0: // north
			for (int ytemp = y; ytemp > (y - ylen); ytemp--) {
				if (ytemp < 0 || ytemp > dungeon.getySize())
					return false;
				for (int xtemp = (x - xlen / 2); xtemp < (x + (xlen + 1) / 2); xtemp++) {
					if (xtemp < 0 || xtemp > dungeon.getxSize())
						return false;
					if (dungeon.getTile(xtemp, ytemp) != t.VOID_1)
						return false;
				}
			}

			for (int ytemp = y; ytemp > (y - ylen); ytemp--) {
				for (int xtemp = (x - xlen / 2); xtemp < (x + (xlen + 1) / 2); xtemp++) {
					if (xtemp == (x - xlen / 2)) {
						dungeon.setTile(xtemp, ytemp, MathUtils.random(t.LEFT_WALL_1, t.LEFT_WALL_10));
						if (ytemp == (y - (ylen - 1)))
							dungeon.setTile(xtemp, ytemp, t.TOP_LEFT_INSIDE);
						if (ytemp == y)
							dungeon.setTile(xtemp, ytemp, t.BOTTOM_LEFT_INSIDE);
					} else if (xtemp == (x + (xlen - 1) / 2)) {
						dungeon.setTile(xtemp, ytemp, t.getRightWall());
						if (ytemp == (y - (ylen - 1)))
							dungeon.setTile(xtemp, ytemp, t.TOP_RIGHT_INSIDE);
						if (ytemp == y)
							dungeon.setTile(xtemp, ytemp, t.BOTTOM_RIGHT_INSIDE);
					} else if (ytemp == y)
						dungeon.setTile(xtemp, ytemp, t.getBottomWall());
					else if (ytemp == (y - ylen + 1))
						dungeon.setTile(xtemp, ytemp, t.getTopWall());
					else
						dungeon.setTile(xtemp, ytemp, t.getFloor1Wall());
				}
			}

			break;

		case 1: // east
			for (int ytemp = (y - ylen / 2); ytemp < (y + (ylen + 1) / 2); ytemp++) {
				if (ytemp < 0 || ytemp > dungeon.getySize())
					return false;
				for (int xtemp = x; xtemp < (x + xlen); xtemp++) {
					if (xtemp < 0 || xtemp > dungeon.getxSize())
						return false;
					if (dungeon.getTile(xtemp, ytemp) != t.VOID_1)
						return false;
				}
			}

			for (int ytemp = (y - ylen / 2); ytemp < (y + (ylen + 1) / 2); ytemp++) {
				for (int xtemp = x; xtemp < (x + xlen); xtemp++) {
					if (xtemp == x) {
						dungeon.setTile(xtemp, ytemp, t.getLeftWall());
						if (ytemp == y - ylen / 2)
							dungeon.setTile(xtemp, ytemp, t.TOP_LEFT_INSIDE);
						if (ylen % 2 == 0) {
							if (ytemp == y + (ylen / 2) - 1)
								dungeon.setTile(xtemp, ytemp, t.BOTTOM_LEFT_INSIDE);
						} else {
							if (ytemp == y + (ylen / 2))
								dungeon.setTile(xtemp, ytemp, t.BOTTOM_LEFT_INSIDE);
						}
					} else if (xtemp == (x + xlen - 1)) {
						dungeon.setTile(xtemp, ytemp, t.getRightWall());
						if (ytemp == y - ylen / 2)
							dungeon.setTile(xtemp, ytemp, t.TOP_RIGHT_INSIDE);
						if (ylen % 2 == 0) {
							if (ytemp == y + (ylen / 2) - 1)
								dungeon.setTile(xtemp, ytemp, t.BOTTOM_RIGHT_INSIDE);
						} else {
							if (ytemp == y + (ylen / 2))
								dungeon.setTile(xtemp, ytemp, t.BOTTOM_RIGHT_INSIDE);
						}
					} else if (ytemp == (y - ylen / 2))
						dungeon.setTile(xtemp, ytemp, t.getTopWall());
					else if (ytemp == (y + (ylen - 1) / 2))
						dungeon.setTile(xtemp, ytemp, t.getBottomWall());
					else
						dungeon.setTile(xtemp, ytemp, t.getFloor2Wall());
				}
			}

			break;

		case 2: // south

			for (int ytemp = y; ytemp < (y + ylen); ytemp++) {
				if (ytemp < 0 || ytemp > dungeon.getySize())
					return false;
				for (int xtemp = (x - xlen / 2); xtemp < (x + (xlen + 1) / 2); xtemp++) {
					if (xtemp < 0 || xtemp > dungeon.getxSize())
						return false;
					if (dungeon.getTile(xtemp, ytemp) != t.VOID_1)
						return false;
				}
			}

			for (int ytemp = y; ytemp < (y + ylen); ytemp++) {
				for (int xtemp = (x - xlen / 2); xtemp < (x + (xlen + 1) / 2); xtemp++) {
					if (xtemp == (x - xlen / 2)) {
						dungeon.setTile(xtemp, ytemp, t.getLeftWall());
						if (ytemp == y)
							dungeon.setTile(xtemp, ytemp, t.TOP_LEFT_INSIDE);
						if (ytemp == (y + (ylen - 1)))
							dungeon.setTile(xtemp, ytemp, t.BOTTOM_LEFT_INSIDE);
					} else if (xtemp == (x + (xlen - 1) / 2)) {
						dungeon.setTile(xtemp, ytemp, t.getRightWall());
						if (ytemp == y)
							dungeon.setTile(xtemp, ytemp, t.TOP_RIGHT_INSIDE);
						if (ytemp == (y + (ylen - 1)))
							dungeon.setTile(xtemp, ytemp, t.BOTTOM_RIGHT_INSIDE);
					} else if (ytemp == y)
						dungeon.setTile(xtemp, ytemp, t.getTopWall());
					else if (ytemp == (y + ylen - 1))
						dungeon.setTile(xtemp, ytemp, t.getBottomWall());
					else
						dungeon.setTile(xtemp, ytemp, t.getFloor3Wall());
				}
			}

			break;

		case 3: // west

			for (int ytemp = (y - ylen / 2); ytemp < (y + (ylen + 1) / 2); ytemp++) {
				if (ytemp < 0 || ytemp > dungeon.getySize())
					return false;
				for (int xtemp = x; xtemp > (x - xlen); xtemp--) {
					if (xtemp < 0 || xtemp > dungeon.getxSize())
						return false;
					if (dungeon.getTile(xtemp, ytemp) != t.VOID_1)
						return false;
				}
			}

			for (int ytemp = (y - ylen / 2); ytemp < (y + (ylen + 1) / 2); ytemp++) {
				for (int xtemp = x; xtemp > (x - xlen); xtemp--) {
					if (xtemp == x) {
						dungeon.setTile(xtemp, ytemp, t.getRightWall());
						if (ytemp == y - ylen / 2)
							dungeon.setTile(xtemp, ytemp, t.TOP_RIGHT_INSIDE);
						if (ylen % 2 == 0) {
							if (ytemp == y + (ylen / 2) - 1)
								dungeon.setTile(xtemp, ytemp, t.BOTTOM_RIGHT_INSIDE);
						} else {
							if (ytemp == y + (ylen / 2))
								dungeon.setTile(xtemp, ytemp, t.BOTTOM_RIGHT_INSIDE);
						}
					} else if (xtemp == (x - xlen + 1)) {
						dungeon.setTile(xtemp, ytemp, t.getLeftWall());
						if (ytemp == y - ylen / 2)
							dungeon.setTile(xtemp, ytemp, t.TOP_LEFT_INSIDE);
						if (ylen % 2 == 0) {
							if (ytemp == y + (ylen / 2) - 1)
								dungeon.setTile(xtemp, ytemp, t.BOTTOM_LEFT_INSIDE);
						} else {
							if (ytemp == y + (ylen / 2))
								dungeon.setTile(xtemp, ytemp, t.BOTTOM_LEFT_INSIDE);
						}
					} else if (ytemp == (y - ylen / 2))
						dungeon.setTile(xtemp, ytemp, t.getTopWall());
					else if (ytemp == (y + (ylen - 1) / 2))
						dungeon.setTile(xtemp, ytemp, t.getBottomWall());
					else
						dungeon.setTile(xtemp, ytemp, t.getFloor4Wall());
				}
			}

			break;
		}
		return true;
	}
	
	private static int getRand(int min, int max) {
		long seed = System.currentTimeMillis() + oldseed;
		oldseed = seed;

		Random randomizer = new Random(seed);
		int n = max - min + 1;
		int i = randomizer.nextInt(n);
		if (i < 0)
			i = -i;
		return min + i;
	}

}
