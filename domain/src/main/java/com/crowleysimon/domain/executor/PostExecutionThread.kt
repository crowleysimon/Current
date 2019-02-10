package com.crowleysimon.domain.executor

import io.reactivex.Scheduler

/**
 * Thread abstraction created to change the execution context of any thread to any other thread.
 */
interface PostExecutionThread {
    val scheduler: Scheduler
}