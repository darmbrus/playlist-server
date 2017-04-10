package com.davidarmbrust.spi.service;

import com.davidarmbrust.spi.domain.api.Album;
import com.davidarmbrust.spi.domain.api.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides functions for playlist manipulation.
 */
@Service
public class PlaylistService {

    private SpotifyService spotifyService;

    @Autowired
    public PlaylistService(
            SpotifyService spotifyService
    ) {
        this.spotifyService = spotifyService;
    }

    /**
     * Resolves a list of tracks to the associated list of full albums.
     */
    public List<Album> getUniqueAlbumList(List<Track> trackList) {
        Set<String> albumIds = new HashSet<>();
        albumIds.addAll(
                trackList.stream()
                        .map(track -> track.getAlbum().getId())
                        .collect(Collectors.toList())
        );
        return albumIds.stream()
                .map(albumId -> spotifyService.getAlbumById(albumId))
                .collect(Collectors.toList());
    }

    /**
     * Returns a randomized list of albums.
     */
    public List<Album> shuffleAlbumList(List<Album> albumList) {
        int listSize = albumList.size();
        List<Album> shuffledList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < listSize; i++) {
            int randomIndex = random.nextInt(listSize - i);
            shuffledList.add(albumList.get(randomIndex));
            albumList.remove(randomIndex);
        }
        return shuffledList;
    }
}
