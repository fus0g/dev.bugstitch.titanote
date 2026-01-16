package dev.bugstitch.titanote.di

import org.koin.dsl.KoinAppDeclaration

fun initKoin(configuration: KoinAppDeclaration? = null){
    initKoin {
        configuration?.invoke(this)
        modules(
            SharedModule,
            PlatformModule
        )
    }
}