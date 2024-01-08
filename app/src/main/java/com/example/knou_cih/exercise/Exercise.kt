package com.example.knou_cih.exercise

//운동 동작 설명시 사용되는 리사이클러뷰를 위한 클래스
data class Exercise(
    val ExerciseImageGif: Int,
    val ExerciseImage: Int,
    val ExerciseScriptFirst: String,
    val ExerciseScriptSecond: String,
    val SlidePrev: String,
    val SlideNext: String
)