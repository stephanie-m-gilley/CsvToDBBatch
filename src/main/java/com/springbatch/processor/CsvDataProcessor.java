package com.springbatch.processor;

import com.springbatch.DTO.CsvDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class CsvDataProcessor implements ItemProcessor<CsvDTO, CsvDTO> {

    @Override
    public CsvDTO process(final CsvDTO item) throws Exception {

        log.info("Processing: " + item);

        return item;
    }



}
