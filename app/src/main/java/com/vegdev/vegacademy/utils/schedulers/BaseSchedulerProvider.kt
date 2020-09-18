package com.vegdev.vegacademy.utils.schedulers

import io.reactivex.rxjava3.core.Scheduler

interface BaseSchedulerProvider {

    fun getFirestoreScheduler(): Scheduler

}