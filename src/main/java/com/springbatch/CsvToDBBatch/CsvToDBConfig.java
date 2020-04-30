package com.springbatch.CsvToDBBatch;

import com.springbatch.DTO.CsvDTO;
import com.springbatch.model.CsvModel;
import com.springbatch.processor.CsvDataProcessor;
import com.springbatch.utils.BatchConstants;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableBatchProcessing
public class CsvToDBConfig {

    @Autowired
    private Environment env;

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Value("${file.input}")
    private String inputFile;

    @Bean
    public DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(env.getProperty(BatchConstants.URL));
        dataSource.setUsername(env.getProperty(BatchConstants.USER_NAME));
        dataSource.setPassword(env.getProperty(BatchConstants.PASSWORD));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    //Helps to create default batch tables
    private void setDataSource(DataSource dataSource) {
    }

    @Bean
    public FlatFileItemReader<CsvDTO> reader(){
        FlatFileItemReader<CsvDTO> reader = new FlatFileItemReader();
        reader.setResource(new FileSystemResource(inputFile));
        reader.setLinesToSkip(1);
        reader.setStrict(false);
        DefaultLineMapper lineMapper = new DefaultLineMapper();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(BatchConstants.INPUT_HEADER_NAMES );
        tokenizer.setDelimiter(",");
        BeanWrapperFieldSetMapper<CsvDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CsvDTO.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(tokenizer);
        reader.setLineMapper(lineMapper);
        log.info("*******Reader");
        return reader;
        }

    @Bean
    public ItemProcessor<CsvDTO, CsvModel> processor(){
        return new CsvDataProcessor();
    }

    //need entity manager factory
    @Bean
    public JpaItemWriter<CsvModel> jpaItemWriter() {
        JpaItemWriter<CsvModel> writer = new JpaItemWriter();
        return writer;
    }

    @Bean
    protected Step step1(ItemReader<CsvDTO> reader,
                         ItemProcessor<CsvDTO, CsvModel> processor,
                         ItemWriter<CsvModel> writer) {
        return steps.get("step1")
                .<CsvDTO, CsvModel> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(@Qualifier("step1") Step step1) {
        return jobs.get("firstBatchJob")
                .start(step1)
                .build();
    }
}
