package dev.ryanhcode.sable.neoforge.mixin.compatibility.create.mechnical_arm;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import dev.ryanhcode.sable.Sable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity.getRange;

@Pseudo
@Mixin(ArmBlockEntity.class)
public abstract class MechanicalArmSublevelInteraction extends KineticBlockEntity {
    public MechanicalArmSublevelInteraction(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @WrapOperation(
            method = "searchForItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/kinetics/mechanicalArm/ArmInteractionPoint;isValid()Z"
            ),
            remap = false
    )
    public boolean sable$searchSubLevelsForItem(ArmInteractionPoint instance, Operation<Boolean> original, @Local ArmInteractionPoint armInteractionPoint) {
        BlockPos armPos = getBlockPos();
        BlockPos pointPos = instance.getPos();
        double distanceSquared = Sable.HELPER.distanceSquaredWithSubLevels(
                getLevel(),
                armPos.getX(), armPos.getY(), armPos.getZ(),
                pointPos.getX(), pointPos.getY(), pointPos.getZ()
        );
        if (distanceSquared > Mth.square(getRange())
        ) {
            return false;
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(
            method = "searchForDestination",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/kinetics/mechanicalArm/ArmInteractionPoint;isValid()Z"
            ),
            remap = false
    )
    public boolean sable$searchSubLevelsForDestination(ArmInteractionPoint instance, Operation<Boolean> original, @Local ArmInteractionPoint armInteractionPoint) {
        BlockPos armPos = getBlockPos();
        BlockPos pointPos = instance.getPos();
        double distanceSquared = Sable.HELPER.distanceSquaredWithSubLevels(
                getLevel(),
                armPos.getX(), armPos.getY(), armPos.getZ(),
                pointPos.getX(), pointPos.getY(), pointPos.getZ()
        );
        if (distanceSquared > Mth.square(getRange())
        ) {
            return false;
        } else {
            return original.call(instance);
        }
    }

}
