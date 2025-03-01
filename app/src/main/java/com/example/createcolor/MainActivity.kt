package com.example.createcolor

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import com.example.createcolor.databinding.ActivityMainBinding
import com.example.createcolor.databinding.DialogColorPickerBinding

class MainActivity : AppCompatActivity()
{

    private lateinit var binding: ActivityMainBinding
    private var currentColor: Int = Color.RED

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOpenDialog.setOnClickListener {
            showColorPickerDialog()
        }
    }

    private fun showColorPickerDialog()
    {
        val dialogBinding = DialogColorPickerBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Выбери цвет")
            .setView(dialogBinding.root)
            .create()

        with(dialogBinding)
        {
            seekBarRed.setOnSeekBarChangeListener(createSeekBarListener(editTextRed, previewColorView, seekBarGreen, seekBarBlue))
            seekBarGreen.setOnSeekBarChangeListener(createSeekBarListener(editTextGreen, previewColorView, seekBarRed, seekBarBlue))
            seekBarBlue.setOnSeekBarChangeListener(createSeekBarListener(editTextBlue, previewColorView, seekBarRed, seekBarGreen))

            buttonConfirm.setOnClickListener {
                currentColor = Color.rgb(
                    seekBarRed.progress,
                    seekBarGreen.progress,
                    seekBarBlue.progress
                )
                binding.heartView.color = currentColor
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun createSeekBarListener(
        editText: EditText,
        previewColorView: View,
        seekBar1: SeekBar,
        seekBar2: SeekBar
    ) = object : SeekBar.OnSeekBarChangeListener
    {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean)
        {
            editText.setText(progress.toString())
            previewColorView.setBackgroundColor(Color.rgb(seekBar1.progress, seekBar2.progress, progress))
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}
        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }
}

