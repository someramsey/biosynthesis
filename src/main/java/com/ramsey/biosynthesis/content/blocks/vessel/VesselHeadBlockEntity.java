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

    public int addPart(Spreader pSpreader) {
        this.parts.add(pSpreader);

        int order = this.parts.size() - 1;
        pSpreader.id = order;
        return order;
    }

    public void replacePart(Spreader pSpreader, int pIndex) {
        this.parts.set(pIndex, pSpreader);
        pSpreader.id = pIndex;
    }

    public abstract static class Spreader {
        protected static final int UNSET = -1;
        protected static final int OCCUPIED = -2;

        public int id = UNSET;
        public final VesselHeadBlockEntity head;
        public final BlockPos blockPos;

        public abstract void spread(Level pLevel, RandomSource pRandom);

        public Spreader(VesselHeadBlockEntity pHead, BlockPos pBlockPos) {
            this.head = pHead;
            this.blockPos = pBlockPos;
        }
    }
}
