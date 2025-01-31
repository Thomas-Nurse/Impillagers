package com.impillagers.mod.entity.custom;

import com.impillagers.mod.entity.ModEntities;
import com.impillagers.mod.item.ModItems;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.TradeOffer;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ImpillagerEntity extends MerchantEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public ImpillagerEntity(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0,new SwimGoal(this));

        this.goalSelector.add(1,new TemptGoal(this, 1.25D, Ingredient.ofItems(ModItems.PINK_GARNET), true));

        this.goalSelector.add(2, new WanderAroundFarGoal(this,1.0D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 4.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 18)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1);
    }

    private void setupAnimationStates(){
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
    }

    @Override
    protected void afterUsing(TradeOffer offer) {

    }

    @Override
    protected void fillRecipes() {

    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.IMPILLAGER.create(world);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(ModItems.IMPILLAGER_SPAWN_EGG) || !this.isAlive() || this.hasCustomer() || this.isSleeping()) {
            return super.interactMob(player, hand);
        } else if (this.isBaby()) {
            this.sayNo();
            return ActionResult.success(this.getWorld().isClient);
        } else {
            if (!this.getWorld().isClient) {
                boolean bl = this.getOffers().isEmpty();
                if (hand == Hand.MAIN_HAND) {
                    if (bl) {
                        this.sayNo();
                    }

                    //player.incrementStat(Stats.TALKED_TO_VILLAGER);
                }

                if (bl) {
                    return ActionResult.CONSUME;
                }

                this.beginTradeWith(player);
            }

            return ActionResult.success(this.getWorld().isClient);
        }
    }

    private void sayNo() {
        this.setHeadRollingTimeLeft(40);
        if (!this.getWorld().isClient()) {
            this.playSound(SoundEvents.ENTITY_VILLAGER_NO);
        }
    }

    private void beginTradeWith(PlayerEntity customer) {
        this.prepareOffersFor(customer);
        this.setCustomer(customer);
        this.sendOffers(customer, this.getDisplayName(), /*this.getVillagerData().getLevel()*/ 1);
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity customer) {
        boolean bl = this.getCustomer() != null && customer == null;
        super.setCustomer(customer);
        if (bl) {
            this.resetCustomer();
        }
    }
    private void prepareOffersFor(PlayerEntity player) {
        int i = 1; //this.getReputation(player);
        if (i != 0) {
            for (TradeOffer tradeOffer : this.getOffers()) {
                tradeOffer.increaseSpecialPrice(-MathHelper.floor((float) i * tradeOffer.getPriceMultiplier()));
            }
        }
    }
}
