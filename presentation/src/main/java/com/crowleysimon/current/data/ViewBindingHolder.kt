package com.crowleysimon.current.data

import androidx.viewbinding.ViewBinding
import com.xwray.groupie.GroupieViewHolder

class ViewBindingHolder<T : ViewBinding>(val binding: T) : GroupieViewHolder(binding.root)