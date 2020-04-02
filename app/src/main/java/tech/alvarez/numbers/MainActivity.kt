package tech.alvarez.numbers

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import tech.alvarez.numbers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButton.setOnClickListener {
            Toast.makeText(this, "hi", Toast.LENGTH_LONG).show()
            Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.searchFragment)
//            it.findNavController().navigate(R.id.searchFragment)
        }
    }
}
