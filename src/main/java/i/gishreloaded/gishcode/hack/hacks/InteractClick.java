package i.gishreloaded.gishcode.hack.hacks;

import org.lwjgl.input.Mouse;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.EnemyManager;
import i.gishreloaded.gishcode.managers.FriendManager;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.managers.XRayManager;

import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class InteractClick extends Hack{

	public InteractClick() {
		super("InteractClick", HackCategory.COMBAT);
	}
	
	@Override
	public String getDescription() {
		return "Left - Add to Enemys, Rigth - Add to Friends, Wheel - Remove from All.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		RayTraceResult object = Wrapper.INSTANCE.mc().objectMouseOver;
		if(object == null) {
			return;
		}
		if(object.typeOfHit == RayTraceResult.Type.ENTITY) {
			Entity entity = object.entityHit;
			if(entity instanceof EntityPlayer && !(entity instanceof EntityArmorStand) && !Wrapper.INSTANCE.player().isDead && Wrapper.INSTANCE.player().canEntityBeSeen(entity)){
				EntityPlayer player = (EntityPlayer)entity;
				String ID = Utils.getPlayerName(player);
				if(Mouse.isButtonDown(1) && Wrapper.INSTANCE.mc().currentScreen == null) 
				{
					FriendManager.addFriend(ID);
				}
				else if(Mouse.isButtonDown(0) && Wrapper.INSTANCE.mc().currentScreen == null) 
				{
					EnemyManager.addEnemy(ID);
				}
				else if(Mouse.isButtonDown(2) && Wrapper.INSTANCE.mc().currentScreen == null) 
				{
					EnemyManager.removeEnemy(ID);
					FriendManager.removeFriend(ID);
				}
			}
    	}
		super.onClientTick(event);
	}

}
