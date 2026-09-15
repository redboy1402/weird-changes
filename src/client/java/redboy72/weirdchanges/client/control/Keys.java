package redboy72.weirdchanges.client.control;


import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import redboy72.weirdchanges.WeirdChanges;

import static net.minecraft.resources.Identifier.fromNamespaceAndPath;

public enum Keys {

    BLOCK_PALETTE("block_palette", InputConstants.KEY_LALT);

    private final String description;
    private final int key;
    private KeyMapping keyMapping;
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(fromNamespaceAndPath(WeirdChanges.MOD_ID, "keys"));


    Keys(String description, int defaultKey){
        this.description = String.join(".", "key", WeirdChanges.MOD_ID, description, "desc");
        this.key = defaultKey;
    }


    public static void init(){
        register();
    }

    private static void register(){
        for (Keys key : values()) {
            key.keyMapping = new KeyMapping(key.description, InputConstants.Type.KEYSYM, key.key, CATEGORY);
            KeyMappingHelper.registerKeyMapping(key.keyMapping);
        }
    }

    public KeyMapping getKeyMapping() {
        return keyMapping;
    }

    public boolean isDown() {
        return keyMapping.isDown();
    }

    public boolean isKeyDown() {
        return isKeyDown(keyMapping.getDefaultKey().getValue());
    }

    public static boolean isKeyDown(int key) {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), key);
    }
}


