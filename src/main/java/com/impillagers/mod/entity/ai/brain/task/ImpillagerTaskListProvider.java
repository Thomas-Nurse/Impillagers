package com.impillagers.mod.entity.ai.brain.task;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.impillagers.mod.entity.ModEntities;
import com.impillagers.mod.entity.custom.ImpillagerEntity;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestTypes;

public class ImpillagerTaskListProvider {

    //----------------------------------------Tasks---------------------------------------
    //-------------------------------------Core Tasks-------------------------------------

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> createCoreTasks(VillagerProfession profession, float speed) {
        return ImmutableList.of(
                Pair.of(0, new StayAboveWaterTask(0.8F)),
                Pair.of(0, OpenDoorsTask.create()),
                Pair.of(0, new LookAroundTask(45, 90)),
                Pair.of(0, WakeUpTask.create()),
                Pair.of(0, HideWhenBellRingsTask.create()),
                Pair.of(0, StartRaidTask.create()),
                Pair.of(0, ForgetCompletedPointOfInterestTask.create(profession.heldWorkstation(), MemoryModuleType.JOB_SITE)),
                Pair.of(0, ForgetCompletedPointOfInterestTask.create(profession.acquirableWorkstation(), MemoryModuleType.POTENTIAL_JOB_SITE)),
                Pair.of(1, RangedApproachTask.create(1.0F)),
                Pair.of(1, MeleeAttackTask.create(20)),
                Pair.of(1, ForgetAttackTargetTask.create()),
                Pair.of(1, new MoveToTargetTask()),
                Pair.of(2, WorkStationCompetitionTask.create()),
                Pair.of(3, new FollowCustomerTask(speed)),
                Pair.of(5, WalkToNearestVisibleWantedItemTask.create(speed, false, 4)),
                Pair.of(
                        6,
                        FindPointOfInterestTask.create(profession.acquirableWorkstation(), MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, true, Optional.empty())
                ),
                Pair.of(7, new WalkTowardJobSiteTask(speed)),
                Pair.of(8, TakeJobSiteTask.create(speed)),
                Pair.of(10, FindPointOfInterestTask.create(poiType -> poiType.matchesKey(PointOfInterestTypes.HOME), MemoryModuleType.HOME, false, Optional.of((byte)14))),
                Pair.of(
                        10,
                        FindPointOfInterestTask.create(poiType -> poiType.matchesKey(PointOfInterestTypes.MEETING), MemoryModuleType.MEETING_POINT, true, Optional.of((byte)14))
                ),
                Pair.of(10, GoToWorkTask.create()),
                Pair.of(10, LoseJobOnSiteLossTask.create())
        );
    }

    //-------------------------------------Idle tasks-------------------------------------

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> createIdleTasks(VillagerProfession profession, float speed) {
        return ImmutableList.of(

                Pair.of(
                        2,
                        new RandomTask<>(
                                ImmutableList.of(
                                        Pair.of(FindEntityTask.create(ModEntities.IMPILLAGER, 8, MemoryModuleType.INTERACTION_TARGET, speed, 2), 2),
                                        Pair.of(
                                                FindEntityTask.create(ModEntities.IMPILLAGER, 8, PassiveEntity::isReadyToBreed, PassiveEntity::isReadyToBreed, MemoryModuleType.BREED_TARGET, speed, 2), 1
                                        ),
                                        Pair.of(FindEntityTask.create(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, speed, 2), 1),
                                        Pair.of(FindWalkTargetTask.create(speed), 1),
                                        Pair.of(GoTowardsLookTargetTask.create(speed, 2), 1),
                                        Pair.of(new JumpInBedTask(speed), 1),
                                        Pair.of(new WaitTask(30, 60), 1)
                                )
                        )
                ),
                Pair.of(3, new GiveGiftsToHeroTask(100)),
                Pair.of(3, FindInteractionTargetTask.create(EntityType.PLAYER, 4)),
                Pair.of(3, new HoldTradeOffersTask(400, 1600)),
                Pair.of(
                        3,
                        new CompositeTask<>(
                                ImmutableMap.of(),
                                ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET),
                                CompositeTask.Order.ORDERED,
                                CompositeTask.RunMode.RUN_ONE,
                                ImmutableList.of(Pair.of(new GatherItemsVillagerTask(), 1))
                        )
                ),
                Pair.of(
                        3,
                        new CompositeTask<>(
                                ImmutableMap.of(),
                                ImmutableSet.of(MemoryModuleType.BREED_TARGET),
                                CompositeTask.Order.ORDERED,
                                CompositeTask.RunMode.RUN_ONE,
                                ImmutableList.of(Pair.of(new VillagerBreedTask(), 1))
                        )
                ),
                createFreeFollowTask(),
                Pair.of(99, ScheduleActivityTask.create())
        );
    }
    private static Pair<Integer, Task<LivingEntity>> createFreeFollowTask() {
        return Pair.of(
                5,
                new RandomTask<>(
                        ImmutableList.of(
                                Pair.of(LookAtMobTask.create(EntityType.CAT, 8.0F), 8),
                                Pair.of(LookAtMobTask.create(EntityType.VILLAGER, 8.0F), 2),
                                Pair.of(LookAtMobTask.create(EntityType.PLAYER, 8.0F), 2),
                                Pair.of(LookAtMobTask.create(SpawnGroup.CREATURE, 8.0F), 1),
                                Pair.of(LookAtMobTask.create(SpawnGroup.WATER_CREATURE, 8.0F), 1),
                                Pair.of(LookAtMobTask.create(SpawnGroup.AXOLOTLS, 8.0F), 1),
                                Pair.of(LookAtMobTask.create(SpawnGroup.UNDERGROUND_WATER_CREATURE, 8.0F), 1),
                                Pair.of(LookAtMobTask.create(SpawnGroup.WATER_AMBIENT, 8.0F), 1),
                                Pair.of(LookAtMobTask.create(SpawnGroup.MONSTER, 8.0F), 1),
                                Pair.of(new WaitTask(30, 60), 2)
                        )
                )
        );
    }

    //-------------------------------------Attack Enemy-------------------------------------

    public static void onAttacked(ImpillagerEntity impillager, LivingEntity attacker) {
        Brain<VillagerEntity> brain = impillager.getBrain();
        brain.forget(MemoryModuleType.PACIFIED);
        brain.forget(MemoryModuleType.BREED_TARGET);
        if (impillager.isBaby()) {
            avoidEnemy(impillager, attacker);
        } else {
            targetEnemy(impillager, attacker);
        }
    }

    private static void targetEnemy(ImpillagerEntity impillager, LivingEntity target) {
        if (!impillager.getBrain().hasActivity(Activity.AVOID)) {
            if (target.getType() != ModEntities.IMPILLAGER) {
                if (!LookTargetUtil.isNewTargetTooFar(impillager, target, 4.0)) {
                    if (Sensor.testAttackableTargetPredicate(impillager, target)) {
                        setAttackTarget(impillager, target);
                    }
                }
            }
        }
    }

    private static void setAttackTarget(ImpillagerEntity impillager, LivingEntity target) {
        Brain<VillagerEntity> brain = impillager.getBrain();
        brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.remember(MemoryModuleType.ATTACK_TARGET, target, 40L);
    }

    //-------------------------------------Avoid Enemy-------------------------------------


    private static final UniformIntProvider AVOID_MEMORY_DURATION = TimeHelper.betweenSeconds(5, 20);

    private static void avoidEnemy(ImpillagerEntity impillager, LivingEntity target) {
        Brain<VillagerEntity> brain = impillager.getBrain();
        LivingEntity livingEntity = LookTargetUtil.getCloserEntity(impillager, brain.getOptionalRegisteredMemory(MemoryModuleType.AVOID_TARGET), target);
        livingEntity = LookTargetUtil.getCloserEntity(impillager, brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET), livingEntity);
        avoid(impillager, livingEntity);
    }

    private static void avoid(ImpillagerEntity impillager, LivingEntity target) {
        impillager.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
        impillager.getBrain().forget(MemoryModuleType.WALK_TARGET);
        impillager.getBrain().remember(MemoryModuleType.AVOID_TARGET, target, (long)AVOID_MEMORY_DURATION.get(impillager.getWorld().random));
    }
}
