package i.gishreloaded.gishcode.hack.hacks;

import java.util.ArrayDeque;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.system.Wrapper;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class StorageESP extends Hack{

	private int maxChests = 1000;
	public boolean shouldInform = true;
	private TileEntityChest openChest;
	private ArrayDeque<TileEntityChest> emptyChests = new ArrayDeque<TileEntityChest>();
	private ArrayDeque<TileEntityChest> nonEmptyChests = new ArrayDeque<TileEntityChest>();
	
	public StorageESP() {
		super("StorageESP", HackCategory.VISUAL);
	}
	
	@Override
	public void onEnable() {
		shouldInform = true;
		emptyChests.clear();
		nonEmptyChests.clear();
		super.onEnable();
	}
	
	@Override
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		int chests = 0;
		for(int i = 0; i < Wrapper.INSTANCE.world().loadedTileEntityList.size(); i++)
		{
			TileEntity tileEntity = Wrapper.INSTANCE.world().loadedTileEntityList.get(i);
			if(chests >= maxChests) {
				break;
			}
			
			if(tileEntity instanceof TileEntityChest){
				chests++;
				TileEntityChest chest = (TileEntityChest)tileEntity;
				boolean trapped = chest.getChestType() == BlockChest.Type.TRAP;
				
				if(emptyChests.contains(tileEntity)) {
					RenderUtils.drawBlockESP(chest.getPos(), 0.25F, 0.25F, 0.25F);
				} else if(nonEmptyChests.contains(tileEntity)) {
					
					if(trapped) {
						RenderUtils.drawBlockESP(chest.getPos(), 0, 1, 0);
					} else {
						RenderUtils.drawBlockESP(chest.getPos(), 1, 0F, 0);
					}
					
				} else if(trapped) {
					RenderUtils.drawBlockESP(chest.getPos(), 0, 1F, 0);
				} else {
					RenderUtils.drawBlockESP(chest.getPos(), 1, 0f, 0);
				}
				
				if(trapped) {
					RenderUtils.drawBlockESP(chest.getPos(), 0, 1F, 0);
				} else {
					RenderUtils.drawBlockESP(chest.getPos(), 1, 0F, 0);
				}
				
			} else if(tileEntity instanceof TileEntityEnderChest) {
				chests++;
				RenderUtils.drawBlockESP(((TileEntityEnderChest)tileEntity).getPos(), 1, 0, 1);
			}
		}
		
		for(int i = 0; i < Wrapper.INSTANCE.world().loadedEntityList.size(); i++){
			Entity entity = Wrapper.INSTANCE.world().loadedEntityList.get(i);
			if(chests >= maxChests) {
				break;
			}
			if(entity instanceof EntityMinecartChest){
				chests++;
				RenderUtils.drawBlockESP(((EntityMinecartChest)entity).getPosition(), 1, 1, 1);
			}
		}
		
		if(chests >= maxChests && shouldInform){
			ChatUtils.warning("To prevent lag, it will only show the first " + maxChests + " chests.");
			shouldInform = false;
		} else if(chests < maxChests) {
			shouldInform = true;
		}
		super.onRenderWorldLast(event);
	}
}
