package redboy72.weirdchanges.client.screen;

import com.google.common.collect.Lists;
import com.jcraft.jogg.Packet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
import redboy72.weirdchanges.networking.blockPalette.ServerBoundPickBlockPayload;

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
    private ItemStack selectedItem = ItemStack.EMPTY;
    private ItemStack selectedResult = ItemStack.EMPTY;
    private Vec2 SCREEN_CENTER;

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
        SCREEN_CENTER = new Vec2((float) Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2, (float) Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2);
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
        resultItems = new ItemStack[stone_items.size()];
        super.init();

        for (var i = 0; i < stone_items.size(); i++) {
            var entry = stone_items.get(i);
            var newItemStack = new ItemStack(entry, selectedItem.getCount());
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
                    (int) ((double) Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + RADIAL_MENU_SIZE * Math.cos(Math.PI * 2 * i / (resultItems.length)) - 8),
                    (int) ((double) Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2 + RADIAL_MENU_SIZE * Math.sin(Math.PI * 2 * i / (resultItems.length)) - 8)
                    );
        }
    }

    @Override
    public boolean mouseClicked(final MouseButtonEvent event, final boolean doubleClick) {
        var dir = new Vec2(
                (float)(event.x() - SCREEN_CENTER.x),
                (float)(event.y() - SCREEN_CENTER.y)
        ).normalized();

        double segment = Math.PI * 2 / resultItems.length;
        double angle = Math.atan2(dir.y, dir.x);

        if (angle < 0) {
            angle += Math.PI * 2;
        }

        int index = (int)((angle + segment / 2) / segment) % resultItems.length;
        selectedResult = resultItems[index];

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
        if (player != null && selectedResult != null && !selectedResult.isEmpty()) {
            player.getInventory().setSelectedItem(selectedResult);
            ClientPlayNetworking.send(new ServerBoundPickBlockPayload(selectedResult));
        }
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
