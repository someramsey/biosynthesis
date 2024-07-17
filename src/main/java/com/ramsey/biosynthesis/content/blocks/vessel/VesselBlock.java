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

    private boolean isValid(ServerLevel pLevel, BlockPos pPos) {
        return isAir(pLevel, pPos) || isStem(pLevel, pPos);
    }

    private void sendParticles(ServerLevel pLevel, BlockPos pPos) {
        pLevel.sendParticles(ParticleTypes.POOF, pPos.getX() + 0.5d, pPos.getY() + 0.5d, pPos.getZ() + 0.5d, 20, 0.3d, 0.3d, 0.3d, 0);
    }

    private void grow(ServerLevel pLevel, BlockState pState, BlockPos pPos, SpreadTask pTask, int age) {
        pLevel.setBlock(pPos, pState.setValue(AgeProperty, age + 1), 2);
        sendParticles(pLevel, pPos);
        pTask.consume();
    }

    private void spreadUpwards(ServerLevel pLevel, BlockPos pPos, SpreadTask pTask) {
        BlockState blockState = this.defaultBlockState()
            .setValue(FacingProperty, Direction.UP)
            .setValue(AgeProperty, 0);

        pLevel.setBlock(pPos.above(), blockState, 3);
        sendParticles(pLevel, pPos);
        pTask.consume();
    }

    private static void spreadStems(ServerLevel pLevel, Orientation spreadOrientation, BlockPos pos) {
        BlockState blockState = BlockRegistry.stemBlock.get().defaultBlockState()
            .setValue(BranchStemBlock.OrientationProperty, spreadOrientation)
            .setValue(BranchStemBlock.RootedProperty, true);

        pLevel.setBlock(pos, blockState, 3);
    }


    private void spreadOut(ServerLevel pLevel, BlockPos pPos, SpreadTask pTask, Orientation spreadOrientation) {
        BlockPos pos = spreadOrientation.step(pPos);

        if (isVessel(pLevel, pos) || isStem(pLevel, pos)) {
            pTask.propagate(pos, spreadOrientation);
        } else {
            BlockPos overNext = spreadOrientation.step(pos);

            if (isAir(pLevel, overNext)) {
                spreadStems(pLevel, spreadOrientation, pos);
            } else {

            }

            pTask.consume();
        }
    }

    @Override
    public void spread(ServerLevel pLevel, BlockState pState, BlockPos pPos, RandomSource pRandom, SpreadTask pTask) {
        int age = pState.getValue(AgeProperty);

        if (age == MaxAge) {
            if (isAir(pLevel, pPos.above())) {
                spreadUpwards(pLevel, pPos, pTask);
            } else if (isVessel(pLevel, pPos.above())) {
                Orientation orientation = Orientation.Horizontal[pRandom.nextInt(4)];
                pTask.propagate(pPos.above(), orientation);
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

        if (isValid(pLevel, pPos.north())) {
            available.add(Orientation.North);
        }

        if (isValid(pLevel, pPos.east())) {
            available.add(Orientation.East);
        }

        if (isValid(pLevel, pPos.south())) {
            available.add(Orientation.South);
        }

        if (isValid(pLevel, pPos.west())) {
            available.add(Orientation.West);
        }

        if (!available.isEmpty()) {
            return available.get(pRandom.nextInt(available.size()));
        }

        return null;
    }
}
