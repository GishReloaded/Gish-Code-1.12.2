package i.gishreloaded.gishcode.utils;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

public class EntityBot {
	
	private String name;
	private int id;
	private UUID uuid;
	private boolean invisible;
	private boolean ground;
	
	public EntityBot(EntityPlayer player) {
		this.name = String.valueOf(player.getGameProfile().getName());
		this.id = player.getEntityId();
		this.uuid = player.getGameProfile().getId();
		this.invisible = player.isInvisible();
		this.ground = player.onGround;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public boolean isInvisible() {
		return invisible;
	}
	
	public boolean isGround() {
		return ground;
	}
}
