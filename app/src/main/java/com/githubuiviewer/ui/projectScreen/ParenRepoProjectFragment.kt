package com.githubuiviewer.ui.projectScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.githubuiviewer.R
import com.githubuiviewer.databinding.ProjectFragmentBinding
import com.githubuiviewer.tools.FragmentArgsDelegate
import com.githubuiviewer.tools.USER_KEY
import com.githubuiviewer.tools.navigator.BaseFragment
import com.githubuiviewer.ui.projectScreen.adapters.DetailInfoReposAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ParenRepoProjectFragment : BaseFragment(R.layout.project_fragment) {

    private var userAndRepoName by FragmentArgsDelegate<UserAndRepoName>(USER_KEY)
    private lateinit var binding: ProjectFragmentBinding
    private lateinit var viewPagerAdapter: DetailInfoReposAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProjectFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabLayout()
    }

    private fun setupTabLayout() {
        viewPagerAdapter = DetailInfoReposAdapter(this, userAndRepoName)
        binding.viewPagerProject.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayoutProject, binding.viewPagerProject) { tab, position ->
            tab.setText(viewPagerAdapter.getItemTabName(position))
        }.attach()
    }

    companion object {
        fun newInstance(userAndRepoName: UserAndRepoName) =
            ParenRepoProjectFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_KEY, userAndRepoName)
                }
            }
    }
}