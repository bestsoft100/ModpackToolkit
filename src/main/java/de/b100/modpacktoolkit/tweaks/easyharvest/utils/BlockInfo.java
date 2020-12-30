package de.b100.modpacktoolkit.tweaks.easyharvest.utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockInfo {
	
	private IBlockState state;
	private BlockPos pos;
	private World world;
	
	public BlockInfo(IBlockState state, BlockPos pos, World world) {
		this.state = state;
		this.pos = pos;
		this.world = world;
	}
	
	public Block getBlock() {
		return getState().getBlock();
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
	public IBlockState getState() {
		return state;
	}
	
	public World getWorld() {
		return world;
	}
	
}
