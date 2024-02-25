package com.afoxxvi.asteorbar.internal;

import com.afoxxvi.asteorbar.AsteorBar;
import com.afoxxvi.asteorbar.AsteorBarForge;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class InternalNetworkHandler {
    private static final byte INDEX_SERVER_REQUEST_PUB_KEY = 0;
    private static final byte INDEX_CLIENT_SEND_PUB_KEY = 1;
    private static final byte INDEX_SERVER_SEND_CHALLENGE = 2;
    private static final byte INDEX_CLIENT_SEND_RESPONSE = 3;
    public static final SimpleChannel INTERNAL = NetworkRegistry.newSimpleChannel(new ResourceLocation(AsteorBar.MOD_ID, "internal"), () -> "1.0", s -> true, s -> true);

    public static final SimpleChannel SECURITY = NetworkRegistry.newSimpleChannel(new ResourceLocation(AsteorBar.MOD_ID, "security"), () -> "1.0", s -> true, s -> true);

    public static void init() {
        INTERNAL.registerMessage(0, Object.class, null, InternalInfo::decodeActivate, InternalNetworkHandler::ignoredHandle);
        INTERNAL.registerMessage(1, Object.class, null, InternalInfo::decodeStatus, InternalNetworkHandler::ignoredHandle);
        INTERNAL.registerMessage(2, Object.class, null, InternalInfo::decodeInfo, InternalNetworkHandler::ignoredHandle);
        INTERNAL.registerMessage(3, Object.class, null, InternalInfo::decodeHelmet, InternalNetworkHandler::ignoredHandle);
        INTERNAL.registerMessage(4, Object.class, null, InternalInfo::decodeToggle, InternalNetworkHandler::ignoredHandle);

        INTERNAL.registerMessage(128, UseSkillPacket.class, UseSkillPacket::encode, null, null);

        SECURITY.registerMessage(INDEX_SERVER_REQUEST_PUB_KEY, RequestPubKeyPacket.class, null, RequestPubKeyPacket::decode, RequestPubKeyPacket::handle);
        SECURITY.registerMessage(INDEX_CLIENT_SEND_PUB_KEY, SendPubKeyPacket.class, SendPubKeyPacket::encode, null, null);
        SECURITY.registerMessage(INDEX_SERVER_SEND_CHALLENGE, ChallengePacket.class, null, ChallengePacket::decode, ChallengePacket::handle);
        SECURITY.registerMessage(INDEX_CLIENT_SEND_RESPONSE, SendResponsePacket.class, SendResponsePacket::encode, null, null);
    }

    @SuppressWarnings("unused")
    private static void ignoredHandle(Object packet, Supplier<NetworkEvent.Context> context) {
        context.get().setPacketHandled(true);
    }

    public static class RequestPubKeyPacket {
        private boolean regen = false;

        public RequestPubKeyPacket(boolean regen) {
            this.regen = regen;
        }

        public static RequestPubKeyPacket decode(FriendlyByteBuf buffer) {
            return new RequestPubKeyPacket(buffer.readBoolean());
        }

        public static void handle(RequestPubKeyPacket packet, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                byte[] publicKey = SecurityInfo.getPublicKey(packet.regen);
                SECURITY.sendToServer(new SendPubKeyPacket(publicKey));
            });
            context.get().setPacketHandled(true);
        }
    }

    public static class SendPubKeyPacket {
        byte[] pubKey;

        public SendPubKeyPacket(byte[] pubKey) {
            this.pubKey = pubKey;
        }

        public static void encode(SendPubKeyPacket packet, FriendlyByteBuf buffer) {
            buffer.writeByteArray(packet.pubKey);
        }
    }

    public static class ChallengePacket {
        byte[] challenge;

        public ChallengePacket(byte[] challenge) {
            this.challenge = challenge;
        }

        public static ChallengePacket decode(FriendlyByteBuf buffer) {
            return new ChallengePacket(buffer.readByteArray());
        }

        public static void handle(ChallengePacket packet, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                //AsteorBarForge.LOGGER.info("Receiving challenge: " + new String(packet.challenge));
                byte[] response = SecurityInfo.handleChallenge(packet.challenge);
                //AsteorBarForge.LOGGER.info("Send back: " + new String(response));
                SECURITY.sendToServer(new SendResponsePacket(response));
            });
            context.get().setPacketHandled(true);
        }
    }

    public static class SendResponsePacket {
        byte[] response;

        public SendResponsePacket(byte[] response) {
            this.response = response;
        }

        public static void encode(SendResponsePacket packet, FriendlyByteBuf buffer) {
            buffer.writeByteArray(packet.response);
        }
    }

    public static class UseSkillPacket {
        int index;

        public UseSkillPacket(int index) {
            this.index = index;
        }

        public static void encode(UseSkillPacket packet, FriendlyByteBuf buffer) {
            buffer.writeByte(packet.index);
        }
    }
}
