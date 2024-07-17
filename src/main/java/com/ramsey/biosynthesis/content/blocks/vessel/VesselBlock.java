package com.ramsey.biosynthesis.content.blocks.vessel;

import com.ramsey.biosynthesis.content.blocks.GrowingBlock;
import com.ramsey.biosynthesis.content.blocks.branch.BranchStemBlock;
import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import com.ramsey.biosynthesis.data.providers.block.common.VesselBlockShapeProvider;
import com.ramsey.biosynthesis.registrate.BlockRegistry;
import net.minecraft.core.BlockPos;
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

public class VesselBlock extends BaseEntityBlock implements GrowingBlock {
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

    private boolean isValid(ServerLevel pLevel, BlockPos pPos) {
        return isAir(pLevel, pPos) || isStem(pLevel, pPos);
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

    private void grow(ServerLevel pLevel, BlockState pState, BlockPos pPos, int age) {
        pLevel.setBlock(pPos, pState.setValue(AgeProperty, age + 1), 2);
        pLevel.sendParticles(ParticleTypes.POOF, pPos.getX() + 0.5d, pPos.getY() + 0.5d, pPos.getZ() + 0.5d, 20, 0.3d, 0.3d, 0.3d, 0);
    }

    @Override
    public void spread(ServerLevel pLevel, BlockState pState, BlockPos pPos, RandomSource pRandom, SpreadTask pTask) {
        int age = pState.getValue(AgeProperty);

        if (age < 2) {
            grow(pLevel, pState, pPos, age);
            pTask.consume();
            return;
        }

        Orientation spreadOrientation = getSpreadOrientation(pLevel, pPos, pRandom);

        if (spreadOrientation == null) {
            //TODO: spread upwards
            grow(pLevel, pState, pPos, age);
            pTask.consume();
            return;
        }

        BlockPos pos = spreadOrientation.step(pPos);

        if (isStem(pLevel, pos)) {
            pTask.propagate(pos, spreadOrientation);
        } else {
            BlockState blockState = BlockRegistry.stemBlock.get().defaultBlockState()
                .setValue(BranchStemBlock.OrientationProperty, spreadOrientation);

            pLevel.setBlock(pos, blockState, 2);
            pTask.consume();
        }
    }

}
