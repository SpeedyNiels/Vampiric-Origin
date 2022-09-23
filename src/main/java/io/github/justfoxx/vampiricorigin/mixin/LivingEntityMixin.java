package io.github.justfoxx.vampiricorigin.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.justfoxx.vampiricorigin.powers.Sucker;
import io.github.justfoxx.vampiricorigin.powers.UndeadEffect;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow protected abstract void applyDamage(DamageSource source, float amount);

    @ModifyReturnValue(method = "getGroup", at = @At("RETURN"))
    private EntityGroup modifyGroup(EntityGroup group) {
        if (PowerHolderComponent.hasPower((LivingEntity) (Object) this, UndeadEffect.class)) {
            return EntityGroup.UNDEAD;
        }
        return group;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (PowerHolderComponent.hasPower((LivingEntity) (Object) this, Sucker.class)) {
            for (Sucker power : PowerHolderComponent.getPowers((LivingEntity) (Object) this, Sucker.class)) {
                power.tick();
            }
        }
    }


//    @ModifyReturnValue(method = "damage", at = @At("RETURN"))
//    private boolean onDamage(boolean bl) {
//        if (bl && PowerHolderComponent.hasPower((LivingEntity) (Object) this, Sucker.class)) {
//            for (Sucker sucker : PowerHolderComponent.getPowers((LivingEntity) (Object) this, Sucker.class)) {
//                Main.LOGGER.info("Sucker");
//                sucker.onDamage();
//                return true;
//            }
//            return true;
//        }
//        Main.LOGGER.info("Sucker i");
//        return bl;
//    }
}