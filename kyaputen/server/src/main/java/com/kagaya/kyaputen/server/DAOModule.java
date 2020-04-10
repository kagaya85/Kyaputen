package com.kagaya.kyaputen.server;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.kagaya.kyaputen.common.metadata.tasks.Task;
import com.kagaya.kyaputen.common.metadata.tasks.TaskDefinition;
import com.kagaya.kyaputen.core.dao.PollingQueueDAOImpl;
import com.kagaya.kyaputen.core.dao.QueueDAO;
import com.kagaya.kyaputen.core.dao.TaskDefQueueDAOImpl;
import com.kagaya.kyaputen.core.dao.TaskQueueDAOImpl;
import com.kagaya.kyaputen.core.events.Message;

public class DAOModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(new TypeLiteral<QueueDAO<Task>>() {}).to(TaskQueueDAOImpl.class);
        bind(new TypeLiteral<QueueDAO<TaskDefinition>>() {}).to(TaskDefQueueDAOImpl.class);
        bind(new TypeLiteral<QueueDAO<Message>>() {}).to(PollingQueueDAOImpl.class);

    }
}
