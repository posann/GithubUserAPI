package com.codenesia.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codenesia.githubuser.FollowAdapter
import com.codenesia.githubuser.MainViewModel
import com.codenesia.githubuser.data.FollowResponseItem
import com.codenesia.githubuser.databinding.FragmentFollowBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_POSITION = "position"
private const val ARG_USERNAME = "username"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var position: Int = 0
    private var username: String = ""

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)!!
        }

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.isLoading.observe(requireActivity()) { showLoading(it) }
        if (position == 1) {
            showFollowing(username)
        } else {
            showFollower(username)
        }
        showAdapterFollower()
    }

    private fun showAdapterFollower() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowerData.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowerData.addItemDecoration(itemDecoration)

    }

    private fun showFollower(username: String) {
        viewModel.getDetailFollower(username)
        viewModel.listFollower.observe(viewLifecycleOwner) { follow ->
            if (follow != null) {
                if (follow.isNotEmpty()) {
                    setFollower(follow)
                }
            }
        }
    }

    private fun showFollowing(username: String) {
        viewModel.getDetailFollowing(username)
        viewModel.listFollowing.observe(viewLifecycleOwner) { follow ->
            if (follow != null) {
                if (follow.isNotEmpty()) {
                    setFollowing(follow)
                }
            }
        }

    }

    private fun setFollower(follow: ArrayList<FollowResponseItem>) {
        val adapter = FollowAdapter(follow)
        binding.rvFollowerData.adapter = adapter
    }

    private fun setFollowing(follow: ArrayList<FollowResponseItem>) {
        val adapter = FollowAdapter(follow)
        binding.rvFollowerData.setHasFixedSize(false)
        binding.rvFollowerData.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean?) {
        binding.progressBar.visibility = if (isLoading == true) View.VISIBLE else View.GONE
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FollowFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(position: Int, username: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                    putString(ARG_USERNAME, username)
                }
            }
    }
}