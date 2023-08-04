package de.comparus.opensource.longmap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LongMapImplTest {

    LongMap<String> map;

    @Before
    public void setUp() {
        map = new LongMapImpl<>(String.class);
    }

    @Test
    public void put() {
        // given
        String expectedValue = "One";

        // when
        String actualValue = map.put(1, "One");

        // then
        Assert.assertEquals("Should return passed value", expectedValue, actualValue);
    }

    @Test
    public void get_key_doesnt_exist() {
        // when
        String value = map.get(1);

        // then
        Assert.assertNull("Should return null", value);
    }

    @Test
    public void get_key_exists() {
        // given
        long key = 1L;
        String expectedValue = "One";
        map.put(key, expectedValue);

        // when
        String actualValue = map.get(key);

        // then
        Assert.assertEquals("Should return passed existing value", expectedValue, actualValue);
    }

    @Test
    public void remove_key_doesnt_exist() {
        // when
        String value = map.remove(1);

        // then
        Assert.assertNull("Should return null", value);
    }

    @Test
    public void remove_key_exists() {
        // given
        long key = 1L;
        String expectedValue = "One";
        map.put(key, expectedValue);

        // when
        String actualValue = map.remove(key);

        // then
        Assert.assertEquals("Should return passed value", expectedValue, actualValue);
    }

    @Test
    public void is_empty_map_is_empty() {
        // when
        boolean expectedValue = map.isEmpty();

        // then
        Assert.assertTrue("Map should be empty", expectedValue);
    }

    @Test
    public void is_empty_map_is_not_empty() {
        // given
        map.put(1, "One");

        // when
        boolean expectedValue = map.isEmpty();

        // then
        Assert.assertFalse("Map should not be empty", expectedValue);
    }

    @Test
    public void contains_key_doesnt_contain_key() {
        // when
        boolean expectedValue = map.containsKey(1);

        // then
        Assert.assertFalse("Map should not contain key", expectedValue);
    }

    @Test
    public void contains_key_contains_key() {
        // given
        long key = 1L;
        map.put(key, "One");

        // when
        boolean expectedValue = map.containsKey(key);

        // then
        Assert.assertTrue("Map should contain key", expectedValue);
    }

    @Test
    public void contains_value_doesnt_contain_value() {
        // when
        boolean expectedValue = map.containsValue("One");

        // then
        Assert.assertFalse("Map should not contain value", expectedValue);
    }

    @Test
    public void contains_value_contains_value() {
        // given
        String value = "One";
        map.put(1, value);

        // when
        boolean expectedValue = map.containsValue(value);

        // then
        Assert.assertTrue("Map should contain value", expectedValue);
    }

    @Test
    public void keys_doesnt_contain_keys() {
        // when
        long[] keys = map.keys();

        // then
        Assert.assertEquals("Map should not contain keys", 0, keys.length);
    }

    @Test
    public void keys_contains_keys() {
        // given
        long key = 1L;
        map.put(key, "One");

        // when
        long[] keys = map.keys();

        // then
        Assert.assertEquals("Map should contain keys", 1, keys.length);
        Assert.assertEquals("Key should be as expected", key, keys[0]);
    }

    @Test
    public void values_doesnt_contain_values() {
        // when
        String[] values = map.values();

        // then
        Assert.assertEquals("Map should not contain values", 0, values.length);
    }

    @Test
    public void values_contains_values() {
        // given
        String value = "One";
        map.put(1, value);

        // when
        String[] values = map.values();

        // then
        Assert.assertEquals("Map should contain values", 1, values.length);
        Assert.assertEquals("Value should be as expected", value, values[0]);
    }

    @Test
    public void size_map_doesnt_contain_records() {
        // when
        long size = map.size();

        // then
        Assert.assertEquals("Map should not contain records", 0, size);
    }

    @Test
    public void size_map_contains_records() {
        // given
        map.put(1, "One");

        // when
        long size = map.size();

        // then
        Assert.assertEquals("Map should contain records", 1, size);
    }

    @Test
    public void clear() {
        // given
        map.put(1, "One");
        long sizeBefore = map.size();
        boolean isEmptyBefore = map.isEmpty();
        long[] keysBefore = map.keys();
        String[] valuesBefore = map.values();

        // when
        map.clear();
        long sizeAfter = map.size();
        boolean isEmptyAfter = map.isEmpty();
        long[] keysAfter = map.keys();
        String[] valuesAfter = map.values();

        // then
        Assert.assertEquals("Size before method should be 1", 1, sizeBefore);
        Assert.assertFalse("Map should not be empty before method", isEmptyBefore);
        Assert.assertEquals("Keys length before method should be 1", 1, keysBefore.length);
        Assert.assertEquals("Values length before method should be 1", 1, valuesBefore.length);

        Assert.assertEquals("Size after method should be 0", 0, sizeAfter);
        Assert.assertTrue("Map should be empty after method", isEmptyAfter);
        Assert.assertEquals("Keys length before method should be 0", 0, keysAfter.length);
        Assert.assertEquals("Values length before method should be 0", 0, valuesAfter.length);
    }
}