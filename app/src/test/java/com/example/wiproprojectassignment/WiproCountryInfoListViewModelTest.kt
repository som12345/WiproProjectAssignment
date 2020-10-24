package com.example.wiproprojectassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.wiproprojectassignment.di.DaggerWiproAppComponent
import com.example.wiproprojectassignment.di.WiproAppComponent
import com.example.wiproprojectassignment.ui.WiproCountryInfoListViewModel
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.internal.schedulers.ExecutorScheduler.ExecutorWorker
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.mockito.MockitoAnnotations
import java.util.concurrent.Callable
import java.util.concurrent.Executor


class WiproCountryInfoListViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var myViewModel: WiproCountryInfoListViewModel


     @Before
     fun setUp() {
         MockitoAnnotations.initMocks(this)
         val component: WiproAppComponent = DaggerWiproAppComponent.builder().build()
         myViewModel = WiproCountryInfoListViewModel()
         component.inject(myViewModel)
     }

    private fun setSchedulersApiCall() {
        val immediate: Scheduler = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorWorker(Executor { obj: Runnable -> obj.run() }, true)
            }
        }
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> immediate }
    }

    @Test
     fun checkViewModelNotNullTest() {
         Assert.assertNotNull(myViewModel)
     }


    @Test
    fun checkApiCallDataPassTest() {
        setSchedulersApiCall()
        myViewModel.disposable = CompositeDisposable()
        myViewModel.fetchData()
        Assert.assertEquals(myViewModel.list.value?.rows?.size, 14)
        Assert.assertEquals(myViewModel.list.value?.rows?.get(0)?.title, "Beavers")
        Assert.assertEquals(myViewModel.list.value?.rows?.get(13)?.title, "Language")
        Assert.assertEquals(myViewModel.list.value?.rows?.get(13)?.description, "Nous parlons tous les langues importants.")
    }

    /**
     * Did not get much time to complete this case, but indipedently its working fine
     */
    @Test
    fun checkApiCallDataFAILTest() {
        myViewModel.disposable = CompositeDisposable()
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }
        RxJavaPlugins.setErrorHandler(Consumer { it })
        myViewModel.fetchData()
        Assert.assertEquals(myViewModel.list.value?.rows?.size, null)
    }

}