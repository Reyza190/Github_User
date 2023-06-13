package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.data.api.ItemsItem
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.viewmodel.DetailUserViewModel
import com.example.githubuser.viewmodel.ViewModelFactory

class FollowFragment : Fragment() {

    private var binding : FragmentFollowingBinding? = null
    private var position = 0
    private var username = ""
    private val viewModelDetailActivity by viewModels<DetailUserViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }

    companion object{
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = ""
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        viewModelDetailActivity.isLoading.observe(viewLifecycleOwner){ loading ->
            showLoading(loading)
        }

        if (position == 1){
            viewModelDetailActivity.getFollowersUser(username)
            viewModelDetailActivity.listUser.observe(viewLifecycleOwner){ user ->
                setListUser(user)
            }
        }else{
            viewModelDetailActivity.getFollowingUser(username)
            viewModelDetailActivity.listUser.observe(viewLifecycleOwner){ user ->
                setListUser(user)
            }
        }

    }

    private fun setListUser(user: List<ItemsItem>) {
        val adapter = UserAdapter(user)
        val layoutManager = LinearLayoutManager(view?.context)
        binding?.rvUser?.layoutManager = layoutManager
        binding?.rvUser?.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}