package ru.ikom.manual_di.di


interface FeatureProvider {
}

class DefaultFeatureProvider(
    private val coreModule: CoreModule
) : FeatureProvider {

}