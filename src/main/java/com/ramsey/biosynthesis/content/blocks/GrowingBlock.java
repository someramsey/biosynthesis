package com.ramsey.biosynthesis.content.blocks;

import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface GrowingBlock {
    void grow(ServerLevel pLevel, BlockState pState, BlockPos pPos, RandomSource pRandom, SpreadTask pSpreadTask);

    class SpreadTask {
        private final ServerLevel level;
        private final RandomSource random;

        public BlockPos pos;
        public Orientation orientation;
        public boolean spreading;

        public SpreadTask(ServerLevel level, RandomSource random, BlockPos pos, Orientation orientation) {
            this.level = level;
            this.random = random;
            this.orientation = orientation;
            this.pos = pos;
            this.spreading = true;
        }

        private void spread() {
            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();

            if (block instanceof GrowingBlock growingBlock) {
                growingBlock.grow(level, state, pos, random, this);
            }
//
//            if (block == BlockRegistry.vesselBlock.get()) {
//                BlockPos below = pos.below();
//
//                if (!canSupport(below.north())) {
//                    return true;
//                }
//
//                int age = state.getValue(AgeProperty);
//
//                if (age < VesselBlock.MaxAge) {
//                    VesselBlock.grow(level, state, pos, age + 1);
//                    return true;
//                }
//
//                if (getBlock(pos.north()) != Blocks.AIR) {
//
//                }
//
//                return false;
//            }
//
//
//            if (block == Blocks.AIR) {
//                BlockState newState = BlockRegistry.stemBlock.get().defaultBlockState()
//                    .setValue(BranchStemBlock.OrientationProperty, orientation)
//                    .setValue(BranchStemBlock.RootedProperty, true);
//                System.out.println(newState.getValue(BranchStemBlock.AgeProperty));
//
//                level.setBlock(newPos, newState, 2);
//            } else if (block == BlockRegistry.stemBlock.get()) {
//                int age = state.getValue(BranchStemBlock.AgeProperty);
//
//                if (age == 4) {
//                    int orientationIndex = random.nextInt(4);
//                    Orientation newOrientation = Orientation.Horizontal[orientationIndex];
//
//                    if (newOrientation == orientation) {
//                        newOrientation = Orientation.Horizontal[(orientationIndex + 1) % 4];
//                    }
//
//                    pos = newPos;
//                    orientation = newOrientation;
//
//                    return false;
//                }
//
//                level.setBlock(newPos, state.setValue(BranchStemBlock.AgeProperty, age + 1), 2);
//            }
//
//            return true;

        }

        public void propagate(Orientation pOrientation) {
            pos = pOrientation.step(pos);
            orientation = pOrientation;
        }
    }
}
