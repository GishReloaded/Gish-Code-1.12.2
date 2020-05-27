package i.gishreloaded.gishcode.hack.hacks;

import java.util.ArrayList;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.BlockUtils;
import i.gishreloaded.gishcode.utils.EntityBot;

import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.system.Connection.Side;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AntiBot extends Hack{
	
	public static ArrayList<EntityBot> bots = new ArrayList<EntityBot>();
	
	public NumberValue level;
	public NumberValue tick;
	
	public BooleanValue ifInAir;
	public BooleanValue ifGround;
	public BooleanValue ifZeroHealth;
	public BooleanValue ifInvisible;
	public BooleanValue ifEntityId;
	public BooleanValue ifTabName;
	public BooleanValue ifPing;
	
	public BooleanValue remove;
	public BooleanValue gwen;
	
	public AntiBot() {
		super("AntiBot", HackCategory.COMBAT);
		
		level = new NumberValue("AILevel", 0.0D, 0.0D, 6.0D);
		tick = new NumberValue("TicksExisted", 0.0D, 0.0D, 999.0D);
		
		ifInvisible = new BooleanValue("Invisible", false);
		ifInAir = new BooleanValue("InAir", false);
		ifGround = new BooleanValue("OnGround", false);
		ifZeroHealth = new BooleanValue("ZeroHealth", false);
		ifEntityId = new BooleanValue("EntityId", false);
		ifTabName = new BooleanValue("OutTabName", false);
		ifPing = new BooleanValue("PingCheck", false);
		
		remove = new BooleanValue("RemoveBots", false);
		gwen = new BooleanValue("Gwen", false);
		
		this.addValue(level, tick, remove, gwen, ifInvisible, ifInAir, ifGround, ifZeroHealth, ifEntityId, ifTabName, ifPing);
	}
	
	@Override
	public String getDescription() {
		return "Ignore/Remove anti cheat bots.";
	}
	
	@Override
	public void onEnable() {
		bots.clear();
		super.onEnable();
	}
	
	@Override
	public boolean onPacket(Object packet, Side side) {
		if(gwen.getValue()) {
			for (Object entity : Utils.getEntityList()) {
				if (packet instanceof SPacketSpawnPlayer) {
					SPacketSpawnPlayer spawn = (SPacketSpawnPlayer) packet;
						double posX = spawn.getX() / 32.0D;
						double posY = spawn.getY() / 32.0D;
						double posZ = spawn.getZ() / 32.0D;
					  
						double difX = Wrapper.INSTANCE.player().posX - posX;
						double difY = Wrapper.INSTANCE.player().posY - posY;
						double difZ = Wrapper.INSTANCE.player().posZ - posZ;

						double dist = Math.sqrt(difX * difX + difY * difY + difZ * difZ);
					  	if ((dist <= 17.0D) && (posX != Wrapper.INSTANCE.player().posX) && (posY != Wrapper.INSTANCE.player().posY) && (posZ != Wrapper.INSTANCE.player().posZ)) {
						  	return false;
					  	}
				}
			}
		}
		return true;
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if (tick.getValue().intValue() > 0.0) {
			bots.clear();
        }
		for(Object object : Utils.getEntityList()) {
			if(object instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase) object;
				if (!(entity instanceof EntityPlayerSP) 
						&& entity instanceof EntityPlayer
						&& !(entity instanceof EntityArmorStand)
						&& entity != Wrapper.INSTANCE.player()) {
					EntityPlayer bot = (EntityPlayer)entity;
					if(!isBotBase(bot)) {
						int ailevel = level.getValue().intValue();
						boolean isAi = ailevel > 0.0;
						if(isAi && botPercentage(bot) > ailevel) {
							addBot(bot);
						} 
						else if(!isAi && botCondition(bot)) {
							addBot(bot);
						}
					} else {
						addBot(bot);
						if(remove.getValue()) {
							Wrapper.INSTANCE.world().removeEntity(bot);
						}
					}
		        }
			}
		}
		super.onClientTick(event);
	}
	
	void addBot(EntityPlayer player) {
		if(!isBot(player)) {
			bots.add(new EntityBot(player));
		}
	}
	
	public static boolean isBot(EntityPlayer player) {
		for(EntityBot bot : bots) {
			if(bot.getName().equals((player.getName()))){
				if(player.isInvisible() != bot.isInvisible()) {
					return player.isInvisible();
				}
				return true;
			}
			else {
				if(bot.getId() == player.getEntityId() 
						|| bot.getUuid().equals(player.getGameProfile().getId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	boolean botCondition(EntityPlayer bot) {
		int percentage = 0;
		if (tick.getValue().intValue() > 0.0 && bot.ticksExisted < tick.getValue().intValue()) {
			return true;
        }
		if (ifInAir.getValue()
				&& bot.isInvisible() 
				&& bot.motionY == 0.0 
				&& bot.posY > Wrapper.INSTANCE.player().posY + 1.0 
				&& BlockUtils.isBlockMaterial(new BlockPos(bot).down(), Blocks.AIR)) {
			return true;
        }
		if (ifGround.getValue()
				&& bot.motionY == 0.0 
        		&& !bot.collidedVertically 
        		&& bot.onGround 
        		&& bot.posY % 1.0 != 0.0 
        		&& bot.posY % 0.5 != 0.0) {
			return true;
        }
		if(ifZeroHealth.getValue() && bot.getHealth() <= 0) {
			return true;
		}
		if (ifInvisible.getValue() && bot.isInvisible()) {
			return true;
		}
		if (ifEntityId.getValue() && bot.getEntityId() >= 1000000000) {
			return true;
        }
		if(ifTabName.getValue()) {
		boolean isTabName = false;
			for (NetworkPlayerInfo npi : Wrapper.INSTANCE.mc().getConnection().getPlayerInfoMap()) {
        		if(npi.getGameProfile() != null) {
        			if(npi.getGameProfile().getName().contains(bot.getName())) {
        				isTabName = true;	
        			}
        		}
			}
			if(!isTabName) {
				return true;
			}
		}
		return false;
	}
	
	int botPercentage(EntityPlayer bot) {
		int percentage = 0;
		if (tick.getValue().intValue() > 0.0 && bot.ticksExisted < tick.getValue().intValue()) {
			percentage++;
        }
		if (ifInAir.getValue()
				&& bot.isInvisible() 
				&& bot.posY > Wrapper.INSTANCE.player().posY + 1.0 
				&& BlockUtils.isBlockMaterial(new BlockPos(bot).down(), Blocks.AIR)) {
			percentage++;
        }
		if (ifGround.getValue()
				&& bot.motionY == 0.0 
        		&& !bot.collidedVertically 
        		&& bot.onGround 
        		&& bot.posY % 1.0 != 0.0 
        		&& bot.posY % 0.5 != 0.0) {
			percentage++;
        }
		if(ifZeroHealth.getValue() && bot.getHealth() <= 0) {
			percentage++;
		}
		if (ifInvisible.getValue() && bot.isInvisible()) {
			percentage++;
		}
		if (ifEntityId.getValue() && bot.getEntityId() >= 1000000000) {
			percentage++;
        }
		if(ifTabName.getValue()) {
		boolean isTabName = false;
			for (NetworkPlayerInfo npi : Wrapper.INSTANCE.mc().getConnection().getPlayerInfoMap()) {
        		if(npi.getGameProfile() != null) {
        			if(npi.getGameProfile().getName().contains(bot.getName())) {
        				isTabName = true;	
        			}
        		}
			}
			if(!isTabName) {
				percentage++;
			}
		}
		return percentage;
	}
	
	boolean isBotBase(EntityPlayer bot) {
		if(isBot(bot)) {
			return true;
		}
		if(bot.getGameProfile() == null) {
			return true;
		}
		GameProfile botProfile = bot.getGameProfile();
		if(bot.getUniqueID() == null) {
			return true;
		}
		UUID botUUID = bot.getUniqueID();
		if(botProfile.getName() == null) {
			return true;
		}
		String botName = botProfile.getName();
		if(botName.contains("Body #") || botName.contains("NPC") 
				|| botName.equalsIgnoreCase(Utils.getEntityNameColor(bot))) {
			return true;
		}
		return false;
	}
}
