package com.davidarmbrust.spi.service;

import com.davidarmbrust.spi.config.SpotifyProperties;
import com.davidarmbrust.spi.domain.Session;
import com.davidarmbrust.spi.domain.api.Album;
import com.davidarmbrust.spi.domain.api.Playlist;
import com.davidarmbrust.spi.domain.api.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides functions for playlist manipulation.
 */
@Service
public class PlaylistService {

    private static final ThreadLocal<SimpleDateFormat> dateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    private SpotifyService spotifyService;
    private SpotifyProperties spotifyProperties;

    @Autowired
    public PlaylistService(
            SpotifyService spotifyService,
            SpotifyProperties spotifyProperties
    ) {
        this.spotifyService = spotifyService;
        this.spotifyProperties = spotifyProperties;
    }

    /**
     * Creates a new playlist based off the track listing of the passed in playlist ID with
     * a randomized order of full albums.
     *
     * @param id of the playlist that is to be used as the original track listing.
     */
    public void createRandomPlaylist(Session session, String id) {
        List<Album> albums = this.getUniqueAlbumList(spotifyService.getPlaylistTracks(session, id));
        String playlistName = spotifyService.getUserPlaylist(session, id).getName();
        albums = this.shuffleAlbumList(albums);
        String today = dateFormat.get().format(new Date());
        Playlist randomPlaylist = spotifyService.createUserPlaylist(today + " - " + playlistName, session);
        this.addAlbumListToPlaylist(session, albums, randomPlaylist);
    }

    /**
     * Creates a new playlist based off of the current discover weekly for the user with a
     * randomized order of full albums.
     */
    void createRandomDiscoverWeekly(Session session) {
        String playlistName = dateFormat.get().format(new Date()) + " - Discover Weekly";
        List<Track> tracks = spotifyService.getDiscoverWeeklyTracks(session, spotifyProperties.getDiscoverWeeklyId());
        List<Album> albums = this.getUniqueAlbumList(tracks);
        Playlist newPlaylist = spotifyService.createUserPlaylist(playlistName, session);
        this.addAlbumListToPlaylist(session, albums, newPlaylist);
    }

    /**
     * Resolves a list of tracks to the associated list of full albums.
     */
    private List<Album> getUniqueAlbumList(List<Track> trackList) {
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
    private List<Album> shuffleAlbumList(List<Album> albumList) {
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

    /**
     * Adds a list of albums to the passed in playlist.
     */
    private void addAlbumListToPlaylist(Session session, List<Album> albumList, Playlist playlist) {
        albumList.forEach(album -> spotifyService.addAlbumToPlaylist(album, playlist, session));
    }
}
