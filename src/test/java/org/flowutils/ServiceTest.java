package org.flowutils;

import org.flowutils.service.ServiceBase;
import org.flowutils.service.ServiceProvider;
import org.flowutils.service.ServiceProviderBase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ServiceTest {

    @Test
    public void testService() throws Exception {
        DummyService dummyService = new DummyService();

        assertEquals(0, dummyService.doInitCallCount);
        assertEquals(0, dummyService.doShutdownCallCount);
        assertEquals(false, dummyService.isInitialized());
        assertEquals(false, dummyService.isActive());
        assertEquals(false, dummyService.isShutdown());

        dummyService.init();

        assertEquals(1, dummyService.doInitCallCount);
        assertEquals(0, dummyService.doShutdownCallCount);
        assertEquals(true, dummyService.isInitialized());
        assertEquals(true, dummyService.isActive());
        assertEquals(false, dummyService.isShutdown());

        dummyService.shutdown();

        assertEquals(1, dummyService.doInitCallCount);
        assertEquals(1, dummyService.doShutdownCallCount);
        assertEquals(true, dummyService.isInitialized());
        assertEquals(false, dummyService.isActive());
        assertEquals(true, dummyService.isShutdown());

        try {
            dummyService.shutdown();
            fail("Should throw exception");
        }
        catch(IllegalStateException e) {
            // Ok
        }

        assertEquals(1, dummyService.doInitCallCount);
        assertEquals(1, dummyService.doShutdownCallCount);
        assertEquals(true, dummyService.isInitialized());
        assertEquals(false, dummyService.isActive());
        assertEquals(true, dummyService.isShutdown());

    }


    @Test
    public void testImmediateShutdown() throws Exception {
        DummyService dummyService = new DummyService();

        assertEquals(0, dummyService.doInitCallCount);
        assertEquals(0, dummyService.doShutdownCallCount);
        assertEquals(false, dummyService.isInitialized());
        assertEquals(false, dummyService.isActive());
        assertEquals(false, dummyService.isShutdown());

        dummyService.shutdown();

        assertEquals(0, dummyService.doInitCallCount);
        assertEquals(0, dummyService.doShutdownCallCount);
        assertEquals(false, dummyService.isInitialized());
        assertEquals(false, dummyService.isActive());
        assertEquals(true, dummyService.isShutdown());

    }

    @Test
    public void testMultipleInit() throws Exception {
        DummyService dummyService = new DummyService();

        assertEquals(0, dummyService.doInitCallCount);
        assertEquals(0, dummyService.doShutdownCallCount);
        assertEquals(false, dummyService.isInitialized());
        assertEquals(false, dummyService.isActive());
        assertEquals(false, dummyService.isShutdown());

        dummyService.init();

        assertEquals(1, dummyService.doInitCallCount);
        assertEquals(0, dummyService.doShutdownCallCount);
        assertEquals(true, dummyService.isInitialized());
        assertEquals(true, dummyService.isActive());
        assertEquals(false, dummyService.isShutdown());

        try {
            dummyService.init();
            fail("Should throw exception");
        }
        catch(IllegalStateException e) {
            // Ok
        }

        assertEquals(1, dummyService.doInitCallCount);
        assertEquals(0, dummyService.doShutdownCallCount);
        assertEquals(true, dummyService.isInitialized());
        assertEquals(true, dummyService.isActive());
        assertEquals(false, dummyService.isShutdown());

    }

    @Test
    public void testServiceProvider() throws Exception {
        ServiceProvider serviceProvider = new ServiceProviderBase("TestProvider");

        final DummyService service1 = serviceProvider.addService(new DummyService("Service 1"));

        serviceProvider.init();

        final DummyService service2 = serviceProvider.addService(new DummyService2("Service 2"));

        assertEquals(1, service1.doInitCallCount);
        assertEquals(0, service1.doShutdownCallCount);
        assertEquals(1, service2.doInitCallCount);
        assertEquals(0, service2.doShutdownCallCount);
        assertEquals(true, service1.isInitialized());
        assertEquals(true, service1.isActive());
        assertEquals(false, service1.isShutdown());

        assertEquals(true, serviceProvider.isInitialized());
        assertEquals(true, serviceProvider.isActive());
        assertEquals(false, serviceProvider.isShutdown());

        assertEquals(service1, serviceProvider.getService(DummyService.class));
        assertEquals(service2, serviceProvider.getService(DummyService2.class));
        assertEquals(true, serviceProvider.getServices().contains(service1));
        assertEquals(true, serviceProvider.getServices().contains(service2));
        assertEquals(2, serviceProvider.getServices().size());

        serviceProvider.shutdown();

        assertEquals(true, service1.isInitialized());
        assertEquals(false, service1.isActive());
        assertEquals(true, service1.isShutdown());
        assertEquals(true, service2.isInitialized());
        assertEquals(false, service2.isActive());
        assertEquals(true, service2.isShutdown());
        assertEquals(1, service1.doInitCallCount);
        assertEquals(1, service1.doShutdownCallCount);
        assertEquals(1, service2.doInitCallCount);
        assertEquals(1, service2.doShutdownCallCount);

        assertEquals(true, serviceProvider.isInitialized());
        assertEquals(false, serviceProvider.isActive());
        assertEquals(true, serviceProvider.isShutdown());
    }

    private static class DummyService extends ServiceBase {
        public int doInitCallCount = 0;
        public int doShutdownCallCount = 0;

        private DummyService() {
            this("Dummy service");
        }

        private DummyService(String name) {
            super(name);
        }

        @Override protected void doInit(ServiceProvider serviceProvider) {
            doInitCallCount++;
        }

        @Override protected void doShutdown() {
            doShutdownCallCount++;
        }
    }

    private static class DummyService2 extends DummyService {
        private DummyService2() {
        }

        private DummyService2(String name) {
            super(name);
        }
    }
}
