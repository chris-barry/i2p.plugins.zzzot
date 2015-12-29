package net.i2p.zzzot;
/*
 *  Copyright 2010 zzz (zzz@mail.i2p)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import net.i2p.util.RandomSource;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RandomSource.class})
public class RandomIteratorTest {

    private RandomIterator<String> randomIterator;

    private List<String> list;

    @Mock
    private RandomSource rand;

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	PowerMockito.mockStatic(RandomSource.class);
	list = new LinkedList<String>();
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailWithNullPointerException() throws Exception {
	randomIterator = new RandomIterator<String>(null);
    }

    @Test
    public void shouldHaveNext() throws Exception {
	list.add("1");
	randomIterator = new RandomIterator<String>(list);
	assertTrue("Should have next", randomIterator.hasNext());
    }

    @Test
    public void shouldNotHaveNext() throws Exception {
	list.clear();
	randomIterator = new RandomIterator<String>(list);
	assertFalse("Should not have next", randomIterator.hasNext());
    }

    @Ignore("Not worth it.")
    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowUnsupportedOperationException() throws Exception {
	randomIterator.remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementException() throws Exception {
	list.clear();
	randomIterator = new RandomIterator<String>(list);
	assertNotNull(randomIterator.next());
    }

    @Test
    public void shouldHaveNextItemInList() throws Exception {
	list.add("1");
	list.add("2");
	list.add("3");
	list.add("4");
	
	when(rand.nextInt(0)).thenReturn(2,1);
	PowerMockito.when(RandomSource.getInstance()).thenReturn(rand);
	
	randomIterator = new RandomIterator<String>(list);

	assertNotNull("list should have a next item", randomIterator.next());
	assertNotNull("list should have a next item", randomIterator.next());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldHaveNextButNoNextNext() throws Exception {
	list.add("1");

	when(rand.nextInt(0)).thenReturn(2,1);
	PowerMockito.when(RandomSource.getInstance()).thenReturn(rand);

	randomIterator = new RandomIterator<String>(list);
	
	assertTrue(randomIterator.hasNext());
	assertNotNull(randomIterator.next());
	assertNull(randomIterator.next());
	// assertThat("should return item that was added to list", randomIterator.next(), isIn(list));
    }

    @Test
    public void shouldHaveNextButNoNextIndexIsUpper() throws Exception {
	list.add("1");
	
	when(rand.nextInt(0)).thenReturn(1);
	PowerMockito.when(RandomSource.getInstance()).thenReturn(rand);
	
	randomIterator = new RandomIterator<String>(list);

	assertNotNull(randomIterator.next());
    }
}
