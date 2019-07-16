package i.gishreloaded.gishcode.hack.hacks;

import java.util.ArrayList;
import java.util.Comparator;

import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.EnemyManager;
import i.gishreloaded.gishcode.managers.FriendManager;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.BlockUtils;
import i.gishreloaded.gishcode.utils.RenderUtils;
import i.gishreloaded.gishcode.utils.RobotUtils;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.ValidUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class FakeFace extends Hack{
	
	public BooleanValue packet;
	public EntityLivingBase target;
	public BlockPos faceBlock;
	
	public FakeFace() {
		super("FakeFace", HackCategory.COMBAT);
		
		packet = new BooleanValue("Packet", true);
		this.addValue(packet);
	}
	
	@Override
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		if(this.faceBlock != null) {
			RenderUtils.drawBlockESP(this.faceBlock, 0.0f, 1.0f, 1.0f);
		}
		super.onRenderWorldLast(event);
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		this.bucket();
		super.onClientTick(event);
	}
	
	@Override
	public void onDisable() {
		this.target = null;
		this.faceBlock = null;
		super.onDisable();
	}
	
	
	void bucket() {
		ItemStack currentItem = Wrapper.INSTANCE.player().inventory.getCurrentItem();
		if(currentItem == null) {
			return;
		}
		/*
		for(BlockPos block : BlockUtils.findBlocksNearEntity(Wrapper.INSTANCE.player(), Blocks.WATER, 3, 3, 4, 4, 4, 4)) {
			if(currentItem.getItem() == Items.BUCKET) {
				faceBlock = block;
				break;
			}
		}
		for(BlockPos block : BlockUtils.findBlocksNearEntity(Wrapper.INSTANCE.player(), Blocks.LAVA, 3, 3, 4, 4, 4, 4)) {
			if(currentItem.getItem() == Items.BUCKET) {
				faceBlock = block;
				break;
			}
		}
		*/
		if(this.faceBlock != null && currentItem.getItem() == Items.BUCKET && Wrapper.INSTANCE.mcSettings().keyBindUseItem.isKeyDown()) {
			if(Wrapper.INSTANCE.player().getDistanceSq(this.faceBlock) <= Wrapper.INSTANCE.mc().playerController.getBlockReachDistance()) {
				faceBlock(this.faceBlock);
				RobotUtils.clickMouse(1);
				this.faceBlock = null;
			}
		}
		
		this.target = this.getClosestEntity();
		if(this.target != null && currentItem.getItem() == Items.LAVA_BUCKET || currentItem.getItem() == Items.WATER_BUCKET) {
			if(Wrapper.INSTANCE.player().getDistance(this.target) <= Wrapper.INSTANCE.mc().playerController.getBlockReachDistance()) {
				this.faceBlock = new BlockPos(this.target);
				if(Wrapper.INSTANCE.mcSettings().keyBindUseItem.isKeyDown()) {
					faceBlock(this.faceBlock);
					RobotUtils.clickMouse(1);
					this.faceBlock = null;
				}
			}
		}
	}
	
	void faceBlock(BlockPos blockPos) {
		if(this.packet.getValue()) {
			BlockUtils.faceBlockPacket(blockPos);
		} else {
			BlockUtils.faceBlockClient(blockPos);
		}
	}
	
	public boolean check(EntityLivingBase entity) {
		if(entity instanceof EntityArmorStand) {
			return false;
		}
		if(ValidUtils.isValidEntity(entity)){
			return false;
		}
		if(!ValidUtils.isNoScreen()) {
			return false;
		}
		if(entity == Wrapper.INSTANCE.player()) {
			return false;
		}
		if(entity.isDead) {
			return false;
		}
		if(ValidUtils.isBot(entity)) {
			return false;
		}
		if(!ValidUtils.isFriendEnemy(entity)) {
			return false;
		}
    	if(!ValidUtils.isInvisible(entity)) {
			return false;
		}
		if(!ValidUtils.isTeam(entity)) {
			return false;
		}
		if(!Wrapper.INSTANCE.player().canEntityBeSeen(entity)) {
			return false;
		}
		return true;
    }
	
	EntityLivingBase getClosestEntity(){
		EntityLivingBase closestEntity = null;
 		for (Object o : Wrapper.INSTANCE.world().loadedEntityList) {
 			if(o instanceof EntityLivingBase && !(o instanceof EntityArmorStand)) {
 				EntityLivingBase entity = (EntityLivingBase)o;
 				if(check(entity)) {
 					if(closestEntity == null || Wrapper.INSTANCE.player().getDistance(entity) < Wrapper.INSTANCE.player().getDistance(closestEntity)) {
 						closestEntity = entity;
 					}
 				}
 			}
 		}
 		return closestEntity;
 	}
	
	
	
}
