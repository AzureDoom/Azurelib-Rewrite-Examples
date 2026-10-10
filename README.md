# AzureLib Examples (Forge 1.7.10)

1.7.10 port of the AzureLib rewrite examples. Pulls AzureLib 1.7.10's deobfuscated `dev` jar from
`https://maven.azuredoom.com/mods` (`mod.azure.azurelib:azurelib-forge-1.7.10:<azurelib_version>:dev`). Players need
AzureLib and UniMixins installed.

| Example | 1.7.10 notes |
|---|---|
| Marauder, Doom Hunter, Juravenator, Manul, Marine, Gremlin | Registered with `EntityRegistry.registerModEntity`; spawned with the mod's own spawn egg item (one damage value per entity) |
| Creeper | Replaces the vanilla creeper renderer |
| Pistol, Peacemaker (gun with arms) | AzureLib registers a Forge `IItemRenderer`; display transforms come from `models/item/<name>.json` |
| Doomicorn armor | `ItemArmor` with diamond stats, animated through `onArmorTick` |
| Diamond sword / diamond armor | Replace the vanilla look |
| Stargate | `BlockContainer` + ticking tile entity + animated block item |

## Differences from the 1.12.2 examples

* Everything is registered in pre-init (`GameRegistry`, `EntityRegistry`); renderers are registered as instances in
  the client proxy's init.
* Mobs that use the task-based AI override `isAIEnabled()` to return `true`, as 1.7.10 requires.
* The Marauder's and Doom Hunter's "aggressive" state is synced to the client through the `DataWatcher`, because the
  attack target only exists on the server. (On 1.12.2 the client-side check was always false, so the Marauder never
  played its run animation.)
* `DelayedAttackAI` is a self-contained melee task; 1.7.10's `EntityAIAttackOnCollide` keeps its state private.
* 1.7.10 has no item cooldowns, so the Peacemaker tracks its fire rate per player itself.
* Sounds are played by name (`azexamples:vigilance_fire`, vanilla `step.stone`, `mob.skeleton.step`, ...).
* The gun mixins target 1.7.10 methods: `EntityRenderer.setupViewBobbing`, `ItemRenderer.updateEquippedItem`,
  `Minecraft` left-click handling (by MCP and SRG name), `EntityLivingBase.swingItem`, `EntityVillager.interact`.
* Item icons for the Doomicorn armor and the diamond armor replacement live in `textures/items`, as 1.7.10 expects.
