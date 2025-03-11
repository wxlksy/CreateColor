import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.createcolor.databinding.ActivityMainBinding
import com.example.createcolor.databinding.DialogColorPickerBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentColor: Int = Color.BLACK
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        binding.buttonOpenDialog.setOnClickListener {
            showColorPickerDialog()
        }
    }

    private fun showColorPickerDialog() {
        val dialogBinding = DialogColorPickerBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Pick a Color")
            .setView(dialogBinding.root)
            .create()

        with(dialogBinding) {
            val savedRed = sharedPreferences.getInt("red", 0)
            val savedGreen = sharedPreferences.getInt("green", 0)
            val savedBlue = sharedPreferences.getInt("blue", 0)

            seekBarRed.progress = savedRed
            seekBarGreen.progress = savedGreen
            seekBarBlue.progress = savedBlue

            // Update preview color
            updatePreviewColor(previewColorView, seekBarRed, seekBarGreen, seekBarBlue)

            seekBarRed.setOnSeekBarChangeListener(createSeekBarListener(previewColorView, seekBarRed, seekBarGreen, seekBarBlue))
            seekBarGreen.setOnSeekBarChangeListener(createSeekBarListener(previewColorView, seekBarRed, seekBarGreen, seekBarBlue))
            seekBarBlue.setOnSeekBarChangeListener(createSeekBarListener(previewColorView, seekBarRed, seekBarGreen, seekBarBlue))

            buttonConfirm.setOnClickListener {
                currentColor = Color.rgb(
                    seekBarRed.progress,
                    seekBarGreen.progress,
                    seekBarBlue.progress
                )
                binding.heartView.color = currentColor

                // Save the selected values
                sharedPreferences.edit()
                    .putInt("red", seekBarRed.progress)
                    .putInt("green", seekBarGreen.progress)
                    .putInt("blue", seekBarBlue.progress)
                    .apply()

                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun createSeekBarListener(
        previewColorView: View,
        seekBarRed: SeekBar,
        seekBarGreen: SeekBar,
        seekBarBlue: SeekBar
    ) = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            updatePreviewColor(previewColorView, seekBarRed, seekBarGreen, seekBarBlue)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}
        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }

    private fun updatePreviewColor(previewColorView: View, seekBarRed: SeekBar, seekBarGreen: SeekBar, seekBarBlue: SeekBar) {
        previewColorView.setBackgroundColor(Color.rgb(seekBarRed.progress, seekBarGreen.progress, seekBarBlue.progress))
    }
}
