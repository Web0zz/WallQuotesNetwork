package com.web0zz.wallquotes.presentation.screen.editor

import androidx.fragment.app.viewModels
import com.web0zz.wallquotes.databinding.FragmentEditorBinding
import com.web0zz.wallquotes.presentation.base.BaseFragment

class EditorFragment : BaseFragment<FragmentEditorBinding, EditorViewModel>(
    FragmentEditorBinding::inflate
) {
    override val mViewModel: EditorViewModel by viewModels()

}