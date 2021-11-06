package tech.gamedev.motorolasolutions.ui.fragments.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import tech.gamedev.motorolasolutions.R
import tech.gamedev.motorolasolutions.databinding.FragmentUserDetailBinding
import tech.gamedev.motorolasolutions.utils.extensions.composeEmail
import javax.inject.Inject

@AndroidEntryPoint
class UserDetailFragment : Fragment() {


    @Inject
    lateinit var glide: RequestManager
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private val args: UserDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserDetails()
        binding.btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun setUserDetails() {
        //User
        val user = args.user
        //Setting profile image
        glide.load(user.profileImage).into(binding.ivThumbnail)
        //Setting full name
        binding.tvFullName.text = user.fullName
        //Setting days till birthday
        if(args.user.getDaysTillBirthday().days > 0) {
            binding.tvDaysTillDob.text = user.getDaysTillBirthday().days.toString()
        } else {
            //User's birthday is today
            binding.tvBirthday.text = getString(R.string.congratulate, user.fullName)
            binding.tvDays.isVisible = false
            binding.tvDaysTillDob.isVisible = false
        }
        //Send message to user
        binding.btnSendMessage.setOnClickListener { user.composeEmail(requireContext()) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}