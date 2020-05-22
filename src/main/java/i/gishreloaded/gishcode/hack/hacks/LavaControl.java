//package i.gishreloaded.gishcode.hack.hacks;
//
//import i.gishreloaded.gishcode.Wrapper;
//import i.gishreloaded.gishcode.hack.Hack;
//import i.gishreloaded.gishcode.hack.HackCategory;
//import i.gishreloaded.gishcode.managers.EnemyManager;
//import i.gishreloaded.gishcode.managers.FriendManager;
//import i.gishreloaded.gishcode.managers.HackManager;
//import i.gishreloaded.gishcode.utils.BlockUtils;
//import i.gishreloaded.gishcode.utils.ChatUtils;
//import i.gishreloaded.gishcode.utils.RenderUtils;
//import i.gishreloaded.gishcode.utils.Utils;
//import net.minecraft.block.Block;
//import net.minecraft.block.material.Material;
//import net.minecraft.entity.EntityLiving;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Items;
//import net.minecraft.item.ItemBow;
//import net.minecraft.item.ItemStack;
//import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.math.BlockPos;
//import net.minecraftforge.client.event.RenderWorldLastEvent;
//import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
//
//public class LavaControl extends Hack{
//	
//	public EntityLivingBase target;
//	
//	public int x = 0;
//	public int y = 0;
//	public int z = 0;
//	public BlockPos blockPrePlace;
//	public BlockPos blockPostPlace;
//	public BlockPos blockDown;
//	public BlockPos blockSouth;
//	public BlockPos blockWest;
//	public BlockPos blockNorth;
//	public BlockPos blockEast;
//	
//	//DEBUG
//	public LavaControl() {
//		super("LavaControl", HackCategory.COMBAT);
//	}
//	
//	@Override
//	public void onEnable() {
//		super.onEnable();
//	}
//	
//	@Override
//	public void onDisable() {
//		this.target = null;
//		super.onDisable();
//	}
//    
//	@Override
//	public void onRenderWorldLast(RenderWorldLastEvent event) {
//		if(this.target != null) {
//			RenderUtils.drawBlockESP(new BlockPos(this.x, this.y, this.z), 1.0f, 0.6f, 0.0f);
//		}
//		super.onRenderWorldLast(event);
//	}
//	
//	@Override
//	public void onClientTick(ClientTickEvent event) {
//		if(this.getClosestEntity() == null) {
//			this.onDisable();
//			return;
//		}
//		
//		this.target = this.getClosestEntity();
//		
//		if(Wrapper.INSTANCE.player().getDistance(this.target) <= Wrapper.INSTANCE.controller().getBlockReachDistance() 
//				&& Wrapper.INSTANCE.world().getBlockState(this.blockPostPlace).getMaterial() != Material.LAVA
//				&& Wrapper.INSTANCE.player().getHealth() > 0
//				&& this.target.onGround) {
//			this.put();	
//		}
//		
//		blockDown = new BlockPos(this.target).down();
//		
//		blockPrePlace = new BlockPos(this.target);
//		
//		blockPostPlace = new BlockPos(this.x, this.y, this.z);
//		
//		blockSouth = new BlockPos(this.x, this.y, this.z).south();
//		
//		blockNorth = new BlockPos(this.x, this.y, this.z).north();
//		
//		blockEast = new BlockPos(this.x, this.y, this.z).east();
//		
//		blockWest = new BlockPos(this.x, this.y, this.z).west();
//		
//		if(Wrapper.INSTANCE.player().getDistance(this.x, this.y, this.z) <= Wrapper.INSTANCE.controller().getBlockReachDistance()) {
//			if(Wrapper.INSTANCE.world().getBlockState(this.blockSouth).getMaterial() == Material.LAVA 
//					|| Wrapper.INSTANCE.world().getBlockState(this.blockNorth).getMaterial() == Material.LAVA
//						|| Wrapper.INSTANCE.world().getBlockState(this.blockEast).getMaterial() == Material.LAVA
//							|| Wrapper.INSTANCE.world().getBlockState(this.blockWest).getMaterial() == Material.LAVA) {
//								take();	
//			} 
//			else
//			if((int) this.target.posX - 1 != this.x || (int) this.target.posY != this.y || (int) this.target.posZ != this.z) {
//				take();
//			}
//			if(this.target.isDead) {
//				take();
//			} 
//		}
//		
//		
//		super.onClientTick(event);
//	}
//	
//	void put() {
//		ItemStack currentItem = Wrapper.INSTANCE.inventory().getCurrentItem();
//		if(currentItem == null) {
//			return;
//		}
//		if(currentItem.getItem() != Items.LAVA_BUCKET) {
//			return;
//		}
//		if(Wrapper.INSTANCE.world().getBlockState(this.blockPrePlace).getMaterial() != Material.AIR) {
//			return;
//		}
//		if(Wrapper.INSTANCE.world().getBlockState(this.blockDown).getMaterial() == Material.AIR) {
//			return;
//		}
//		
//		this.x = (int) this.target.posX - 1;
//		this.y = (int) this.target.posY;
//		this.z = (int) this.target.posZ;
//		
//		this.click(new BlockPos(this.target));
//		
//	}
//	
//	void take() {
//		ItemStack currentItem = Wrapper.INSTANCE.inventory().getCurrentItem();
//		if(currentItem == null) {
//			return;
//		}
//		if(currentItem.getItem() != Items.BUCKET) {
//			return;
//		}
//		if(Wrapper.INSTANCE.world().getBlockState(this.blockPostPlace).getMaterial() != Material.LAVA) {
//			return;
//		}
//		this.click(new BlockPos(this.target));
//	}
//	
//	void click(BlockPos pos) {
//		BlockUtils.placeBlockLegit(pos);
//		
//	}
//	
//	public boolean check(EntityLivingBase entity) {
//		if(Utils.checkEntity(entity) == null){
//			return false;
//		}
//		if(HackManager.getHack("NoScreenEvents").isToggled()) {
//			if(!Utils.checkScreen()) {
//				return false;
//			}
//		}
//		if(entity == Wrapper.INSTANCE.player()) {
//			return false;
//		}
//		if(!entity.isEntityAlive()) {
//			return false;
//		}
//		if(FriendManager.friendsList.contains(entity.getName())) {
//			return false;
//		}
//		if(HackManager.getHack("Enemys").isToggled()) {
//			if(!EnemyManager.enemysList.contains(entity.getName())) {
//				return false;
//			}
//		}
//		Hack targets = HackManager.getHack("Targets");
//		if(!targets.getBooleanValue("Invisibles").getValue()) {
//			if(entity.isInvisible()) {
//				return false;
//			}
//		}
//		Hack teams = HackManager.getHack("Teams");
//		if(teams.isToggled()) {
//			if(entity instanceof EntityPlayer) {
//				EntityPlayer player = (EntityPlayer) entity;
//				if(teams.getBooleanValue("Base").getValue()) {
//					if(player.getTeam() != null && Wrapper.INSTANCE.player().getTeam() != null) {
//						if(player.getTeam().isSameTeam(Wrapper.INSTANCE.player().getTeam())){
//							return false;
//						}
//					}
//				}
//				if(teams.getBooleanValue("ArmorColor").getValue()) {
//					if(!Utils.checkEnemyColor(player)) {
//						return false;
//					}
//				}
//				
//			}
//		}
//		if(!Wrapper.INSTANCE.player().canEntityBeSeen(entity)) {
//			return false;
//		}
//		if (targets.getBooleanValue("NoBot").getValue()) {
//			if(!Utils.checkBot(entity)) {
//				return false;
//			}
//		}
//		return true;
//    }
//    
//	EntityLivingBase getClosestEntity(){
//		EntityLivingBase closestEntity = null;
// 		for (Object o : Utils.getEntityList()) {
// 			if(o instanceof EntityLivingBase) {
// 				EntityLivingBase entity = (EntityLivingBase)o;
// 				if(check(entity)) {
// 					if(closestEntity == null || Wrapper.INSTANCE.player().getDistance(entity) < Wrapper.INSTANCE.player().getDistance(closestEntity)) {
// 						closestEntity = entity;
// 					}
// 				}
// 			}
// 		}
// 		return closestEntity;
// 	}
//	
//}
