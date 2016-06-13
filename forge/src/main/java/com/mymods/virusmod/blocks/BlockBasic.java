package com.mymods.virusmod.blocks;

import java.util.Random;

import com.mymods.virusmod.tileentities.TileEntityVirus;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBasic extends Block implements ITileEntityProvider{
	
	protected BlockBasic(Material material) {
		super(material);
		setTickRandomly(true);
		// TODO Auto-generated constructor stub
	}
    public void updateTick(World worldObj, int x, int y, int z, Random rand) {
		check(worldObj, x, y, z);
    }
	public void decay(World worldObj, int xCoord, int yCoord, int zCoord){
		TileEntityVirus v  = (TileEntityVirus) worldObj.getTileEntity(xCoord, yCoord, zCoord);
		worldObj.setBlock(xCoord, yCoord, zCoord, Block.getBlockById(v.ID));
	}
	public void check(World worldObj, int xCoord, int yCoord, int zCoord) {}
	
    @Override
    public void breakBlock(World world, int x, int y, int z, Block b, int a) {
        super.breakBlock(world, x,y,z, b,a);
        world.removeTileEntity(x,y,z);
    }
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return null;
	}
	public void onPostBlockPlaced(World worldObj, int xCoord, int yCoord, int zCoord, int meta) {
		super.onPostBlockPlaced(worldObj, xCoord,yCoord,zCoord,meta);
		TileEntityVirus e = (TileEntityVirus) worldObj.getTileEntity(xCoord, yCoord, zCoord);
		e.setOrigin(xCoord, yCoord, zCoord);
	};
	public boolean onBlockActivated(World worldObj, int xCoord, int yCoord, int zCoord,
			EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if(!worldObj.isRemote){
			check(worldObj,xCoord,yCoord,zCoord);
		}
		return super.onBlockActivated(worldObj, xCoord, yCoord, zCoord, p_149727_5_, p_149727_6_, p_149727_7_,
				p_149727_8_, p_149727_9_);
	}

}
