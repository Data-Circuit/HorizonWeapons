package io.github.datacircuit.horizonweapons.client;

import io.github.datacircuit.horizonweapons.client.registry.HorizonWeaponsParticleProviders;
import io.github.datacircuit.horizonweapons.client.render.PlinthBlockEntityRenderer;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class HorizonweaponsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HorizonWeaponsParticleProviders.init();

        BlockEntityRenderers.register(HorizonWeaponsBlockEntities.PLINTH_BLOCK_ENTITY, PlinthBlockEntityRenderer::new);
    }
}
