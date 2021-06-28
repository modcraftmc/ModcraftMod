package fr.modcraftmc.modcraftmod.common.discord.tracker;

import fr.modcraftmc.modcraftmod.ModcraftMod;
import net.dv8tion.jda.api.entities.Message;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

public class ServerStatusTracker {

    private static final String START_EMOTE = ":white_check_mark:";
    private static final String STOP_EMOTE = ":octagonal_sign:";
    private final String LOADING_EMOTE = ":clock1:";

    private List<InternalState> pastState = new ArrayList<>();
    private InternalState currentState;
    private Message statusMessageId;

    public enum State {
        SERVER_STARTING, SERVER_STARTED, SERVER_STOPPED, SERVER_CRASHED,
    }

    public ServerStatusTracker() {
    }

    public void refreshStatusMessage(InternalState updatedState, boolean isFirst) {

        if (pastState.contains(updatedState)) return;
        pastState.add(updatedState);
        currentState = updatedState;
        StringBuilder builder = new StringBuilder();

        builder.append("**status du chargement des mods**");

        for (InternalState state : InternalState.values()) {
            builder.append("\n");
            if (currentState == state || pastState.contains(state)) {
                builder.append(START_EMOTE).append(" ").append(state.name());
            } else {
                builder.append(LOADING_EMOTE).append(" ").append(state.name());
            }
        }

        if (isFirst) {
            ModcraftMod.discordChat.sendStatusMessage(builder.toString(), (msg) -> statusMessageId = msg);
        } else {
            if (statusMessageId != null)
                statusMessageId.editMessage(builder.toString()).queue();
        }
    }

    public enum InternalState {
        CONSTRUCT,
        CREATE_REGISTRIES,
        LOAD_REGISTRIES,
        COMMON_SETUP,
        SIDED_SETUP,
        ENQUEUE_IMC,
        PROCESS_IMC,
        COMPLETE
    }

    public void pushServerState(State state) {

        String result = "";
        switch (state) {
            case SERVER_STARTING: {
                result = String.format("** %s Le serveur commence à démarrer **", LOADING_EMOTE);
                break;
            }

            case SERVER_STARTED: {
                result = String.format("** %s Le serveur vient de démarrer **", START_EMOTE);
                break;
            }

            case SERVER_STOPPED: {
                result = String.format("** %s Le serveur vient de s'arrêter **", STOP_EMOTE);
                break;
            }

            case SERVER_CRASHED: {
                result = String.format("** %s Le serveur vient de s'arrêter (oh no :( crash) **");
                break;
            }
        }
        ModcraftMod.discordChat.sendStatusMessage(result, null);
    }
}
