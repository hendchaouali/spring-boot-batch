package com.rest.playlist.config;

import com.rest.playlist.batch.SongProcessor;
import com.rest.playlist.batch.SongWriter;
import com.rest.playlist.model.Song;
import com.rest.playlist.model.SongFieldSetMapper;
import com.rest.playlist.service.ISongService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Value("${header.names}")
    private String names;

    @Value("${line.delimiter}")
    private String delimiter;

    private static final String FILE_NAME = "songs.csv";
    private static final String JOB_NAME = "listSongsJob";
    private static final String STEP_NAME = "processingStep";
    private static final String READER_NAME = "songItemReader";

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private ISongService songService;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ISongService songService) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.songService = songService;
    }

    @Bean
    public Job listSongsJob(Step stepBatch) {
        return jobBuilderFactory.get(JOB_NAME)
                .start(stepBatch)
                .build();
    }

    @Bean
    public Step songStep() {
        return stepBuilderFactory.get(STEP_NAME)
                .<Song, Song>chunk(5)
                .reader(songItemReader())
                .processor(songItemProcessor())
                .writer(studentItemWriter())
                .build();
    }

    @Bean
    public ItemReader<Song> songItemReader() {
        FlatFileItemReader<Song> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(FILE_NAME));
        reader.setName(READER_NAME);
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper());
        return reader;

    }

    @Bean
    public ItemProcessor<Song, Song> songItemProcessor() {
        return new SongProcessor();
    }

    @Bean
    public ItemWriter<Song> studentItemWriter() {
        return new SongWriter(songService);
    }

    @Bean
    public LineMapper<Song> lineMapper() {

        final DefaultLineMapper<Song> defaultLineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(delimiter);
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(names.split(delimiter));

        final SongFieldSetMapper fieldSetMapper = new SongFieldSetMapper();
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
