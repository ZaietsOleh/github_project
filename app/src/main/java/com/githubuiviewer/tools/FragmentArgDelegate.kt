package com.githubuiviewer.tools

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class FragmentArgsDelegate<T : Any>(
    private val key: String,
) : ReadWriteProperty<Fragment, T> {

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) =
        with(thisRef.arguments ?: Bundle().also(thisRef::setArguments)) {
            when (value) {
                is String -> putString(key, value)
                is Bundle -> putBundle(key, value)
                is Parcelable -> putParcelable(key, value)
                else -> throw IllegalStateException("Error type of property $key")
            }
        }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return thisRef.arguments?.get(key) as? T
            ?: throw IllegalStateException("Property ${property.name} error")
    }
}