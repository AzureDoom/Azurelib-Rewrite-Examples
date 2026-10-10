# AzureLib Examples (Forge 1.12.2)

1.12.2 port of the AzureLib rewrite examples. Pulls AzureLib 1.12.2 from `https://maven.azuredoom.com/mods`
(`mod.azure.azurelib:AzureLib-Forge-1.12.2`, version set by `azurelib_version` in `gradle.properties`).

## What's in here

| Example | 1.12.2 notes |
|---|---|
| Marauder, Doom Hunter, Juravenator, Manul, Marine, Gremlin | Entities registered with `EntityEntryBuilder`; vanilla spawn eggs via `.egg(...)` |
| Creeper | Replaces the vanilla creeper renderer |
| Pistol, Peacemaker (gun with arms) | Items with `builtin/entity` models |
| Doomicorn armor | `ItemArmor` with diamond stats, animated through `onArmorTick` |
| Diamond sword / diamond armor | Replace the vanilla look (the 1.18 version replaced netherite, which 1.12.2 doesn't have) |
| Stargate | Block + `ITickable` tile entity + animated block item |

## Differences from 1.18.2

* Single Forge project; no common/fabric split, service loader or `DeferredRegister`.
* Registration through `RegistryEvent.Register` (`registry/RegistryEvents`), item models through `ModelRegistryEvent`.
* Renderers are registered in `proxy/ClientProxy` (entity renderers in pre-init, item/armor/TESR renderers in init).
* `AzIdentityRegistry.register(...)` runs in common init so server-created stacks get AzureLib IDs too.
* The netherite replacements became diamond replacements (`items/diamondreplace`); the chestplate's animation is
  driven by a `PlayerTickEvent` instead of a mixin on the armor item.
* AzureNavigation was not ported to AzureLib 1.12.2, so the Marauder uses vanilla ground navigation with
  `stepHeight = 2`.
* `DelayedAttackGoal` became `DelayedAttackAI` (extends `EntityAIAttackMelee`). It now starts the attack animation
  when the wind-up begins; the 1.18 version compared the counter after decrementing it, so the callback never fired.
* Custom spawn egg items, the data-fixer silencing mixin and the MixinExtras-based mixins are gone. The gun mixins
  were rewritten with plain Mixin `@Inject`/`@Redirect` against 1.12.2 classes (`EntityRenderer`, `ItemRenderer`,
  `Minecraft`, `EntityLivingBase`, `EntityVillager`).
* Lang is `en_us.lang`; blockstates use the `normal` variant.
