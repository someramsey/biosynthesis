package com.ramsey.biosynthesis.content.blocks.vessel;

import com.ramsey.biosynthesis.content.blocks.SpreadingBlock;
import com.ramsey.biosynthesis.content.blocks.branch.BranchStemBlock;
import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import com.ramsey.biosynthesis.data.providers.block.common.VesselBlockShapeProvider;
import com.ramsey.biosynthesis.registrate.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class VesselBlock extends BaseEntityBlock implements SpreadingBlock {
    public static final int MaxAge = 5;
    public static DirectionProperty FacingProperty = BlockStateProperties.FACING;
    public static IntegerProperty AgeProperty = IntegerProperty.create("age", 0, MaxAge);

    public VesselBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FacingProperty);
        pBuilder.add(AgeProperty);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return VesselBlockShapeProvider.getShape(pState);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new VesselBlockEntity(pPos, pState);
    }

    private boolean isAir(ServerLevel pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos).isAir();
    }

    private boolean isStem(ServerLevel pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos).getBlock() == BlockRegistry.stemBlock.get();
    }

    private boolean isVessel(ServerLevel pLevel, BlockPos pos) {
        return pLevel.getBlockState(pos).getBlock() == this;
    }

    private boolean canSpreadTo(ServerLevel pLevel, BlockPos pPos) {
        return isAir(pLevel, pPos) || isStem(pLevel, pPos);
    }

    private void completeSpread(ServerLevel pLevel, BlockPos pPos, SpreadTask pTask) {
        pLevel.sendParticles(ParticleTypes.POOF, pPos.getX() + 0.5d, pPos.getY() + 0.5d, pPos.getZ() + 0.5d, 20, 0.3d, 0.3d, 0.3d, 0);
        pTask.consume();
    }

    private void grow(ServerLevel pLevel, BlockState pState, BlockPos pPos, SpreadTask pTask, int age) {
        pLevel.setBlock(pPos, pState.setValue(AgeProperty, age + 1), 2);
        completeSpread(pLevel, pPos, pTask);
    }

    private void spreadBodyUpwards(ServerLevel pLevel, BlockPos pPos, SpreadTask pTask) {
        BlockState blockState = this.defaultBlockState()
            .setValue(FacingProperty, Direction.UP)
            .setValue(AgeProperty, 0);

        pLevel.setBlock(pPos, blockState, 3);
        completeSpread(pLevel, pPos, pTask);
    }

    private void spreadStems(ServerLevel pLevel, Orientation spreadOrientation, BlockPos pos, SpreadTask pTask) {
        BlockState blockState = BlockRegistry.stemBlock.get().defaultBlockState()
            .setValue(BranchStemBlock.OrientationProperty, spreadOrientation)
            .setValue(BranchStemBlock.RootedProperty, true);

        pLevel.setBlock(pos, blockState, 3);
        completeSpread(pLevel, pos, pTask);
    }

    private void spreadBodyHorizontal(ServerLevel pLevel, SpreadTask pTask, Orientation spreadOrientation, BlockPos spreadPos) {
        Direction direction = spreadOrientation.toHorizontalDirection().getOpposite();

        BlockState blockState = this.defaultBlockState()
            .setValue(FacingProperty, direction)
            .setValue(AgeProperty, 0);

        pLevel.setBlock(spreadPos, blockState, 3);
        completeSpread(pLevel, spreadPos, pTask);
    }

    private void spreadOut(ServerLevel pLevel, BlockPos pPos, SpreadTask pTask, Orientation spreadOrientation) {
        BlockPos spreadPos = spreadOrientation.step(pPos);

        if (isVessel(pLevel, spreadPos) || isStem(pLevel, spreadPos)) {
            pTask.propagate(spreadPos);
        } else {
            BlockPos overNext = spreadOrientation.step(spreadPos);

            if (isAir(pLevel, overNext)) {
                spreadStems(pLevel, spreadOrientation, spreadPos,pTask);
            } else {
                spreadBodyHorizontal(pLevel, pTask, spreadOrientation, spreadPos);
            }
        }
    }

    @Override
    public void spread(ServerLevel pLevel, BlockState pState, BlockPos pPos, RandomSource pRandom, SpreadTask pTask) {
        //TODO: test no available spread positions
        //TODO: test spread as body upwards
        //TODO: test spread as body horizontal

        int age = pState.getValue(AgeProperty);

        if (age == MaxAge) {
            BlockPos above = pPos.above();

            if (isVessel(pLevel, above)) {
                pTask.propagate(above);
            } else {
                if (!isAir(pLevel, above)) {
                    pLevel.destroyBlock(above, true);
                }

                spreadBodyUpwards(pLevel, above, pTask);
            }

            return;
        } else if (age >= 2) {
            if (pRandom.nextInt(age + 2) == 0) {
                grow(pLevel, pState, pPos, pTask, age);
                return;
            }

            Orientation spreadOrientation = getSpreadOrientation(pLevel, pPos, pRandom);

            if (spreadOrientation != null) {
                spreadOut(pLevel, pPos, pTask, spreadOrientation);
                return;
            }
        }

        grow(pLevel, pState, pPos, pTask, age);
    }

    private Orientation getSpreadOrientation(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        ArrayList<Orientation> available = new ArrayList<>();

        if (canSpreadTo(pLevel, pPos.north())) {
            available.add(Orientation.North);
        }

        if (canSpreadTo(pLevel, pPos.east())) {
            available.add(Orientation.East);
        }

        if (canSpreadTo(pLevel, pPos.south())) {
            available.add(Orientation.South);
        }

        if (canSpreadTo(pLevel, pPos.west())) {
            available.add(Orientation.West);
        }

        if (!available.isEmpty()) {
            return available.get(pRandom.nextInt(available.size()));
        }

        return null;
    }
}
