package com.mymods.virusmod;

import com.mymods.virusmod.blocks.BlockAntiVirus;
import com.mymods.virusmod.blocks.BlockVirus;
import com.mymods.virusmod.blocks.BlockVirusMiner;
import com.mymods.virusmod.blocks.BlockVirusSand;
import com.mymods.virusmod.tileentities.TileEntityVirus;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = VirusMod.ID, version = VirusMod.VERSION, name = VirusMod.NAME, guiFactory = "com.mymods.virusmod.ModGuiFactory")
public class VirusMod {
	public static final String ID = "virusmod";
	public static final String VERSION = "1.1.0";
	public static final String NAME = "VirusMod";


	public static final BlockVirus BLOCK_VIRUS = new BlockVirus();
	public static final BlockAntiVirus BLOCK_ANTIVIRUS = new BlockAntiVirus();
	public static final BlockVirusSand BLOCK_VIRUS_SAND = new BlockVirusSand();
	public static final BlockVirusMiner BLOCK_VIRUS_MINER = new BlockVirusMiner();

	@Mod.Instance("virusmod")
	public static VirusMod instance;

	public static Configuration configFile;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		configFile = new Configuration(event.getSuggestedConfigurationFile());
		configFile.load();
		syncConfig();
		
		GameRegistry.registerBlock(BLOCK_VIRUS,"BlockVirus");
		GameRegistry.registerBlock(BLOCK_ANTIVIRUS,"BlockAntiVirus");
		GameRegistry.registerBlock(BLOCK_VIRUS_SAND, "BlockVirusSand");
		GameRegistry.registerBlock(BLOCK_VIRUS_MINER, "BlockVirusMiner");
		
		LanguageRegistry.addName(BLOCK_VIRUS_SAND, "Sand Virus Block");
		LanguageRegistry.addName(BLOCK_VIRUS, "Virus Block");
		LanguageRegistry.addName(BLOCK_ANTIVIRUS, "AntiVirus Block");
		LanguageRegistry.addName(BLOCK_VIRUS_MINER, "Miner Virus Block");
		GameRegistry.registerTileEntity(TileEntityVirus.class, "Virus_Tile_Entity");
		TileEntity.addMapping(TileEntityVirus.class, "VirusTileEntity");

	}
	public static void syncConfig(){
		int virusRange = configFile.getInt("Virus Range", "ranges", 50, -1, 10000, "The maximum range a virus can infect from its host block");
		TileEntityVirus.range = virusRange;
		if(configFile.hasChanged()){
			configFile.save();
		}
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if(eventArgs.modID.equals("virusmod"))
			syncConfig();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		FMLCommonHandler.instance().bus().register(instance);
	}
	@EventHandler
	public void postInit(FMLInitializationEvent event){
		BlockVirus.init();
		BlockAntiVirus.init();
		BlockVirusSand.init();
		BlockVirusMiner.init();
	}

}
