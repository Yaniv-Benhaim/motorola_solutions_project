package tech.gamedev.motorolasolutions.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import tech.gamedev.motorolasolutions.R
import tech.gamedev.motorolasolutions.data.models.User
import tech.gamedev.motorolasolutions.databinding.FragmentHomeBinding
import tech.gamedev.motorolasolutions.ui.adapters.UserAdapter
import tech.gamedev.motorolasolutions.utils.extensions.composeEmail
import tech.gamedev.motorolasolutions.viewmodels.UserViewModel
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) , UserAdapter.UserClickedListener {

    @Inject
    lateinit var glide: RequestManager
    private val userViewModel by activityViewModels<UserViewModel>()
    private lateinit var userAdapter: UserAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Setting view binding to prevent Null pointers
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        swipeToRefresh()
    }

    private fun subscribeToObservers() {

        userViewModel.users.observe(viewLifecycleOwner) { users ->
            setupUserRecyclerView(users)
        }

        userViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeToRefresh.isRefreshing = isLoading
        }

    }

    private fun setupUserRecyclerView(users: List<User>) {
        userAdapter = UserAdapter(users, glide, this)
        binding.userRv.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun swipeToRefresh() = binding.swipeToRefresh.setOnRefreshListener {
        userViewModel.fetchNewUsers()
    }

    override fun onUserClickedListener(user: User) {
        val action = HomeFragmentDirections.actionNavigationHomeToUserDetailFragment(user)
        findNavController().navigate(action)
    }

    override fun onLongUserClickedListener(user: User) {
        user.composeEmail(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}