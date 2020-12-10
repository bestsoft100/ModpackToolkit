package de.b100.modpacktoolkit.customworldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class JsonUtils {
	
	public static List<IBlockState> getBlockStates(JsonElement element){
		System.out.println("GetBlockStates");
		
		List<IBlockState> blockStates = new ArrayList<IBlockState>();
		
		if(element.isJsonPrimitive()) {
			blockStates.add(getBlockState(element));
		}
		if(element.isJsonObject()) {
			System.out.println("IsJsonObject");
			JsonObject obj = element.getAsJsonObject();
			
			if(obj.has("blockstates")) {
				System.out.println("Has BlockStates");
				JsonArray blockstates = obj.get("blockstates").getAsJsonArray();
				
				for(int i=0; i < blockstates.size(); i++) {
					List<IBlockState> blockStates2 = getBlockStates(blockstates.get(i));
					blockStates.addAll(blockStates2);
				}
			}else if(obj.has("blocks")) {
				System.out.println("Has Blocks");
				List<IBlockState> blockStates2 = getBlocks(obj.get("blocks"));
				
				if(obj.has("properties")) {
					blockStates2 = getAllBlockStatesWithProperty(blockStates2, obj.get("properties").getAsJsonObject());
				}
				
				blockStates.addAll(blockStates2);
			}
		}
		
		return blockStates;
	}
	
	public static IBlockState getBlockState(JsonElement element) {
		if(element.isJsonPrimitive()) {
			Block block = getBlock(element);
			if(block != null) {
				return block.getDefaultState();
			}
			return null;
		}else{
			JsonObject object = element.getAsJsonObject();
			
			Block block = getBlock(object.get("name"));
			if(block == null) {
				return null;
			}
			IBlockState state = block.getDefaultState();
			
			if(object.has("properties")) {
				state = withProperties(state, object.get("properties").getAsJsonObject());
			}
			
			return state;
		}
	}
	
	public static List<IBlockState> getAllBlockStatesWithProperty(List<IBlockState> blockStates, JsonObject properties){
		List<IBlockState> blockStatesNew = new ArrayList<IBlockState>();
		
		for(IBlockState blockState : blockStates) {
			blockStatesNew.add(withProperties(blockState, properties));
		}
		
		return blockStatesNew;
	}
	
	public static List<IBlockState> getBlocks(JsonElement blocks){
		List<IBlockState> list = new ArrayList<IBlockState>();
		if(blocks.isJsonArray()) {
			JsonArray arr = blocks.getAsJsonArray();
			for(int i=0; i < arr.size(); i++) {
				list.add(getBlock(arr.get(i)).getDefaultState());
			}
		}
		if(blocks.isJsonPrimitive()) {
			list.add(getBlock(blocks).getDefaultState());
		}
		return list;
	}
	
	public static Block getBlock(JsonElement element) {
		return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(element.getAsString()));
	}
	
	public static IBlockState withProperties(IBlockState state, JsonObject properties) {
		Set<Entry<String, JsonElement>> definedPropertiesSet = properties.entrySet();
		
		for(Entry<String, JsonElement> definedProperty : definedPropertiesSet) {
			state = withProperty(state, definedProperty.getKey(), definedProperty.getValue());
		}
		
		return state;
	}
	
	public static IBlockState withProperty(IBlockState state, String propertyName, JsonElement value) {
		Set<Entry<IProperty<?>, Comparable<?>>> blockPropertiesSet = state.getProperties().entrySet();
		
		for(Entry<IProperty<?>, Comparable<?>> blockProperty : blockPropertiesSet) {
			if(blockProperty.getKey().getName().equals(propertyName)) {
				return withProperty(state, blockProperty.getKey(), value);
			}
		}
		
		return state;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static IBlockState withProperty(IBlockState state, IProperty<?> prop, JsonElement value) {
		if(prop instanceof PropertyInteger) {
			PropertyInteger propInt = (PropertyInteger) prop;
			
			state = state.withProperty(propInt, value.getAsInt());
		}
		if(prop instanceof PropertyBool) {
			PropertyBool propBool = (PropertyBool) prop;
			
			state = state.withProperty(propBool, value.getAsBoolean());
		}
		if(prop instanceof PropertyEnum) {
			PropertyEnum propEnum = (PropertyEnum) prop;
			String valueStr = value.getAsString();
			
			//TODO fix this garbage
			for(int i=0; i < prop.getAllowedValues().size(); i++) {
				state = state.cycleProperty(propEnum);
				IStringSerializable ivalue = (IStringSerializable) state.getValue(propEnum);
				
				if(ivalue.toString().equalsIgnoreCase(valueStr)) {
					break;
				}
			}
		}
		
		return state;
	}
	
}
