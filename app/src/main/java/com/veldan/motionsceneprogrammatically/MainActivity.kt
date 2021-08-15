package com.veldan.motionsceneprogrammatically

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.motion.widget.TransitionBuilder
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.veldan.motionsceneprogrammatically.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Start
    private fun ConstraintLayout.initStartSet() = ConstraintSet().apply {
        clone(this@initStartSet)
        applyTo(this@initStartSet)
    }

    // End
    private fun ConstraintLayout.initEndSet() = ConstraintSet().apply {
        clone(this@initEndSet)
        constrainPercentWidth(binding.view.id, .5f)
        connect(binding.view.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        connect(binding.view.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        applyTo(this@initEndSet)
    }

    // Rotation
    private fun ConstraintLayout.initRotationSet() = ConstraintSet().apply {
        clone(this@initRotationSet)
        setRotation(binding.view.id, 360f)
        applyTo(this@initRotationSet)
    }

    private var clickCounter = 0
    private lateinit var scene: MotionScene

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.root.setOnClickListener {
            when (clickCounter) {
                0 -> {
                    scene = MotionScene(binding.root).apply {
                        val transition = TransitionBuilder.buildTransition(this, R.id.transition_id, R.id.start_set_id, binding.root.initStartSet(), R.id.end_set_id, binding.root.initEndSet())
                        setTransition(transition)
                        binding.root.setScene(this)
                    }

                    binding.root.apply {
                        setTransitionDuration(1000)
                        transitionToEnd()
                    }

                    clickCounter++
                }
                1 -> {
                    scene.apply {
                        val transition = TransitionBuilder.buildTransition(this, R.id.transition_rotation_id, R.id.end_set_id, binding.root.initEndSet(), R.id.rotation_set_id, binding.root.initRotationSet())
                        addTransition(transition)
                    }

                    ConstraintSet().apply {
                        clone(binding.root)
                        setTransformPivot(binding.view.id, binding.view.width / 2f, binding.view.height / 2f)
                        applyTo(binding.root)
                    }

                    binding.root.apply {
                        setTransition(R.id.transition_rotation_id)
                        setTransitionDuration(5000)
                        transitionToEnd()
                    }
                }
            }
        }

    }

}