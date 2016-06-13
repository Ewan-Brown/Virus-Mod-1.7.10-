package com.mymods.virusmod;

import cpw.mods.fml.client.config.ConfigGuiType;
import cpw.mods.fml.client.config.DummyConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries.NumberSliderEntry;
import cpw.mods.fml.client.config.GuiSlider;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class ModConfigGui extends GuiConfig {
	public ModConfigGui(GuiScreen parent) {
		super(parent,
				new ConfigElement(VirusMod.configFile.getCategory("ranges")).getChildElements(),
				VirusMod.ID, false, false, GuiConfig.getAbridgedConfigPath(VirusMod.configFile.toString()));
	}
	@Override
	public void initGui()
	{
		super.initGui();
	}


	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		// You can do things like create animations, draw additional elements, etc. here
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
	}

}