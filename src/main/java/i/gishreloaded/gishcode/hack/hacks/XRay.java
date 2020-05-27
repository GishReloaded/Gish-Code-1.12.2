package i.gishreloaded.gishcode.hack.hacks;

import java.util.LinkedList;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.XRayManager;
import i.gishreloaded.gishcode.utils.BlockUtils;

import i.gishreloaded.gishcode.utils.TimerUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import i.gishreloaded.gishcode.xray.XRayBlock;
import i.gishreloaded.gishcode.xray.XRayData;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class XRay extends Hack{

    public NumberValue distance;
	public NumberValue delay;

	public TimerUtils timer;
	
	LinkedList<XRayBlock> blocks = new LinkedList<XRayBlock>();
	
	public XRay() {
		super("XRay", HackCategory.VISUAL);
		distance = new NumberValue("Distance", 50D, 4D, 100D);
		delay = new NumberValue("UpdateDelay", 100D, 0D, 300D);
		timer = new TimerUtils();
		this.addValue(distance, delay);
	}

	@Override
	public String getDescription() {
		return "Allows you to see blocks through walls.";
	}
	
	@Override
	public void onEnable() {
		blocks.clear();
	}

	@Override
	public void onClientTick(ClientTickEvent event) {
		int distance = this.distance.getValue().intValue();
		if(!timer.isDelay((long) (delay.getValue().intValue() * 10))) {
			return;
		}
		blocks.clear();
		for(XRayData data : XRayManager.xrayList) {
			for (BlockPos blockPos : BlockUtils.findBlocksNearEntity(Wrapper.INSTANCE.player(), data.getId(), data.getMeta(), distance)) {
				XRayBlock xRayBlock = new XRayBlock(blockPos, data);
				blocks.add(xRayBlock);
			}
		}
		timer.setLastMS();
		super.onClientTick(event);
	}

	@Override
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		RenderUtils.drawXRayBlocks(blocks, event.getPartialTicks());
		super.onRenderWorldLast(event);
	}
}
