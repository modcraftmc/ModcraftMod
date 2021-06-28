package fr.modcraftmc.modcraftmod.common.discord.tracker;

import fr.modcraftmc.modcraftmod.ModcraftMod;
import net.minecraft.entity.player.ServerPlayerEntity;

public class PlayerStatusTracker {

    private final String JOIN_EMOTE = "<:join:577530406312869929>";
    private final String LEAVE_EMOTE = "<:leave:577530421521285151>";
    private final String DIE_EMOTE = ":skull:";
    private final String ADVANCEMENT_EMOTE = ":first_place:";

    public enum State {
        PLAYER_LOGIN, PLAYER_LOGOUT, PLAYER_DIE, PLAYER_ADVANCEMENT
    }

    public void pushPlayerState(ServerPlayerEntity player, State state, String... params) {

        String username = player.getDisplayName().getString();
        String result = "";

        switch (state) {
            case PLAYER_LOGIN: {
                result = String.format("** %s %s à rejoint le serveur **", JOIN_EMOTE, username);
                break;
            }

            case PLAYER_LOGOUT: {
                result = String.format("** %s %s à quitté le serveur **", LEAVE_EMOTE, username);
                break;
            }

            case PLAYER_DIE: {
                result = String.format("** %s %s **", DIE_EMOTE, params[0]);
                break;
            }

            case PLAYER_ADVANCEMENT: {
                result = String.format("** %s Le joueur %s à obtenu le progrès %s !**", ADVANCEMENT_EMOTE, username, params[0]);
                break;
            }
        }

        ModcraftMod.discordChat.sendStatusMessage(result, null);
    }
}
