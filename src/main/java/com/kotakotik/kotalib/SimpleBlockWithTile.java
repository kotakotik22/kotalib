package com.kotakotik.kotalib;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public abstract class SimpleBlockWithTile<T extends TileEntity> extends Block {
    private static final DirectionProperty FACING = BlockStateProperties.FACING;
    private T tileEntity;

    public SimpleBlockWithTile(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    /**
     * @return Whether the block can be rotated, if true, the block will automatically rotated to face the player when placed
     */
    public boolean canBeRotated() {return false;}

    /**
     * Ignored if {@link #canBeRotated()} returns false.
     *
     * @return Whether the block can be rotated vertically, if true, the block will face the player vertically when placed
     */
    public boolean canBeRotatedVertically() {return false;}

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return this.tileEntity = createTileEntity();
    }

    /**
     * Ran when {@link #createTileEntity(BlockState, IBlockReader)} is ran, automatically sets {@link #tileEntity} to the returned TileEntity
     *
     * @return The tile entity to create
     */
    public abstract T createTileEntity();

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        if(canBeRotated()) {
            builder.add(FACING);
        }

        super.fillStateContainer(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        if(canBeRotated()) {
            return this.getDefaultState().with(FACING, canBeRotatedVertically() ?
                    context.getNearestLookingDirection().getOpposite()
                    : // Only rotate vertically if canBeRotatedVertically() returns true
                    context.getPlacementHorizontalFacing().getOpposite());
        }
        return super.getStateForPlacement(context);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        if(canBeRotated()) {
            return state.with(FACING, rot.rotate(state.get(FACING)));
        }
        return super.rotate(state, rot);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        if(canBeRotated()) {
            return state.rotate(mirrorIn.toRotation(state.get(FACING)));
        }
        return super.mirror(state, mirrorIn);
    }
}
