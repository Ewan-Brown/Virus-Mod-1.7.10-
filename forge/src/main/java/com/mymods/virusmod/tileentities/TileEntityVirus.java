package com.mymods.virusmod.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityVirus extends TileEntity{
	
	public int ID;

	public int originX;
	public int originY;
	public int originZ;
	public static int range = 5;

	public TileEntityVirus(){}
	public void setOrigin(int x, int y,int z){
		originX = x;
		originY = y;
		originZ = z;
	}
	public boolean isInRange(int x, int y,int z){
		if(range == -1){
			return true;
		}
		double distance = getRange(x,y,z);
		if (distance < range){
			return true;
		}
		else{
			return false;
		}
	}
	public double getRange(int x, int y,int z){
		return Math.sqrt((x-originX)* (x-originX) + (y-originY)*(y-originY) + (z-originZ)*(z-originZ));
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setInteger("ID", ID);
		nbt.setInteger("originX", originX);
		nbt.setInteger("originY", originY);
		nbt.setInteger("originZ", originZ);
		
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		ID = nbt.getInteger("ID");
		originX = nbt.getInteger("originX");
		originY = nbt.getInteger("originY");
		originZ = nbt.getInteger("originZ");
	}
	@Override
	public int getBlockMetadata() {
		return ID;
	}

}
