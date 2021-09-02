package com.example.chatbook.Adapters

import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.chatbook.Fragments.CallsFragment
import com.example.chatbook.Fragments.ChatsFragment
import com.example.chatbook.Fragments.StatusFragment

class FragmentsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {return ChatsFragment()}
            1 -> {return StatusFragment()}
            2-> {return CallsFragment()}
            else -> {return ChatsFragment()}
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "CHATS"
            }
            1 -> {
                return "STATUS"
            }
            2 -> {
                return "CALLS"
            }
        }

        return super.getPageTitle(position)
    }

}