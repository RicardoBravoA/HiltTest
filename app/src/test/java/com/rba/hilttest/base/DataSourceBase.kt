package com.rba.hilttest.base

import io.mockk.impl.annotations.RelaxedMockK
import okhttp3.ResponseBody

open class DataSourceBase : MockkBase() {

    val bodyNull = null
    val code404 = 404

    @RelaxedMockK
    lateinit var responseBody: ResponseBody

}
