package floris0106.tieredfurnaces.data;

import floris0106.tieredfurnaces.TieredFurnaces;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class ItemModels extends ItemModelProvider
{
    public ItemModels(PackOutput output, ExistingFileHelper fileHelper)
    {
        super(output, TieredFurnaces.MODID, fileHelper);
    }

    @Override
    protected void registerModels()
    {
        TieredFurnaces.UPGRADE_ITEMS.values().forEach(item -> handheldItem(Objects.requireNonNull(item).get()));
		handheldItem(TieredFurnaces.PELTIER_ELEMENT_ITEM.get());
    }
}