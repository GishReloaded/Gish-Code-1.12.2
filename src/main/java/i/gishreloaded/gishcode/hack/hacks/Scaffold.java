package i.gishreloaded.gishcode.hack.hacks;

import java.util.Random;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.BlockData;
import i.gishreloaded.gishcode.utils.BlockUtils;

import i.gishreloaded.gishcode.utils.RobotUtils;
import i.gishreloaded.gishcode.utils.TimerUtils;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Scaffold extends Hack{
	
	public ModeValue mode;
	
	public TimerUtils timer;
	public BlockData blockData;
	boolean isBridging = false;
	BlockPos blockDown = null;
	public static float[] facingCam = null;
	float startYaw = 0;
	float startPitch = 0;
	
	  
	public Scaffold() {
		super("Scaffold", HackCategory.PLAYER);
		
		this.mode = new ModeValue("Mode", new Mode("AAC", false), new Mode("Simple", true));
		
		this.addValue(mode);
		
		this.timer = new TimerUtils();
	}
	
	@Override
	public String getDescription() {
		return "Automatically places blocks below your feet.";
	}
	
	@Override
	public void onDisable() {
		facingCam = null;
		super.onDisable();
	}
	
	@Override
	public void onEnable() {
		blockDown = null;
		facingCam = null;
		isBridging = false;
		startYaw = 0;
		startPitch = 0;
		if(mode.getMode("AAC").isToggled() && Wrapper.INSTANCE.mcSettings().keyBindBack.isKeyDown()) {
			KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindBack.getKeyCode(), false);
		}
		super.onEnable();
	}
	
    @Override
    public void onClientTick(ClientTickEvent event) {
    	if(mode.getMode("AAC").isToggled()) {
    		AAC();
    	} else if(mode.getMode("Simple").isToggled()){
    		Simple();
    	}
    	super.onClientTick(event);
    }
    
	@Override
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		if(blockDown != null) {
			RenderUtils.drawBlockESP(blockDown, 1F, 1F, 1F);
			if(mode.getMode("AAC").isToggled()) {
				BlockPos blockDown2 = new BlockPos(Wrapper.INSTANCE.player()).down();
				BlockPos blockDown3 = new BlockPos(Wrapper.INSTANCE.player()).down();
				if(Wrapper.INSTANCE.player().getHorizontalFacing() == EnumFacing.EAST) {
					blockDown2 = new BlockPos(Wrapper.INSTANCE.player()).down().west();
					blockDown3 = new BlockPos(Wrapper.INSTANCE.player()).down().west(2);
				} 
				else if(Wrapper.INSTANCE.player().getHorizontalFacing() == EnumFacing.NORTH) {
					blockDown2 = new BlockPos(Wrapper.INSTANCE.player()).down().south();
					blockDown3 = new BlockPos(Wrapper.INSTANCE.player()).down().south(2);
				}
				else if(Wrapper.INSTANCE.player().getHorizontalFacing() == EnumFacing.SOUTH) {
					blockDown2 = new BlockPos(Wrapper.INSTANCE.player()).down().north();
					blockDown3 = new BlockPos(Wrapper.INSTANCE.player()).down().north(2);
				}
				else if(Wrapper.INSTANCE.player().getHorizontalFacing() == EnumFacing.WEST) {
					blockDown2 = new BlockPos(Wrapper.INSTANCE.player()).down().east();
					blockDown3 = new BlockPos(Wrapper.INSTANCE.player()).down().east(2);
				}
				RenderUtils.drawBlockESP(blockDown2, 1F, 0F, 0F);
				RenderUtils.drawBlockESP(blockDown3, 1F, 0F, 0F);
			}
		}
		super.onRenderWorldLast(event);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void onCameraSetup(CameraSetup event) {
		if(mode.getMode("AAC").isToggled() && event.getEntity() == Wrapper.INSTANCE.player()) {
			if(startYaw == 0 || startPitch == 0) {
				return;
			}
			event.setYaw(startYaw);
			event.setPitch(startPitch - 70);
			facingCam = new float[] { startYaw - 180, startPitch - 70};
		}
		super.onCameraSetup(event);
	}
	
	void AAC() {
		EntityPlayerSP player = Wrapper.INSTANCE.player();
		int oldSlot = -1;
		if(!check()) {
			if(isBridging) {
				KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindSneak.getKeyCode(),
						BlockUtils.isBlockMaterial(new BlockPos(player).down(), Blocks.AIR));
				isBridging = false;
				if(oldSlot != -1) {
					player.inventory.currentItem = oldSlot;
				}
			}
			startYaw = 0;
			startPitch = 0;
			facingCam = null;
			blockDown = null;
			return;
		}
		startYaw = Wrapper.INSTANCE.player().rotationYaw;
		startPitch = Wrapper.INSTANCE.player().rotationPitch;
		KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindRight.getKeyCode(), false);
		KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindLeft.getKeyCode(), false);
		blockDown = new BlockPos(player).down();
		float r1 = new Random().nextFloat();
		if(r1 == 1.0f) r1--;
		int newSlot = findSlotWithBlock();
        if (newSlot == -1) return;
        oldSlot = player.inventory.currentItem;
        player.inventory.currentItem = newSlot;
		player.rotationPitch = Utils.updateRotation(player.rotationPitch, (82.0f - r1), 15.0f);
    	int currentCPS = Utils.random(3, 4);
		if(timer.isDelay(1000 / currentCPS)) {
			RobotUtils.clickMouse(1);
			Utils.swingMainHand();
			timer.setLastMS();
		}
        isBridging = true;
        KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindSneak.getKeyCode(), 
        		BlockUtils.isBlockMaterial(new BlockPos(player).down(), Blocks.AIR));
	}
	
	void Simple() {
		blockDown = new BlockPos(Wrapper.INSTANCE.player()).down();
        if (!BlockUtils.getBlock(blockDown).getMaterial(BlockUtils.getBlock(blockDown).getDefaultState()).isReplaceable()) return;
		int newSlot = findSlotWithBlock();
        if (newSlot == -1) return;
        final int oldSlot = Wrapper.INSTANCE.inventory().currentItem;
        Wrapper.INSTANCE.inventory().currentItem = newSlot;
        Utils.placeBlockScaffold(blockDown);
        Wrapper.INSTANCE.inventory().currentItem = oldSlot;
	}
	
	public int findSlotWithBlock() {
		for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Wrapper.INSTANCE.inventory().getStackInSlot(i);
            if(stack != null && stack.getItem() instanceof ItemBlock) {
            	Block block = Block.getBlockFromItem(stack.getItem()).getDefaultState().getBlock();
            	if(block.isFullBlock(BlockUtils.getBlock(blockDown).getDefaultState()) && block != Blocks.SAND && block != Blocks.GRAVEL){
            		return i;
            	}
            }
        }
		return -1;
	}
    
    boolean check() {
    	RayTraceResult object = Wrapper.INSTANCE.mc().objectMouseOver;
		EntityPlayerSP player = Wrapper.INSTANCE.player();
		ItemStack stack = player.inventory.getCurrentItem();
		if(object == null || stack == null) {
			return false;
		}
		if(object.typeOfHit != RayTraceResult.Type.BLOCK) {
			return false;
		}
		if(player.rotationPitch <= 70 || !player.onGround || player.isOnLadder() || player.isInLava() || player.isInWater()) {
			return false;
		}
		if(!Wrapper.INSTANCE.mcSettings().keyBindBack.isKeyDown()) {
			return false;
		}
		return true;
    }
}
