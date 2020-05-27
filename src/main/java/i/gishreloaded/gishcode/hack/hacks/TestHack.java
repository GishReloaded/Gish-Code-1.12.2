package i.gishreloaded.gishcode.hack.hacks;

import java.util.ArrayList;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.system.Connection.Side;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class TestHack extends Hack{
	
	public TestHack() {
		super("TestHack", HackCategory.ANOTHER);
	}
	
//	@Override
//	public void onClientTick(ClientTickEvent event) {
//		PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer(0));
//		packetBuffer.writeByte(36);
//		packetBuffer.writeByte(2);
//		packetBuffer.writeInt(Wrapper.INSTANCE.player().getEntityId());
//        CPacketCustomPayload packet = new CPacketCustomPayload("", packetBuffer);
//        Wrapper.INSTANCE.sendPacket(packet);
//		super.onClientTick(event);
//	}
}
