package com.springbatch.processor;

import com.springbatch.DTO.CsvDTO;
import com.springbatch.model.CsvModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class CsvDataProcessor implements ItemProcessor<CsvDTO, CsvModel> {



    public CsvModel process(CsvDTO item) throws Exception {
        log.info("Processing: " + item);

        CsvModel newItem = new CsvModel();
        return newItem;
    }

    public CsvModel getNewItem (CsvModel newItem){
        CsvDTO item = new CsvDTO();

        newItem.setId(newItem.getId());
        newItem.setSurName(item.getSurName());
        newItem.setFirstName(item.getFirstName());
        newItem.setInit(item.getInitial());
        newItem.setAddrL1(item.getAddrL1());
        newItem.setAddrL2(item.getAddrL2());
        newItem.setCity(item.getCity());
        newItem.setProvince(item.getProvince());
        newItem.setZip(Integer.parseInt(item.getZip()));
        newItem.setCountry(item.getCountry());

        return newItem;
    }
}
