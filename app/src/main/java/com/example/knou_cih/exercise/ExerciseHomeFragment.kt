package com.example.knou_cih.exercise

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knou_cih.MainActivity
import com.example.knou_cih.R
import com.example.knou_cih.databinding.ExerciseFragmentHomeBinding

//운동 동작을 위한 HomeFragment
class ExerciseHomeFragment : Fragment(), View.OnClickListener,
    ExerciseNeckItemAdapter.OnItemClickListener,
    ExerciseSpineItemAdapter.OnItemClickListener,
    ExercisePelvisItemAdapter.OnItemClickListener {

    private var _binding: ExerciseFragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterNeckItem: ExerciseNeckItemAdapter
    private lateinit var adapterSpineItem: ExerciseSpineItemAdapter
    private lateinit var adapterPelvisItem: ExercisePelvisItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExerciseFragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.prevDirectory.setOnClickListener(this)
        binding.exerciseNeck.setOnClickListener(this)
        binding.exerciseSpine.setOnClickListener(this)
        binding.exercisePelvis.setOnClickListener(this)
        binding.exerciseNeckRoot.setOnClickListener(this)
        binding.exerciseSpineRoot.setOnClickListener(this)
        binding.exercisePelvisRoot.setOnClickListener(this)

        //세 가지 리사이클러뷰로 운동 동작 아이콘들을 보여줌
        //목 운동, 척추 운동, 골반 운동으로 나뉨
        val exerciseNeckItemList = arrayListOf(
            ExerciseMenu(R.drawable.icon_neckmasage, "[거북목]목마사지"),
            ExerciseMenu(R.drawable.icon_neckstretching1, "[거북목]턱당기기"),
            ExerciseMenu(R.drawable.icon_neckstretching2, "[일자목]스트레칭")
        )

        val exerciseSpineItemList = arrayListOf(
            ExerciseMenu(R.drawable.icon_abdominis_stretching, "[굽은등]상복부스트레칭"),
            ExerciseMenu(R.drawable.icon_backextension_t_shape, "[굽은등]백익스텐션T"),
            ExerciseMenu(R.drawable.icon_backextension_y_shape, "[굽은등]백익스텐션Y"),
            ExerciseMenu(R.drawable.icon_curlup, "[편평등]컬업"),
            ExerciseMenu(R.drawable.icon_standingrolldown, "[편평등]스탠딩롤다운")
        )

        val exercisePelvisItemList = arrayListOf(
            ExerciseMenu(R.drawable.icon_pelvisstretching1, "[전방경사]스트레칭1"),
            ExerciseMenu(R.drawable.icon_pelvisstretching2, "[전방경사]스트레칭2"),
            ExerciseMenu(R.drawable.icon_pelvisstretching3, "[전방경사]스트레칭3"),
            ExerciseMenu(R.drawable.icon_pelvisstretching4, "[후방경사]스트레칭")
        )

        binding.recyclerviewNeckItem.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapterNeckItem = ExerciseNeckItemAdapter(exerciseNeckItemList)
        adapterNeckItem.setOnItemClickListener(this@ExerciseHomeFragment)
        binding.recyclerviewNeckItem.adapter = adapterNeckItem
        binding.recyclerviewNeckItem.setHasFixedSize(true)
        binding.recyclerviewNeckItem.setItemViewCacheSize(3)

        binding.recyclerviewSpineItem.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapterSpineItem = ExerciseSpineItemAdapter(exerciseSpineItemList)
        adapterSpineItem.setOnItemClickListener(this@ExerciseHomeFragment)
        binding.recyclerviewSpineItem.adapter = adapterSpineItem
        binding.recyclerviewSpineItem.setHasFixedSize(true)
        binding.recyclerviewSpineItem.setItemViewCacheSize(5)

        binding.recyclerviewPelvisItem.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapterPelvisItem = ExercisePelvisItemAdapter(exercisePelvisItemList)
        adapterPelvisItem.setOnItemClickListener(this@ExerciseHomeFragment)
        binding.recyclerviewPelvisItem.adapter = adapterPelvisItem
        binding.recyclerviewPelvisItem.setHasFixedSize(true)
        binding.recyclerviewPelvisItem.setItemViewCacheSize(4)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        val rootShow = AnimationUtils.loadAnimation(requireContext(), R.anim.exercise_root_show)
        val rootClose = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_close)
        val buttonShow = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_show_fast)
        val recyclerViewEnter =
            AnimationUtils.loadAnimation(requireContext(), R.anim.recyclerview_enter)
        val recyclerViewExit =
            AnimationUtils.loadAnimation(requireContext(), R.anim.recyclerview_exit)
        val mHandler = Handler(Looper.getMainLooper())

        when (v?.id) {
            binding.exerciseNeck.id -> {

                binding.recyclerviewNeckLayout.startAnimation(recyclerViewEnter)

                mHandler.postDelayed({
                    binding.exerciseNeckRoot.startAnimation(rootShow)
                    binding.exerciseNeck.startAnimation(rootClose)
                    binding.textExerciseNeck.startAnimation(rootClose)
                }, 800)
            }

            binding.exerciseSpine.id -> {
                binding.recyclerviewSpineLayout.startAnimation(recyclerViewEnter)

                mHandler.postDelayed({
                    binding.exerciseSpineRoot.startAnimation(rootShow)
                    binding.exerciseSpine.startAnimation(rootClose)
                    binding.textExerciseSpine.startAnimation(rootClose)
                }, 800)
            }

            binding.exercisePelvis.id -> {
                binding.recyclerviewPelvisLayout.startAnimation(recyclerViewEnter)

                mHandler.postDelayed({
                    binding.exercisePelvisRoot.startAnimation(rootShow)
                    binding.exercisePelvis.startAnimation(rootClose)
                    binding.textExercisePelvis.startAnimation(rootClose)
                }, 800)
            }

            binding.prevDirectory.id -> {
                val intent = Intent(activity, MainActivity::class.java)
                intent.putExtra("HomeFragment", "MainHome")
                startActivity(intent)
                activity?.finish()
            }

            binding.exerciseNeckRoot.id -> {
                binding.exerciseNeckRoot.startAnimation(rootClose)
                binding.recyclerviewNeckLayout.startAnimation(recyclerViewExit)
                binding.exerciseNeck.startAnimation(buttonShow)
                binding.textExerciseNeck.startAnimation(buttonShow)
            }

            binding.exerciseSpineRoot.id -> {
                binding.exerciseSpineRoot.startAnimation(rootClose)
                binding.recyclerviewSpineLayout.startAnimation(recyclerViewExit)
                binding.exerciseSpine.startAnimation(buttonShow)
                binding.textExerciseSpine.startAnimation(buttonShow)
            }

            binding.exercisePelvisRoot.id -> {
                binding.exercisePelvisRoot.startAnimation(rootClose)
                binding.recyclerviewPelvisLayout.startAnimation(recyclerViewExit)
                binding.exercisePelvis.startAnimation(buttonShow)
                binding.textExercisePelvis.startAnimation(buttonShow)
            }
        }
    }


    //각 리사이클러뷰의 아이템 클릭 시, 운동 동작에 맞는 Fragment를 띄워 줌
    override fun onNeckItemClick(pos: Int) {
        when (pos) {
            0 -> requireActivity().supportFragmentManager.commit {
                add(
                    R.id.fragmentContainer_exercise,
                    ExerciseNeckMassageFragment::class.java,
                    Bundle()
                )
                addToBackStack(null)
            }

            1 -> requireActivity().supportFragmentManager.commit {
                add(
                    R.id.fragmentContainer_exercise,
                    ExerciseNeckStretchingFirstFragment::class.java,
                    Bundle()
                )
                addToBackStack(null)
            }

            2 -> requireActivity().supportFragmentManager.commit {
                add(
                    R.id.fragmentContainer_exercise,
                    ExerciseNeckStretchingSecondFragment::class.java,
                    Bundle()
                )
                addToBackStack(null)
            }
        }
    }

    override fun onSpineItemClick(pos: Int) {
        when (pos) {
            0 -> requireActivity().supportFragmentManager.commit {
                add(
                    R.id.fragmentContainer_exercise,
                    ExerciseSpineAbdominisStretchingFragment::class.java,
                    Bundle()
                )
                addToBackStack(null)
            }

            1 -> requireActivity().supportFragmentManager.commit {
                add(
                    R.id.fragmentContainer_exercise,
                    ExerciseSpineBackExtensionTshapeFragment::class.java,
                    Bundle()
                )
                addToBackStack(null)
            }

            2 -> requireActivity().supportFragmentManager.commit {
                add(
                    R.id.fragmentContainer_exercise,
                    ExerciseSpineBackExtensionYshapeFragment::class.java,
                    Bundle()
                )
                addToBackStack(null)
            }

            3 -> requireActivity().supportFragmentManager.commit {
                add(
                    R.id.fragmentContainer_exercise,
                    ExerciseSpineCurlUpFragment::class.java,
                    Bundle()
                )
                addToBackStack(null)
            }

            4 -> requireActivity().supportFragmentManager.commit {
                add(
                    R.id.fragmentContainer_exercise,
                    ExerciseSpineStandingRollDownFragment::class.java,
                    Bundle()
                )
                addToBackStack(null)
            }
        }
    }

    override fun onPelvisItemClick(pos: Int) {
        when (pos) {
            0 -> requireActivity().supportFragmentManager.commit {
                add(
                    R.id.fragmentContainer_exercise,
                    ExercisePelvisStretchingFirstFragment::class.java,
                    Bundle()
                )
                addToBackStack(null)
            }

            1 -> requireActivity().supportFragmentManager.commit {
                add(
                    R.id.fragmentContainer_exercise,
                    ExercisePelvisStretchingSecondFragment::class.java,
                    Bundle()
                )
                addToBackStack(null)
            }

            2 -> requireActivity().supportFragmentManager.commit {
                add(
                    R.id.fragmentContainer_exercise,
                    ExercisePelvisStretchingThirdFragment::class.java,
                    Bundle()
                )
                addToBackStack(null)
            }

            3 -> requireActivity().supportFragmentManager.commit {
                add(
                    R.id.fragmentContainer_exercise,
                    ExercisePelvisStretchingFourthFragment::class.java,
                    Bundle()
                )
                addToBackStack(null)
            }
        }
    }
}