package com.codenesia.githubuser

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codenesia.githubuser.fragment.FollowFragment

class SectionPager(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        val fragment = FollowFragment.newInstance(position, username)

        return fragment
    }

}