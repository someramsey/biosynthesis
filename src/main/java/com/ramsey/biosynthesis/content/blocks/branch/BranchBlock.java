package com.ramsey.biosynthesis.content.blocks.branch;

import com.ramsey.biosynthesis.data.providers.block.common.branch.BranchBlockShapeProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BranchBlock extends Block {
    public static final EnumProperty<ConnectionState> ConnectedFrontProperty = EnumProperty.create("front", ConnectionState.class);
    public static final EnumProperty<ConnectionState> ConnectedRightProperty = EnumProperty.create("right", ConnectionState.class);
    public static final EnumProperty<ConnectionState> ConnectedLeftProperty = EnumProperty.create("left", ConnectionState.class);
    public static final EnumProperty<Orientation> OrientationProperty = EnumProperty.create("orientation", Orientation.class);

    public BranchBlock(Properties pProperties) {
        super(pProperties);

        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(ConnectedFrontProperty, ConnectionState.Flat)
                .setValue(ConnectedRightProperty, ConnectionState.None)
                .setValue(ConnectedLeftProperty, ConnectionState.None)
                .setValue(OrientationProperty, Orientation.UpN)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);

        pBuilder.add(ConnectedFrontProperty);
        pBuilder.add(ConnectedRightProperty);
        pBuilder.add(ConnectedLeftProperty);
        pBuilder.add(OrientationProperty);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return BranchBlockShapeProvider.buildShape(pState);
    }

    public enum ConnectionState implements StringRepresentable {
        Up("up"),
        Flat("flat"),
        Down("down"),
        None("none");

        private final String name;

        ConnectionState(String state) {
            this.name = state;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }

}
