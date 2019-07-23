package i.gishreloaded.gishcode.xray;

import net.minecraft.util.math.BlockPos;

public class XRayBlock {

    private BlockPos blockPos;
    private XRayData xRayData;

    public XRayBlock(BlockPos blockPos, XRayData xRayData){
        this.blockPos = blockPos;
        this.xRayData = xRayData;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public XRayData getxRayData() {
        return xRayData;
    }
}
