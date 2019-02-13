package net.hydonclient.mixins.client.renderer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(BlockModelShapes.class)
public abstract class MixinBlockModelShapes {

    @Shadow @Final private BlockStateMapper blockStateMapper;
    @Shadow @Final private ModelManager modelManager;
    private Map<IBlockState, ModelResourceLocation> modelLocations;

    /**
     * @author Runemoro
     * @reason Don't get all models during initialization, instead store location
     */
    @Overwrite
    public void reloadModels() {
        modelLocations = blockStateMapper.putAllStateModelLocations();
    }

    /**
     * @author Runemoro
     * @reason Get the stored location for the state, and get the model location from the manager
     */
    @Overwrite
    public IBakedModel getModelForState(IBlockState state) {
        IBakedModel model = modelManager.getModel(modelLocations.get(state));

        if (model == null) {
            model = modelManager.getMissingModel();
        }

        return model;
    }
}
