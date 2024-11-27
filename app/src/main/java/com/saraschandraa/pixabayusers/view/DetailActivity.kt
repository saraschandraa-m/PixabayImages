package com.saraschandraa.pixabayusers.view

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.saraschandraa.pixabayusers.BR
import com.saraschandraa.pixabayusers.R
import com.saraschandraa.pixabayusers.model.Sports

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ViewDataBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this@DetailActivity, R.layout.activity_detail)

        val sports = intent.extras!!.getSerializable("SPORT", Sports::class.java)

        binding.setVariable(BR.sports, sports)
        binding.executePendingBindings()


        setSupportActionBar(binding.root.findViewById(R.id.tl_detail))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}