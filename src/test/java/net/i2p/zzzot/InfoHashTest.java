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

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class InfoHashTest {

    private InfoHash infoHash;
    private static final byte[] GOOD_DATA = "aaaaaaaaaaaaaaaaaaaa".getBytes();
    private static final byte[] BAD_DATA = "a".getBytes();

	public static void main(String [] args) {
	System.out.println("wooo");
	}

    @Before
    public void startUp() throws Exception {
	infoHash = new InfoHash(GOOD_DATA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWithBadDataSize() throws Exception {
	infoHash = new InfoHash(BAD_DATA);
    }

    @Test
    public void shouldGetLength() throws Exception {
	assertThat(infoHash.length(), equalTo(InfoHash.LENGTH));
    }

}