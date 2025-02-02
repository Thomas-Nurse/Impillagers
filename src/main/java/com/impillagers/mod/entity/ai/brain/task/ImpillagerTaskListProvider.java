package com.impillagers.mod.entity.ai.brain.task;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.impillagers.mod.entity.custom.ImpillagerEntity;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import java.util.function.Predicate;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestTypes;

public class ImpillagerTaskListProvider {

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> createCoreTasks(VillagerProfession profession, float speed) {
        return ImmutableList.of(
                Pair.of(0, new StayAboveWaterTask(0.8F)),
                Pair.of(0, OpenDoorsTask.create()),
                Pair.of(0, new LookAroundTask(45, 90)),
                Pair.of(0, new PanicTask()),
                Pair.of(0, WakeUpTask.create()),
                Pair.of(0, HideWhenBellRingsTask.create()),
                Pair.of(0, StartRaidTask.create()),
                Pair.of(0, ForgetCompletedPointOfInterestTask.create(profession.heldWorkstation(), MemoryModuleType.JOB_SITE)),
                Pair.of(0, ForgetCompletedPointOfInterestTask.create(profession.acquirableWorkstation(), MemoryModuleType.POTENTIAL_JOB_SITE)),
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
    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> createIdleTasks(VillagerProfession profession, float speed) {
        return ImmutableList.of(
                Pair.of(
                        2,
                        new RandomTask<>(
                                ImmutableList.of(
                                        Pair.of(FindEntityTask.create(EntityType.VILLAGER, 8, MemoryModuleType.INTERACTION_TARGET, speed, 2), 2),
                                        Pair.of(
                                                FindEntityTask.create(EntityType.VILLAGER, 8, PassiveEntity::isReadyToBreed, PassiveEntity::isReadyToBreed, MemoryModuleType.BREED_TARGET, speed, 2), 1
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
    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> createFightTasks(VillagerEntity impillager, float speed) {
        return ImmutableList.of(
                        Pair.of(0, ForgetAttackTargetTask.create((Predicate<LivingEntity>)(target -> !isPreferredAttackTarget(impillager, target)))),
                        Pair.of(0, AttackTask.create(5, 0.75F)),
                        Pair.of(0, RangedApproachTask.create(1.0F)),
                        Pair.of(0, MeleeAttackTask.create(20)),
                        Pair.of(0, new CrossbowAttackTask()),
                        Pair.of(0, ForgetTask.create(ImpillagerTaskListProvider::getNearestVillager, MemoryModuleType.ATTACK_TARGET))
                );
    }
    private static boolean isPreferredAttackTarget(VillagerEntity impillager, LivingEntity target) {
        return getPreferredTarget(impillager).filter(preferredTarget -> preferredTarget == target).isPresent();
    }

    private static Optional<? extends LivingEntity> getPreferredTarget(VillagerEntity impillager) {
        Brain<VillagerEntity> brain = impillager.getBrain();
        if (getNearestVillager(impillager)) {
            return Optional.empty();
        } else {
            Optional<LivingEntity> optional = LookTargetUtil.getEntity(impillager, MemoryModuleType.ANGRY_AT);
            if (optional.isPresent() && Sensor.testAttackableTargetPredicateIgnoreVisibility(impillager, (LivingEntity) optional.get())) {
                return optional;
            }
        }
        return Optional.empty();
    }
    private static boolean getNearestVillager(VillagerEntity impillager) {
        Brain<VillagerEntity> brain = impillager.getBrain();
        if (brain.hasMemoryModule(com.impillagers.mod.entity.ai.brain.MemoryModuleType.NEAREST_VISIBLE_VILLAGER)) {
            LivingEntity livingEntity = (LivingEntity)brain.getOptionalRegisteredMemory(com.impillagers.mod.entity.ai.brain.MemoryModuleType.NEAREST_VISIBLE_VILLAGER).get();
            return impillager.isInRange(livingEntity, 6.0);
        } else {
            return false;
        }
    }
}
