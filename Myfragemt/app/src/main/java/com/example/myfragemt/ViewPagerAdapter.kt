import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myfragemt.firstFragment
import com.example.myfragemt.secondFragment
import com.example.myfragemt.thirdFragment



class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3 // Total number of fragments
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> firstFragment()
            1 -> secondFragment()
            2 -> thirdFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}