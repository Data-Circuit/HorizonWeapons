package io.github.datacircuit.horizonweapons.block;

import com.mojang.serialization.MapCodec;
import io.github.datacircuit.horizonweapons.block.entity.PlinthBlockEntity;
import io.github.datacircuit.horizonweapons.gods.ChosenManager;
import io.github.datacircuit.horizonweapons.item.weapon.HorizonWeapon;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockAndLightGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class PlinthBlock extends BaseEntityBlock {
    public PlinthBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(PlinthBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos worldPosition, @NonNull BlockState blockState) {
        return new PlinthBlockEntity(worldPosition, blockState);
    }

    @Override
    protected @NonNull InteractionResult useItemOn(@NonNull ItemStack itemStack, @NonNull BlockState state, Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof PlinthBlockEntity plinth)) {
            return InteractionResult.PASS;
        }

        if (!player.getItemInHand(hand).isEmpty() && plinth.isEmpty()) {
            plinth.setItem(0, player.getItemInHand(hand).copy());
            player.getItemInHand(hand).setCount(0);
            plinth.setChanged();
        } else if (player.getItemInHand(hand).isEmpty() && !plinth.isEmpty()) {
            Item item = plinth.getItem(0).getItem();
            if (item instanceof HorizonWeapon weapon) {
                if (!weapon.getOriginalOwner().equals(ChosenManager.getInstance().getGod(player))) return InteractionResult.PASS;
            }

            player.setItemInHand(hand, plinth.getItem(0).copy());
            plinth.setItem(0, ItemStack.EMPTY);
            plinth.setChanged();
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.or(
                Block.column(16, 0, 2),
                Block.column(14, 2, 4),
                Block.column(18, 4, 6),
                Block.column(14, 6, 12),
                Block.column(16, 12, 14)
        );
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(BlockStateProperties.FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
        builder.add(BlockStateProperties.WATERLOGGED);
    }
}
