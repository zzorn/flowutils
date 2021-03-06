package org.flowutils;

import org.flowutils.serializer.ConcurrentSerializerWrapper;
import org.flowutils.serializer.KryoSerializer;
import org.flowutils.serializer.Serializer;
import org.flowutils.serializer.SerializerFactory;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class SerializerTest {

    @Test
    public void testSerializer() throws Exception {

        // Normal single threaded case
        assertSerializerWorks(createKryoSerializerWithRegisteredClasses());

        // Check that concurrent serializer can delegate class registration
        final ConcurrentSerializerWrapper concurrentSerializer = new ConcurrentSerializerWrapper(KryoSerializer.class);
        registerAllowedClasses(concurrentSerializer);
        assertSerializerWorksConcurrently(concurrentSerializer);

        // Test concurrent serializer with factory
        assertSerializerWorksConcurrently(new ConcurrentSerializerWrapper(new SerializerFactory() {
            @Override public Serializer createSerializer() {
                return createKryoSerializerWithRegisteredClasses();
            }
        }));
    }

    private KryoSerializer createKryoSerializerWithRegisteredClasses() {
        final KryoSerializer serializer = new KryoSerializer();
        registerAllowedClasses(serializer);
        return serializer;
    }

    private void registerAllowedClasses(Serializer serializer) {
        serializer.registerAllowedClass(DummyObj.class);
        serializer.registerCommonCollectionClasses();
    }

    private void assertSerializerWorksConcurrently(final Serializer serializer) {

        TestUtils.testConcurrently("Multiple threads should be able to serialize at the same time", 10, 10000, new TestRun() {
            @Override public void run() throws Exception {
                assertSerializerWorks(serializer);
            }
        });

    }


    private void assertSerializerWorks(Serializer serializer) {

        Map<String, Integer> testMap = new HashMap<String, Integer>();
        testMap.put("foo", 1);
        testMap.put("bar", 3);
        testMap.put("baz", 4);

        Set<String> testSet = new HashSet<String>();
        testSet.add("Foo");
        testSet.add("Bar");
        testSet.add("Baz");

        assertSerializes(serializer, "foobar");
        assertSerializes(serializer, 5);
        assertSerializes(serializer, new ArrayList<Object>(Arrays.asList("foo", "Bar", 3.0, 'a')));
        assertSerializes(serializer, testMap);
        assertSerializes(serializer, testSet);
        assertSerializes(serializer, new DummyObj("bar", 5, 3, new DummyObj("baz"), null));
        assertDoesNotSerialize(serializer,
                               new DummyObj("bar", 5, 3, new DummyObj("baz"), new OtherDummyObj("hidden payload"))
        );
        assertDoesNotSerialize(serializer, new OtherDummyObj("dummy"));
    }

    private void assertSerializes(final Serializer serializer, Object original) {
        final byte[] data = serializer.serialize(original);
        final Object serializedObj = serializer.deserialize(data);
        assertEquals(original, serializedObj);
    }

    private void assertDoesNotSerialize(final Serializer serializer, Object original) {
        try {
            serializer.serialize(original);
            fail("Should not serialize");
        }
        catch (Throwable e) {
            // Ok.
        }
    }


    private static class DummyObj {
        public String name;
        public int i;
        public Integer integer;
        public DummyObj nextDummy;
        public OtherDummyObj otherDummy;

        private DummyObj(String name) {
            this.name = name;
        }

        private DummyObj(String name,
                         int i,
                         Integer integer,
                         DummyObj nextDummy,
                         OtherDummyObj otherDummy) {
            this.name = name;
            this.i = i;
            this.integer = integer;
            this.nextDummy = nextDummy;
            this.otherDummy = otherDummy;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DummyObj dummyObj = (DummyObj) o;

            if (i != dummyObj.i) return false;
            if (integer != null ? !integer.equals(dummyObj.integer) : dummyObj.integer != null) return false;
            if (name != null ? !name.equals(dummyObj.name) : dummyObj.name != null) return false;
            if (nextDummy != null ? !nextDummy.equals(dummyObj.nextDummy) : dummyObj.nextDummy != null) return false;
            if (otherDummy != null ? !otherDummy.equals(dummyObj.otherDummy) : dummyObj.otherDummy != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + i;
            result = 31 * result + (integer != null ? integer.hashCode() : 0);
            result = 31 * result + (nextDummy != null ? nextDummy.hashCode() : 0);
            result = 31 * result + (otherDummy != null ? otherDummy.hashCode() : 0);
            return result;
        }
    }

    private static class OtherDummyObj {
        public String name;

        private OtherDummyObj(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            OtherDummyObj that = (OtherDummyObj) o;

            if (name != null ? !name.equals(that.name) : that.name != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }
}
