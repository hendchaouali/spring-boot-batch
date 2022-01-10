package com.rest.playlist.batch;

import com.rest.playlist.model.Song;
import org.springframework.batch.item.ItemProcessor;

public class SongProcessor implements ItemProcessor<Song, Song> {

    @Override
    public Song process(Song song) {
        song.setArtistName(song.getArtistName().toUpperCase());
        return song;
    }
}
