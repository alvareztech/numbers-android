package tech.alvarez.numbers

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import tech.alvarez.numbers.databinding.ActivityMainBinding
import tech.alvarez.numbers.util.show

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButton.setOnClickListener {
            this.show("Open!")
            Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.searchFragment)
//            it.findNavController().navigate(R.id.searchFragment)
            binding.floatingActionButton.visibility = View.GONE
        }
    }
}
