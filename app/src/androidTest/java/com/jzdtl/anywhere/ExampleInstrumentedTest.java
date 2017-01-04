package com.jzdtl.anywhere;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.jzdtl.anywhere.adapter.IndexAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.ContentValues.TAG;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        IndexAdapter adapter = new IndexAdapter(appContext);
        Log.i(TAG, "useAppContext: "+adapter.getDestinationsPath(".1.5.109."));
        assertEquals("109", adapter.getDestinationsPath(".1.5.109."));
    }
}