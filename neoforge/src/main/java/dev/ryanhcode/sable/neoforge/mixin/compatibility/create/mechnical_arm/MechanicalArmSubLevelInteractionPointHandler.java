package dev.ryanhcode.sable.neoforge.mixin.compatibility.create.mechnical_arm;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointHandler;
import dev.ryanhcode.sable.Sable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ArmInteractionPointHandler.class)
public class MechanicalArmSubLevelInteractionPointHandler {

    @Redirect(method = "flushSettings", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;closerThan(Lnet/minecraft/core/Vec3i;D)Z"))
    private static boolean sable$accountForSubLevels(final BlockPos instance, final Vec3i pos, final double maxDistance) {
        final Level level = Minecraft.getInstance().level;
        if (level == null) {
            return instance.closerThan(pos, maxDistance);
        }

        return Sable.HELPER.distanceSquaredWithSubLevels(level, instance.getX(), instance.getY(), instance.getZ(), pos.getX(), pos.getY(), pos.getZ()) < Mth.square(maxDistance);
    }
}
