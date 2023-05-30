package com.lq.helloworld

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.FragmentUtils
import com.lq.helloworld.camera.CameraFragment
import com.lq.helloworld.camerax.CameraxActivity
import com.lq.helloworld.databinding.ActivityMainBinding
import com.permissionx.guolindev.PermissionX

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.cameraxButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, CameraxActivity::class.java))
        }

        viewBinding.camera2Button.setOnClickListener {

        }


        PermissionX.init(this)
            .permissions(android.Manifest.permission.CAMERA)
            .request { allGranted, _, _ ->
                if (allGranted) {
                    FragmentUtils.add(
                        supportFragmentManager,
                        CameraFragment.newInstance(),
                        R.id.contentContainer,
                        "camera"
                    )
                }
            }
    }
}