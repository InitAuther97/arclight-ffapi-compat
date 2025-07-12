package io.github.initauther97.afc.mixin;

import io.izzel.arclight.common.bridge.core.network.login.ServerLoginPacketListenerBridge;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.impl.networking.NetworkHandlerExtensions;
import net.fabricmc.fabric.impl.networking.payload.PacketByteBufLoginQueryResponse;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.login.ServerboundCustomQueryAnswerPacket;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("UnstableApiUsage")
@Mixin(ServerLoginPacketListenerImpl.class)
public abstract class ServerLoginPacketListenerImplMixin implements ServerLoginPacketListenerBridge, NetworkHandlerExtensions {

    @Shadow @Final private static Logger LOGGER;

    @Override
    public FriendlyByteBuf arclight$platform$customQAData(ServerboundCustomQueryAnswerPacket packet) {
        // The payload is fully consumed during its handler if it's not understood.
        // Otherwise it depends on the custom handler.
        // Arclight:
        return packet.payload() instanceof PacketByteBufLoginQueryResponse(ByteBuf buf)
                ? new FriendlyByteBuf(Unpooled.wrappedBuffer(Unpooled.copyBoolean(true), buf.readerIndex(0))) : ServerLoginPacketListenerBridge.super.arclight$platform$customQAData(packet);
    }

    @Override
    public void arclight$platform$onCustomQA(ServerboundCustomQueryAnswerPacket payload) {
        // Do nothing: they already know
        // ((ServerLoginNetworkAddon) getAddon()).handle(payload);
    }
}
