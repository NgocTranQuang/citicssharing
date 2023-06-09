package com.citics.valuation.adapter.choice

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.citics.cbank.databinding.LayoutRowMultichoiceBinding
import com.citics.valuation.adapter.base.BaseAdapter
import com.citics.valuation.data.model.others.ChooserItem
import timber.log.Timber

class MultiChoiceNewAdapter(
    val context: Context,
    val hasImageTitle: Boolean,
    val lst: MutableList<ChooserItem>,
    var onChange: (String) -> Unit
) : BaseAdapter<LayoutRowMultichoiceBinding, ChooserItem>(context, lst) {


    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> LayoutRowMultichoiceBinding
        get() = LayoutRowMultichoiceBinding::inflate

    override fun ChooserItem.handleItem(binding: LayoutRowMultichoiceBinding?, position: Int) {
        binding?.vChoice?.setData(hasImageTitle, this)
        binding?.vChoice?.setOnChangeListener {
            isSelected = it
            onChange.invoke(name)
            Timber.d("MultiChoiceAdapter", "${this.id}")
        }
    }

    fun getListSelected(): MutableList<ChooserItem> {
        return lst.filter { it.isSelected }.toMutableList()
    }
}