package com.example.wiproprojectassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.wiproprojectassignment.di.DaggerWiproAppComponent
import com.example.wiproprojectassignment.di.WiproAppComponent
import com.example.wiproprojectassignment.model.WiproCountryInfoListModel
import com.example.wiproprojectassignment.remote.WiproRemoteRepository
import com.example.wiproprojectassignment.ui.WiproCountryInfoListViewModel
import com.google.gson.Gson
import io.reactivex.Observable
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.nio.charset.Charset


class WiproCountryInfoListViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var myViewModel: WiproCountryInfoListViewModel
    @Mock
    lateinit var mRepository: WiproRemoteRepository
    @Mock
    lateinit var observer: Observer<WiproCountryInfoListModel>


     @Before
     fun setUp() {
         MockitoAnnotations.initMocks(this)
         val component: WiproAppComponent = DaggerWiproAppComponent.builder().build()
         myViewModel = WiproCountryInfoListViewModel()
         component.inject(myViewModel)
     }

    @Test
     fun checkViewModelNotNullTest() {
         Assert.assertNotNull(myViewModel)
     }


    @Test
    fun checkApiCallDataPassTest() {
        mockServiceResponse("wbservice_response.json")
        Assert.assertEquals(14, myViewModel.list.value?.rows?.size)
        Assert.assertEquals("Beavers", myViewModel.list.value?.rows?.get(0)?.title)
        Assert.assertEquals("Language", myViewModel.list.value?.rows?.get(13)?.title)
        Assert.assertEquals("Nous parlons tous les langues importants.", myViewModel.list.value?.rows?.get(13)?.description)

    }

    /**
     * Method to check api failed state
     */
    @Test
    fun checkApiCallDataFAILTest() {
        mockServiceResponse("error_mock.json")
        Assert.assertEquals(null, myViewModel.list.value?.rows?.size)
    }

    /**
     * Method to mock web service response
     */
    private fun mockServiceResponse(jsonFileName: String) {
        val content = this.javaClass.classLoader?.getResource(jsonFileName)?.readText(
            Charset.forName("UTF-8")
        )
        val jsonModel = Gson().fromJson(content, WiproCountryInfoListModel::class.java)
        val countryInfoListModel = MutableLiveData<WiproCountryInfoListModel>()
        countryInfoListModel.value = jsonModel
        Mockito.`when`(mRepository.getCountryInfoListData()).thenReturn(Observable.just(jsonModel))
        myViewModel.list.observeForever(observer)
        myViewModel.list.value = jsonModel
    }

}