package com.example.kanbanprojectapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImagePickerBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImagePickerBottomSheetFragment : BottomSheetDialogFragment() {

    interface ImagePickerListener {
        fun onOptionSelected(option: ImageOption)
    }

    enum class ImageOption {
        GALLERY, CAMERA
    }

    private var listener: ImagePickerListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? ImagePickerListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image_picker_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.optionGallery).setOnClickListener {
            listener?.onOptionSelected(ImageOption.GALLERY)
            dismiss()
        }

        view.findViewById<TextView>(R.id.optionCamera).setOnClickListener {
            listener?.onOptionSelected(ImageOption.CAMERA)
            dismiss()
        }
    }
}