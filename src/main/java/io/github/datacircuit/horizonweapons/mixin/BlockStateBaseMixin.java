package io.github.datacircuit.horizonweapons.mixin;

import io.github.datacircuit.horizonweapons.block.entity.PlinthBlockEntity;
import io.github.datacircuit.horizonweapons.gods.ChosenManager;
import io.github.datacircuit.horizonweapons.item.weapon.HorizonWeapon;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class BlockStateBaseMixin {

    @Inject(method = "getDestroyProgress", at = @At("RETURN"), cancellable = true)
    private void getDestroyProgress(Player player, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (level.getBlockEntity(pos) instanceof PlinthBlockEntity plinth) {
            if (plinth.getItem(0).getItem() instanceof HorizonWeapon weapon) {
                if (!weapon.getOriginalOwner().equals(ChosenManager.getInstance().getGod(player))) cir.setReturnValue(0f);
            }
        }
    }
}
