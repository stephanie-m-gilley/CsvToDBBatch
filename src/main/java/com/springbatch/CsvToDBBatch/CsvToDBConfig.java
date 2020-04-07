package com.springbatch.CsvToDBBatch;

import com.springbatch.ConsoleItemWriter;
import com.springbatch.DTO.CsvDTO;
import com.springbatch.processor.CsvDataProcessor;
import com.springbatch.utils.BatchConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableBatchProcessing
public class CsvToDBConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Value("${file.input}")
    private String inputFile;

    public void setDataSource(DataSource dataSource) {
        //This BatchConfigurer ignores any DataSource
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
    public ItemProcessor<CsvDTO, CsvDTO> processor(){
        return new CsvDataProcessor();
    }

    @Bean
    public ItemWriter<CsvDTO> writer(){
        return new ConsoleItemWriter();
    }

    //item writer

    @Bean
    protected Step step1(ItemReader<CsvDTO> reader,
                         ItemProcessor<CsvDTO, CsvDTO> processor,
                         ItemWriter<CsvDTO> writer) {
        return steps.get("step1")
                .<CsvDTO, CsvDTO> chunk(10)
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
