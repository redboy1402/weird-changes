package redboy72.weirdchanges.client.entity.MiniGolemEntity;// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.brigadier.Command;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.commands.CommandSourceStack;

public class CustomModel extends EntityModel<MiniGolemEntityRenderState> {
	private Command<CommandSourceStack> stack;
	public CustomModel(ModelPart root) {
        super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(36, 0).addBox(-3.0F, -30.0F, -2.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-6.0F, -23.0F, -5.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(48, 12).addBox(3.0F, -10.0F, -1.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(48, 12).addBox(7.0F, -22.0F, -1.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(48, 12).addBox(-11.0F, -22.0F, -1.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(48, 12).addBox(-7.0F, -10.0F, -1.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}
}