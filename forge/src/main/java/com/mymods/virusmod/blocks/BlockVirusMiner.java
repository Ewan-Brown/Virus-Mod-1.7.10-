package com.mymods.virusmod.blocks;

import java.util.ArrayList;

import com.mymods.virusmod.VirusMod;
import com.mymods.virusmod.tileentities.TileEntityVirus;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockVirusMiner extends BlockVirus{

	static final ArrayList<Block> blackListBlock = new ArrayList<Block>();
	static ArrayList<Integer> blackListInt = new ArrayList<Integer>();
	public static void init(){
		blackListBlock.add(Block.getBlockFromName("air"));
		blackListBlock.add(Block.getBlockFromName("coal_ore"));
		blackListBlock.add(Block.getBlockFromName("gold_ore"));
		blackListBlock.add(Block.getBlockFromName("iron_ore"));
		blackListBlock.add(Block.getBlockFromName("lapis_ore"));
		blackListBlock.add(Block.getBlockFromName("diamond_ore"));
		blackListBlock.add(Block.getBlockFromName("redstone_ore"));
		blackListBlock.add(Block.getBlockFromName("chest"));
		blackListBlock.add(Block.getBlockFromName("bedrock"));
		blackListBlock.add(VirusMod.BLOCK_VIRUS_MINER);

		for(Block b : blackListBlock){
			blackListInt.add(new Integer(Block.getIdFromBlock(b)));
		}
	}
	
	public BlockVirusMiner() {
		setBlockTextureName(VirusMod.ID+":"+"VirusMinerBlock");
		setBlockName(VirusMod.ID+"_"+"VirusMinerBlock");
		setBlockTextureName("cake_top");
	}
	
	public void check(World worldObj, int xCoord, int yCoord, int zCoord){
		boolean areaCleared = true;
		if(!worldObj.isRemote){
			TileEntityVirus e = (TileEntityVirus	)worldObj.getTileEntity(xCoord, yCoord, zCoord);
			if(!e.isInRange(xCoord, yCoord, zCoord)){
				System.out.println("Alrigh Alright Alright!");
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
								infect(worldObj,x+xCoord, y+ yCoord, z+zCoord,target,e);
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
	public void infect(World worldObj, int xCoord, int yCoord, int zCoord,Block target,TileEntityVirus e){
		worldObj.setBlock(xCoord, yCoord, zCoord, this,0,2);
		TileEntityVirus e2 = (TileEntityVirus) worldObj.getTileEntity(xCoord, yCoord, zCoord);
		ID = Block.getIdFromBlock(target);
		e2.ID = ID;
		e2.originX = e.originX;
		e2.originY = e.originY;
		e2.originZ = e.originZ;
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this);
	}
	public void decay(World worldObj, int xCoord, int yCoord, int zCoord){
		worldObj.setBlock(xCoord, yCoord, zCoord, Block.getBlockFromName("air"));
		worldObj.notifyBlockOfNeighborChange(xCoord, yCoord, zCoord, this);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this);
	}
	public TileEntity createNewTileEntity(World worldIn, int meta){
		return new TileEntityVirus();
	}

	
}
