package edu.ricm3.game.whaler.Interpretor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.ricm3.game.whaler.Direction;
import edu.ricm3.game.whaler.Location;
import edu.ricm3.game.whaler.Model;
import edu.ricm3.game.whaler.Options;
import edu.ricm3.game.whaler.Tile;
import edu.ricm3.game.whaler.Entities.Entity;
import edu.ricm3.game.whaler.Entities.Entity.EntityType;
import edu.ricm3.game.whaler.Entities.MobileEntity;
import edu.ricm3.game.whaler.Game_exception.Map_exception;

public abstract class ICondition {

	public static EntityDangerLevel[][] entity_behaviour = new EntityDangerLevel[10][10];

	public static void fillDangerLevelMatrice() {
		/*
		 * DESTROYER = 0 OIL = 1 PLAYER = 2 PROJECTILE = 3 WHALE = 4 WHALER = 5
		 */
		// Column 1
		entity_behaviour[EntityType.DESTROYER.ordinal()][EntityType.DESTROYER.ordinal()] = EntityDangerLevel.TEAM;
		entity_behaviour[EntityType.OIL.ordinal()][EntityType.DESTROYER.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.PLAYER.ordinal()][EntityType.DESTROYER.ordinal()] = EntityDangerLevel.ADVERSAIRE;
		entity_behaviour[EntityType.PROJECTILE.ordinal()][EntityType.DESTROYER.ordinal()] = EntityDangerLevel.MISSILE;
		entity_behaviour[EntityType.WHALE.ordinal()][EntityType.DESTROYER.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.WHALER.ordinal()][EntityType.DESTROYER.ordinal()] = EntityDangerLevel.TEAM;
		entity_behaviour[EntityType.ISLAND.ordinal()][EntityType.DESTROYER.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.STONE.ordinal()][EntityType.DESTROYER.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.ICEBERG.ordinal()][EntityType.DESTROYER.ordinal()] = EntityDangerLevel.DANGER;

		// Column 2
		entity_behaviour[EntityType.DESTROYER.ordinal()][EntityType.OIL.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.OIL.ordinal()][EntityType.OIL.ordinal()] = EntityDangerLevel.TEAM;
		entity_behaviour[EntityType.PLAYER.ordinal()][EntityType.OIL.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.PROJECTILE.ordinal()][EntityType.OIL.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.WHALE.ordinal()][EntityType.OIL.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.WHALER.ordinal()][EntityType.OIL.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.ISLAND.ordinal()][EntityType.OIL.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.STONE.ordinal()][EntityType.OIL.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.ICEBERG.ordinal()][EntityType.OIL.ordinal()] = EntityDangerLevel.DANGER;

		// Column 3
		entity_behaviour[EntityType.DESTROYER.ordinal()][EntityType.PLAYER.ordinal()] = EntityDangerLevel.ADVERSAIRE;
		entity_behaviour[EntityType.OIL.ordinal()][EntityType.PLAYER.ordinal()] = EntityDangerLevel.PRENABLE;
		entity_behaviour[EntityType.PLAYER.ordinal()][EntityType.PLAYER.ordinal()] = EntityDangerLevel.TEAM;
		entity_behaviour[EntityType.PROJECTILE.ordinal()][EntityType.PLAYER.ordinal()] = EntityDangerLevel.MISSILE;
		entity_behaviour[EntityType.WHALE.ordinal()][EntityType.PLAYER.ordinal()] = EntityDangerLevel.TEAM;
		entity_behaviour[EntityType.WHALER.ordinal()][EntityType.PLAYER.ordinal()] = EntityDangerLevel.ADVERSAIRE;
		entity_behaviour[EntityType.ISLAND.ordinal()][EntityType.PLAYER.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.STONE.ordinal()][EntityType.PLAYER.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.ICEBERG.ordinal()][EntityType.PLAYER.ordinal()] = EntityDangerLevel.DANGER;

		// Column 4
		entity_behaviour[EntityType.DESTROYER.ordinal()][EntityType.PROJECTILE
				.ordinal()] = EntityDangerLevel.ADVERSAIRE;
		entity_behaviour[EntityType.OIL.ordinal()][EntityType.PROJECTILE.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.PLAYER.ordinal()][EntityType.PROJECTILE.ordinal()] = EntityDangerLevel.ADVERSAIRE;
		entity_behaviour[EntityType.PROJECTILE.ordinal()][EntityType.PROJECTILE.ordinal()] = EntityDangerLevel.TEAM;
		entity_behaviour[EntityType.WHALE.ordinal()][EntityType.PROJECTILE.ordinal()] = EntityDangerLevel.ADVERSAIRE;
		entity_behaviour[EntityType.WHALER.ordinal()][EntityType.PROJECTILE.ordinal()] = EntityDangerLevel.ADVERSAIRE;
		entity_behaviour[EntityType.ISLAND.ordinal()][EntityType.PROJECTILE.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.STONE.ordinal()][EntityType.PROJECTILE.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.ICEBERG.ordinal()][EntityType.PROJECTILE.ordinal()] = EntityDangerLevel.DANGER;

		// Column 5
		entity_behaviour[EntityType.DESTROYER.ordinal()][EntityType.WHALE.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.OIL.ordinal()][EntityType.WHALE.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.PLAYER.ordinal()][EntityType.WHALE.ordinal()] = EntityDangerLevel.TEAM;
		entity_behaviour[EntityType.PROJECTILE.ordinal()][EntityType.WHALE.ordinal()] = EntityDangerLevel.MISSILE;
		entity_behaviour[EntityType.WHALE.ordinal()][EntityType.WHALE.ordinal()] = EntityDangerLevel.TEAM;
		entity_behaviour[EntityType.WHALER.ordinal()][EntityType.WHALE.ordinal()] = EntityDangerLevel.ADVERSAIRE;
		entity_behaviour[EntityType.ISLAND.ordinal()][EntityType.WHALE.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.STONE.ordinal()][EntityType.WHALE.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.ICEBERG.ordinal()][EntityType.WHALE.ordinal()] = EntityDangerLevel.DANGER;

		// Column 6
		entity_behaviour[EntityType.DESTROYER.ordinal()][EntityType.WHALER.ordinal()] = EntityDangerLevel.TEAM;
		entity_behaviour[EntityType.OIL.ordinal()][EntityType.WHALER.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.PLAYER.ordinal()][EntityType.WHALER.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.PROJECTILE.ordinal()][EntityType.WHALER.ordinal()] = EntityDangerLevel.MISSILE;
		entity_behaviour[EntityType.WHALE.ordinal()][EntityType.WHALER.ordinal()] = EntityDangerLevel.ADVERSAIRE;
		entity_behaviour[EntityType.WHALER.ordinal()][EntityType.WHALER.ordinal()] = EntityDangerLevel.TEAM;
		entity_behaviour[EntityType.ISLAND.ordinal()][EntityType.WHALER.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.STONE.ordinal()][EntityType.WHALER.ordinal()] = EntityDangerLevel.DANGER;
		entity_behaviour[EntityType.ICEBERG.ordinal()][EntityType.WHALER.ordinal()] = EntityDangerLevel.DANGER;

		entity_behaviour[EntityType.DESTROYER.ordinal()][EntityType.VOID.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.OIL.ordinal()][EntityType.VOID.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.PLAYER.ordinal()][EntityType.VOID.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.PROJECTILE.ordinal()][EntityType.VOID.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.WHALE.ordinal()][EntityType.VOID.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.WHALER.ordinal()][EntityType.VOID.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.ISLAND.ordinal()][EntityType.VOID.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.STONE.ordinal()][EntityType.VOID.ordinal()] = EntityDangerLevel.VOID;
		entity_behaviour[EntityType.ICEBERG.ordinal()][EntityType.VOID.ordinal()] = EntityDangerLevel.VOID;
	}

	public abstract boolean eval(MobileEntity current, Model model) throws Map_exception; // Il y aura besoin de
																							// rajouter (au moins) la
																							// map (voir model complet)
																							// et
	// l'entité courante

	public static Direction strToDir(String str) {
		if (str.equals("N")) {
			return Direction.NORTH;
		} else if (str.equals("S")) {
			return Direction.SOUTH;
		} else if (str.equals("E")) {
			return Direction.EAST;
		} else if (str.equals("O")) {
			return Direction.WEST;
		} else if (str.equals("F")) {
			return Direction.FORWARD;
		} else if (str.equals("B")) {
			return Direction.BACKWARD;
		} else if (str.equals("R")) {
			return Direction.RIGHT;
		} else if (str.equals("L")) {
			return Direction.LEFT;
		} else {
			System.out.println("Unknown Direction, will be interpreted as FORWARD");
			return Direction.FORWARD;
		}
	}

	enum EntityDangerLevel {
		VOID, TEAM, ADVERSAIRE, DANGER, PRENABLE, JUMPABLE, GATE, MISSILE;
	}

	static EntityDangerLevel strToEDL(String s) {

		switch (s) {
		case "V":
			return EntityDangerLevel.VOID;
		case "T":
			return EntityDangerLevel.TEAM;
		case "A":
			return EntityDangerLevel.ADVERSAIRE;
		case "D":
			return EntityDangerLevel.DANGER;
		case "P":
			return EntityDangerLevel.PRENABLE;
		case "J":
			return EntityDangerLevel.JUMPABLE;
		case "G":
			return EntityDangerLevel.GATE;
		case "M":
			return EntityDangerLevel.MISSILE;

		default:
			System.out.println("Unknown Entity, will be interpreted as VOID");
			return EntityDangerLevel.VOID;
		}
	}

	/**
	 * La condition est toujours vérifiée
	 */
	public static class ITrue extends ICondition {

		public ITrue() {

		}

		public boolean eval(MobileEntity current, Model model) {
			return true;
		}
	}

	/**
	 * La Touche m_key est enfoncée m_key peut valoir : [a-z] [A-Z] [0-9] "SPACE"
	 * "ENTER" "FU" "FD" "FR" "FL"
	 */
	public static class IKey extends ICondition {
		String m_key;

		public IKey(String key) {
			m_key = key;
		}

		public boolean eval(MobileEntity current, Model model) {
			m_key = m_key.toUpperCase();

			int length = m_key.length();
			char carac = m_key.charAt(0);
			int ascii = (int) carac;

			if (length > 1) {
				char carac2 = m_key.charAt(1);
				if (carac == 'S') {
					ascii = 32;
				} else if (carac == 'E') {
					ascii = 10;
				} else if (carac2 == 'U') {
					ascii = 38;
				} else if (carac2 == 'D') {
					ascii = 40;
				} else if (carac2 == 'R') {
					ascii = 39;
				} else if (carac2 == 'L') {
					ascii = 37;
				}
			}

			return model.keyPressed[ascii];
		}
	}

	/**
	 * 
	 * L'entité courante est orientée dans la direction m_dir NB (@Tanguy) : Il
	 * faudra passer l'entité courante en argument de eval ce qui risque d'être pas
	 * mal chiant
	 */
	public static class IMyDir extends ICondition {
		Direction m_dir;

		public IMyDir(String string) {
			m_dir = strToDir(string);
		}

		public boolean eval(MobileEntity current, Model model) {
			return current.m_direction == m_dir;

		}
	}

	/**
	 * 
	 * La cellule dans la direction m_dir contient une entité de type m_entity
	 * m_entity peut valoir : V T A D P J G M NB: Une entité dangereux pour le
	 * joueur n'est pas dangereux pour un Destroyer "DANGER" est donc à définir en
	 * fonction de l'entité courante
	 */
	public static class ICell extends ICondition {
		EntityDangerLevel m_entity;
		Direction m_dir;

		public ICell(String dir, String entity) {
			m_entity = strToEDL(entity);
			m_dir = strToDir(dir);
		}

		public boolean eval(MobileEntity current, Model model) throws Map_exception {

			int px = current.getx();
			int py = current.gety();
			int cx = px;
			int cy = py;

			switch (m_dir) {

			case FORWARD:
				m_dir = current.getFDir();
			case BACKWARD:
				m_dir = current.getBDir();
			case LEFT:
				m_dir = current.getLDir();
			case RIGHT:
				m_dir = current.getRDir();
			case NORTH:
				cx = px;
				cy = py - 1;
				break;
			case SOUTH:
				cx = px;
				cy = py + 1;
				break;
			case EAST:
				cx = px + 1;
				cy = py;
				break;
			case WEST:
				cx = px - 1;
				cy = py;
				break;
			}

			if (!model.UNDER_WATER) {
				Iterator<Entity> iter = model.map().tile(cx, cy).iterator();

				if (!iter.hasNext() && (m_entity == EntityDangerLevel.VOID)) { // Cas Particulier si la case est vide et
																				// qu'on cherche du void
					return true;
				}

				while (iter.hasNext()) {

					Entity e = iter.next();

					EntityDangerLevel level = entity_behaviour[e.getType().ordinal()][current.getType().ordinal()];

					if (level == m_entity) {
						return true;
					}
				}
			}
			return false;
		}
	}

	public static class IClosest extends ICondition {
		EntityDangerLevel m_entity;
		Direction m_dir;

		public IClosest(String entity, String dir) {
			m_entity = strToEDL(entity);
			m_dir = strToDir(dir);
		}

		public static double distance(Location a, Location b) {
			return Math.sqrt(Math.pow((a.x - b.x), 2D) + Math.pow((a.y - b.y), 2D));
		}

		public boolean eval(MobileEntity current, Model model) throws Map_exception {

			if (m_entity == EntityDangerLevel.VOID) {
				return true;
			}

			List<Entity> entitiesToSearch = new LinkedList<Entity>();

			int currentType = current.getType().ordinal();

			Location l = current.getLoc();
			

			for (int i = l.x - 10; i < l.x + 10; i++) {
				for (int j = l.y - 10; j < l.y + 10; j++)
					try {
						Tile t = model.map().tile(i, j);
						Iterator<Entity> iter = t.iterator();
						while (iter.hasNext()) {
							Entity e = iter.next();
							if (entity_behaviour[e.getType().ordinal()][currentType] == m_entity) {
								entitiesToSearch.add(e);
							}
						}
					} catch (Map_exception e) {
						// Catch si en dehors de la map
					}
			}
			
			if (entitiesToSearch.isEmpty()){
				return false;
			}

			double distTab[] = new double[entitiesToSearch.size()];

			int min = 0;

			for (int i = 0; i < distTab.length; i++) {
				distTab[i] = distance(current.getLoc(), entitiesToSearch.get(i).getLoc());
				if (distTab[i] <= distTab[min]) {
					min = i;
				}
			}

			Entity closestEntity = entitiesToSearch.get(min);

			double angle = Math.atan(((double) (closestEntity.gety() - current.gety())) / ((double) (closestEntity.getx() - current.getx()) ));

			if (closestEntity.getx() - current.getx() < 0) {
				angle += Math.PI;
			}
			
			angle = Math.toDegrees(angle);
			
			if (angle >= -45 && angle <= 45) {
				return Direction.EAST == m_dir;
			} else if (angle >= 45 && angle <= 135) {
				return Direction.SOUTH == m_dir;
			} else if (angle >= 135 && angle <= 225) {
				return Direction.WEST == m_dir;
			} else {
				return Direction.NORTH == m_dir;
			}
		}
	}

	/**
	 * Reste t-il de l'énergie à l'entité NB : Regarder pour faire un truc relatif à
	 * la vie
	 */
	public static class IGotPower extends ICondition {

		public IGotPower() {
		}

		public boolean eval(MobileEntity current, Model model) {
			return current.m_life > 5;
		}

	}

	/**
	 * Reste t-il des choses dans le store ? NB (@Tanguy) : Celle là on a qu'à la
	 * laisser à FAUX tout le temps
	 */
	public static class IGotStuff extends ICondition {

		public IGotStuff() {
		}

		public boolean eval(MobileEntity current, Model model) {
			return false;
		}
	}

	/**
	 * 
	 * Conjonction : Condition m_a & Condition m_b
	 *
	 */
	public static class IAnd extends ICondition {
		ICondition m_a;
		ICondition m_b;

		public IAnd(ICondition a, ICondition b) {
			m_a = a;
			m_b = b;
		}

		public boolean eval(MobileEntity current, Model model) throws Map_exception {
			return (m_a.eval(current, model) && m_b.eval(current, model));
		}
	}

	/**
	 * 
	 * Disjonction : Condition m_a || Condition m_b
	 *
	 */
	public static class IOr extends ICondition {
		ICondition m_a;
		ICondition m_b;

		public IOr(ICondition a, ICondition b) {
			m_a = a;
			m_b = b;
		}

		public boolean eval(MobileEntity current, Model model) throws Map_exception {
			return (m_a.eval(current, model) || m_b.eval(current, model));
		}
	}

	/**
	 * 
	 * Negation : not(Condition m_a)
	 *
	 */
	public static class INot extends ICondition {
		ICondition m_a;

		public INot(ICondition a) {
			m_a = a;
		}

		public boolean eval(MobileEntity current, Model model) throws Map_exception {
			return !(m_a.eval(current, model));
		}
	}

}