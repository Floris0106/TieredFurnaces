package floris0106.tieredfurnaces.data;

import floris0106.tieredfurnaces.TieredFurnaces;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class PoiTypeTags extends PoiTypeTagsProvider
{
    public PoiTypeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper fileHelper)
    {
        super(output, lookup, TieredFurnaces.MODID, fileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider)
    {
        tag(net.minecraft.tags.PoiTypeTags.ACQUIRABLE_JOB_SITE).add(TieredFurnaces.KILN_POI_TYPE.getKey());
    }
}