package com.ramsey.biosynthesis.content.blocks.vessel;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VesselHeadBlockEntity extends VesselBlockEntity {
    public final ArrayList<Spreader> parts = new ArrayList<>();
    public final Spreader base;

    public VesselHeadBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        super(pPos, pState);
        this.base = new VesselBlock.Spreader(this, pPos);
    }

    public abstract static class Spreader {
        public final VesselHeadBlockEntity head;
        public final BlockPos blockPos;
        public final int[] neighbours;
        public int availableNeighbours;

        public abstract void spread(Level pLevel, RandomSource pRandom);

        public Spreader(VesselHeadBlockEntity pHead, BlockPos pBlockPos, int pNeighbourCount) {
            this.head = pHead;
            this.blockPos = pBlockPos;
            this.neighbours = new int[pNeighbourCount];
        }

        protected boolean hasUnsetNeighbours() {
            return availableNeighbours < neighbours.length;
        }

        protected void connect(Spreader pNeighbour) {
            head.parts.add(pNeighbour);

            int neighbourIndex = head.parts.size() - 1;
            pNeighbour.neighbours[pNeighbour.availableNeighbours++] = neighbourIndex;
        }

        protected void propagate(RandomSource pRandom) {
            int index = pRandom.nextInt(0, availableNeighbours);
            int reference = neighbours[index];

            Spreader neighbour = head.parts.get(reference);
            neighbour.spread(head.getLevel(), pRandom);
        }
    }
}
