package i.gishreloaded.gishcode.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

import com.mojang.authlib.GameProfile;

import i.gishreloaded.gishcode.managers.EnemyManager;
import i.gishreloaded.gishcode.utils.system.Mapping;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Utils {
	
	public static boolean lookChanged;
	public static float[] rotationsToBlock = null;
	private static final Random RANDOM = new Random();

	public static boolean nullCheck() { return (Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() == null); }
	
	public static void copy(String content) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(content), null);
    }
	
    public static int random(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static Vec3d getRandomCenter(AxisAlignedBB bb) {
        return new Vec3d(bb.minX + (bb.maxX - bb.minX) * 0.8 * Math.random(), bb.minY + (bb.maxY - bb.minY) * Math.random() + 0.1 * Math.random(), bb.minZ + (bb.maxZ - bb.minZ) * 0.8 * Math.random());
    }
    
	public static boolean isMoving(Entity e) {
        return e.motionX != 0.0 && e.motionZ != 0.0 && (e.motionY != 0.0 || e.motionY > 0.0);
    }
	
	public static boolean canBeClicked(final BlockPos pos) {
        return BlockUtils.getBlock(pos).canCollideCheck(BlockUtils.getState(pos), false);
    }
	
	public static Vec3d getEyesPos() {
        return new Vec3d(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + Wrapper.INSTANCE.player().getEyeHeight(), Wrapper.INSTANCE.player().posZ);
    }
	
    public static void faceVectorPacketInstant(final Vec3d vec) {
        Utils.rotationsToBlock = getNeededRotations(vec);
    }
    
    public static List<Entity> getEntityList(){
    	return Wrapper.INSTANCE.world().getLoadedEntityList();
    }
    
    public static boolean isNullOrEmptyStack(ItemStack stack) {
		return stack == null || stack.isEmpty();
	}
    
    public static void windowClick(int windowId, int slotId, int mouseButton, ClickType type) {
    	Wrapper.INSTANCE.controller().windowClick(windowId, slotId, mouseButton, type, Wrapper.INSTANCE.player());
    }
    
    public static void swingMainHand() { 
		Wrapper.INSTANCE.player().swingArm(EnumHand.MAIN_HAND); 
	}
	
	public static void attack(Entity entity) { 
		Wrapper.INSTANCE.controller().attackEntity(Wrapper.INSTANCE.player(), entity); 
	}
	
	public static void addEffect(int id, int duration, int amplifier) {
		Wrapper.INSTANCE.player().addPotionEffect(new PotionEffect(Potion.getPotionById(id), duration, amplifier));
	}
	
	public static void removeEffect(int id) {
		Wrapper.INSTANCE.player().removePotionEffect(Potion.getPotionById(id));
	}
	
	public static void clearEffects() {
		for(PotionEffect effect : Wrapper.INSTANCE.player().getActivePotionEffects()) {
			Wrapper.INSTANCE.player().removePotionEffect(effect.getPotion());
		}
	}
    
    public static double[] teleportToPosition(double[] startPosition, double[] endPosition, double setOffset, double slack, boolean extendOffset, boolean onGround) {
        boolean wasSneaking = false;

        if (Wrapper.INSTANCE.player().isSneaking())
            wasSneaking = true;

        double startX = startPosition[0];
        double startY = startPosition[1];
        double startZ = startPosition[2];

        double endX = endPosition[0];
        double endY = endPosition[1];
        double endZ = endPosition[2];

        double distance = Math.abs(startX - startY) + Math.abs(startY - endY) + Math.abs(startZ - endZ);

        int count = 0;
        while (distance > slack) {
            distance = Math.abs(startX - endX) + Math.abs(startY - endY) + Math.abs(startZ - endZ);

            if (count > 120) {
                break;
            }

            double offset = extendOffset && (count & 0x1) == 0 ? setOffset + 0.15D : setOffset;

            double diffX = startX - endX;
            double diffY = startY - endY;
            double diffZ = startZ - endZ;

            if (diffX < 0.0D) {
                if (Math.abs(diffX) > offset) {
                    startX += offset;
                } else {
                    startX += Math.abs(diffX);
                }
            }
            if (diffX > 0.0D) {
                if (Math.abs(diffX) > offset) {
                    startX -= offset;
                } else {
                    startX -= Math.abs(diffX);
                }
            }
            if (diffY < 0.0D) {
                if (Math.abs(diffY) > offset) {
                    startY += offset;
                } else {
                    startY += Math.abs(diffY);
                }
            }
            if (diffY > 0.0D) {
                if (Math.abs(diffY) > offset) {
                    startY -= offset;
                } else {
                    startY -= Math.abs(diffY);
                }
            }
            if (diffZ < 0.0D) {
                if (Math.abs(diffZ) > offset) {
                    startZ += offset;
                } else {
                    startZ += Math.abs(diffZ);
                }
            }
            if (diffZ > 0.0D) {
                if (Math.abs(diffZ) > offset) {
                    startZ -= offset;
                } else {
                    startZ -= Math.abs(diffZ);
                }
            }

            if (wasSneaking) {
            	Wrapper.INSTANCE.sendPacket(new CPacketEntityAction(Wrapper.INSTANCE.player(), CPacketEntityAction.Action.STOP_SNEAKING));
            }
            Wrapper.INSTANCE.mc().getConnection().getNetworkManager().sendPacket(new CPacketPlayer.Position(startX, startY, startZ, onGround));
            count++;
        }

        if (wasSneaking) {
        	Wrapper.INSTANCE.sendPacket(new CPacketEntityAction(Wrapper.INSTANCE.player(), CPacketEntityAction.Action.START_SNEAKING));
        }

        return new double[]{startX, startY, startZ};
    }
    
    public static void selfDamage(double posY) {
		if(!Wrapper.INSTANCE.player().onGround) 
			return;
		for (int i = 0; i <= 64.0D; i++) {
			Wrapper.INSTANCE.sendPacket(new CPacketPlayer.Position(
					Wrapper.INSTANCE.player().posX,
					Wrapper.INSTANCE.player().posY + posY,
					Wrapper.INSTANCE.player().posZ, 
					false));
			Wrapper.INSTANCE.sendPacket(new CPacketPlayer.Position(
					Wrapper.INSTANCE.player().posX,
					Wrapper.INSTANCE.player().posY,
					Wrapper.INSTANCE.player().posZ, 
					(i == 64.0D)));
		}
		Wrapper.INSTANCE.player().motionX *= 0.2;
		Wrapper.INSTANCE.player().motionZ *= 0.2;
    	Utils.swingMainHand();
	}
    
    public static String getPlayerName(EntityPlayer player) {
    	return player.getGameProfile() != null ? 
				player.getGameProfile().getName() : player.getName();
    }
    
    public static boolean isPlayer(Entity entity) {
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			String entityName = getPlayerName(player);
			String playerName = getPlayerName(Wrapper.INSTANCE.player());
			if(entityName.equals(playerName)) {
				return true;
			}
		}
		return false;
    }
    
    public static boolean isMurder(EntityLivingBase entity) {
    	Utils.mysteryFind(entity, 0);
		if(!EnemyManager.murders.isEmpty()) {
			if(entity instanceof EntityPlayer) {
				EntityPlayer murder = (EntityPlayer) entity;
				for(String name : EnemyManager.murders) {
					if(murder.getGameProfile().getName().equals(name)) {
						return true;
					}
				}
			}
		}
    	return false;
    }
    
    public static boolean isDetect(EntityLivingBase entity) {
    	Utils.mysteryFind(entity, 1);
		if(!EnemyManager.detects.isEmpty()) {
			if(entity instanceof EntityPlayer) {
				EntityPlayer murder = (EntityPlayer) entity;
				for(String name : EnemyManager.detects) {
					if(murder.getGameProfile().getName().equals(name)) {
						return true;
					}
				}
			}
		}
    	return false;
    }
    
    public static void mysteryFind(EntityLivingBase entity, int target) {
    	if(target == 0) {
    		if(!EnemyManager.murders.isEmpty()) {
    			for(int index = 0; index < EnemyManager.murders.size(); index++) {
					EntityLivingBase murder = Utils.getWorldEntityByName(EnemyManager.murders.get(index));
					if(murder == null) {
						EnemyManager.murders.remove(index);
    				}
    			}
    		}
    	} else if(target == 1) {
    		if(!EnemyManager.detects.isEmpty()) {
    			for(int index = 0; index < EnemyManager.detects.size(); index++) {
					EntityLivingBase detect = Utils.getWorldEntityByName(EnemyManager.detects.get(index));
					if(detect == null) {
						EnemyManager.detects.remove(index);
    				}
    			}
    		}
    	}
    	if (entity instanceof EntityPlayerSP) {
            return;
        }
		
		if(!(entity instanceof EntityPlayer)) {
    		return;
    	}
		
		EntityPlayer player = (EntityPlayer)entity;
		if(player.getGameProfile() == null) {
			return;
		}
		
		GameProfile profile = player.getGameProfile();
		
		if(profile.getName() == null) {
			return;
		}
		if(EnemyManager.murders.contains(profile.getName()) 
				|| EnemyManager.detects.contains(profile.getName())) {
			return;
		}
		
		if(player.inventory == null) {
			return;
		}
		
		for(int slot = 0; slot < 36; slot++){
			
			ItemStack stack = player.inventory.getStackInSlot(slot);
			if(stack == null) {
				continue;
			}
			
			Item item = stack.getItem();
			
			if(item == null) {
				continue;
			}
			if(target == 0) {
			if(		//swords
					item == Items.IRON_SWORD 
					|| item == Items.DIAMOND_SWORD
					|| item == Items.GOLDEN_SWORD
					|| item == Items.STONE_SWORD
					|| item == Items.WOODEN_SWORD
					//shovels
					|| item == Items.IRON_SHOVEL 
					|| item == Items.DIAMOND_SHOVEL 
					|| item == Items.GOLDEN_SHOVEL 
					|| item == Items.STONE_SHOVEL 
					|| item == Items.WOODEN_SHOVEL
					//axes
					|| item == Items.IRON_AXE
					|| item == Items.DIAMOND_AXE
					|| item == Items.GOLDEN_AXE
					|| item == Items.STONE_AXE
					|| item == Items.WOODEN_AXE
					//pickaxes
					|| item == Items.IRON_PICKAXE 
					|| item == Items.DIAMOND_PICKAXE 
					|| item == Items.GOLDEN_PICKAXE 
					|| item == Items.STONE_PICKAXE 
					|| item == Items.WOODEN_PICKAXE 
					//hoes
					|| item == Items.IRON_HOE
					|| item == Items.DIAMOND_HOE
					|| item == Items.GOLDEN_HOE
					|| item == Items.STONE_HOE
					|| item == Items.WOODEN_HOE
					//others
					|| item == Items.STICK
					|| item == Items.BLAZE_ROD
					|| item == Items.FISHING_ROD
					|| item == Items.CARROT
					|| item == Items.GOLDEN_CARROT
					|| item == Items.BONE
					|| item == Items.COOKIE
					|| item == Items.FEATHER
					|| item == Items.PUMPKIN_PIE
					|| item == Items.COOKED_FISH
					|| item == Items.FISH
					|| item == Items.SHEARS
					|| item == Items.CARROT_ON_A_STICK
					) {
				String name = player.getGameProfile().getName();
				EnemyManager.murders.add(name);
			}
			} else if(target == 1) {
				if(item == Items.BOW) {
					String name = player.getGameProfile().getName();
					EnemyManager.detects.add(name);
				}
			}
		}
    }
    
    public static boolean checkEnemyNameColor(EntityLivingBase entity) {
    	String name = entity.getDisplayName().getFormattedText();
    	if(getEntityNameColor(Wrapper.INSTANCE.player()).equals(getEntityNameColor(entity))) {
    		return false;
    	}
    	return true;
    }
    
    public static String getEntityNameColor(EntityLivingBase entity) {
    	String name = entity.getDisplayName().getFormattedText();
    	if(name.contains("\u00a7")) {
    	if(name.contains("\u00a71")) { return "\u00a71"; } else
    	if(name.contains("\u00a72")) { return "\u00a72"; } else
    	if(name.contains("\u00a73")) { return "\u00a73"; } else
    	if(name.contains("\u00a74")) { return "\u00a74"; } else
    	if(name.contains("\u00a75")) { return "\u00a75"; } else
    	if(name.contains("\u00a76")) { return "\u00a76"; } else
    	if(name.contains("\u00a77")) { return "\u00a77"; } else
    	if(name.contains("\u00a78")) { return "\u00a78"; } else
    	if(name.contains("\u00a79")) { return "\u00a79"; } else
    	if(name.contains("\u00a70")) { return "\u00a70"; } else
    	if(name.contains("\u00a7e")) { return "\u00a7e"; } else
    	if(name.contains("\u00a7d")) { return "\u00a7d"; } else
    	if(name.contains("\u00a7a")) { return "\u00a7a"; } else
    	if(name.contains("\u00a7b")) { return "\u00a7b"; } else
    	if(name.contains("\u00a7c")) { return "\u00a7c"; } else
    	if(name.contains("\u00a7f")) { return "\u00a7f"; };
    	}
    	return "null";
    }
	
	public static int getPlayerArmorColor(EntityPlayer player, ItemStack stack) {
    	if(player == null || stack == null || stack.getItem() == null || !(stack.getItem() instanceof ItemArmor))
    		return -1;
    	ItemArmor itemArmor = (ItemArmor) stack.getItem();
		if(itemArmor == null || itemArmor.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER)
			return -1;
    	return itemArmor.getColor(stack);
    }
	
	public static boolean checkEnemyColor(EntityPlayer enemy) {
    	int colorEnemy0 = getPlayerArmorColor(enemy, enemy.inventory.armorItemInSlot(0));
    	int colorEnemy1 = getPlayerArmorColor(enemy, enemy.inventory.armorItemInSlot(1));
    	int colorEnemy2 = getPlayerArmorColor(enemy, enemy.inventory.armorItemInSlot(2));
    	int colorEnemy3 = getPlayerArmorColor(enemy, enemy.inventory.armorItemInSlot(3));
    			
    	int colorPlayer0 = getPlayerArmorColor(Wrapper.INSTANCE.player(), Wrapper.INSTANCE.inventory().armorItemInSlot(0));
    	int colorPlayer1 = getPlayerArmorColor(Wrapper.INSTANCE.player(), Wrapper.INSTANCE.inventory().armorItemInSlot(1));
    	int colorPlayer2 = getPlayerArmorColor(Wrapper.INSTANCE.player(), Wrapper.INSTANCE.inventory().armorItemInSlot(2));
    	int colorPlayer3 = getPlayerArmorColor(Wrapper.INSTANCE.player(), Wrapper.INSTANCE.inventory().armorItemInSlot(3));
    			
    	if(colorEnemy0 == colorPlayer0 && colorPlayer0 != -1 && colorEnemy0 != 1
    			|| colorEnemy1 == colorPlayer1 && colorPlayer1 != -1 && colorEnemy1 != 1
    					|| colorEnemy2 == colorPlayer2 && colorPlayer2 != -1 && colorEnemy2 != 1
    							|| colorEnemy3 == colorPlayer3 && colorPlayer3 != -1 && colorEnemy3 != 1) {
    		return false;
    	}
    	return true;
    }
	
	public static boolean screenCheck() {
		if(Wrapper.INSTANCE.mc().currentScreen instanceof GuiContainer
    			|| Wrapper.INSTANCE.mc().currentScreen instanceof GuiChat
    			|| Wrapper.INSTANCE.mc().currentScreen instanceof GuiScreen) 
			return false;
		return true;
	}
    
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static EntityLivingBase getWorldEntityByName(String name) {
    	EntityLivingBase entity = null;
    	for (Object object : Utils.getEntityList()) {
            if (object instanceof EntityLivingBase) {
            	EntityLivingBase entityForCheck = (EntityLivingBase) object;
                if(entityForCheck.getName().contains(name)) {
                	entity = entityForCheck;
                }
            } 
        }
    	return entity;
    }
    
    public static float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
        EntityEgg var4 = new EntityEgg(Wrapper.INSTANCE.world());
        var4.posX = (double) var0 + 0.5D;
        var4.posY = (double) var1 + 0.5D;
        var4.posZ = (double) var2 + 0.5D;
        var4.posX += (double) var3.getDirectionVec().getX() * 0.25D;
        var4.posY += (double) var3.getDirectionVec().getY() * 0.25D;
        var4.posZ += (double) var3.getDirectionVec().getZ() * 0.25D;
        return getDirectionToEntity(var4);
    }
    
    private static float[] getDirectionToEntity(Entity var0) {
        return new float[]{getYaw(var0) + Wrapper.INSTANCE.player().rotationYaw, getPitch(var0) + Wrapper.INSTANCE.player().rotationPitch};
    }
    
    public static float getPitch(Entity entity) {
        double x = entity.posX - Wrapper.INSTANCE.player().posX;
        double y = entity.posY - Wrapper.INSTANCE.player().posY;
        double z = entity.posZ - Wrapper.INSTANCE.player().posZ;
        y /= Wrapper.INSTANCE.player().getDistance(entity);
        double pitch = Math.asin(y) * 57.29577951308232;
        pitch = -pitch;
        return (float)pitch;
    }
    
    public static float getYaw(Entity entity) {
        double x = entity.posX - Wrapper.INSTANCE.player().posX;
        double y = entity.posY - Wrapper.INSTANCE.player().posY;
        double z = entity.posZ - Wrapper.INSTANCE.player().posZ;
        double yaw = Math.atan2(x, z) * 57.29577951308232;
        yaw = -yaw;
        return (float)yaw;
    }
	
    public static float[] getNeededRotations(Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { Wrapper.INSTANCE.player().rotationYaw + MathHelper.wrapDegrees(yaw - Wrapper.INSTANCE.player().rotationYaw), Wrapper.INSTANCE.player().rotationPitch + MathHelper.wrapDegrees(pitch - Wrapper.INSTANCE.player().rotationPitch) };
    }
    
    public static float getDirection() {
    	float var1 = Wrapper.INSTANCE.player().rotationYaw;
        if (Wrapper.INSTANCE.player().moveForward < 0.0F) {
        	var1 += 180.0F;
        }
        float forward = 1.0F;
        if (Wrapper.INSTANCE.player().moveForward < 0.0F) {
            forward = -0.5F;
        } else if (Wrapper.INSTANCE.player().moveForward > 0.0F) {
            forward = 0.5F;
        }
        if (Wrapper.INSTANCE.player().moveStrafing > 0.0F) {
        	var1 -= 90.0F * forward;
        }
        if (Wrapper.INSTANCE.player().moveStrafing < 0.0F) {
        	var1 += 90.0F * forward;
        }
        var1 *= 0.017453292F;
        return var1;
    }
    
    public static void faceVectorPacket(Vec3d vec)
	{
		float[] rotations = getNeededRotations(vec);
		EntityPlayerSP pl = Minecraft.getMinecraft().player;

		float preYaw = pl.rotationYaw;
		float prePitch = pl.rotationPitch;

		pl.rotationYaw = rotations[0];
		pl.rotationPitch = rotations[1];

		try {
			Method onUpdateWalkingPlayer = pl.getClass().getDeclaredMethod(Mapping.onUpdateWalkingPlayer);
			onUpdateWalkingPlayer.setAccessible(true);
			onUpdateWalkingPlayer.invoke(pl, new Object[0]);
		} catch(Exception ex) {}

		pl.rotationYaw = preYaw;
		pl.rotationPitch = prePitch;
	}
    
	public static void setEntityBoundingBoxSize(Entity entity, float width, float height) {
		if(entity.width == width && entity.height == height) return;
            entity.width = width;  
            entity.height = height;
                double d0 = (double)width / 2.0D;
                entity.setEntityBoundingBox(
                		new AxisAlignedBB(
                				entity.posX - d0,
                				entity.posY,
                				entity.posZ - d0,
                				entity.posX + d0,
                				entity.posY + (double)entity.height,
                				entity.posZ + d0));
        
    }
	
	public static boolean placeBlockScaffold(final BlockPos pos) {
        final Vec3d eyesPos = new Vec3d(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + Wrapper.INSTANCE.player().getEyeHeight(), Wrapper.INSTANCE.player().posZ);
        EnumFacing[] values;
        for (int length = (values = EnumFacing.values()).length, i = 0; i < length; ++i) {
            final EnumFacing side = values[i];
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (eyesPos.squareDistanceTo(new Vec3d(pos).addVector(0.5, 0.5, 0.5)) < eyesPos.squareDistanceTo(new Vec3d(neighbor).addVector(0.5, 0.5, 0.5)) && canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d(neighbor).addVector(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (eyesPos.squareDistanceTo(hitVec) <= 18.0625) {
                    Utils.faceVectorPacketInstant(hitVec);
                    Utils.swingMainHand();
                    Wrapper.INSTANCE.controller().processRightClickBlock(Wrapper.INSTANCE.player(), Wrapper.INSTANCE.world(), neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                    try {
                    	Field f = Minecraft.class.getDeclaredField(Mapping.rightClickDelayTimer);
                    	f.setAccessible(true);
            			f.set(Wrapper.INSTANCE.mc(), 4);
            		} catch (Exception e) {
            			e.printStackTrace();
            		}
                    return true;
                }
            }
        }
        return false;
    }
	
	public static boolean isInsideBlock(EntityLivingBase entity) {
        for (int x = MathHelper.floor(entity.getEntityBoundingBox().minX); x < MathHelper.floor(entity.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int y = MathHelper.floor(entity.getEntityBoundingBox().minY); y < MathHelper.floor(entity.getEntityBoundingBox().maxY) + 1; ++y) {
                for (int z = MathHelper.floor(entity.getEntityBoundingBox().minZ); z < MathHelper.floor(entity.getEntityBoundingBox().maxZ) + 1; ++z) {
                    final Block block = BlockUtils.getBlock(new BlockPos(x, y, z));
                    final AxisAlignedBB boundingBox;
                    if (block != null && !(block instanceof BlockAir) && (boundingBox = 
                    		block.getCollisionBoundingBox(
                    				BlockUtils.getState(new BlockPos(x, y, z)),
                    				Wrapper.INSTANCE.world(),
                    				new BlockPos(x, y, z)))
                    		!= null && entity.getEntityBoundingBox().intersects(boundingBox)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
	
	public static boolean isBlockEdge(EntityLivingBase entity) {
		return (Wrapper.INSTANCE.world().getCollisionBoxes(entity, entity.getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(0.001D, 0.0D, 0.001D)).isEmpty() && entity.onGround);
	}
    
    public static void faceEntity(EntityLivingBase entity) {
	    if (entity == null) {
	    	return;
	    }
	    
        double d0 = entity.posX - Wrapper.INSTANCE.player().posX;
        double d4 = entity.posY - Wrapper.INSTANCE.player().posY;
        double d1 = entity.posZ - Wrapper.INSTANCE.player().posZ;
        double d2 = Wrapper.INSTANCE.player().posY + (double)Wrapper.INSTANCE.player().getEyeHeight() - (entity.posY + (double)entity.getEyeHeight());
        double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1);
        
        float f = (float)(Math.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float)(-(Math.atan2(d2, d3) * 180.0D / Math.PI));
        
        Wrapper.INSTANCE.player().rotationYaw = f;
        Wrapper.INSTANCE.player().rotationPitch = f1;
    }

	
	public static void assistFaceEntity(Entity entity, float yaw, float pitch) {
	    if (entity == null) {
	    	return;
	    }
	    
	    double diffX = entity.posX - Wrapper.INSTANCE.player().posX;
	    double diffZ = entity.posZ - Wrapper.INSTANCE.player().posZ;
	    double yDifference;
	    
	    if (entity instanceof EntityLivingBase)
	    {
	      EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
	      yDifference = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (
	    		  Wrapper.INSTANCE.player().posY + Wrapper.INSTANCE.player().getEyeHeight());
	    }
	    else
	    {
	      yDifference = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0D - (
	    		  Wrapper.INSTANCE.player().posY + Wrapper.INSTANCE.player().getEyeHeight());
	    }
	    
	    double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
	    float rotationYaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
	    float rotationPitch = (float)-(Math.atan2(yDifference, dist) * 180.0D / Math.PI);
	    
	    if(yaw > 0) {
	    	Wrapper.INSTANCE.player().rotationYaw = updateRotation(Wrapper.INSTANCE.player().rotationYaw, rotationYaw, yaw / 4);
	    }
	    if(pitch > 0) {
	    	Wrapper.INSTANCE.player().rotationPitch = updateRotation(Wrapper.INSTANCE.player().rotationPitch, rotationPitch, pitch / 4);
	    }
	}
	
	public static float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
	    float var4 = MathHelper.wrapDegrees(p_70663_2_ - p_70663_1_);
	    if (var4 > p_70663_3_) {
	      var4 = p_70663_3_;
	    }
	    if (var4 < -p_70663_3_) {
	      var4 = -p_70663_3_;
	    }
	    return p_70663_1_ + var4;
	}
	
	public static int getDistanceFromMouse(final EntityLivingBase entity) {
        final float[] neededRotations = getRotationsNeeded(entity);
        if (neededRotations != null) {
            final float neededYaw = Wrapper.INSTANCE.player().rotationYaw - neededRotations[0];
            final float neededPitch = Wrapper.INSTANCE.player().rotationPitch - neededRotations[1];
            final float distanceFromMouse = MathHelper.sqrt(neededYaw * neededYaw + neededPitch * neededPitch * 2.0f);
            return (int)distanceFromMouse;
        }
        return -1;
    }
	
	public static float[] getSmoothNeededRotations(Vec3d vec, float yaw, float pitch) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float rotationYaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float rotationPitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
	    
	    return new float[] { 
	    		updateRotation(Wrapper.INSTANCE.player().rotationYaw, rotationYaw, yaw / 4), 
	    		updateRotation(Wrapper.INSTANCE.player().rotationPitch, rotationPitch, pitch / 4) 
	    };
	}
	
	public static float[] getRotationsNeeded(Entity entity) {
	    if (entity == null) {
	    	return null;
	    }
	    
	    double diffX = entity.posX - Wrapper.INSTANCE.mc().player.posX;
	    double diffZ = entity.posZ - Wrapper.INSTANCE.mc().player.posZ;
	    double diffY;
	    
	    if ((entity instanceof EntityLivingBase))
	    {
	      EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
	      diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (Wrapper.INSTANCE.mc().player.posY + Wrapper.INSTANCE.mc().player.getEyeHeight());
	    }
	    else
	    {
	      diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0D - (Wrapper.INSTANCE.mc().player.posY + Wrapper.INSTANCE.mc().player.getEyeHeight());
	    }
	    
	    double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
	    float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
	    float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
	    
	    return new float[] { 
	    		Wrapper.INSTANCE.mc().player.rotationYaw + MathHelper.wrapDegrees(yaw - Wrapper.INSTANCE.mc().player.rotationYaw), 
	    		Wrapper.INSTANCE.mc().player.rotationPitch + MathHelper.wrapDegrees(pitch - Wrapper.INSTANCE.mc().player.rotationPitch) 
	    };
	}
}
