package com.springbatch;

import com.springbatch.DTO.CsvDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class ConsoleItemWriter implements ItemWriter<CsvDTO> {

    @Override
    public void write(List<? extends CsvDTO> items) throws Exception {
        log.trace("Console item writer starts");
        for (CsvDTO item : items) {
            log.info(ToStringBuilder.reflectionToString(item));
        }
        log.trace("Console item writer ends");

    }
}
