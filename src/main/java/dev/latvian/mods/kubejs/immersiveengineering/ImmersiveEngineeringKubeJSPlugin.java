package dev.latvian.mods.kubejs.immersiveengineering;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.api.crafting.*;
import blusunrize.immersiveengineering.api.energy.ThermoelectricSource;
import blusunrize.immersiveengineering.api.energy.WindmillBiome;
import blusunrize.immersiveengineering.api.multiblocks.MultiblockHandler;
import blusunrize.immersiveengineering.common.util.RecipeSerializers;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.event.EventResult;
import dev.latvian.mods.kubejs.immersiveengineering.event.IEEvents;
import dev.latvian.mods.kubejs.immersiveengineering.event.MultiblockFormEventJS;
import dev.latvian.mods.kubejs.immersiveengineering.recipe.*;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

public class ImmersiveEngineeringKubeJSPlugin extends KubeJSPlugin {
	@Override
	public void init() {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.addListener(ImmersiveEngineeringKubeJSPlugin::onMultiblockForm);
	}

	@Override
	public void registerEvents() {
		IEEvents.GROUP.register();
	}

	@Override
	public void registerRecipeTypes(RegisterRecipeSchemasEvent event) {
		event.namespace(ImmersiveEngineering.MODID)
				.shaped(RecipeSerializers.TURN_AND_COPY_SERIALIZER.getId().getPath())
				.register(name(AlloyRecipe.SERIALIZER), AlloyRecipeSchema.SCHEMA)
				.register(name(ArcFurnaceRecipe.SERIALIZER), ArcFurnaceRecipeSchema.SCHEMA)
				.register(name(BlastFurnaceFuel.SERIALIZER), BlastFurnaceFuelRecipeSchema.SCHEMA)
				.register(name(BlastFurnaceRecipe.SERIALIZER), BlastFurnaceRecipeSchema.SCHEMA)
				.register(name(BlueprintCraftingRecipe.SERIALIZER), BlueprintRecipeSchema.SCHEMA)
				.register(name(BottlingMachineRecipe.SERIALIZER), BottlingRecipeSchema.SCHEMA)
				.register(name(ClocheFertilizer.SERIALIZER), ClocheFertilizerRecipeSchema.SCHEMA)
				// .register(name(ClocheRecipe.SERIALIZER), ClocheRecipeSchema.SCHEMA) // FIXME: ClocheRecipeSchema
				.register(name(CokeOvenRecipe.SERIALIZER), CokeOvenRecipeSchema.SCHEMA)
				.register(name(CrusherRecipe.SERIALIZER), CrusherRecipeSchema.SCHEMA)
				.register(name(FermenterRecipe.SERIALIZER), FermenterRecipeSchema.SCHEMA)
				.register(name(MetalPressRecipe.SERIALIZER), MetalPressRecipeSchema.SCHEMA)
				// .register(name("immersiveengineering:mineral_mix"), MineralMixRecipeSchema.SCHEMA)
				.register(name(MixerRecipe.SERIALIZER), MixerRecipeSchema.SCHEMA)
				.register(name(RefineryRecipe.SERIALIZER), RefineryRecipeSchema.SCHEMA)
				.register(name(SawmillRecipe.SERIALIZER), SawmillRecipeSchema.SCHEMA)
				.register(name(SqueezerRecipe.SERIALIZER), SqueezerRecipeSchema.SCHEMA)
				.register(name(ThermoelectricSource.SERIALIZER), ThermoelectricSourceSchema.SCHEMA)
				.register(name(WindmillBiome.SERIALIZER), WindmillBiomeSchema.SCHEMA)
		;
	}

	private static String name(RegistryObject<?> reg) {
		return reg.getId().getPath();
	}

	@SubscribeEvent
	public static void onMultiblockForm(MultiblockHandler.MultiblockFormEvent event) {
		var result = IEEvents.MULTIBLOCK_FORM.post(ScriptType.STARTUP, event.getMultiblock().getUniqueName(), new MultiblockFormEventJS(event));
		if (result.type() == EventResult.Type.INTERRUPT_FALSE) {
			event.setCanceled(true);
		}
	}
}
