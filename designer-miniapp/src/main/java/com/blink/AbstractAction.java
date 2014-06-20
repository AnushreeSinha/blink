package com.blink;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAction {

@Autowired
EntityManager em;

@Autowired
Queue queue;

public abstract void createAction(Object obj);

public abstract void readAction(Object obj);

public abstract void deleteAction(Object obj);

public abstract void updateAction(Object obj);


}
