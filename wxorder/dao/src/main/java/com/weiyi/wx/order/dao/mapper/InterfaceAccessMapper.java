package com.weiyi.wx.order.dao.mapper;

import com.weiyi.wx.order.dao.entity.InterfaceAccess;
import com.weiyi.wx.order.dao.request.GetInterfaceAccessRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InterfaceAccessMapper
{
    void addRecord(InterfaceAccess interfaceAccess);

    void deleteRecord(int id);

    void updateRecord(InterfaceAccess interfaceAccess);

    int queryListCount(GetInterfaceAccessRequest request);

    List<InterfaceAccess> queryList(GetInterfaceAccessRequest request);
}
