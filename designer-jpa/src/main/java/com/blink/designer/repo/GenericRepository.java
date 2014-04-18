package com.blink.designer.repo;


import org.springframework.data.repository.CrudRepository;


import com.blink.designer.model.BaseBlinkModel;

public interface GenericRepository<T extends BaseBlinkModel, LongD>  extends CrudRepository {

 
 
}
