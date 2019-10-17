package com.sn.quizapp.utilities

import android.content.Context
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class FileReaderUtilsTest {

    val mockedAndroidContext = Mockito.mock(Context::class.java)

    lateinit var mFileReaderUtils: FileReaderUtils

    @Before
    fun setUp() {

        mFileReaderUtils = FileReaderUtils(mockedAndroidContext)
    }

    @Test
    fun test_read_file_content_returns_expected_length() {
        val fileContent = mFileReaderUtils.readFileContent("input_data.json")
        assertEquals(61, fileContent.length)
    }
    //Helper Methods-----------------

}