package com.ramsey.biosynthesis.content.blocks.branch;

import com.ramsey.biosynthesis.content.blocks.SpreadingBlock;
import com.ramsey.biosynthesis.data.providers.block.common.stem.BranchStemBlockShapeProvider;
import com.ramsey.biosynthesis.registrate.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BranchStemBlock extends Block implements SpreadingBlock {
    public static final IntegerProperty AgeProperty = IntegerProperty.create("age", 0, 4);
    public static final BooleanProperty RootedProperty = BooleanProperty.create("rooted");
    public static final EnumProperty<Orientation> OrientationProperty = EnumProperty.create("orientation", Orientation.class);

    public BranchStemBlock(Properties pProperties) {
        super(pProperties);

        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(AgeProperty, 0)
                .setValue(RootedProperty, false)
                .setValue(OrientationProperty, Orientation.UpN)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);

        pBuilder.add(AgeProperty);
        pBuilder.add(OrientationProperty);
        pBuilder.add(RootedProperty);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return BranchStemBlockShapeProvider.getShape(pState);
    }

    private void placeStem(ServerLevel pLevel, BlockPos pPos, Orientation pOrientation) {
        BlockState blockState = this.defaultBlockState()
            .setValue(OrientationProperty, pOrientation)
            .setValue(RootedProperty, false);

        pLevel.setBlock(pPos, blockState, 3);
    }

    private void placeEdge(ServerLevel pLevel, BlockPos pPos, Orientation pOrientation) {
        BlockState blockState = BlockRegistry.branchBlock.get().defaultBlockState()
            .setValue(BranchBlock.OrientationProperty, pOrientation)
            .setValue(BranchBlock.ConnectedFrontProperty, BranchBlock.ConnectionState.None);

        pLevel.setBlock(pPos, blockState, 3);
    }

    //TODO: add splits
    private void placeBranch(ServerLevel pLevel, BlockPos pCurrentPos, BlockPos pNextPos, Orientation pCurrentOrientation) {
        if (!pLevel.getBlockState(pNextPos).isAir()) {
            BlockState blockState = BlockRegistry.branchBlock.get().defaultBlockState()
                .setValue(BranchBlock.OrientationProperty, pCurrentOrientation)
                .setValue(BranchBlock.ConnectedFrontProperty, BranchBlock.ConnectionState.Up);

            pLevel.setBlock(pCurrentPos, blockState, 3);



        }

    }

    @Override
    public void spread(ServerLevel pLevel, BlockState pState, BlockPos pPos, RandomSource pRandom, SpreadTask pTask) {
        //TODO: reimplement
//        int age = pState.getValue(AgeProperty);
//
//        if (age < 3) {
//            pLevel.setBlock(pPos, pState.setValue(AgeProperty, age + 1), 3);
//            pTask.consume();
//            return;
//        }
//
//        Orientation orientation = pState.getValue(OrientationProperty);
//
//        BlockPos nextPos = orientation.step(pPos);
//        BlockState nextBlockState = pLevel.getBlockState(nextPos);
//        Block nextBlock = nextBlockState.getBlock();
//
//        if (SpreadingBlock.canPropagate(nextBlock)) {
//            pTask.propagate(nextPos);
//            return;
//        }
//
//        boolean rooted = pState.getValue(RootedProperty);
//
//        if (rooted) {
//            if (!nextBlockState.isAir()) {
//                pLevel.destroyBlock(nextPos, true);
//            }
//
//            placeStem(pLevel, nextPos, orientation);
//            return;
//        }
//
//        placeBranch(pLevel, pPos, nextPos, orientation);

        pTask.consume();
    }
}
