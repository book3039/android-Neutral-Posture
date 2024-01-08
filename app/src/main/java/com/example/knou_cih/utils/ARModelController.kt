package com.example.knou_cih.utils

import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.arcore.LightEstimationMode
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.CursorNode
import io.github.sceneview.math.Rotation

//AR 모델을 관리하는 컨트롤러
class ARModelController {

    companion object {
        const val MODEL_MAX_SCALE = 3.0f
        const val MODEL_MIN_SCALE = 0.25f
    }

    private lateinit var modelNode: ArModelNode
    private val cursorNode: CursorNode = CursorNode()
    private var modelScale = 1.0f


    fun setArSceneView(mArSceneView: ArSceneView) {
        mArSceneView.planeRenderer.isVisible = false
        mArSceneView.lightEstimationMode = LightEstimationMode.ENVIRONMENTAL_HDR
    }

    fun loadArModel(mArSceneView: ArSceneView, name: String) {
        modelNode = ArModelNode(
            modelGlbFileLocation = "models/${name}.glb",
            autoAnimate = true,
            scaleToUnits = modelScale
        ).apply {
            maxEditableScale = MODEL_MAX_SCALE
            minEditableScale = MODEL_MIN_SCALE
            isEditable = false
        }
    }

    fun placeArModel(mArSceneView: ArSceneView) {
        if (mArSceneView.children.contains(cursorNode)) {
            cursorNode.createAnchor()?.let {
                if (mArSceneView.children.contains(modelNode)) {
                    modelNode.anchor = it
                } else {
                    mArSceneView.addChild(modelNode)
                    modelNode.anchor = it
                }
            }
        }
    }

    fun cursorOn(mArSceneView: ArSceneView) {
        if (!mArSceneView.children.contains(cursorNode))
            mArSceneView.addChild(cursorNode)
    }

    fun cursorOff(mArSceneView: ArSceneView) {
        if (mArSceneView.children.contains(cursorNode))
            mArSceneView.removeChild(cursorNode)
    }

    fun modelSizeUp() {
        if (modelScale <= modelNode.maxEditableScale) {
            modelScale += 0.015f
            modelNode.setScale(modelScale)
        }
    }

    fun modelSizeDown() {
        if (modelScale >= modelNode.minEditableScale) {
            modelScale -= 0.02f
            modelNode.setScale(modelScale)
        }
    }

    fun rotateModel(progress: Float) {
        modelNode.transform(rotation = Rotation(0f, progress - 180f, 0f))
    }
}