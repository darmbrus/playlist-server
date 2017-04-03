package com.davidarmbrust.spi.service;

import com.davidarmbrust.spi.domain.api.Album;
import com.davidarmbrust.spi.domain.api.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 4/3/2017.
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
}
