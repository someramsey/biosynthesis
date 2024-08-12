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
        private static final int UNSET = -1;

        private int east = UNSET;
        private int west = UNSET;
        private int north = UNSET;
        private int south = UNSET;
        private int above = UNSET;

        private int age = 0;

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

        private boolean canMature() {
            return east != UNSET && head.parts.get(east) instanceof Spreader
                && west != UNSET && head.parts.get(west) instanceof Spreader
                && north != UNSET && head.parts.get(north) instanceof Spreader
                && south != UNSET && head.parts.get(south) instanceof Spreader;
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
            int index = pRandom.nextInt(0, 4);
            int neighbour = getNeighbour(index);

            if (neighbour == UNSET) {
                placeStem(pLevel, pRandom, index);
                return;
            }

            if (age == VesselBlock.MaxAge) {
                spreadMature(pLevel, pRandom, neighbour, index);
            } else if (age == VesselBlock.MaxAge - 1) {
                spreadPremature(pLevel, pRandom, index, neighbour);
            } else {
                spreadImmature(pLevel, pRandom, index, neighbour);
            }
        }

        private void spreadMature(Level pLevel, RandomSource pRandom, int neighbour, int index) {
            if (pRandom.nextInt(0, 3) == 0) {
                propagateAboveSafe(pLevel, pRandom);
            } else {
                propagateNeighbourSafe(pLevel, pRandom, neighbour, index);
            }
        }

        private void spreadPremature(Level pLevel, RandomSource pRandom, int index, int neighbour) {
            if (canMature()) {
                grow(pLevel);
            } else {
                propagateNeighbourSafe(pLevel, pRandom, neighbour, index);
            }
        }

        private void spreadImmature(Level pLevel, RandomSource pRandom, int index, int neighbour) {
            if (pRandom.nextInt(0, age + 1) == 0) {
                grow(pLevel);
            } else {
                propagateNeighbourSafe(pLevel, pRandom, neighbour, index);
            }
        }

        private void propagateAboveSafe(Level pLevel, RandomSource pRandom) {
            if (above != UNSET) {
                if (propagateNeighbour(pLevel, pRandom, above)) {
                    above = UNSET;
                }
            } else if (canMature()) {
                placeAbove(pLevel);
            }
        }

        private void propagateNeighbourSafe(Level pLevel, RandomSource pRandom, int index, int neighbour) {
            if (propagateNeighbour(pLevel, pRandom, neighbour)) {
                setNeighbour(index, UNSET);
            }
        }

        private void grow(Level pLevel) {
            BlockState blockState = pLevel.getBlockState(blockPos);
            System.out.println(blockState);
            if (!(blockState.getBlock() instanceof VesselBlock)) { //Corrupted state
                head.parts.remove(this);
                return;
            }

            pLevel.setBlock(blockPos, blockState.setValue(VesselBlock.AgeProperty, ++age), 3);
        }

        private boolean propagateNeighbour(Level pLevel, RandomSource pRandom, int reference) {
            VesselHeadBlockEntity.Spreader part = head.parts.get(reference);

            if (part == null) {
                return true;
            }

            part.spread(pLevel, pRandom);
            return false;
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

        private void placeStem(Level pLevel, RandomSource pRandom, int index) {
            BlockPos targetPos = getNeighbourPosition(index);
            BlockState targetBlockState = pLevel.getBlockState(targetPos);

            if (!targetBlockState.isAir()) {
                if (pRandom.nextInt(0, 3) > 0) {
                    return;
                }

                pLevel.destroyBlock(targetPos, true);
            }

            BlockPos targetBelowPos = targetPos.below();
            BlockState targetBelowState = pLevel.getBlockState(targetBelowPos);

            if (!canSupportStems(pLevel, targetBelowPos, targetBelowState)) {
                return;
            }

            Orientation stemOrientation = getNeighbourOrientation(index);
            BlockState stemBlockState = BlockRegistry.stemBlock.get().defaultBlockState()
                .setValue(BranchStemBlock.RootedProperty, true)
                .setValue(BranchStemBlock.OrientationProperty, stemOrientation);

            pLevel.setBlock(targetPos, stemBlockState, 3);

            BranchStemBlock.Spreader part = new BranchStemBlock.Spreader(this.head, targetPos, stemOrientation, true);
            int reference = this.head.addPart(part);

            setNeighbour(index, reference);
        }
    }
}
