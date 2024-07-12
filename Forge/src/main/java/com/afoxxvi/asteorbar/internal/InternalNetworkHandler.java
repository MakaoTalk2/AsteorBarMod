package com.afoxxvi.asteorbar.internal;

import com.afoxxvi.asteorbar.AsteorBar;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

import java.util.function.BiConsumer;

public class InternalNetworkHandler {
    private static final byte INDEX_SERVER_REQUEST_PUB_KEY = 0;
    private static final byte INDEX_CLIENT_SEND_PUB_KEY = 1;
    private static final byte INDEX_SERVER_SEND_CHALLENGE = 2;
    private static final byte INDEX_CLIENT_SEND_RESPONSE = 3;
    private static final byte INDEX_SERVER_SEND_ACTIVATE = 4;
    public static final SimpleChannel INTERNAL = ChannelBuilder
            .named(new ResourceLocation(AsteorBar.MOD_ID, "internal"))
            .networkProtocolVersion(1)
            .optional()
            .acceptedVersions((status, version) -> true)
            .simpleChannel();

    public static final SimpleChannel SECURITY = ChannelBuilder
            .named(new ResourceLocation(AsteorBar.MOD_ID, "security"))
            .networkProtocolVersion(1)
            .optional()
            .acceptedVersions((status, version) -> true)
            .simpleChannel();

    public static void init() {
        INTERNAL.messageBuilder(InternalInfo.InternalActivatePacket.class, 0)
                .encoder(null)
                .decoder(InternalInfo::decodeActivate)
                .consumerNetworkThread(InternalNetworkHandler::ignoredHandle)
                .add();
        INTERNAL.messageBuilder(InternalInfo.InternalStatusPacket.class, 1)
                .encoder(null)
                .decoder(InternalInfo::decodeStatus)
                .consumerNetworkThread(InternalNetworkHandler::ignoredHandle)
                .add();
        INTERNAL.messageBuilder(InternalInfo.InternalInfoPacket.class, 2)
                .encoder(null)
                .decoder(InternalInfo::decodeInfo)
                .consumerNetworkThread(InternalNetworkHandler::ignoredHandle)
                .add();
        INTERNAL.messageBuilder(InternalInfo.InternalHelmetPacket.class, 3)
                .encoder(null)
                .decoder(InternalInfo::decodeHelmet)
                .consumerNetworkThread(InternalNetworkHandler::ignoredHandle)
                .add();
        INTERNAL.messageBuilder(InternalInfo.InternalTogglePacket.class, 4)
                .encoder(null)
                .decoder(InternalInfo::decodeToggle)
                .consumerNetworkThread(InternalNetworkHandler::ignoredHandle)
                .add();

        INTERNAL.messageBuilder(UseSkillPacket.class, 64)
                .encoder(UseSkillPacket::encode)
                .decoder(null)
                .consumerNetworkThread((BiConsumer<UseSkillPacket, CustomPayloadEvent.Context>) (packet, context) -> context.setPacketHandled(true))
                .add();
        INTERNAL.messageBuilder(RushPacket.class, 65)
                .encoder(RushPacket::encode)
                .decoder(null)
                .consumerNetworkThread((BiConsumer<RushPacket, CustomPayloadEvent.Context>) (packet, context) -> context.setPacketHandled(true))
                .add();


        SECURITY.messageBuilder(RequestPubKeyPacket.class, INDEX_SERVER_REQUEST_PUB_KEY)
                .encoder(null)
                .decoder(RequestPubKeyPacket::decode)
                .consumerNetworkThread(RequestPubKeyPacket::handle)
                .add();
        SECURITY.messageBuilder(SendPubKeyPacket.class, INDEX_CLIENT_SEND_PUB_KEY)
                .encoder(SendPubKeyPacket::encode)
                .decoder(null)
                .consumerNetworkThread((BiConsumer<SendPubKeyPacket, CustomPayloadEvent.Context>) (packet, context) -> context.setPacketHandled(true))
                .add();
        SECURITY.messageBuilder(ChallengePacket.class, INDEX_SERVER_SEND_CHALLENGE)
                .encoder(null)
                .decoder(ChallengePacket::decode)
                .consumerNetworkThread(ChallengePacket::handle)
                .add();
        SECURITY.messageBuilder(SendResponsePacket.class, INDEX_CLIENT_SEND_RESPONSE)
                .encoder(SendResponsePacket::encode)
                .decoder(null)
                .consumerNetworkThread((BiConsumer<SendResponsePacket, CustomPayloadEvent.Context>) (packet, context) -> context.setPacketHandled(true))
                .add();
        SECURITY.messageBuilder(ActivatePacket.class, INDEX_SERVER_SEND_ACTIVATE)
                .encoder(ActivatePacket::encode)
                .decoder(ActivatePacket::decode)
                .consumerNetworkThread(ActivatePacket::handle)
                .add();
    }

    @SuppressWarnings("unused")
    private static void ignoredHandle(Object packet, CustomPayloadEvent.Context context) {
        context.setPacketHandled(true);
    }

    public static class ActivatePacket {
        boolean activated;

        public ActivatePacket(boolean activated) {
            this.activated = activated;
        }

        public static void encode(ActivatePacket packet, FriendlyByteBuf buffer) {
            buffer.writeBoolean(packet.activated);
        }

        public static ActivatePacket decode(FriendlyByteBuf buffer) {
            return new ActivatePacket(buffer.readBoolean());
        }

        public static void handle(ActivatePacket packet, CustomPayloadEvent.Context context) {
            context.enqueueWork(() -> {
                InternalInfo.activated = packet.activated;
                SECURITY.send(new ActivatePacket(packet.activated), PacketDistributor.SERVER.noArg());
            });
            context.setPacketHandled(true);
        }
    }

    public static class RequestPubKeyPacket {
        private boolean regen = false;

        public RequestPubKeyPacket(boolean regen) {
            this.regen = regen;
        }

        public static RequestPubKeyPacket decode(FriendlyByteBuf buffer) {
            return new RequestPubKeyPacket(buffer.readBoolean());
        }

        public static void handle(RequestPubKeyPacket packet, CustomPayloadEvent.Context context) {
            context.enqueueWork(() -> {
                byte[] publicKey = SecurityInfo.getPublicKey(packet.regen);
                SECURITY.send(new SendPubKeyPacket(publicKey), PacketDistributor.SERVER.noArg());
            });
            context.setPacketHandled(true);
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

        public static void handle(ChallengePacket packet, CustomPayloadEvent.Context context) {
            context.enqueueWork(() -> {
                byte[] response = SecurityInfo.handleChallenge(packet.challenge);
                SECURITY.send(new SendResponsePacket(response), PacketDistributor.SERVER.noArg());
            });
            context.setPacketHandled(true);
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

    public static class RushPacket {
        int direction;

        public RushPacket(int direction) {
            this.direction = direction;
        }

        public static void encode(RushPacket packet, FriendlyByteBuf buffer) {
            buffer.writeByte(packet.direction);
        }
    }
}
