package mod.azure.azexamples;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import mod.azure.azexamples.proxy.CommonProxy;

@Mod(
    modid = CommonStrings.MOD_ID,
    name = CommonStrings.MOD_NAME,
    useMetadata = true,
    acceptedMinecraftVersions = "[1.12.2]",
    dependencies = "required-after:azurelib"
)
public final class AzExamplesMod {

    @SidedProxy(
        clientSide = "mod.azure.azexamples.proxy.ClientProxy",
        serverSide = "mod.azure.azexamples.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        CommonMod.init();
        proxy.init();
    }
}
