package io.github.datacircuit.horizonweapons;

import io.github.datacircuit.horizonweapons.client.render.PlinthBlockEntityRenderer;
import io.github.datacircuit.horizonweapons.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(HorizonWeapons.MOD_ID)
public class HorizonWeapons {

    public static final String MOD_ID = "horizonweapons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public HorizonWeapons(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        HorizonWeaponsDataComponents.init(modEventBus);
        HorizonWeaponsArmorMaterials.init(modEventBus);
        HorizonWeaponsEffects.init(modEventBus);
        HorizonWeaponsPotions.init(modEventBus);
        HorizonWeaponsItems.init(modEventBus);
        HorizonWeaponsBlocks.init(modEventBus);
        HorizonWeaponsBlockEntities.init(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Loading Horizon Weapons");
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    @EventBusSubscriber(Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Starting client setup");
        }

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(HorizonWeaponsBlockEntities.PLINTH_BLOCK_ENTITY.get(), PlinthBlockEntityRenderer::new);
        }
    }
}
