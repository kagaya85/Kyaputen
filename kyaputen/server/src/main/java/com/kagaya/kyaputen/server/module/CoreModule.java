package com.kagaya.kyaputen.server.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.kagaya.kyaputen.core.algorithm.Scheduler;
import com.kagaya.kyaputen.core.algorithm.SchedulerImpl;
import com.kagaya.kyaputen.core.dao.PollingQueueDAOImpl;
import com.kagaya.kyaputen.core.dao.QueueDAO;
import com.kagaya.kyaputen.core.events.TaskMessage;
import com.kagaya.kyaputen.core.execution.WorkflowExecutor;

public class CoreModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(new TypeLiteral<QueueDAO<TaskMessage>>() {}).to(PollingQueueDAOImpl.class);
        bind(Scheduler.class).to(SchedulerImpl.class);
    }
}
