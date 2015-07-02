package com.google.common.base;

import com.google.common.collect.ImmutableList;
import com.google.repacked.common.base.Optional;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;
import static junit.framework.Assert.assertSame;
import static org.junit.Assert.*;


public class OptionalTest {

    @Test
    public void testAbsent() {
        Optional<String> optionalName = Optional.absent();
        assertFalse(optionalName.isPresent());
    }

    @Test
    public void testOf() {
        assertEquals("training", Optional.of("training").get());
    }

    @Test
    public void testOf_null() {
        try {
            Optional.of(null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void testFromNullable() {
        Optional<String> optionalName = Optional.fromNullable("bob");
        assertEquals("bob", optionalName.get());
    }

    @Test
    public void testFromNullable_null() {
        // not promised by spec, but easier to test
        assertSame(Optional.absent(), Optional.fromNullable(null));
    }

    @Test
    public void testIsPresent_no() {
        assertFalse(Optional.absent().isPresent());
    }

    @Test
    public void testIsPresent_yes() {
        assertTrue(Optional.of("training").isPresent());
    }

    @Test
    public void testGet_absent() {
        Optional<String> optional = Optional.absent();
        try {
            optional.get();
            fail();
        } catch (IllegalStateException expected) {
        }
    }

    @Test
    public void testGet_present() {
        assertEquals("training", Optional.of("training").get());
    }

    @Test
    public void testOr_T_present() {
        assertEquals("a", Optional.of("a").or("default"));
    }

    @Test
    public void testOr_T_absent() {
        assertEquals("default", Optional.absent().or("default"));
    }

    @Test
    public void testOr_Optional_present() {
        assertEquals(Optional.of("a"), Optional.of("a").or(Optional.of("fallback")));
    }

    @Test
    public void testOr_Optional_absent() {
        assertEquals(Optional.of("fallback"), Optional.absent().or(Optional.of("fallback")));
    }

    @Test
    public void testOrNull_present() {
        assertEquals("a", Optional.of("a").orNull());
    }

    @Test
    public void testOrNull_absent() {
        assertNull(Optional.absent().orNull());
    }

    @Test
    public void testAsSet_present() {
        Set<String> expected = Collections.singleton("a");
        assertEquals(expected, Optional.of("a").asSet());
    }

    @Test
    public void testAsSet_absent() {
        assertTrue("Returned set should be empty", Optional.absent().asSet().isEmpty());
    }

    @Test
    public void testAsSet_presentIsImmutable() {
        Set<String> presentAsSet = Optional.of("a").asSet();
        try {
            presentAsSet.add("b");
            fail();
        } catch (UnsupportedOperationException expected) {
        }
    }

    @Test
    public void testAsSet_absentIsImmutable() {
        Set<Object> absentAsSet = Optional.absent().asSet();
        try {
            absentAsSet.add("foo");
            fail();
        } catch (UnsupportedOperationException expected) {
        }
    }

    // TODO(kevinb): use EqualsTester
    @Test
    public void testEqualsAndHashCode_absent() {
        assertEquals(Optional.<String>absent(), Optional.<Integer>absent());
        assertEquals(Optional.absent().hashCode(), Optional.absent().hashCode());
    }

    @Test
    public void testEqualsAndHashCode_present() {
        assertEquals(Optional.of("training"), Optional.of("training"));
        assertFalse(Optional.of("a").equals(Optional.of("b")));
        assertFalse(Optional.of("a").equals(Optional.absent()));
        assertEquals(Optional.of("training").hashCode(), Optional.of("training").hashCode());
    }

    @Test
    public void testToString_absent() {
        assertEquals("Optional.absent()", Optional.absent().toString());
    }

    @Test
    public void testToString_present() {
        assertEquals("Optional.of(training)", Optional.of("training").toString());
    }
    @Test
    public void testPresentInstances_allPresent() {
        List<Optional<String>> optionals =
                ImmutableList.of(Optional.of("a"), Optional.of("b"), Optional.of("c"));
        assertThat(Optional.presentInstances(optionals)).containsAllOf("a", "b", "c");
    }
    @Test
    public void testPresentInstances_allAbsent() {
        List<Optional<Object>> optionals =
                ImmutableList.of(Optional.absent(), Optional.absent());
        assertThat(Optional.presentInstances(optionals)).isEmpty();
    }
    @Test
    public void testPresentInstances_somePresent() {
        List<Optional<String>> optionals =
                ImmutableList.of(Optional.of("a"), Optional.<String>absent(), Optional.of("c"));
        assertThat(Optional.presentInstances(optionals)).containsAllOf("a", "c");
    }
    @Test
    public void testPresentInstances_callingIteratorTwice() {
        List<Optional<String>> optionals =
                ImmutableList.of(Optional.of("a"), Optional.<String>absent(), Optional.of("c"));
        Iterable<String> onlyPresent = Optional.presentInstances(optionals);
        assertThat(onlyPresent).containsAllOf("a", "c");
    }
}
