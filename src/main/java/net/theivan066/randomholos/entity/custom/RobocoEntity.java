package net.theivan066.randomholos.entity.custom;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.theivan066.randomholos.entity.ai.RobocoAttackGoal;
import net.theivan066.randomholos.entity.variant.RobocoVariant;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RobocoEntity extends Animal implements RangedAttackMob {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(RobocoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT =
            SynchedEntityData.defineId(RobocoEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> BOLTED =
            SynchedEntityData.defineId(RobocoEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState attackLoadedAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    public final AnimationState idleAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;

    public RobocoEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new RobocoAttackGoal(this, 1.2, 1, 32));

        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes().add(Attributes.MAX_HEALTH, 68.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 32D)
                .add(Attributes.ATTACK_DAMAGE, 1f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.05f);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = this.entityData.get(BOLTED) ? 30 : 43; // Length in ticks of your animation
            if (this.entityData.get(BOLTED)) {
                attackLoadedAnimationState.start(this.tickCount);
            } else {
                attackAnimationState.start(this.tickCount);
                this.entityData.set(BOLTED, true);
            }
        } else {
            --this.attackAnimationTimeout;
            this.entityData.set(BOLTED, false);
        }

        if (!this.isAttacking()) {
            attackAnimationState.stop();
            attackLoadedAnimationState.stop();
            this.entityData.set(BOLTED, false);
        }
    }

    protected void updateWalkAnimation(float v) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(v * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }
        this.walkAnimation.update(f, 0.2F);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
        this.entityData.define(BOLTED, false);
    }


    //VARIANT
    public RobocoVariant getVariant() {
        return RobocoVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(RobocoVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason,
                                        @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        RobocoVariant variant = Util.getRandom(RobocoVariant.values(), this.random);
        this.setVariant(variant);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(DATA_ID_TYPE_VARIANT, pCompound.getInt("Variant"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getTypeVariant());
    }

    //Mob-specifics
    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        Random ran = new Random();

        BulletProjectileEntity bullet = new BulletProjectileEntity(this.level(), this, 40, 1);
        this.lookControl.setLookAt(pTarget.getX(), pTarget.getY(), pTarget.getZ());

        bullet.setPos(this.getX(), this.getEyeY(), this.getZ());
        float vel = 35;
        Vec3 lookDirect = this.getLookAngle().scale(vel);

        bullet.setDeltaMovement(lookDirect.x + ran.nextFloat(-1f, 1f), lookDirect.y , lookDirect.z + ran.nextFloat(-1f, 1f));
        bullet.setOwner(this);

        this.level().addFreshEntity(bullet);
    }
}

