/**
 * 
 */
package com.someguyssoftware.dungeonsengine.generator;

import java.util.List;
import java.util.Random;

import com.someguyssoftware.dungeonsengine.config.LevelConfig;
import com.someguyssoftware.dungeonsengine.graph.Wayline;
import com.someguyssoftware.dungeonsengine.model.Room;
import com.someguyssoftware.dungeonsengine.style.StyleSheet;
import com.someguyssoftware.dungeonsengine.style.Theme;

import net.minecraft.world.World;

/**
 * @author Mark Gottschling on Aug 15, 2016
 *
 */
public interface IShaftGenerator {

	/**
	 * 
	 * @param world
	 * @param random
	 * @param level
	 * @param wayline
	 * @param theme
	 * @param styleSheet
	 * @param config
	 */
	void generate(World world, Random random, List<Room> rooms, Wayline wayline, Theme theme,
			StyleSheet styleSheet, LevelConfig config);
}
