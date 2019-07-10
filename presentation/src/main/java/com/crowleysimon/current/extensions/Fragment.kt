package com.crowleysimon.current.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/** Finds the fragment of type T or creates and adds the fragment if it is not found */
inline fun <reified T: Fragment> FragmentManager.resolveFragment(factory: () -> T): T {
    val tag = T::class.java.simpleName
    return findFragmentByTag(tag) as? T ?: factory().also { inTransaction { add(it, tag) } }
}

/** Block based api for performing and committing a fragment transaction.
 * Begins a transaction, executes the provided
 * block, and then commits the transaction
 * */
inline fun FragmentManager.inTransaction(block: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    block(transaction)
    transaction.commit()
}