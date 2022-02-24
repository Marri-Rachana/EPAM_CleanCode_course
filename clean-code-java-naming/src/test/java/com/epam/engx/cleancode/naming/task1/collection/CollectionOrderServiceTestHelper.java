package com.epam.engx.cleancode.naming.task1.collection;

import com.epam.engx.cleancode.naming.task1.thirdpartyjar.CollectionService;
import com.epam.engx.cleancode.naming.task1.thirdpartyjar.Submitable;

public class CollectionOrderServiceTestHelper {

    public OrderCollectionService getService(){
        return new OrderCollectionService();
    }

    public void submit(Submitable collectOrderService) {
        ((OrderCollectionService) collectOrderService).submitOrder(new OrderDummy());
    }

    public void setNotificationManager(NotificationManagerMock notificationManagerMock, Submitable collectOrderService) {
        ((OrderCollectionService) collectOrderService).setNotificationManagerService(notificationManagerMock);
    }

    public void setCollectionService(Submitable collectOrderService, CollectionService collectionServiceStub) {
        ((OrderCollectionService) collectOrderService).setCollectionService(collectionServiceStub);
    }
}
