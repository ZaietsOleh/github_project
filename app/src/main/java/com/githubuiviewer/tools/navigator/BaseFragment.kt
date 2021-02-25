package com.githubuiviewer.tools.navigator

import androidx.fragment.app.Fragment
import com.githubuiviewer.ui.mainActivity.NavigationActivity

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    protected val navigation: Navigator by lazy {
        (requireActivity() as NavigationActivity).navigator
    }
}