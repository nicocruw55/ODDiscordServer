package com.odfin.voicechat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VoiceRestController {

    /*// Beispiel-Daten für Teilnehmer
    private final List<VoiceClientHandler> participants = VoiceServer.clientHandlers;

    @GetMapping("/voicecall/participants")
    public List<Integer> getParticipants(@RequestParam("voiceChatID") int voiceChatID) {
        List<Integer> participantIdsInCall = new ArrayList<>();
        for (VoiceClientHandler participant : participants) {
            if (participant.voiceChatID == voiceChatID) {
                participantIdsInCall.add(participant.userId);
            }
        }
        return participantIdsInCall;
    }*/
}
