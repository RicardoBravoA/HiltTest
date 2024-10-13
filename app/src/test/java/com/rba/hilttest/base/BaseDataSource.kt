package com.rba.hilttest.base

import io.mockk.impl.annotations.RelaxedMockK
import okhttp3.ResponseBody

abstract class BaseDataSource : BaseMockk() {

    protected val bodyNull = null
    protected val code404 = 404

    @RelaxedMockK
    protected lateinit var responseBody: ResponseBody

}
