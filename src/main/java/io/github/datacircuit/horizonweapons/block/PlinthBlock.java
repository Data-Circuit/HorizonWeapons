package io.github.datacircuit.horizonweapons.block;

import com.mojang.serialization.MapCodec;
import io.github.datacircuit.horizonweapons.block.entity.PlinthBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class PlinthBlock extends BaseEntityBlock {
    public PlinthBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(PlinthBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new PlinthBlockEntity(worldPosition, blockState);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof PlinthBlockEntity plinth)) {
            return InteractionResult.PASS;
        }

        if (!player.getItemInHand(hand).isEmpty() && plinth.isEmpty()) {
            plinth.setItem(0, player.getItemInHand(hand).copy());
            player.getItemInHand(hand).setCount(0);
        }

        if (player.getItemInHand(hand).isEmpty() && !plinth.isEmpty()) {
            player.setItemInHand(hand, plinth.getItem(0).copy());
            plinth.setItem(0, ItemStack.EMPTY);
        }

        return InteractionResult.SUCCESS;
    }
}
