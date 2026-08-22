package io.github.datacircuit.horizonweapons.block;

import com.mojang.serialization.MapCodec;
import io.github.datacircuit.horizonweapons.block.entity.PlinthBlockEntity;
import io.github.datacircuit.horizonweapons.gods.ChosenManager;
import io.github.datacircuit.horizonweapons.item.weapon.HorizonWeapon;
import io.github.datacircuit.horizonweapons.registry.HorizonWeaponsBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> type) {
        return createTickerHelper(type, HorizonWeaponsBlockEntities.PLINTH_BLOCK_ENTITY.get(), PlinthBlockEntity::tick);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NonNull ItemStack itemStack, @NonNull BlockState state, Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof PlinthBlockEntity plinth)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!player.getItemInHand(hand).isEmpty() && plinth.isEmpty()) {
            plinth.setItem(0, player.getItemInHand(hand).copy());
            player.getItemInHand(hand).setCount(0);
            plinth.setChanged();
        } else if (player.getItemInHand(hand).isEmpty() && !plinth.isEmpty()) {
            Item item = plinth.getItem(0).getItem();
            if (item instanceof HorizonWeapon weapon) {
                if (!weapon.getOriginalOwner().equals(ChosenManager.getInstance().getGod(player))) {
                    player.sendSystemMessage(Component.literal("You cannot acquire this item"));
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }
            }

            player.setItemInHand(hand, plinth.getItem(0).copy());
            plinth.setItem(0, ItemStack.EMPTY);
            plinth.setChanged();
        }

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.or(
                column(16, 0, 2),
                column(14, 2, 4),
                column(18, 4, 6),
                column(14, 6, 12),
                column(16, 12, 14)
        );
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    private static VoxelShape column(final double sizeXZ, final double minY, final double maxY) {
        double halfX = sizeXZ / 2.0f;
        double halfZ = sizeXZ / 2.0f;
        return Block.box(8.0 - halfX, minY, 8.0 - halfZ, 8.0 + halfX, maxY, 8.0 + halfZ);
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
