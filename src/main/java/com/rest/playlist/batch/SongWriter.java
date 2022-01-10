package com.rest.playlist.batch;

import com.rest.playlist.model.Song;
import com.rest.playlist.service.ISongService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class SongWriter implements ItemWriter<Song> {
    private ISongService songService;

    public SongWriter(ISongService songService) {
        this.songService = songService;
    }

    @Override
    public void write(List<? extends Song> songs) {
        songs.stream().forEach(song -> {
            log.info("Enregistrement en base de l'objet {}", song);
            songService.createSong(song);
        });
    }
}
