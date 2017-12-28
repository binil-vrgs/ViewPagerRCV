/*
 * Copyright © 2017. by binil.
 * All rights reserved. This file or any portion thereof
 * may not be reproduced or used in any manner whatsoever
 * without the express written permission of the author.
 */

package com.dev.binil.viewpager;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.dev.binil.viewpager.test", appContext.getPackageName());
    }
}
