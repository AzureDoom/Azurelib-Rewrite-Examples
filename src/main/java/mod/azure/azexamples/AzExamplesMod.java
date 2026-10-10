package mod.azure.azexamples;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import mod.azure.azexamples.proxy.CommonProxy;

@Mod(
    modid = CommonStrings.MOD_ID,
    name = CommonStrings.MOD_NAME,
    useMetadata = true,
    acceptedMinecraftVersions = "[1.7.10]",
    dependencies = "required-after:azurelib"
)
public final class AzExamplesMod {

    @Mod.Instance(CommonStrings.MOD_ID)
    public static AzExamplesMod instance;

    @SidedProxy(
        clientSide = "mod.azure.azexamples.proxy.ClientProxy",
        serverSide = "mod.azure.azexamples.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CommonMod.preInit();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        CommonMod.init();
        proxy.init();
    }
}
