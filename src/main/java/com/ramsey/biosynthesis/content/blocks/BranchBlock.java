package com.ramsey.biosynthesis.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BranchBlock extends Block {
    public static final EnumProperty<BranchSide> ConnectedFrontProperty = EnumProperty.create("front", BranchSide.class);
    public static final EnumProperty<BranchSide> ConnectedRightProperty = EnumProperty.create("right", BranchSide.class);
    public static final EnumProperty<BranchSide> ConnectedLeftProperty = EnumProperty.create("left", BranchSide.class);
    public static final EnumProperty<BranchOrientation> OrientationProperty = EnumProperty.create("orientation", BranchOrientation.class);
    public static final DirectionProperty SideProperty = DirectionProperty.create("side", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

    public BranchBlock(Properties pProperties) {
        super(pProperties);

        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(ConnectedFrontProperty, BranchSide.None)
                .setValue(ConnectedRightProperty, BranchSide.None)
                .setValue(ConnectedLeftProperty, BranchSide.None)
                .setValue(OrientationProperty, BranchOrientation.Horizontal)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);

        pBuilder.add(ConnectedFrontProperty);
        pBuilder.add(ConnectedRightProperty);
        pBuilder.add(ConnectedLeftProperty);
        pBuilder.add(OrientationProperty);
        pBuilder.add(SideProperty);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return super.getShape(pState, pLevel, pPos, pContext);
    }

    public enum BranchSide implements StringRepresentable {
        Up("up"),
        Flat("flat"),
        None("none");

        private final String name;

        BranchSide(String state) {
            this.name = state;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }

    public enum BranchOrientation implements StringRepresentable {
        Up("up"),
        Horizontal("horizontal"),
        Down("down");

        private final String name;

        BranchOrientation(String state) {
            this.name = state;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
