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

public class BlockAntiVirus extends BlockBasic{
	int decay;
	Block original = Block.getBlockFromName("air");
	public BlockAntiVirus() {
		super(Material.glass);
		setBlockTextureName(VirusMod.ID+":"+"AntiVirusBlock");
		setCreativeTab(CreativeTabs.tabBlock);
		setHardness(2.0F);
		setResistance(6.0F);
		setLightLevel(1.0F);
		setBlockName(VirusMod.ID+"_"+"AntiVirusBlock");
		setHarvestLevel("pickaxe", 3);
	}
	static final ArrayList<Block> cureList = new ArrayList<Block>();
	static ArrayList<Integer> cureListInt = new ArrayList<Integer>();
	static final Random rand = new Random();
	public static void init(){
		cureList.add(VirusMod.BLOCK_VIRUS);
		cureList.add(VirusMod.BLOCK_VIRUS_SAND);
		for(Block b : cureList){
			cureListInt.add(new Integer(Block.getIdFromBlock(b)));
		}
	}
	public void check(World worldObj, int xCoord, int yCoord, int zCoord){
		boolean areaCleansed = true;
		if(!worldObj.isRemote){
			for(int x = -1; x < 2;x++){
				for(int y = -1; y < 2; y++){
					for(int z = -1; z < 2; z++){
						if(!(x == 0 && y == 0 && z == 0)){
							Block target;
							target = worldObj.getBlock(x+ xCoord, y+ yCoord, z+ zCoord);
							boolean isTarget = false;
							for(Integer ID: cureListInt){
								if(Block.getIdFromBlock(target) == ID){
									isTarget = true;
									break;
								}
							}
							if(isTarget && rand.nextInt(100) < 70){
								areaCleansed = false;
								infect(worldObj,x+ xCoord, y+ yCoord, z+ zCoord,target);

							}
							worldObj.notifyBlocksOfNeighborChange(x+xCoord, y+ yCoord, z+zCoord, this);
						}
					}
				}
			}
			if(areaCleansed){
				decay(worldObj,xCoord,yCoord,zCoord);
			}
		}

	}

	public TileEntity createNewTileEntity(World worldIn, int meta){
		return new TileEntityVirus();
	}
	public void infect(World worldObj, int xCoord, int yCoord, int zCoord,Block target){
		TileEntityVirus v = (TileEntityVirus) worldObj.getTileEntity(xCoord, yCoord, zCoord);
		int ID = v.ID;
		worldObj.setBlock(xCoord, yCoord, zCoord, this,0,2);
		TileEntityVirus v2 = (TileEntityVirus) worldObj.getTileEntity(xCoord, yCoord, zCoord);
		v2.ID = ID;
	}
	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int a) {
		super.breakBlock(world, x,y,z, b,a);
		world.removeTileEntity(x,y,z);
	}

}
