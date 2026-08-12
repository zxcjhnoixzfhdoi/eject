/*
 * Decompiled with CFR 0.152.
 */
package modification.managers;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.List;
import modification.enummerates.Category;
import modification.extenders.Module;
import modification.modules.combat.BowAimbot;
import modification.modules.combat.KillAura;
import modification.modules.combat.Teleport;
import modification.modules.combat.Velocity;
import modification.modules.misc.GUI;
import modification.modules.misc.HUD;
import modification.modules.misc.IRC;
import modification.modules.misc.MCF;
import modification.modules.misc.NameProtect;
import modification.modules.misc.NoFriends;
import modification.modules.misc.NoSwing;
import modification.modules.misc.Teams;
import modification.modules.movement.AntiCobweb;
import modification.modules.movement.FastLadder;
import modification.modules.movement.Fly;
import modification.modules.movement.InventoryMove;
import modification.modules.movement.NoSlowdown;
import modification.modules.movement.Speed;
import modification.modules.movement.Sprint;
import modification.modules.movement.Step;
import modification.modules.movement.Strafe;
import modification.modules.player.AutoPlay;
import modification.modules.player.AutoTool;
import modification.modules.player.ChestStealer;
import modification.modules.player.InventoryManager;
import modification.modules.visuals.BlockAnimation;
import modification.modules.visuals.BlockOverlay;
import modification.modules.visuals.Chams;
import modification.modules.visuals.ESP;
import modification.modules.visuals.FullBright;
import modification.modules.visuals.ItemPhysics;
import modification.modules.visuals.NameTags;
import modification.modules.visuals.NoBob;
import modification.modules.visuals.NoHurtCam;
import modification.modules.visuals.Scoreboard;
import modification.modules.visuals.TargetAlert;
import modification.modules.visuals.Trajectories;
import modification.modules.world.BedFucker;
import modification.modules.world.DeathBack;
import modification.modules.world.Scaffold;
import modification.modules.world.Tower;

public final class ModuleManager {
    public static final List<Module> MODULES = Lists.newArrayList();

    public final void initialize() {
        new HUD("HUD", Category.MISC);
        new GUI("GUI", Category.MISC);
        new Speed("Speed", Category.MOVEMENT);
        new Sprint("Sprint", Category.MOVEMENT);
        new Step("Step", Category.MOVEMENT);
        new Fly("Fly", Category.MOVEMENT);
        new Velocity("Velocity", Category.COMBAT);
        new ChestStealer("ChestStealer", Category.PLAYER);
        new Chams("Chams", Category.VISUALS);
        new ESP("ESP", Category.VISUALS);
        new NoSlowdown("NoSlowdown", Category.MOVEMENT);
        new FullBright("FullBright", Category.VISUALS);
        new InventoryManager("InventoryManager", Category.PLAYER);
        new Tower("Tower", Category.WORLD);
        new BowAimbot("BowAimbot", Category.COMBAT);
        new NoFriends("NoFriends", Category.MISC);
        new NameProtect("NameProtect", Category.MISC);
        new Teams("Teams", Category.MISC);
        new FastLadder("FastLadder", Category.MOVEMENT);
        new InventoryMove("InventoryMove", Category.MOVEMENT);
        new AutoTool("AutoTool", Category.PLAYER);
        new NoHurtCam("NoHurtcam", Category.VISUALS);
        new NoBob("NoBob", Category.VISUALS);
        new BlockOverlay("BlockOverlay", Category.VISUALS);
        new ItemPhysics("ItemPhysics", Category.VISUALS);
        new NameTags("NameTags", Category.VISUALS);
        new Trajectories("Trajectories", Category.VISUALS);
        new Scoreboard("Scoreboard", Category.VISUALS);
        new Strafe("Strafe", Category.MOVEMENT);
        new MCF("MCF", Category.MISC);
        new AutoPlay("AutoPlay", Category.PLAYER);
        new AntiCobweb("AntiCobweb", Category.MOVEMENT);
        new Teleport("Teleport", Category.COMBAT);
        new IRC((String)"IRC", (Category)Category.MISC).enabled = true;
        new NoSwing("NoSwing", Category.MISC);
        new BlockAnimation("BlockAnimation", Category.VISUALS);
        new KillAura("KillAura", Category.COMBAT);
        new TargetAlert("TargetAlert", Category.VISUALS);
        new BedFucker("BedFucker", Category.WORLD);
        new DeathBack("DeathBack", Category.WORLD);
        new Scaffold("Scaffold", Category.WORLD);
        MODULES.sort(Comparator.comparing(module -> module.name));
    }

    public final Module checkModuleForName(String string) {
        if (!MODULES.isEmpty()) {
            for (Module module : MODULES) {
                if (!module.name.equalsIgnoreCase(string)) continue;
                return module;
            }
        }
        return null;
    }
}
