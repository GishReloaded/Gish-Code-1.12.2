package i.gishreloaded.gishcode.hack.hacks;

import java.util.ArrayList;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.system.Connection.Side;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class FakeCreative extends Hack {
	
	public GameType gameType;
	public BooleanValue showItemsId;
	
	public FakeCreative() {
		super("FakeCreative", HackCategory.ANOTHER);
		
		showItemsId = new BooleanValue("ShowItemsID", true);
		this.addValue(showItemsId);
	}
	
	@Override
	public void onGuiOpen(GuiOpenEvent event) {
		if(event.getGui() instanceof GuiContainerCreative)
			event.setGui(new i.gishreloaded.gishcode.gui.GuiContainerCreative(Wrapper.INSTANCE.player()));
		super.onGuiOpen(event);
	}
	
	@Override
	public void onDisable() {
		if(gameType == null) return;
		Wrapper.INSTANCE.controller().setGameType(gameType);
		gameType = null;
		super.onDisable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(Wrapper.INSTANCE.controller().getCurrentGameType() == GameType.CREATIVE) return;
		gameType = Wrapper.INSTANCE.controller().getCurrentGameType();
		Wrapper.INSTANCE.controller().setGameType(GameType.CREATIVE);
		super.onClientTick(event);
	}
}
