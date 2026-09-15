package redboy72.weirdchanges.client.screen;

import com.google.common.collect.Lists;
import com.jcraft.jogg.Packet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import redboy72.weirdchanges.WeirdChanges;
import redboy72.weirdchanges.client.control.Keys;

import java.util.concurrent.Executor;


@Environment(EnvType.CLIENT)
public class BlockPaletteMenuScreen extends Screen {
    private static final BlockPaletteMenuScreen INSTANCE = new BlockPaletteMenuScreen();
    protected final Executor screenExecutor = (_) -> {
    };

    private final int BUTTON_WIDTH = 20;
    private final int BUTTON_HEIGHT = 20;
    private final int RADIAL_MENU_SIZE = 50;
    private ItemStack[] resultItems;
    private ItemStack selectedItem;
    private ItemStack selectedResult;

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
        resultItems = null;
        var player = Minecraft.getInstance().player;
        if (player == null) { return; }
        selectedItem = player.getInventory().getSelectedItem();
        if (selectedItem.isEmpty()) {
            return;
        }
        if (!selectedItem.tags().toList().contains(WeirdChanges.PaletteItems.STONES.getTag()) ) {
            return;
        }
        var stone_items = Lists.newArrayList(BuiltInRegistries.ITEM.getTagOrEmpty(WeirdChanges.PaletteItems.STONES.getTag()));
        resultItems = new ItemStack[stone_items.size() - 1];
        super.init();

        for (var i = 0; i < stone_items.size(); i++) {
            var entry = stone_items.get(i);
            var newItemStack = new ItemStack(entry, selectedItem.getCount());
            if (newItemStack.getItem() == selectedItem.getItem()) { continue; }
            resultItems[i] = newItemStack;
        }
    }

    private void renderGUIBlock(GuiGraphicsExtractor graphics) {
        if (resultItems == null) { return; }
        for (int i = 0; i < resultItems.length; i++) {
            var item = resultItems[i];
            if (item == null || item.isEmpty()) { continue; }
            graphics.item(
                    item,
                    (int) ((double) Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + RADIAL_MENU_SIZE * Math.cos(Math.PI * 2 * i / (resultItems.length))),
                    (int) ((double) Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + RADIAL_MENU_SIZE * Math.sin(Math.PI * 2 * i / (resultItems.length)))
                    );

        }
//        var item = new ItemStack(Blocks.DIAMOND_BLOCK);
//        graphics.item(item, Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - BUTTON_WIDTH / 2, Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 - BUTTON_HEIGHT / 2);
    }

    @Override
    public boolean mouseDragged(final MouseButtonEvent event, final double dx, final double dy){
        if (!super.mouseDragged(event, dx, dy)) return false;
        var dir = new Vec2((float)dx, (float)dy).normalized();
        var up = new Vec2(1f, 0f);

        double dot = dir.dot(up);
        double det = ((dir.x * up.y) - (dir.y * up.x));
        double angle = (Math.toDegrees(Math.atan2(det, dot)) + 180) / 360;
        selectedResult = resultItems[(int) (angle * resultItems.length)];
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (!Keys.BLOCK_PALETTE.isKeyDown()) {
            onClose();
        }
    }

    @Override
    public void onClose(){
        var player = Minecraft.getInstance().player;
        player.getInventory().setSelectedItem(selectedResult);
        super.onClose();
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
