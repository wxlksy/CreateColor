package com.example.createcolor

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
            try
            {
                val initialRed = editTextRed.text.toString().toIntOrNull() ?: 0
                val initialGreen = editTextGreen.text.toString().toIntOrNull() ?: 0
                val initialBlue = editTextBlue.text.toString().toIntOrNull() ?: 0

                seekBarRed.progress = initialRed
                seekBarGreen.progress = initialGreen
                seekBarBlue.progress = initialBlue

                updatePreviewColor(previewColorView, seekBarRed, seekBarGreen, seekBarBlue)

                seekBarRed.setOnSeekBarChangeListener(createSeekBarListener(editTextRed, previewColorView, seekBarRed, seekBarGreen, seekBarBlue))
                seekBarGreen.setOnSeekBarChangeListener(createSeekBarListener(editTextGreen, previewColorView, seekBarRed, seekBarGreen, seekBarBlue))
                seekBarBlue.setOnSeekBarChangeListener(createSeekBarListener(editTextBlue, previewColorView, seekBarRed, seekBarGreen, seekBarBlue))

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

            catch (e: Exception)
            {
                Log.e("DialogWindow", "Error", e)
            }
        }
        dialog.show()
    }

    private fun createSeekBarListener(
        editText: EditText,
        previewColorView: View,
        seekBarRed: SeekBar,
        seekBarGreen: SeekBar,
        seekBarBlue: SeekBar
    ) = object : SeekBar.OnSeekBarChangeListener
    {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean)
        {
            editText.setText(progress.toString())
            updatePreviewColor(previewColorView, seekBarRed, seekBarGreen, seekBarBlue)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}
        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }

    private fun updatePreviewColor(previewColorView: View, seekBarRed: SeekBar, seekBarGreen: SeekBar, seekBarBlue: SeekBar)
    {
        previewColorView.setBackgroundColor(Color.rgb(seekBarRed.progress, seekBarGreen.progress, seekBarBlue.progress))
    }
}