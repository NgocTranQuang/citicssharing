package com.citics.valuation.data.repository.base

import com.citics.valuation.data.datasource.local.PreferenceManager
import com.citics.valuation.service.header.ApiHeadersProvider
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by ChinhQT on 01/10/2022.
 */
@Singleton
abstract class BaseRepository() {
    @Inject
    lateinit var preferenceManager: PreferenceManager

//    @Inject
//    lateinit var bankDao: BankDao
//
//    suspend fun insertAll(bankEntities: List<BankEntity>) {
//        bankDao.insertAll(bankEntities)
//    }
}