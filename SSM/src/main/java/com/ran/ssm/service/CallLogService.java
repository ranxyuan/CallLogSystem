package com.ran.ssm.service;

import com.ran.ssm.domain.CallLog;
import com.ran.ssm.domain.CallLogRange;

import java.util.List;

/**
 *
 */
public interface CallLogService {
    public List<CallLog> findAll();

    /**
     * 按照范围查询通话记录
     */
    public List<CallLog> findCallogs(String call,List<CallLogRange> list);
}
