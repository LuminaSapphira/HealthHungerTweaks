package net.cerulan.healthhungertweaks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = ModInfo.MODID, version = ModInfo.VERSION, dependencies="required-after:applecore@[3.2.0,)", updateJSON="https://raw.githubusercontent.com/CerulanLumina/HealthHungerTweaks/master/versions.json")
public class HealthHungerTweaks
{
	
	public static Logger Log = LogManager.getLogger("healthhungertweaks");		
	
	@Instance
	public static HealthHungerTweaks instance;
	
	@SidedProxy(serverSide="net.cerulan.healthhungertweaks.CommonProxy", clientSide="net.cerulan.healthhungertweaks.ClientProxy")
	public static CommonProxy sidedProxy;

	
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	HealthHungerTweaks.Log.info("Initialization");
        sidedProxy.init();
    }
    
    
}
