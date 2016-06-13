package com.mymods.virusmod.blocks;

import com.mymods.virusmod.VirusMod;
import com.mymods.virusmod.tileentities.TileEntityVirus;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockVirusSand extends BlockVirus{

	public BlockVirusSand(){
		setBlockTextureName(VirusMod.ID+":"+"SandVirusBlock");
		setBlockName(VirusMod.ID+"_"+"SandVirusBlock");
	}
	public static void init(){
		blackListBlock.add(Block.getBlockFromName("air"));
		blackListBlock.add(VirusMod.BLOCK_VIRUS); //165
		blackListBlock.add(VirusMod.BLOCK_ANTIVIRUS); //166
		blackListBlock.add(VirusMod.BLOCK_VIRUS_SAND);
		blackListBlock.add(Block.getBlockFromName("sand"));
		for(Block b : blackListBlock){
			blackListInt.add(new Integer(Block.getIdFromBlock(b)));
		}
	}
	public void check(World worldObj, int xCoord, int yCoord, int zCoord){
		boolean areaCleared = true;
		if(!worldObj.isRemote){
			TileEntityVirus e = (TileEntityVirus) worldObj.getTileEntity(xCoord, yCoord, zCoord);
			if(!e.isInRange(xCoord, yCoord, zCoord)){
				return;
			}
			for(int x = -1; x < 2;x++){
				for(int y = -1; y < 2; y++){
					for(int z = -1; z < 2; z++){
						if(!(x == 0 && y == 0 && z == 0)){
							Block target;
							boolean isTarget = true;
							target = worldObj.getBlock(x+ xCoord, y+ yCoord, z+ zCoord);
							for(Integer ID : blackListInt){
								if(Block.getIdFromBlock(target) == ID.intValue()){
									isTarget = false;
									break;
								}
							}
							if(isTarget && rand.nextInt(100) < 50){
								areaCleared = false;
								infect(worldObj,x+xCoord, y+yCoord, z+zCoord,target,e);
							}
							worldObj.notifyBlocksOfNeighborChange(x+xCoord, y+ yCoord, z+zCoord, this);
						}
					}
				}
			}
		}
		if(areaCleared){
			decay(worldObj,xCoord, yCoord, zCoord);
		}
	}
	public void decay(World worldObj, int xCoord, int yCoord, int zCoord){
		worldObj.setBlock(xCoord, yCoord, zCoord, Block.getBlockFromName("sand"));
		worldObj.notifyBlockOfNeighborChange(xCoord, yCoord, zCoord, this);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this);
	}
}
