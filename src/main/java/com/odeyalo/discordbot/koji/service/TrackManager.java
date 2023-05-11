package com.odeyalo.discordbot.koji.service;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

/**
 * TrackManager is capable for handling {@link AudioTrack}.
 * TrackManager must be thread-safe and must create a new Audio player for every Guild.
 */
public interface TrackManager extends PlayTrackService {

}
