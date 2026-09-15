package redboy72.weirdchanges.client.screen;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import redboy72.weirdchanges.WeirdChanges;
import redboy72.weirdchanges.client.control.Keys;
import redboy72.weirdchanges.networking.blockPalette.ServerBoundPickBlockPayload;

import java.util.concurrent.Executor;


@Environment(EnvType.CLIENT)
public class BlockPaletteMenuScreen extends Screen {
    private static final BlockPaletteMenuScreen INSTANCE = new BlockPaletteMenuScreen();
    protected final Executor screenExecutor = (_) -> {
    };

    private final int BUTTON_WIDTH = 20;
    private final int BUTTON_HEIGHT = 20;

    public BlockPaletteMenuScreen() {
        super(Component.translatable(String.join(".", WeirdChanges.MOD_ID, "screen", "radial_menu")));
    }

    public static BlockPaletteMenuScreen getInstance() {
        return INSTANCE;
    }

    public static void playMenuSound() {
        SoundManager soundManager = Minecraft.getInstance().getSoundManager();
        soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1f));
    }

    @Override
    protected void init() {
        var player = Minecraft.getInstance().player;
        if (player == null) { return; }
        var oldItemStack = player.getInventory().getSelectedItem();
        if (oldItemStack.isEmpty()) {
            return;
        }
        if (!oldItemStack.tags().toList().contains(WeirdChanges.PaletteItems.STONES.getTag()) ) {
            return;
        }
        var stone_items = Lists.newArrayList(BuiltInRegistries.ITEM.getTagOrEmpty(WeirdChanges.PaletteItems.STONES.getTag()));

        if (stone_items.isEmpty()) { return; }
        super.init();

        var xMin = 0;
        var yMin = 0;
        for (var i = 0; i < stone_items.size(); i++) {
            var entry = stone_items.get(i);
            var newItemStack = new ItemStack(entry, oldItemStack.getCount());

            int posX = xMin + i % 4 * 16;
            int row = i / 4;
            int posY = yMin + row * 18 + 2;
            Button buttonWidget = Button.builder(Component.literal(newItemStack.getItemName().getString()), (btn) -> {
                        ClientPlayNetworking.send(new ServerBoundPickBlockPayload(newItemStack));
                        player.getInventory().setSelectedItem(newItemStack);
                })
                    .bounds(
                            (int) posX /*(Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - BUTTON_WIDTH / 2)*/,
                            (int) posY /*(Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 - BUTTON_HEIGHT / 2)*/,
                            BUTTON_WIDTH,
                            BUTTON_HEIGHT)
                    .build();
            this.addRenderableWidget(buttonWidget);
        }
    }

    private void renderGUIBlock(GuiGraphicsExtractor graphics) {
        var item = new ItemStack(Blocks.DIAMOND_BLOCK);
        graphics.item(item, Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - BUTTON_WIDTH / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 - BUTTON_HEIGHT / 2);
    }


    @Override
    public void tick() {
        super.tick();

        if (!Keys.BLOCK_PALETTE.isKeyDown()) {
            onClose();
            return;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
    }

    @Override
    public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        renderGUIBlock(graphics);
    }
}
