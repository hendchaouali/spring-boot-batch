package com.rest.playlist.repository;

import com.rest.playlist.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaAuditing
public interface SongRepository extends JpaRepository<Song, Long> {
}
