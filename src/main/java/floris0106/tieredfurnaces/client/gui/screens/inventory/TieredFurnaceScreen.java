package floris0106.tieredfurnaces.client.gui.screens.inventory;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.BlastingRecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.SmokingRecipeBookComponent;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import floris0106.tieredfurnaces.TieredFurnaces;
import floris0106.tieredfurnaces.client.gui.screens.recipebook.FiringRecipeBookComponent;
import floris0106.tieredfurnaces.inventory.AbstractTieredFurnaceMenu;
import floris0106.tieredfurnaces.inventory.TieredBlastFurnaceMenu;
import floris0106.tieredfurnaces.inventory.TieredFurnaceMenu;
import floris0106.tieredfurnaces.inventory.TieredKilnMenu;
import floris0106.tieredfurnaces.inventory.TieredSmokerMenu;

@ParametersAreNonnullByDefault
public class TieredFurnaceScreen<T extends AbstractTieredFurnaceMenu> extends AbstractFurnaceScreen<T>
{
	private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/furnace.png");
	private static final ResourceLocation LIT_PROGRESS_SPRITE = ResourceLocation.withDefaultNamespace("container/furnace/lit_progress");
	private static final ResourceLocation BURN_PROGRESS_SPRITE = ResourceLocation.withDefaultNamespace("container/furnace/burn_progress");
	private static final ResourceLocation ENERGY_BUFFER_SPRITE = TieredFurnaces.resourceLocation("container/furnace/energy_buffer");
	private static final ResourceLocation STORED_ENERGY_SPRITE = TieredFurnaces.resourceLocation("container/furnace/stored_energy");

	private TieredFurnaceScreen(T menu, AbstractFurnaceRecipeBookComponent recipeBookComponent, Inventory playerInventory, Component title)
	{
		super(menu, recipeBookComponent, playerInventory, title, TEXTURE, LIT_PROGRESS_SPRITE, BURN_PROGRESS_SPRITE);
	}

	public static TieredFurnaceScreen<TieredFurnaceMenu> forFurnace(TieredFurnaceMenu menu, Inventory playerInventory, Component title)
	{
		return new TieredFurnaceScreen<>(menu, new SmeltingRecipeBookComponent(), playerInventory, title);
	}
	public static TieredFurnaceScreen<TieredBlastFurnaceMenu> forBlastFurnace(TieredBlastFurnaceMenu menu, Inventory playerInventory, Component title)
	{
		return new TieredFurnaceScreen<>(menu, new BlastingRecipeBookComponent(), playerInventory, title);
	}
	public static TieredFurnaceScreen<TieredSmokerMenu> forSmoker(TieredSmokerMenu menu, Inventory playerInventory, Component title)
	{
		return new TieredFurnaceScreen<>(menu, new SmokingRecipeBookComponent(), playerInventory, title);
	}
	public static TieredFurnaceScreen<TieredKilnMenu> forKiln(TieredKilnMenu menu, Inventory playerInventory, Component title)
	{
		return new TieredFurnaceScreen<>(menu, new FiringRecipeBookComponent(), playerInventory, title);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
	{
		super.render(guiGraphics, mouseX, mouseY, partialTick);

		int rightPos = leftPos + imageWidth;
		if (menu.isUsingPeltierElement() && mouseX > rightPos - 26 && mouseX < rightPos - 7 && mouseY > topPos + 6 && mouseY < topPos + 79)
			guiGraphics.renderTooltip(font, Language.getInstance().getVisualOrder(List.of(
				Component.translatable("container.abstracttieredfurnace.energy", menu.getEnergyStored(), menu.getMaxEnergyStored())
			)), mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY)
	{
		super.renderBg(guiGraphics, partialTick, mouseX, mouseY);

		if (menu.isUsingPeltierElement())
		{
			int rightPos = leftPos + imageWidth;
			int energyHeight = Mth.floor((float) menu.getEnergyStored() / menu.getMaxEnergyStored() * 70.0f);
			guiGraphics.blitSprite(ENERGY_BUFFER_SPRITE, rightPos - 25, topPos + 7, 18, 72);
			guiGraphics.blitSprite(STORED_ENERGY_SPRITE, 16, 70, 0, 70 - energyHeight, rightPos - 24, topPos + 78 - energyHeight, 16, energyHeight);
		}
	}
}