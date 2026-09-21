package com.souls.starcraft.client;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;

public class ModKeyMappings {
    public static final KeyMapping PREVIOUS_SPELL = new KeyMapping(
        "key.starcraft.previous_spell", 
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_LEFT_BRACKET,
        "key.categories.starcraft"
    );

    public static final KeyMapping NEXT_SPELL = new KeyMapping(
        "key.starcraft.next_spell", 
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_RIGHT_BRACKET,
        "key.categories.starcraft"
    );
}
