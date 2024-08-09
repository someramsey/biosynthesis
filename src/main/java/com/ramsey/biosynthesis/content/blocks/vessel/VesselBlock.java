package com.ramsey.biosynthesis.content.blocks.vessel;

import com.ramsey.biosynthesis.content.blocks.branch.BranchStemBlock;
import com.ramsey.biosynthesis.content.blocks.branch.Orientation;
import com.ramsey.biosynthesis.data.providers.block.common.vessel.VesselBlockShapeProvider;
import com.ramsey.biosynthesis.registrate.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VesselBlock extends BaseEntityBlock {
    public static final int MaxAge = 5;
    public static EnumProperty<Alignment> AlignmentProperty = EnumProperty.create("alignment", Alignment.class);
    public static IntegerProperty AgeProperty = IntegerProperty.create("age", 0, MaxAge);

    public VesselBlock(Properties pProperties) {
        super(pProperties);

        this.registerDefaultState(stateDefinition.any()
            .setValue(VesselBlock.AlignmentProperty, Alignment.Middle)
            .setValue(VesselBlock.AgeProperty, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AlignmentProperty);
        pBuilder.add(AgeProperty);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return VesselBlockShapeProvider.buildShape(pState);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new VesselBlockEntity(pPos, pState);
    }

    public static class Spreader extends VesselHeadBlockEntity.Spreader {
        private int east = -1;
        private int west = -1;
        private int north = -1;
        private int south = -1;
        private int above = -1;

        public Spreader(VesselHeadBlockEntity pHead, BlockPos pPos) {
            super(pHead, pPos);
        }

        private BlockPos getNeighbourPosition(int index) {
            return switch (index) {
                case 0 -> blockPos.east();
                case 1 -> blockPos.west();
                case 2 -> blockPos.north();
                case 3 -> blockPos.south();
                default -> throw new IllegalArgumentException("Invalid state");
            };
        }

        private Orientation getNeighbourOrientation(int index) {
            return switch (index) {
                case 0 -> Orientation.UpE;
                case 1 -> Orientation.UpW;
                case 2 -> Orientation.UpN;
                case 3 -> Orientation.UpS;
                default -> throw new IllegalArgumentException("Invalid state");
            };
        }

        private int getNeighbour(int index) {
            return switch (index) {
                case 0 -> east;
                case 1 -> west;
                case 2 -> north;
                case 3 -> south;
                default -> throw new IllegalArgumentException("Invalid state");
            };
        }

        private void setNeighbour(int index, int value) {
            switch (index) {
                case 0 -> east = value;
                case 1 -> west = value;
                case 2 -> north = value;
                case 3 -> south = value;
            }
        }

        private boolean canSupportStems(Level pLevel, BlockPos pPos, BlockState pState) {
            if (pState.is(BlockRegistry.vesselBlock.get())) {
                return pState.getValue(VesselBlock.AgeProperty) == VesselBlock.MaxAge;
            }

            return Block.isShapeFullBlock(pState.getCollisionShape(pLevel, pPos));
        }

        @Override
        public void spread(Level pLevel, RandomSource pRandom) {
            int index = pRandom.nextInt(4);
            int neighbour = getNeighbour(index);

            if (neighbour == -1) {
                tryPlaceStem(pLevel, pRandom, index);
                return;
            }

            BlockState blockState = pLevel.getBlockState(blockPos);
            int age = blockState.getValue(VesselBlock.AgeProperty);

            if (pRandom.nextInt(0, age + 1) > 0) {
                propagate(pLevel, pRandom, neighbour);
            } else if (age < VesselBlock.MaxAge) {
                grow(pLevel);
            } else if (above == -1) {
                placeAbove(pLevel);
            } else {
                propagate(pLevel, pRandom, above);
            }
        }

        private void grow(Level pLevel) {
            BlockState blockState = pLevel.getBlockState(blockPos);
            int age = blockState.getValue(VesselBlock.AgeProperty);

            pLevel.setBlock(blockPos, blockState.setValue(VesselBlock.AgeProperty, age + 1), 3);
        }

        private void propagate(Level pLevel, RandomSource pRandom, int reference) {
            VesselHeadBlockEntity.Spreader part = head.parts.get(reference);
            part.spread(pLevel, pRandom);
        }

        private void placeAbove(Level pLevel) {
            BlockPos targetPos = blockPos.above();
            BlockState targetBlockState = pLevel.getBlockState(targetPos);

            if (!targetBlockState.isCollisionShapeFullBlock(pLevel, targetPos)) {
                pLevel.destroyBlock(targetPos, true);
            }

            BlockState vesselBlockState = BlockRegistry.vesselBlock.get().defaultBlockState()
                .setValue(VesselBlock.AlignmentProperty, Alignment.Middle);

            pLevel.setBlock(targetPos, vesselBlockState, 3);

            Spreader part = new Spreader(this.head, targetPos);
            above = head.addPart(part);
        }

        private void tryPlaceStem(Level pLevel, RandomSource pRandom, int index) {
            BlockPos targetPos = getNeighbourPosition(index);
            BlockState targetBlockState = pLevel.getBlockState(targetPos);

            if (!targetBlockState.isAir()) {
                if (pRandom.nextInt(0, 3) > 0) {
                    return;
                }

                pLevel.destroyBlock(targetPos, true);
            }

            BlockPos targetSupportingPos = targetPos.below();
            BlockState targetSupportingBlockState = pLevel.getBlockState(targetSupportingPos);

            if (!canSupportStems(pLevel, targetSupportingPos, targetSupportingBlockState)) {
                return;
            }

            Orientation stemOrientation = getNeighbourOrientation(index);
            BlockState stemBlockState = BlockRegistry.stemBlock.get().defaultBlockState()
                .setValue(BranchStemBlock.RootedProperty, true)
                .setValue(BranchStemBlock.OrientationProperty, stemOrientation);

            pLevel.setBlock(targetPos, stemBlockState, 3);

            BranchStemBlock.Spreader part = new BranchStemBlock.Spreader(this.head, targetPos);
            int reference = this.head.addPart(part);

            setNeighbour(index, reference);
        }
    }
}
