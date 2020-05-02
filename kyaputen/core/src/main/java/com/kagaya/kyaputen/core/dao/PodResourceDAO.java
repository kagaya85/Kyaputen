package com.kagaya.kyaputen.core.dao;

import com.kagaya.kyaputen.common.runtime.Pod;
import com.kagaya.kyaputen.common.runtime.Pod.PodStatus;

import java.util.*;

public class PodResourceDAO {

    private static Map<String, Pod> podResourceMap = new HashMap<>();

    public void addPod(Pod pod) {
        podResourceMap.put(pod.getPodId(), pod);
    }

    public Pod getPod(String podId) {
        return podResourceMap.get(podId);
    }

    public List<String> getPodIdList() {
        return new ArrayList<>(podResourceMap.keySet());
    }

    public List<Pod> getRunningList() {
        List<Pod> runningList = new LinkedList<>();

        for (Pod p: podResourceMap.values()) {
            if (p.getStatus().equals(PodStatus.RUNNING))
                runningList.add(p);
        }

        return runningList;
    }

    public List<Pod> getRunningList(List<String> podIdList) {
        List<Pod> runningList = new LinkedList<>();

        for (String podId: podIdList) {
            runningList.add(podResourceMap.get(podId));
        }

        return runningList;
    }

}
