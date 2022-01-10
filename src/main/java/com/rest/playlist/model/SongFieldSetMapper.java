package com.rest.playlist.model;

import com.rest.playlist.enums.SongCategory;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class SongFieldSetMapper implements FieldSetMapper<Song> {

    @Override
    public Song mapFieldSet(FieldSet fieldSet) {
        Song song = new Song();
        song.setArtistName(fieldSet.readString(0));
        song.setTitle(fieldSet.readString(1));
        song.setDescription(fieldSet.readString(2));
        song.setDuration(fieldSet.readString(3));
        SongCategory searchedCategory = EnumUtils.getEnumIgnoreCase(SongCategory.class, fieldSet.readString(4).toUpperCase());
        if (searchedCategory != null) {
            song.setCategory(searchedCategory);
        }
        return song;
    }
}

