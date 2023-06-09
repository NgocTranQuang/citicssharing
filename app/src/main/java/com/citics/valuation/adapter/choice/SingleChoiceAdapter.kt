package com.citics.valuation.adapter.choice

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.citics.cbank.databinding.LayoutRowSingleChoiceBinding
import com.citics.valuation.adapter.base.BaseAdapter
import com.citics.valuation.data.model.others.ChooserItem
import com.citics.valuation.extension.applyCiticsStyle
import com.google.android.gms.common.util.DataUtils


class SingleChoiceAdapter(
    var context: Context,
    var selected: String?,
    var list: MutableList<ChooserItem>,
    var onChange: () -> Unit
) : BaseAdapter<LayoutRowSingleChoiceBinding, ChooserItem>(context, list) {
    private var lastCheckedPosition = -1

    init {
        lastCheckedPosition = list.indexOfFirst { it.name == selected }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> LayoutRowSingleChoiceBinding
        get() = LayoutRowSingleChoiceBinding::inflate

    override fun ChooserItem.handleItem(binding: LayoutRowSingleChoiceBinding?, position: Int) {
        binding?.btnRadio?.setOnCheckedChangeListener(null);
        binding?.btnRadio?.text = this.name
        binding?.btnRadio?.isChecked = lastCheckedPosition == position
        binding?.btnRadio?.setOnCheckedChangeListener { buttonView, isChecked ->
            val copyOfLastCheckedPosition = lastCheckedPosition
            lastCheckedPosition = position
            notifyItemChanged(copyOfLastCheckedPosition)
            notifyItemChanged(lastCheckedPosition)
            onChange.invoke()
        }
        binding?.btnRadio?.applyCiticsStyle()
    }

    fun getSelected(): ChooserItem? {
        return list.getOrNull(lastCheckedPosition)
    }
}