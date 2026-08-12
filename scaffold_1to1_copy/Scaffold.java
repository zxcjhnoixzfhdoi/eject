/*
 * Decompiled with CFR 0.152.
 */
package modification.modules.world;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.List;
import java.util.Objects;
import modification.enummerates.Category;
import modification.events.EventFallDown;
import modification.events.EventRender2D;
import modification.events.EventSendPacket;
import modification.events.EventTick;
import modification.extenders.Module;
import modification.extenders.Value;
import modification.interfaces.Event;
import modification.main.Modification;
import modification.utilities.ColorUtil;
import modification.utilities.RotationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public final class Scaffold
extends Module {
    private static final List<Vec3i> DIRECTION_VECTORS = Lists.newArrayList();
    public final Value<Boolean> intave;
    private final Value<Boolean> silent;
    private final Value<Boolean> showBlocks;
    private final Value<Boolean> toggleAura;
    private int slot;
    private int item;
    private int counter;
    private float[] rotations;
    private boolean prevAura;
    private boolean rotated;

    public Scaffold(String string, Category category) {
        super(string, category);
        for (EnumFacing enumFacing : EnumFacing.values()) {
            DIRECTION_VECTORS.add(enumFacing.getDirectionVec());
        }
        DIRECTION_VECTORS.add(new Vec3i(1, 0, -1));
        DIRECTION_VECTORS.add(new Vec3i(-1, 0, 1));
        DIRECTION_VECTORS.add(new Vec3i(1, 0, 1));
        DIRECTION_VECTORS.add(new Vec3i(-1, 0, -1));
        this.silent = new Value<Boolean>("Silent", Boolean.valueOf(true), this, new String[0]);
        this.intave = new Value<Boolean>("Intave", Boolean.valueOf(true), this, new String[0]);
        this.showBlocks = new Value<Boolean>("Show blocks", Boolean.valueOf(true), this, new String[0]);
        this.toggleAura = new Value<Boolean>("Toggle aura", Boolean.valueOf(true), this, new String[0]);
    }

    @Override
    protected void onActivated() {
        this.rotations = null;
        if (this.rotated) {
            RotationUtil.resetRotations();
            this.rotated = false;
        }
        this.slot = Scaffold.MC.thePlayer.inventory.currentItem;
        this.prevAura = Objects.requireNonNull(Modification.MODULE_MANAGER.checkModuleForName((String)"KillAura")).enabled;
    }

    @Override
    public void onEvent(Event event) {
        Object[] objectArray;
        Event event2;
        if (event instanceof EventTick) {
            this.tag = (Boolean)this.silent.value != false ? "Silent" : "";
            this.item = this.findBlock(Scaffold.MC.thePlayer.inventoryContainer);
            if (this.shouldPlace(this.item)) {
                if (((Boolean)this.toggleAura.value).booleanValue()) {
                    Objects.requireNonNull(Modification.MODULE_MANAGER.checkModuleForName((String)"KillAura")).enabled = false;
                }
                if (this.item != -1 && this.slot != this.item) {
                    this.slot = this.item;
                    MC.getNetHandler().addToSendQueue((Packet)new C09PacketHeldItemChange(this.slot));
                }
                event2 = new BlockPos(Scaffold.MC.thePlayer.posX, Scaffold.MC.thePlayer.posY - 0.5, Scaffold.MC.thePlayer.posZ);
                if (this.rotations != null) {
                    if (((Boolean)this.intave.value).booleanValue()) {
                        RotationUtil.currentRotation = RotationUtil.fixedRotations(Scaffold.MC.thePlayer.rotationYaw + 180.0f, 82.5);
                        this.rotated = true;
                        if (!Scaffold.MC.thePlayer.onGround) {
                            Scaffold.MC.thePlayer.motionX *= 0.8;
                            Scaffold.MC.thePlayer.motionZ *= 0.8;
                        }
                    } else {
                        RotationUtil.currentRotation = RotationUtil.fixedRotations(this.rotations[0], this.rotations[1]);
                        this.rotated = true;
                    }
                }
                if (Scaffold.MC.theWorld.isAirBlock((BlockPos)event2) && RotationUtil.currentRotation != null) {
                    if (!this.allowPlacing()) {
                        return;
                    }
                    objectArray = (Boolean)this.silent.value != false ? Scaffold.MC.thePlayer.inventoryContainer.getSlot(this.slot + 36).getStack() : Scaffold.MC.thePlayer.getCurrentEquippedItem();
                    MovingObjectPosition movingObjectPosition = Modification.RAY_TRACE_UTIL.rayTraceBlock(RotationUtil.lastRotation.yaw, RotationUtil.lastRotation.pitch);
                    if (movingObjectPosition != null && !Scaffold.MC.theWorld.isAirBlock(movingObjectPosition.getBlockPos()) && Scaffold.MC.playerController.onPlayerRightClick(Scaffold.MC.thePlayer, Scaffold.MC.theWorld, (ItemStack)objectArray, movingObjectPosition.getBlockPos(), movingObjectPosition.sideHit, movingObjectPosition.hitVec)) {
                        ++this.counter;
                        if (((Boolean)this.intave.value).booleanValue()) {
                            MC.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Scaffold.MC.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                        }
                        Scaffold.MC.thePlayer.swingItem();
                        if (((Boolean)this.intave.value).booleanValue()) {
                            MC.getNetHandler().addToSendQueue((Packet)new C0BPacketEntityAction((Entity)Scaffold.MC.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
                        }
                    }
                }
            }
        }
        if (event instanceof EventSendPacket && ((Boolean)this.silent.value).booleanValue() && this.slot != Scaffold.MC.thePlayer.inventory.currentItem) {
            event2 = (EventSendPacket)event;
            if (((EventSendPacket)event2).packet instanceof C09PacketHeldItemChange) {
                ((EventSendPacket)event2).packet = new C09PacketHeldItemChange(this.slot);
            }
        }
        if (event instanceof EventRender2D && ((Boolean)this.showBlocks.value).booleanValue()) {
            event2 = (EventRender2D)event;
            int n = this.findBlockValue(Scaffold.MC.thePlayer.inventoryContainer);
            if (n > 0) {
                int n2 = event2.resolution.getScaledWidth() / 2 + 4;
                int n3 = event2.resolution.getScaledHeight() / 2 + 4;
                Modification.RENDER_UTIL.drawBorderedRect(n2, n3, Scaffold.MC.fontRendererObj.getStringWidth(Integer.toString(n).concat(" Blocks ")), Scaffold.MC.fontRendererObj.FONT_HEIGHT, 1, ColorUtil.BACKGROUND_DARKER, Color.BLACK.getRGB());
                Scaffold.MC.fontRendererObj.drawStringWithShadow(Integer.toString(n).concat(" Blocks "), (float)n2, (float)(n3 + 1), -1);
            }
        }
        if (event instanceof EventFallDown) {
            event2 = new BlockPos(Scaffold.MC.thePlayer.posX, Scaffold.MC.thePlayer.posY - 0.5, Scaffold.MC.thePlayer.posZ);
            objectArray = this.findBlockData((BlockPos)event2);
            if (objectArray != null) {
                this.rotate(objectArray);
            }
            ((EventFallDown)event).canceled = (Boolean)this.intave.value != false || this.item == -1;
        }
    }

    @Override
    protected void onDeactivated() {
        if (((Boolean)this.silent.value).booleanValue() && this.slot != Scaffold.MC.thePlayer.inventory.currentItem) {
            MC.getNetHandler().addToSendQueue((Packet)new C09PacketHeldItemChange(Scaffold.MC.thePlayer.inventory.currentItem));
        }
        RotationUtil.currentRotation = null;
        Objects.requireNonNull(Modification.MODULE_MANAGER.checkModuleForName((String)"KillAura")).enabled = this.prevAura;
    }

    private boolean allowPlacing() {
        double d = 0.024;
        BlockPos blockPos = new BlockPos(Scaffold.MC.thePlayer.posX - 0.024, Scaffold.MC.thePlayer.posY - 0.5, Scaffold.MC.thePlayer.posZ - 0.024);
        BlockPos blockPos2 = new BlockPos(Scaffold.MC.thePlayer.posX - 0.024, Scaffold.MC.thePlayer.posY - 0.5, Scaffold.MC.thePlayer.posZ + 0.024);
        BlockPos blockPos3 = new BlockPos(Scaffold.MC.thePlayer.posX + 0.024, Scaffold.MC.thePlayer.posY - 0.5, Scaffold.MC.thePlayer.posZ + 0.024);
        BlockPos blockPos4 = new BlockPos(Scaffold.MC.thePlayer.posX + 0.024, Scaffold.MC.thePlayer.posY - 0.5, Scaffold.MC.thePlayer.posZ - 0.024);
        return Scaffold.MC.thePlayer.worldObj.getBlockState(blockPos).getBlock() == Blocks.air && Scaffold.MC.thePlayer.worldObj.getBlockState(blockPos2).getBlock() == Blocks.air && Scaffold.MC.thePlayer.worldObj.getBlockState(blockPos3).getBlock() == Blocks.air && Scaffold.MC.thePlayer.worldObj.getBlockState(blockPos4).getBlock() == Blocks.air;
    }

    private boolean allowRotation() {
        double d = 0.1;
        BlockPos blockPos = new BlockPos(Scaffold.MC.thePlayer.posX - 0.1, Scaffold.MC.thePlayer.posY - 0.5, Scaffold.MC.thePlayer.posZ - 0.1);
        BlockPos blockPos2 = new BlockPos(Scaffold.MC.thePlayer.posX - 0.1, Scaffold.MC.thePlayer.posY - 0.5, Scaffold.MC.thePlayer.posZ + 0.1);
        BlockPos blockPos3 = new BlockPos(Scaffold.MC.thePlayer.posX + 0.1, Scaffold.MC.thePlayer.posY - 0.5, Scaffold.MC.thePlayer.posZ + 0.1);
        BlockPos blockPos4 = new BlockPos(Scaffold.MC.thePlayer.posX + 0.1, Scaffold.MC.thePlayer.posY - 0.5, Scaffold.MC.thePlayer.posZ - 0.1);
        return Scaffold.MC.thePlayer.worldObj.getBlockState(blockPos).getBlock() == Blocks.air && Scaffold.MC.thePlayer.worldObj.getBlockState(blockPos2).getBlock() == Blocks.air && Scaffold.MC.thePlayer.worldObj.getBlockState(blockPos3).getBlock() == Blocks.air && Scaffold.MC.thePlayer.worldObj.getBlockState(blockPos4).getBlock() == Blocks.air;
    }

    private void rotate(Object[] objectArray) {
        BlockPos blockPos = new BlockPos(Scaffold.MC.thePlayer.posX, Scaffold.MC.thePlayer.posY - 1.0, Scaffold.MC.thePlayer.posZ);
        Vec3 vec3 = new Vec3(Scaffold.MC.thePlayer.posX, Scaffold.MC.thePlayer.getEntityBoundingBox().minY + (double)Scaffold.MC.thePlayer.getEyeHeight(), Scaffold.MC.thePlayer.posZ);
        Vec3 vec32 = new Vec3((Vec3i)objectArray[1]);
        Vec3 vec33 = new Vec3((Vec3i)blockPos).add(vec32).addVector(0.5, -3.0, 0.5);
        float[] fArray = Modification.ROTATION_UTIL.rotationsToVector(vec33);
        MovingObjectPosition movingObjectPosition = Modification.RAY_TRACE_UTIL.rayTraceBlock(fArray[0], fArray[1]);
        if (this.allowRotation()) {
            this.rotations = fArray;
        }
    }

    private Object[] findBlockData(BlockPos blockPos) {
        for (Vec3i vec3i : DIRECTION_VECTORS) {
            BlockPos blockPos2 = blockPos.add(vec3i);
            if (Scaffold.MC.theWorld.isAirBlock(blockPos2)) continue;
            return new Object[]{blockPos2, vec3i};
        }
        return null;
    }

    private boolean shouldPlace(int n) {
        ItemStack itemStack = Scaffold.MC.thePlayer.getCurrentEquippedItem();
        if (itemStack != null && itemStack.getItem() instanceof ItemBlock) {
            return true;
        }
        return (Boolean)this.silent.value != false && n != -1;
    }

    public final int findBlock(Container container) {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = container.getSlot(i + 36).getStack();
            if (itemStack == null || !(itemStack.getItem() instanceof ItemBlock)) continue;
            return i;
        }
        return -1;
    }

    private int findBlockValue(Container container) {
        int n = 0;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = container.getSlot(i + 36).getStack();
            if (itemStack == null || !(itemStack.getItem() instanceof ItemBlock)) continue;
            n += itemStack.stackSize;
        }
        return n;
    }
}
