package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.metadata.tasks.PollData;

import java.util.List;

public interface PollDataDAO {

    void updateLastPollData(String taskDefName, String domain, String workerId);

    PollData getPollData(String taskDefName, String domain);

    List<PollData> getPollData(String taskDefName);
}

