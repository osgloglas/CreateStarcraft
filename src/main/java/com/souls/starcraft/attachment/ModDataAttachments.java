package com.souls.starcraft.attachment;

import java.util.function.Supplier;

import com.souls.starcraft.StarCraft;
import com.souls.starcraft.constellation.CelestialKnowledge;
import com.souls.starcraft.mana.StarlightMana;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModDataAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(
        NeoForgeRegistries.ATTACHMENT_TYPES, 
        StarCraft.MODID
    );

    public static final Supplier<AttachmentType<StarlightMana>> STARLIGHT_MANA = ATTACHMENT_TYPES.register(
        "starlight_mana", () -> AttachmentType.builder(StarlightMana::new)
            .serialize(StarlightMana.CODEC)
            .build()
    );

    public static final Supplier<AttachmentType<CelestialKnowledge>> CELESTIAL_KNOWLEDGE = ATTACHMENT_TYPES.register(
        "celestial_knowledge", () -> AttachmentType.builder(CelestialKnowledge::new)
            .serialize(CelestialKnowledge.CODEC)
            .build());

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
