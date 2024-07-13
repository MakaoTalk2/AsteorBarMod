package com.afoxxvi.asteorbar.internal;


import com.afoxxvi.asteorbar.AsteorBar;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class InternalNetworkHandler {
    private static final ResourceLocation INTERNAL = new ResourceLocation(AsteorBar.MOD_ID, "internal");
    private static final ResourceLocation SECURITY = new ResourceLocation(AsteorBar.MOD_ID, "security");
    private static final byte INDEX_SERVER_REQUEST_PUB_KEY = 0;
    private static final byte INDEX_CLIENT_SEND_PUB_KEY = 1;
    private static final byte INDEX_SERVER_SEND_CHALLENGE = 2;
    private static final byte INDEX_CLIENT_SEND_RESPONSE = 3;
    private static final byte INDEX_SERVER_SEND_ACTIVATE = 4;

    @Environment(EnvType.CLIENT)
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(INTERNAL, (client, handler, buf, responseSender) -> {
            byte index = buf.readByte();
            switch (index) {
                case 0 -> InternalInfo.decodeActivate(buf);
                case 1 -> InternalInfo.decodeStatus(buf);
                case 2 -> InternalInfo.decodeInfo(buf);
                case 3 -> InternalInfo.decodeHelmet(buf);
                case 4 -> InternalInfo.decodeToggle(buf);
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(SECURITY, (client, handler, buf, responseSender) -> {
            byte index = buf.readByte();
            switch (index) {
                case INDEX_SERVER_REQUEST_PUB_KEY -> handleServerRequestPubKey(buf);
                case INDEX_SERVER_SEND_CHALLENGE -> handleServerSendChallenge(buf);
            }
        });
    }

    public static void sendUseSkillPacket(int skillIndex) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeByte(64);
        buf.writeByte(skillIndex);
        ClientPlayNetworking.send(INTERNAL, buf);
    }

    public static void sendRushPacket(int direction) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeByte(65);
        buf.writeByte(direction);
        ClientPlayNetworking.send(INTERNAL, buf);
    }

    private static void handleServerRequestPubKey(FriendlyByteBuf buf) {
        boolean regen = buf.readBoolean();
        byte[] publicKey = SecurityInfo.getPublicKey(regen);
        FriendlyByteBuf buf2 = new FriendlyByteBuf(Unpooled.buffer());
        buf2.writeByte(INDEX_CLIENT_SEND_PUB_KEY);
        buf2.writeByteArray(publicKey);
        ClientPlayNetworking.send(SECURITY, buf2);
    }

    private static void handleServerSendChallenge(FriendlyByteBuf buf) {
        byte[] challenge = buf.readByteArray();
        byte[] response = SecurityInfo.handleChallenge(challenge);
        FriendlyByteBuf buf2 = new FriendlyByteBuf(Unpooled.buffer());
        buf2.writeByte(INDEX_CLIENT_SEND_RESPONSE);
        buf2.writeByteArray(response);
        ClientPlayNetworking.send(SECURITY, buf2);
    }
}
