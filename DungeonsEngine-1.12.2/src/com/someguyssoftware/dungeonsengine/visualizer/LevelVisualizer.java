/**
 * 
 */
package com.someguyssoftware.dungeonsengine.visualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.someguyssoftware.dungeonsengine.builder.IRoomBuilder;
import com.someguyssoftware.dungeonsengine.builder.ISurfaceRoomBuilder;
import com.someguyssoftware.dungeonsengine.builder.LevelBuilder;
import com.someguyssoftware.dungeonsengine.builder.RoomBuilder;
import com.someguyssoftware.dungeonsengine.builder.SurfaceLevelBuilder;
import com.someguyssoftware.dungeonsengine.builder.SurfaceRoomBuilder;
import com.someguyssoftware.dungeonsengine.config.LevelConfig;
import com.someguyssoftware.dungeonsengine.model.ILevel;
import com.someguyssoftware.dungeonsengine.model.IRoom;
import com.someguyssoftware.gottschcore.Quantity;
import com.someguyssoftware.gottschcore.positional.Coords;
import com.someguyssoftware.gottschcore.positional.ICoords;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * @author Mark Gottschling on Sep 17, 2018
 *
 */
public class LevelVisualizer {
	public static Logger logger = LogManager.getLogger("DungeonsEngine");

	/**
	 * 
	 */
	public LevelVisualizer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// set up the builder
//		LevelBuilder builder = new LevelBuilder();
//		LevelConfig config = new LevelConfig();
		
//		ICoords startPoint = new Coords(500, 100, 500);
		
		// default seed for random
		long seed = System.currentTimeMillis();
		
		if (args.length ==1) seed = Long.valueOf(args[0]);
		Random random = new Random(seed);
		
		// set a map to contain properties
		Map<String, Object> props = new HashMap<>();
		
		// build a level
		LevelConfig config = new LevelConfig();
		config.setNumberOfRooms(new Quantity(25, 50)); // VAST = 25-50
		double factor = 3.2;
		config.setWidth(new Quantity(5, 15));
		config.setDepth(new Quantity(5, 15));
		config.setHeight(new Quantity(5, 10));
		config.setDegrees(new Quantity(2, 4));
		config.setXDistance(new Quantity(-(30*factor), (30*factor)));
		config.setZDistance(new Quantity(-30*factor, 30*factor));
		config.setYVariance(new Quantity(0, 0));
		config.setMinecraftConstraintsOn(false);
		config.setSupportOn(false);		
		
		/*
		 * setup values for builders
		 */
		ICoords startPoint = new Coords(100, 0, 100);
		AxisAlignedBB levelField = new AxisAlignedBB(new BlockPos(0,0,0), new BlockPos(200, 0, 200));
		AxisAlignedBB roomField = new AxisAlignedBB(new BlockPos(60,0,60), new BlockPos(140, 0, 140));
		int w = (int) Math.abs(roomField.maxX - roomField.minX);
		int d = (int) Math.abs(roomField.maxZ - roomField.minZ);
		AxisAlignedBB endField = new AxisAlignedBB(
				new BlockPos(Math.max(roomField.minX-(w/3), levelField.minX), 0,
						Math.max(roomField.minZ-(d/3), levelField.minZ)),
				new BlockPos(Math.min(roomField.maxX+(w/3), levelField.maxX), 0, 
						Math.min(roomField.maxZ+(d/3), levelField.maxZ)));
		
		// add the additional fields to the props map
		props.put("seed",  new Long(seed));
		props.put("roomField", roomField);
		props.put("endField", endField);
		
//		// test - add an extra planned room
//		List<IRoom> plannedRooms = new ArrayList<>();
//		
//		IRoomBuilder roomBuilder = new RoomBuilder(roomField);		
////		IRoomBuilder endRoomBuilder = new RoomBuilder(random, endField, startPoint, config);	
//		LevelBuilder builder = new LevelBuilder(null, random, levelField, startPoint, config); // TODO require room builder in constructor
//		builder.setRoomBuilder(roomBuilder);
//		
//		IRoom startRoom = roomBuilder.buildStartRoom(random, startPoint, config);
////		Room extraRoom = new Room().setDegrees(3)
////				.setDepth(10).setWidth(10).setAnchor(true)
////				.setCoords(new Coords(startRoom.getCoords().getX(), 0, startRoom.getCoords().getZ() - 20))
////				.setDirection(Direction.getByCode(RandomHelper.randomInt(2, 5)));
////		extraRoom.setDistance(extraRoom.getCenter().getDistanceSq(startPoint));
//		
//		plannedRooms.add(startRoom);
////		plannedRooms.add(extraRoom);
//		IRoom endRoom = roomBuilder.buildEndRoom(random, endField, startPoint, config, plannedRooms);//.setAnchor(false);
//
//		ILevel level = builder
//			.withStartRoom(startRoom)
//			.withEndRoom(endRoom)
////			.withRoom(extraRoom)
//			.build();		
//		System.out.println(level);
//		System.out.println(level.getField());
//		logger.debug(level);
		
		//======= Surface Builder ========
		config.setNumberOfRooms(new Quantity(3, 5));
		ISurfaceRoomBuilder sfRoomBuilder = new SurfaceRoomBuilder(null, roomField);
		SurfaceLevelBuilder sfBuilder = new SurfaceLevelBuilder(null, random, levelField, startPoint);
		sfBuilder.setConfig(config);
		sfBuilder.setRoomBuilder((IRoomBuilder) sfRoomBuilder);
		IRoom entranceRoom = sfRoomBuilder.buildEntranceRoom(random, startPoint, config);
		ILevel sfLevel = sfBuilder
				.withStartRoom(entranceRoom)
				.build();		
			System.out.println(sfLevel);
			System.out.println(sfLevel.getField());
			logger.debug(sfLevel);
		
		// visualize the level
		// draw out rectangles
		JFrame window = new JFrame();
//		JPanel panel = new LevelPanel(level, builder, props);
		JPanel panel = new LevelPanel(sfLevel, sfBuilder, props);
		window.setTitle("Dungeons2! Level Visualizer 2");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, 1400, 750);
		window.add(panel);
		window.setVisible(true);
	}

}
