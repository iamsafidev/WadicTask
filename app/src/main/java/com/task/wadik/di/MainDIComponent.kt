package com.task.wadik.di

/**
 * Main dependency component.
 * This will create and provide required dependencies with sub dependencies.
 */
val appComponent = listOf(repositoryModule,NetworkDependency)