package com.sn.quizapp.app


import android.app.Application
import com.sn.quizapp.R
import com.sn.quizapp.di.appUtils
import com.sn.quizapp.di.fileUtils
import com.sn.quizapp.utilities.AppUtils
import com.sn.quizapp.utilities.FileReaderUtils
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito.mock


@RunWith(JUnit4::class)
class MyQuizApplicationTest : KoinTest {

    val mockedAndroidContext = mock(Application::class.java)

    val appUtilityTestObj : AppUtils by inject()

    val fileUtilityTestObj : FileReaderUtils by inject()


    @Before
    fun setUp() {

        startKoin {

            androidContext(mockedAndroidContext)
            modules(provideComponent())
        }
    }

    @Test
    fun test_injected_apputil_fileutil_functionality(){

        assertNotNull(appUtilityTestObj.getCurrentDateTimeFormatted())

        assertNotNull(fileUtilityTestObj.convertStringToJSON(getTestJson()))

        assertNotNull(fileUtilityTestObj.readFileContent(R.raw.input_data))
    }

    //Helper Methods-----------------
    fun getTestJson(): String{
        return "[\r\n  {\r\n    \"text_eng\": \"primary school\",\r\n    \"text_spa\": \"escuela primaria\"\r\n  },\r\n  {\r\n    \"text_eng\": \"teacher\",\r\n    \"text_spa\": \"profesor  profesora\"\r\n  },\r\n  {\r\n    \"text_eng\": \"pupil\",\r\n    \"text_spa\": \"alumno alumna\"\r\n  },\r\n  {\r\n    \"text_eng\": \"holidays\",\r\n    \"text_spa\": \"vacaciones \"\r\n  },\r\n  {\r\n    \"text_eng\": \"class\",\r\n    \"text_spa\": \"curso\"\r\n  },\r\n  {\r\n    \"text_eng\": \"bell\",\r\n    \"text_spa\": \"timbre\"\r\n  },\r\n  {\r\n    \"text_eng\": \"group\",\r\n    \"text_spa\": \"grupo\"\r\n  },\r\n  {\r\n    \"text_eng\": \"exercise book\",\r\n    \"text_spa\": \"cuaderno\"\r\n  },\r\n  {\r\n    \"text_eng\": \"quiet\",\r\n    \"text_spa\": \"quieto\"\r\n  }]"
    }

    private fun provideComponent(): List<Module> {
        return listOf(appUtils, fileUtils)
    }

}