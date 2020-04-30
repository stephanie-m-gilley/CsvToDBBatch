package com.springbatch.DAO;

import com.springbatch.model.CsvModel;
import org.springframework.data.repository.CrudRepository;

public interface CSVDao extends CrudRepository<CsvModel, Integer> {
}
