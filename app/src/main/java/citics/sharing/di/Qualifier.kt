package citics.sharing.di

import javax.inject.Qualifier

/**
 * Created by ChinhQT on 21/10/2022.
 */

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiUploader

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiAgent

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiAgentSearch