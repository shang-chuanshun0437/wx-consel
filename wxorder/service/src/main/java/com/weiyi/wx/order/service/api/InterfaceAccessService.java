package com.weiyi.wx.order.service.api;

import com.weiyi.wx.order.dao.entity.InterfaceAccess;
import com.weiyi.wx.order.dao.request.GetInterfaceAccessRequest;

import java.util.List;

public interface InterfaceAccessService
{
    void addRecord(InterfaceAccess interfaceAccess);

    void deleteRecord(int id);

    void updateRecord(InterfaceAccess interfaceAccess);

    List<InterfaceAccess> queryList(GetInterfaceAccessRequest request);

    InterfaceAccess queryInterfaceAccess(Long userPhone, String interfaceName);

    int queryListCount(GetInterfaceAccessRequest request);
}
