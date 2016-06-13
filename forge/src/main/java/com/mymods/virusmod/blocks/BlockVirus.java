package com.mymods.virusmod.blocks;

import java.util.ArrayList;
import java.util.Random;

import com.mymods.virusmod.VirusMod;
import com.mymods.virusmod.tileentities.TileEntityVirus;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockVirus extends BlockBasic{

	public BlockVirus() {
		super(Material.ground);
		setCreativeTab(CreativeTabs.tabBlock);
		setHardness(2.0F);
		setResistance(6.0F);
		setLightLevel(1.0F);
		setBlockTextureName(VirusMod.ID+":"+"VirusBlock");
		setBlockName(VirusMod.ID+"_"+"VirusBlock");
		setHarvestLevel("pickaxe", 3);
	}
	static final ArrayList<Block> blackListBlock = new ArrayList<Block>();
	static ArrayList<Integer> blackListInt = new ArrayList<Integer>();
	static final Random rand = new Random();
	public static void init(){
		blackListBlock.add(Block.getBlockFromName("air"));
		blackListBlock.add(VirusMod.BLOCK_VIRUS); //165
		blackListBlock.add(VirusMod.BLOCK_ANTIVIRUS); //166
		blackListBlock.add(VirusMod.BLOCK_VIRUS_MINER);
		for(Block b : blackListBlock){
			blackListInt.add(new Integer(Block.getIdFromBlock(b)));
		}
	}
	int ID = 0;
	public void check(World worldObj, int xCoord, int yCoord, int zCoord){
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
								infect(worldObj,x+xCoord, y+ yCoord, z+zCoord,target,e);
							}
							worldObj.notifyBlocksOfNeighborChange(x+xCoord, y+ yCoord, z+zCoord, this);
						}
					}
				}
			}
		}
	}   
	public void infect(World worldObj, int xCoord, int yCoord, int zCoord,Block target,TileEntityVirus e){
		worldObj.setBlock(xCoord, yCoord, zCoord, this,0,2);
		TileEntityVirus newVirus = (TileEntityVirus) worldObj.getTileEntity(xCoord, yCoord, zCoord);
		ID = Block.getIdFromBlock(target);
		newVirus.ID = ID;
		newVirus.originX = e.originX;
		newVirus.originY = e.originY;
		newVirus.originZ = e.originZ;
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this);
	}
	public TileEntity createNewTileEntity(World worldIn, int meta){
		return new TileEntityVirus();
	}

	public void breakBlock(World worldObj, int xCoord, int yCoord, int zCoord, Block b, int a) {
		super.breakBlock(worldObj, xCoord,yCoord,zCoord, b,a);
		worldObj.removeTileEntity(xCoord,yCoord,zCoord);
	}
	



}